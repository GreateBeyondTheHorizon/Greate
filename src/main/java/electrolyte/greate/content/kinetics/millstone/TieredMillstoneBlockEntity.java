package electrolyte.greate.content.kinetics.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.sound.SoundScapes;
import com.simibubi.create.foundation.utility.VecHelper;
import electrolyte.greate.GreateEnums;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.ViewOnlyWrappedStorageView;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TieredMillstoneBlockEntity extends TieredKineticBlockEntity implements ITieredKineticBlockEntity, SidedStorageBlockEntity {

    public ItemStackHandlerContainer inputInv;
    public ItemStackHandler outputInv;
    public TieredMillstoneInventoryHandler capability;
    public int timer;
    private ProcessingRecipe<Container> lastRecipe;
    private int maxItemsPerRecipe;

    public TieredMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inputInv = new ItemStackHandlerContainer(1);
        outputInv = new ItemStackHandler(9);
        capability = new TieredMillstoneInventoryHandler();
        maxItemsPerRecipe = GreateEnums.TIER.getTierMultiplier(this.getTier(), 2);
    }

    @Override
    public double getMaxCapacity() {
        return this.getTier().getStressCapacity();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this));
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.MILLSTONE);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void tickAudio() {
        super.tickAudio();
        if(getSpeed() == 0) return;
        if(inputInv.getStackInSlot(0).isEmpty()) return;
        float pitch = Mth.clamp((Math.abs(getSpeed()) / 256f) + .45f, .85f, 1f);
        SoundScapes.play(SoundScapes.AmbienceGroup.MILLING, worldPosition, pitch);
    }

    @Override
    public void tick() {
        super.tick();

        if(getSpeed() == 0) return;

        for(int i = 0; i < outputInv.getSlotCount(); i++) {
            if(outputInv.getStackInSlot(i).getCount() == outputInv.getSlotLimit(i)) return;
            if(outputInv.getStackInSlot(i).getCount() + maxItemsPerRecipe > outputInv.getSlotLimit(i)) return;
        }

        if(timer > 0 && lastRecipe != null && lastRecipe.matches(inputInv, level)) {
            timer -= getProcessingSpeed();

            if(level.isClientSide) {
                spawnParticles();
                return;
            }

            if(timer <= 0) {
                process();
            }
            return;
        }

        if(inputInv.getStackInSlot(0).isEmpty()) return;

        if(lastRecipe == null || !lastRecipe.matches(inputInv, level)) {
            Optional<ProcessingRecipe<Container>> recipe = findRecipe();
            if(recipe.isEmpty()) {
                timer = 100;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingDuration();
                sendData();
            }
            return;
        }

        timer = lastRecipe.getProcessingDuration();
        sendData();
    }

    public Optional<ProcessingRecipe<Container>> findRecipe() {
        Optional<ProcessingRecipe<Container>> millingRecipe = ModRecipeTypes.MILLING.find(inputInv, level, this.getTier());
        if(millingRecipe.isEmpty()) {
            millingRecipe = AllRecipeTypes.MILLING.find(inputInv, level);
        }
        if (millingRecipe.isEmpty()) {
            Optional<GTRecipe> test = level.getRecipeManager().getAllRecipesFor(GTRecipeTypes.MACERATOR_RECIPES).stream().filter(r ->
                    ((Ingredient) r.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).test(inputInv.getItem(0))).findFirst();
            if(test.isPresent()) {
                TieredProcessingRecipe<Container> convertedRecipe = TieredMillingRecipe.convertGT(test.get());
                if(convertedRecipe.getRecipeTier().compareTo(this.getTier()) <= 0) {
                    return Optional.of(convertedRecipe);
                }
            }
        }
        return millingRecipe;
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(level, worldPosition, inputInv);
        ItemHelper.dropContents(level, worldPosition, outputInv);
    }

    private void process() {
        if(lastRecipe == null || !lastRecipe.matches(inputInv, level)) {
            Optional<ProcessingRecipe<Container>> recipe = findRecipe();
            if(recipe.isEmpty()) return;
            lastRecipe = recipe.get();
        }

        for(int i = 0; i < Math.min(inputInv.getStackInSlot(0).getCount(), maxItemsPerRecipe); i++) {
            try(Transaction t = TransferUtil.getTransaction()) {
                ItemStackHandlerSlot slot = inputInv.getSlot(0);
                slot.extract(slot.getResource(), 1, t);
                lastRecipe.rollResults().forEach(stack -> outputInv.insert(ItemVariant.of(stack), stack.getCount(), t));
                t.commit();
            }
        }
        award(AllAdvancements.MILLSTONE);

        sendData();
        setChanged();
    }

    public void spawnParticles() {
        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        if(stackInSlot.isEmpty()) return;

        ItemParticleOption data = new ItemParticleOption(ParticleTypes.ITEM, stackInSlot);
        float angle = level.random.nextFloat() * 360;
        Vec3 offset = new Vec3(0,0, 0.5f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        Vec3 target = VecHelper.rotate(offset, getSpeed() > 0 ? 25 : -25, Direction.Axis.Y);

        Vec3 center = offset.add(VecHelper.getCenterOf(worldPosition));
        target = VecHelper.offsetRandomly(target.subtract(offset), level.random, 1 / 128f);
        level.addParticle(data, center.x, center.y, center.z, target.x, target.y, target.z);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("Timer", timer);
        compound.put("InputInventory", inputInv.serializeNBT());
        compound.put("OutputInventory", outputInv.serializeNBT());
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        timer = compound.getInt("Timer");
        inputInv.deserializeNBT(compound.getCompound("InputInventory"));
        outputInv.deserializeNBT(compound.getCompound("OutputInventory"));
        super.read(compound, clientPacket);
    }

    public int getProcessingSpeed() {
        return Mth.clamp((int) Math.abs(getSpeed() / 16f), 1, 512);
    }

    @Override
    public Storage<ItemVariant> getItemStorage(Direction side) {
        return capability;
    }

    private boolean canProcess(ItemStack stack) {
        ItemStackHandlerContainer tester = new ItemStackHandlerContainer(1);
        tester.setStackInSlot(0, stack);

        if(lastRecipe != null && lastRecipe.matches(tester, level)) return true;
        return inputInv.getStackInSlot(0).isEmpty() && outputInv.getStackInSlot(0).isEmpty();
    }

    private class TieredMillstoneInventoryHandler extends CombinedStorage<ItemVariant, ItemStackHandler> {

        public TieredMillstoneInventoryHandler() {
            super(List.of(inputInv, outputInv));
        }

        @Override
        public long insert(ItemVariant resource, long amount, TransactionContext t) {
            if(canProcess(resource.toStack())) return inputInv.insert(resource, amount, t);
            return 0;
        }

        @Override
        public long extract(ItemVariant resource, long amount, TransactionContext t) {
            return outputInv.extract(resource, amount, t);
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            return new MillstoneInventoryHandlerIterator();
        }

        private class MillstoneInventoryHandlerIterator implements Iterator<StorageView<ItemVariant>> {
            private boolean output = true;
            private Iterator<StorageView<ItemVariant>> wrapped;

            public MillstoneInventoryHandlerIterator() {
                wrapped = outputInv.iterator();
            }

            @Override
            public boolean hasNext() {
                return wrapped.hasNext();
            }

            @Override
            public StorageView<ItemVariant> next() {
                StorageView<ItemVariant> view = wrapped.next();
                if(!output) view = new ViewOnlyWrappedStorageView<>(view);
                if(output && !hasNext()) {
                    wrapped = inputInv.iterator();
                    output = false;
                }
                return view;
            }
        }
    }
}
