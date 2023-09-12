package electrolyte.greate.content.kinetics.crusher;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllDamageTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.sound.SoundScapes;
import com.simibubi.create.foundation.sound.SoundScapes.AmbienceGroup;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TieredCrushingWheelControllerBlockEntity extends SmartBlockEntity {

    public Entity processingEntity;
    private UUID entityUUID;
    protected boolean searchForEntity;
    public ProcessingInventory inventory;
    protected LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(() -> inventory);
    private RecipeWrapper wrapper;
    public float crushingSpeed;

    public TieredCrushingWheelControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new ProcessingInventory(this::itemInserted) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack) && processingEntity == null;
            }
        };
        wrapper = new RecipeWrapper(inventory);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this).onlyInsertWhen(this::supportsDirectBeltInput));
    }

    private boolean supportsDirectBeltInput(Direction side) {
        if(getBlockState() == null) return false;
        Direction dir = getBlockState().getValue(TieredCrushingWheelControllerBlock.FACING);
        return dir == Direction.DOWN || dir == side;
    }

    @Override
    public void tick() {
        super.tick();
        if(searchForEntity) {
            searchForEntity = false;
            List<Entity> entities = level.getEntities((Entity) null, new AABB(getBlockPos()), e -> entityUUID.equals(e.getUUID()));
            if(entities.isEmpty()) clear();
            else processingEntity = entities.get(0);
        }

        if(!isOccupied()) return;
        if(crushingSpeed == 0) return;
        if(level.isClientSide) DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::tickAudio);

        float speed = crushingSpeed * 4;

        Vec3 centerPos = VecHelper.getCenterOf(worldPosition);
        Direction dir = getBlockState().getValue(TieredCrushingWheelControllerBlock.FACING);
        int offset = dir.getAxisDirection().getStep();
        Vec3 outSpeed = new Vec3((dir.getAxis() == Axis.X ? 0.25D : 0D) * offset,
                offset == 1 ? (dir.getAxis() == Axis.Y ? 0.5D : 0D) : 0D,
                (dir.getAxis() == Axis.Z ? 0.25D : 0D) * offset);
        Vec3 outPos = centerPos.add((dir.getAxis() == Axis.X ? 0.55F * offset : 0F),
                (dir.getAxis() == Axis.Y ? 0.55F * offset : 0F), (dir.getAxis() == Axis.Z ? 0.55F * offset : 0F));
        if(!hasEntity()) {
            float processingSpeed = Mth.clamp((speed) / (!inventory.appliedRecipe ? Mth.log2(inventory.getStackInSlot(0).getCount()) : 1), 0.25F, 20);
            inventory.remainingTime -= processingSpeed;
            spawnParticles(inventory.getStackInSlot(0));
            if(level.isClientSide) return;
            if(inventory.remainingTime < 20 && !inventory.appliedRecipe) {
                applyRecipe();
                inventory.appliedRecipe = true;
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2 | 16);
                return;
            }
            if(inventory.remainingTime > 0) return;
            inventory.remainingTime = 0;
            if(dir != Direction.UP) {
                BlockPos pos = worldPosition.offset(dir.getAxis() == Axis.X ? 1 * offset : 0, (-1), dir.getAxis() == Axis.Z ? 1 * offset : 0);
                DirectBeltInputBehaviour behaviour = BlockEntityBehaviour.get(level, pos, DirectBeltInputBehaviour.TYPE);
                if(behaviour != null) {
                    boolean changed = false;
                    if(!behaviour.canInsertFromSide(dir)) return;
                    for(int slot = 0; slot < inventory.getSlots(); slot++) {
                        ItemStack stack = inventory.getStackInSlot(slot);
                        if(stack.isEmpty()) continue;
                        ItemStack remainder = behaviour.handleInsertion(stack, dir, false);
                        if(remainder.equals(stack, false)) continue;
                        inventory.setStackInSlot(slot, remainder);
                        changed = true;
                    }
                    if(changed) {
                        setChanged();
                        sendData();
                    }
                    return;
                }
            }

            for(int slot = 0; slot < inventory.getSlots(); slot++) {
                ItemStack stack = inventory.getStackInSlot(slot);
                if(stack.isEmpty()) continue;
                ItemEntity itemEntity = new ItemEntity(level, outPos.x, outPos.y, outPos.z, stack);
                itemEntity.setDeltaMovement(outSpeed);
                itemEntity.getPersistentData().put("BypassCrushingWheel", NbtUtils.writeBlockPos(worldPosition));
                level.addFreshEntity(itemEntity);
            }
            inventory.clear();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2 | 16);
            return;
        }

        if(!processingEntity.isAlive() || !processingEntity.getBoundingBox().intersects(new AABB(worldPosition).inflate(0.5F))) {
            clear();
            return;
        }

        double xMotion = ((worldPosition.getX() + 0.5F) - processingEntity.getX()) / 2F;
        double zMotion = ((worldPosition.getZ() + 0.5F) - processingEntity.getZ()) / 2F;
        if(processingEntity.isShiftKeyDown()) xMotion = zMotion = 0;
        double movement = Math.max(-speed / 4F, -0.5F) * -offset;
        processingEntity.setDeltaMovement(new Vec3(
                dir.getAxis() == Axis.X ? movement : xMotion,
                dir.getAxis() == Axis.Y ? movement : 0F,
                dir.getAxis() == Axis.Z ? movement : zMotion));
        if(level.isClientSide) return;
        if(!(processingEntity instanceof ItemEntity)) {
            Vec3 entityPos = outPos.add(
                    dir.getAxis() == Axis.X ? 0.5F * offset : 0F,
                    dir.getAxis() == Axis.Y ? 0.5F * offset : 0F,
                    dir.getAxis() == Axis.Z ? 0.5F * offset : 0F);
            int crusherDamage = AllConfigs.server().kinetics.crushingDamage.get();
            if (processingEntity instanceof LivingEntity) {
                if ((((LivingEntity) processingEntity).getHealth() - crusherDamage <= 0) &&
                        (((LivingEntity) processingEntity).hurtTime <= 0)) {
                    processingEntity.setPos(entityPos.x, entityPos.y, entityPos.z);
                }
            }
            processingEntity.hurt(AllDamageTypes.CRUSH.source(getLevel()), crusherDamage);
            if (!processingEntity.isAlive()) {
                processingEntity.setPos(entityPos.x, entityPos.y, entityPos.z);
            }
            return;
        }
        ItemEntity itemEntity = (ItemEntity) processingEntity;
        itemEntity.setPickUpDelay(20);
        if(dir.getAxis() == Axis.Y) {
            if(processingEntity.getY() * -offset < (centerPos.y - 0.25F) * -offset) {
                intakeItem(itemEntity);
            }
        } else if (dir.getAxis() == Axis.Z) {
            if(processingEntity.getZ() * -offset < (centerPos.z - 0.25F) * -offset) {
                intakeItem(itemEntity);
            }
        } else {
            if(processingEntity.getX() * -offset < (centerPos.x - 0.25F) * -offset) {
                intakeItem(itemEntity);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        float pitch = Mth.clamp((crushingSpeed / 256F) + 0.45F, 0.85F, 1F);
        if(entityUUID == null && inventory.getStackInSlot(0).isEmpty()) return;
        SoundScapes.play(AmbienceGroup.CRUSHING, worldPosition, pitch);
    }

    private void intakeItem(ItemEntity itemEntity) {
        inventory.clear();
        inventory.setStackInSlot(0, itemEntity.getItem().copy());
        itemInserted(inventory.getStackInSlot(0));
        itemEntity.discard();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2 | 16);
    }

    protected void spawnParticles(ItemStack stack) {
        if(stack == null || stack.isEmpty()) return;
        ParticleOptions particleData = null;
        if(stack.getItem() instanceof BlockItem blockItem) {
            particleData = new BlockParticleOption(ParticleTypes.BLOCK, blockItem.getBlock().defaultBlockState());
        } else {
            particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);
        }
        RandomSource rand = level.random;
        for(int i = 0; i < 4; i++) {
            level.addParticle(particleData, worldPosition.getX() + rand.nextFloat(), worldPosition.getY() + rand.nextFloat(), worldPosition.getZ() + rand.nextFloat(), 0, 0, 0);
        }
    }

    private void applyRecipe() {
        Optional<ProcessingRecipe<RecipeWrapper>> recipe = findRecipe();
        List<ItemStack> list = new ArrayList<>();
        if(recipe.isPresent()) {
            int rolls = inventory.getStackInSlot(0).getCount();
            inventory.clear();
            for(int roll = 0; roll < rolls; roll++) {
                List<ItemStack> rolledResults = recipe.get().rollResults();
                for(int i = 0; i < rolledResults.size(); i++) {
                    ItemStack stack = rolledResults.get(i);
                    ItemHelper.addToList(stack, list);
                }
            }
            for(int slot = 0; slot < list.size() && slot + 1 < inventory.getSlots(); slot++) {
                inventory.setStackInSlot(slot + 1, list.get(slot));
            }
        } else {
            inventory.clear();
        }
    }

    public Optional<ProcessingRecipe<RecipeWrapper>> findRecipe() {
        Optional<ProcessingRecipe<RecipeWrapper>> crushingRecipe = AllRecipeTypes.CRUSHING.find(wrapper, level);
        if(crushingRecipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> modifiedRecipe = TieredCrushingRecipe.convertNormalCrushing(crushingRecipe.get(), getExtraPercent(0.085F, crushingRecipe.get()));
            return Optional.of(modifiedRecipe);
        }
        crushingRecipe = AllRecipeTypes.MILLING.find(wrapper, level);
        if(crushingRecipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> modifiedRecipe = TieredCrushingRecipe.convertNormalCrushing(crushingRecipe.get(), getExtraPercent(0.085F, crushingRecipe.get()));
            return Optional.of(modifiedRecipe);
        }
        crushingRecipe = ModRecipeTypes.CRUSHING.find(wrapper, level, ((TieredCrushingWheelControllerBlock) getBlockState().getBlock()).getTier());
        if(crushingRecipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> modifiedRecipe = TieredCrushingRecipe.convertTieredCrushing(crushingRecipe.get(), getExtraPercent(0.085F, crushingRecipe.get()));
            return Optional.of(modifiedRecipe);
        }
        Optional<GTRecipe> recipe = level.getRecipeManager().getAllRecipesFor(GTRecipeTypes.MACERATOR_RECIPES).stream().filter(r ->
                ((Ingredient) r.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).test(wrapper.getItem(0))).findFirst();
        if(recipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> convertedRecipe = TieredCrushingRecipe.convertGT(recipe.get(), getExtraPercentGT(0.085F, recipe.get()));
            if(convertedRecipe.getRecipeTier().compareTo(((TieredCrushingWheelBlock) TieredCrushingWheelControllerBlock.MAP.get(getBlockState().getBlock())).getTier()) <= 0) {
                return Optional.of(convertedRecipe);
            }
        }
        return Optional.empty();
    }

    private float getExtraPercentGT(float baseExtraPercent, GTRecipe recipe) {
        return baseExtraPercent * (TIER.indexOfTier(((TieredCrushingWheelControllerBlock) getBlockState().getBlock()).getTier()) -
                TIER.indexOfTier(TIER.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP))));
    }

    private float getExtraPercent(float baseExtraPercent, ProcessingRecipe<?> recipe) {
        TIER recipeTier = recipe instanceof TieredProcessingRecipe<?> ? ((TieredProcessingRecipe<?>) recipe).getRecipeTier() : TIER.ULTRA_LOW;
        return baseExtraPercent * (TIER.indexOfTier(((TieredCrushingWheelControllerBlock) getBlockState().getBlock()).getTier()) -
                TIER.indexOfTier(recipeTier));
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        if(hasEntity()) {
            tag.put("Entity", NbtUtils.createUUID(entityUUID));
        }
        tag.put("Inventory", inventory.serializeNBT());
        tag.putFloat("Speed", crushingSpeed);
        super.write(tag, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if(tag.contains("Entity") && !isOccupied()) {
            entityUUID = NbtUtils.loadUUID(NBTHelper.getINBT(tag, "Entity"));
            this.searchForEntity = true;
        }
        crushingSpeed = tag.getFloat("Speed");
        inventory.deserializeNBT(tag.getCompound("Inventory"));
    }

    public void startCrushing(Entity entity) {
        processingEntity = entity;
        entityUUID = entity.getUUID();
    }

    private void itemInserted(ItemStack stack) {
        Optional<ProcessingRecipe<RecipeWrapper>> recipe = findRecipe();
        inventory.remainingTime = recipe.map(ProcessingRecipe::getProcessingDuration).orElse(100);
        inventory.appliedRecipe = false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return handler.cast();
        return super.getCapability(cap, side);
    }

    public void clear() {
        processingEntity = null;
        entityUUID = null;
    }

    public boolean isOccupied() {
        return hasEntity() || !inventory.isEmpty();
    }

    public boolean hasEntity() {
        return processingEntity != null;
    }
}
