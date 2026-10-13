package electrolyte.greate.compat.gtceu.machine.kinetic.saw;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic.Status;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.gregtechceu.gtceu.client.model.machine.MachineRenderState;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.utils.ExtendedUseOnContext;
import com.gregtechceu.gtceu.utils.ISubscription;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.mixin.accessor.UseOnContextAccessor;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.millstone.KineticMachineRecipeLogic;
import lombok.Getter;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TieredSawMachine extends TieredKineticWorkableMachine {

    @SaveField
    private final NotifiableItemStackHandler importItems, exportItems;

    @SaveField
    private final NotifiableFluidTank importTank;

    @SaveField
    @SyncToClient
    @Getter
    private FluidStack containedFluid;

    @SaveField
    @SyncToClient
    @Getter
    private ItemStack renderedImportStack;

    @Nullable
    protected ISubscription importFluidSub;
    @Nullable
    protected ISubscription importItemSub;

    @SyncToClient
    private ItemStack playEvent = ItemStack.EMPTY;
    @SyncToClient
    private boolean shouldPlaySound = false;

    private TickableSubscription autoOutputSub;
    private TickableSubscription nonGTRecipeSub;

    @SyncToClient
    @SaveField
    @Getter
    private boolean appliedRecipe = false;

    @SyncToClient
    @SaveField
    @Getter
    private int remainingTime = -1;

    @SyncToClient
    @SaveField
    @Getter
    private int recipeDuration = 20;

    @SyncToClient
    @SaveField
    @Getter
    private int recipeProgress = 0;


    public TieredSawMachine(BlockEntityCreationInfo info, int tier) {
        super(info, tier, new TieredSawMachineBlockEntity(info.type(), info.pos(), info.state()));
        this.importItems = attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.IN));
        this.exportItems = attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxOutputs(ItemRecipeCapability.CAP), IO.OUT));
        this.importTank = attachTrait(new NotifiableFluidTank(getRecipeType().getMaxInputs(FluidRecipeCapability.CAP),
                        GTMachineUtils.defaultTankSizeFunction.apply(this.getTier()),
                        IO.IN, IO.BOTH));
        this.containedFluid = FluidStack.EMPTY;
        this.renderedImportStack = ItemStack.EMPTY;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.updateStoredFluidFromCache();
        this.importFluidSub = this.importTank.addChangedListener(this::onFluidChanged);
        this.importItemSub = this.importItems.addChangedListener(this::onImportItemChanged);

        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateAutoOutputSubscription));
            serverLevel.getServer().tell(new TickTask(0, this::updateNonGTRecipeSubscription));
        }
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if(this.importFluidSub != null) {
            this.importFluidSub.unsubscribe();
            this.importFluidSub = null;
        }
        if(this.importItemSub != null) {
            this.importItemSub.unsubscribe();
            this.importItemSub = null;
        }
        if(this.autoOutputSub != null) {
            this.autoOutputSub.unsubscribe();
            this.autoOutputSub = null;
        }
        if(this.nonGTRecipeSub != null) {
            this.nonGTRecipeSub.unsubscribe();
            this.nonGTRecipeSub = null;
        }
    }

    private void updateAutoOutputSubscription() {
        autoOutputSub = subscribeServerTick(autoOutputSub, () -> {
            if(!canRunRecipes()) return;
            if(getSpeed() == 0) return;
            if(exportItems.isEmpty()) return;
            Vec3 itemMovement = getItemMovementVec();
            Direction movementFacing = Direction.getNearest(itemMovement.x, itemMovement.y, itemMovement.z);
            for(int slot = 0; slot < exportItems.getSize(); slot++) {
                ItemStack stack = exportItems.getStackInSlot(slot);
                if(stack.isEmpty()) continue;
                ItemStack tryExportingToBeltFunnel = getBehaviour(DirectBeltInputBehaviour.TYPE)
                        .tryExportingToBeltFunnel(stack, movementFacing.getOpposite(), false);
                if(tryExportingToBeltFunnel != null) {
                    if(tryExportingToBeltFunnel.getCount() != stack.getCount()) {
                        exportItems.setStackInSlot(slot, tryExportingToBeltFunnel);
                        notifyUpdate();
                        return;
                    }
                    if(!tryExportingToBeltFunnel.isEmpty()) return;
                }
            }
            BlockPos nextPos = worldPosition.offset(BlockPos.containing(itemMovement));
            DirectBeltInputBehaviour behaviour = BlockEntityBehaviour.get(level, nextPos, DirectBeltInputBehaviour.TYPE);
            if(behaviour != null) {
                boolean changed = false;
                if(!behaviour.canInsertFromSide(movementFacing)) return;
                if(level.isClientSide && !isVirtual()) return;
                for(int slot = 0; slot < exportItems.getSize(); slot++) {
                    ItemStack stack = exportItems.getStackInSlot(slot);
                    if(stack.isEmpty()) continue;
                    ItemStack remainder = behaviour.handleInsertion(stack, movementFacing, false);
                    if(remainder.equals(stack, false)) continue;
                    exportItems.setStackInSlot(slot, remainder);
                    changed = true;
                }
                if(changed) {
                    setChanged();
                }
                return;
            }

            Vec3 outPos = VecHelper.getCenterOf(worldPosition)
                    .add(itemMovement.scale(0.5f)).add(0, 0.5, 0);
            Vec3 outMotion = itemMovement.scale(0.0625f).add(0, 0.125, 0);
            for(int slot = 0; slot < exportItems.getSize(); slot++) {
                ItemStack stack = exportItems.getStackInSlot(slot);
                if(stack.isEmpty()) continue;
                ItemEntity entityIn = new ItemEntity(level, outPos.x, outPos.y, outPos.z, stack);
                entityIn.setDeltaMovement(outMotion);
                level.addFreshEntity(entityIn);
                exportItems.setStackInSlot(slot, ItemStack.EMPTY);
            }
            level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        });
    }

    private void updateNonGTRecipeSubscription() {
        nonGTRecipeSub = subscribeServerTick(nonGTRecipeSub, () -> {
            this.syncDataHolder.markClientSyncFieldDirty("recipeDuration");
            this.syncDataHolder.markClientSyncFieldDirty("remainingTime");
            if(!canRunRecipes()) return;
            if(getSpeed() == 0) return;
            if(importItems.isEmpty()) return;
            if(this.getRecipeLogic().isActive()) {
                this.recipeProgress = this.getRecipeLogic().getProgress();
                this.syncDataHolder.markClientSyncFieldDirty("recipeProgress");
                return;
            }
            if(!hasNonGTRecipe(importItems.getStackInSlot(0))) {
                if(remainingTime != -1 || appliedRecipe) {
                    remainingTime = -1;
                    appliedRecipe = false;
                }
                return;
            }

            float processingSpeed = Mth.clamp(Math.abs(getSpeed()) / 24, 1, 128);
            this.remainingTime -= processingSpeed;
            if(remainingTime > 0) {
                spawnParticles(importItems.getStackInSlot(0));
                return;
            }
            if(remainingTime < 0 && !appliedRecipe) {
                playEvent = importItems.getStackInSlot(0);
                this.syncDataHolder.markClientSyncFieldDirty("playEvent");
                this.
                recipeDuration = 20;
                remainingTime = 20;
                return;
            }
            if(!appliedRecipe) {
                applyNonGTRecipe();
                appliedRecipe = true;
            }
            remainingTime = -1;
            appliedRecipe = false;
            recipeDuration = 20;
        });
    }

    private boolean hasNonGTRecipe(ItemStack stack) {
        if(PackageItem.isPackage(stack)) return true;
        //todo: sequenced assembly
        return false;
    }

    private void applyNonGTRecipe() {
        ItemStack input = importItems.getStackInSlot(0);
        List<ItemStack> list = new ArrayList<>();
        if(PackageItem.isPackage(input)) {
            importItems.setStackInSlot(0, ItemStack.EMPTY);
            ItemStackHandler results = PackageItem.getContents(input);
            for(int slot = 0; slot < results.getSlots(); slot++) {
                ItemStack result = results.getStackInSlot(slot);
                if(!result.isEmpty()) ItemHelper.addToList(result, list);
            }
            for(int slot = 0; slot < list.size() && slot < exportItems.getSlots(); slot++) {
                exportItems.setStackInSlot(slot, list.get(slot));
            }
            shouldPlaySound = true;
            this.syncDataHolder.markClientSyncFieldDirty("shouldPlaySound");
            ((KineticMachineRecipeLogic) this.getRecipeLogic()).postEffects(true);
            return;
        }
        //todo: sequenced assembly recipes
    }

    @Override
    public InteractionResult onUseWithItem(ExtendedUseOnContext context) {
        if(!canRunRecipes()) return InteractionResult.PASS;
        if (FluidUtil.getFluidHandler(context.getItemInHand()).isPresent()) {
            if (isRemote()) {
                return InteractionResult.SUCCESS;
            }
            if (FluidUtil.interactWithFluidHandler(context.getPlayer(), context.getHand(), importTank)) {
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        }
        if(context.getItemInHand().is(Items.SPONGE) &&
                !this.importTank.drain(Integer.MAX_VALUE, FluidAction.EXECUTE).isEmpty()) {
            context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }
        return super.onUseWithItem(context);
    }

    @Override
    public InteractionResult onUse(ExtendedUseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        BlockHitResult hit = ((UseOnContextAccessor) context).create$getHitResult();
        ItemStack heldItem = player.getItemInHand(hand);
        IPlacementHelper placementHelper = PlacementHelpers.get(TieredSawMachineBlock.PLACEMENT_HELPER_ID);
        if(!player.isShiftKeyDown() && player.mayBuild()) {
            if(placementHelper.matchesItem(heldItem) && placementHelper.getOffset(player, world, state, pos, hit)
                    .placeInWorld(world, (BlockItem) heldItem.getItem(), player, hand, hit).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        if(player.isSpectator() || !heldItem.isEmpty() || !canRunRecipes()) return InteractionResult.PASS;

        boolean emptyOutput = true;
        NotifiableItemStackHandler inv = this.exportItems;
        for(int slot = 0; slot < inv.getSlots(); slot++) {
            ItemStack heldItemStack = inv.getStackInSlot(slot);
            if(!heldItemStack.isEmpty()) emptyOutput = false;
            player.getInventory().placeItemBackInInventory(heldItemStack);
            inv.setStackInSlot(slot, ItemStack.EMPTY);
        }

        if(emptyOutput) {
            inv = this.importItems;
            for(int slot = 0; slot < inv.getSlots(); slot++) {
                player.getInventory().placeItemBackInInventory(inv.getStackInSlot(slot));
                inv.setStackInSlot(slot, ItemStack.EMPTY);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void onFluidChanged() {
        if(!this.isRemote()) {
            this.syncDataHolder.markClientSyncFieldDirty("containedFluid");
            this.updateStoredFluidFromCache();
        }
    }

    private void onImportItemChanged() {
        if(!this.isRemote()) {
            this.syncDataHolder.markClientSyncFieldDirty("renderedImportStack");
            this.updateStoredImportItemFromCache();
        }
    }

    private void updateStoredFluidFromCache() {
        FluidStack cachedFluid = this.importTank.getFluidInTank(0);
        this.containedFluid = cachedFluid.isEmpty() ? FluidStack.EMPTY : cachedFluid;
    }

    private void updateStoredImportItemFromCache() {
        ItemStack cachedStack = this.importItems.getStackInSlot(0);
        this.renderedImportStack = cachedStack.isEmpty() ? ItemStack.EMPTY : this.importItems.getStackInSlot(0);
    }

    @Override
    public @NotNull MachineRenderState getRenderState() {
        MachineRenderState machineRenderState = super.getRenderState();
        BlockState blockState = getLevel().getBlockState(getBlockPos());
        if(blockState.hasProperty(SawBlock.FACING)) {
            machineRenderState = machineRenderState.setValue(SawBlock.FACING, blockState.getValue(SawBlock.FACING));
        }
        if(blockState.hasProperty(SawBlock.FLIPPED)) {
            machineRenderState = machineRenderState.setValue(SawBlock.FLIPPED, blockState.getValue(SawBlock.FLIPPED));
        }
        if(blockState.hasProperty(SawBlock.AXIS_ALONG_FIRST_COORDINATE)) {
            machineRenderState = machineRenderState.setValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE, blockState.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE));
        }
        return machineRenderState;
    }

    @Override
    public void onMachineDestroyed() {
        super.onMachineDestroyed();
        importItems.dropInventoryInWorld();
        exportItems.dropInventoryInWorld();
    }

    @Override
    public boolean onWorking() {
        if(!canRunRecipes()) {
            this.recipeLogic.resetRecipeLogic();
            this.recipeLogic.setStatus(Status.SUSPEND);
            return false;
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if(!canRunRecipes()) return false;
        this.playEvent = renderedImportStack;
        this.getSyncDataHolder().markClientSyncFieldDirty("playEvent");
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        this.shouldPlaySound = true;
        this.getSyncDataHolder().markClientSyncFieldDirty("shouldPlaySound");
        this.kineticMachineTrait.getKineticBlockEntity().award(AllAdvancements.SAW_PROCESSING);
    }

    @Override
    public void clientTick() {
        if(getSpeed() == 0 || !canRunRecipes()) return;
        if((getRecipeLogic().isActive() || remainingTime != -1) && !renderedImportStack.isEmpty()) {
            spawnParticles(renderedImportStack);
        }
		if(shouldPlaySound && ((KineticMachineRecipeLogic) this.getRecipeLogic()).postEffects()) {
			boolean isWood = false;
			Item item = renderedImportStack.getItem();
			if (item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				isWood = block.getSoundType(block.defaultBlockState(), level, worldPosition, null) == SoundType.WOOD;
			}
			spawnEventParticles(playEvent);
            shouldPlaySound = false;
            playEvent = ItemStack.EMPTY;
            if(!isWood)
                AllSoundEvents.SAW_ACTIVATE_STONE.playAt(level, worldPosition, 3, 1, true);
            else
                AllSoundEvents.SAW_ACTIVATE_WOOD.playAt(level, worldPosition, 3, 1, true);

            return;
		}
        super.clientTick();
    }

    public boolean canRunRecipes() {
        return getBlockState().getValue(SawBlock.FACING) == Direction.UP;
    }

    protected void spawnParticles(ItemStack stack) {
		ParticleOptions particleData = null;
		float speed = 1;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleOption(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock()
				.defaultBlockState());
		else {
			particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);
			speed = .125f;
		}

		RandomSource r = level.random;
		Vec3 vec = getItemMovementVec();
		Vec3 pos = VecHelper.getCenterOf(this.worldPosition);
        float duration = this.getRecipeLogic().getLastUnrolledRecipe() == null ? recipeDuration : this.getRecipeLogic().getLastUnrolledRecipe().duration;
		float offset = this.getRecipeProgress() != 0 ? ((float) this.getRecipeProgress() / duration) - 1.0f : ((float) remainingTime / duration) - 1.0f; //todo: fix particle drift from rendered stack
        offset /= 2;
		if (appliedRecipe) {
            offset = -offset;
			offset -= .5f;
        }
		level.addParticle(particleData, pos.x() + -vec.x * offset, pos.y() + .45f, pos.z() + -vec.z * offset,
			vec.x * speed, r.nextFloat() * speed, vec.z * speed);
	}

    public Vec3 getItemMovementVec() {
		boolean alongX = !getRenderState().getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE);
		int offset = getSpeed() < 0 ? -1 : 1;
		return new Vec3(offset * (alongX ? 1 : 0), 0, offset * (alongX ? 0 : -1));
	}

    protected void spawnEventParticles(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return;

		ParticleOptions particleData = null;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleOption(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock()
				.defaultBlockState());
		else
			particleData = new ItemParticleOption(ParticleTypes.ITEM, stack);

		RandomSource r = level.random;
		Vec3 v = VecHelper.getCenterOf(this.worldPosition)
			.add(0, 5 / 16f, 0);
		for (int i = 0; i < 10; i++) {
			Vec3 m = VecHelper.offsetRandomly(new Vec3(0, 0.25f, 0), r, .125f);
			level.addParticle(particleData, v.x, v.y, v.z, m.x, m.y, m.y);
		}
	}
}
