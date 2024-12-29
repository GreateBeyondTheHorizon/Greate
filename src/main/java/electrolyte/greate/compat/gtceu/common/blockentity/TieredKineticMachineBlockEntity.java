package electrolyte.greate.compat.gtceu.common.blockentity;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.managed.MultiManagedStorage;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.IRotate.SpeedLevel;
import com.simibubi.create.content.kinetics.base.KineticEffectHandler;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.compat.gtceu.common.machine.TieredKineticMachineDefinition;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

public class TieredKineticMachineBlockEntity extends TieredKineticBlockEntity implements IMachineBlockEntity {

    public final MultiManagedStorage managedStorage = new MultiManagedStorage();

    public final MetaMachine metaMachine;
    private final long offset = GTValues.RNG.nextInt(20);
    public float workingSpeed, workingStress, impact;
    public boolean reActivateSource;

    @DescSynced
    private UUID owner;
    @DescSynced
    private String ownerName;
    private Class<?> ownerType;

    protected TieredKineticMachineBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.metaMachine = getDefinition().createMetaMachine(this);
    }

    public static TieredKineticMachineBlockEntity create(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        return new TieredKineticMachineBlockEntity(typeIn, pos, state);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        var result = MetaMachineBlockEntity.getCapability(getMetaMachine(), cap, side);
        return result == null ? super.getCapability(cap, side) : result;
    }

    //TODO: fix whatever the fuck this is
    public static void onBlockEntityRegister(BlockEntityType blockEntityType,
                                             NonNullSupplier<BiFunction<MaterialManager, TieredKineticMachineBlockEntity, BlockEntityInstance<? super TieredKineticMachineBlockEntity>>> instanceFactory,
                                             boolean renderNormally) {
        if(instanceFactory != null && LDLib.isClient()) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OneTimeEventReceiver.addModListener(GreateRegistries.REGISTRATE, FMLClientSetupEvent.class, ($) -> InstancedRenderRegistry.configure(blockEntityType).factory(instanceFactory.get()).skipRender((be) -> !renderNormally).apply()));
        }
    }

    @Override
    public TieredKineticMachineDefinition getDefinition() {
        return (TieredKineticMachineDefinition) IMachineBlockEntity.super.getDefinition();
    }

    @Override
    public TieredKineticMachineBlockEntity self() {
        return this;
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if(pId == 1) {
            if(level != null && level.isClientSide) {
                scheduleRenderUpdate();
            }
            return true;
        }
        return false;
    }

    @Override
    public MetaMachine getMetaMachine() {
        return metaMachine;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public MultiManagedStorage getRootStorage() {
        return managedStorage;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        metaMachine.onUnload();
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        metaMachine.onLoad();
    }

    @Override
    public boolean shouldRenderGrid(Player player, BlockPos pos, BlockState state, ItemStack held, Set<GTToolType> toolTypes) {
        return metaMachine.shouldRenderGrid(player, pos, state, held, toolTypes);
    }

    @Override
    public ResourceTexture sideTips(Player player, BlockPos pos, BlockState state, Set<GTToolType> toolTypes, Direction side) {
        return metaMachine.sideTips(player, pos, state, toolTypes, side);
    }

    public KineticEffectHandler getEffects() {
        return effects;
    }

    public float scheduleWorkingStress(float su, boolean simulate) {
        if(getDefinition().isSource()) {
            float stressGenerated = Math.min(su, Float.MAX_VALUE) / this.getTheoreticalSpeed();
            workingStress = stressGenerated;
            updateGeneratedRotation();
            return stressGenerated;
        }
        return 0;
    }

    public void scheduleWorkingStress(float su) {
        scheduleWorkingStress(su, false);
    }

    public float scheduleWorkingRPM(float rpmIn, boolean simulate) {
        if(getDefinition().isSource()) {
            float rpm = Math.min(rpmIn, 256f);
            workingSpeed = rpm;
            updateGeneratedRotation();
            return rpm;
        }
        return 0;
    }

    public void scheduleWorkingRPM(float rpmIn) {
        scheduleWorkingRPM(rpmIn, false);
    }

    public void stopWorking() {
        if(getDefinition().isSource() && getGeneratedSpeed() != 0) {
            workingSpeed = 0;
            updateGeneratedRotation();
        }
    }

    @Override
    public float getGeneratedSpeed() {
        return workingSpeed;
    }

    protected void notifyStressCapacityChange(float capacity) {
        this.getOrCreateNetwork().updateCapacityFor(this, capacity);
    }

    @Override
    public void removeSource() {
        if(getDefinition().isSource() && this.hasSource() && this.isSource()) {
            this.reActivateSource = true;
        }
        super.removeSource();
    }

    @Override
    public void setSource(BlockPos source) {
        super.setSource(source);
        if(! getDefinition().isSource()) return;
        BlockEntity be = this.level.getBlockEntity(source);
        if(be instanceof TieredKineticBlockEntity kbe) {
            if(this.reActivateSource && Math.abs(kbe.getSpeed()) >= Math.abs(this.getGeneratedSpeed())) {
                this.reActivateSource = false;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(getDefinition().isSource() && this.reActivateSource) {
            this.updateGeneratedRotation();
            this.reActivateSource = false;
        }
    }

    public void updateGeneratedRotation() {
        if(!getDefinition().isSource()) return;
        float speed = this.getGeneratedSpeed();
        float prevSpeed = this.speed;
        if(!this.level.isClientSide) {
            if(prevSpeed != speed) {
                if(!this.hasSource()) {
                    SpeedLevel levelBefore = SpeedLevel.of(this.speed);
                    SpeedLevel levelAfter = SpeedLevel.of(speed);
                    if(levelBefore != levelAfter) {
                        this.effects.queueRotationIndicators();
                    }
                }
                this.applyNewSpeed(prevSpeed, speed);
            }
            if(this.hasNetwork() && speed != 0.0F) {
                KineticNetwork network = this.getOrCreateNetwork();
                this.notifyStressCapacityChange(this.calculateAddedStressCapacity());
                this.getOrCreateNetwork().updateStressFor(this, this.calculateStressApplied());
                network.updateStress();
            }

            this.onSpeedChanged(prevSpeed);
            this.sendData();
        }
    }

    @Override
    public float calculateAddedStressCapacity() {
        this.lastCapacityProvided = capacity;
        return workingSpeed * workingStress;
    }

    @Override
    public float calculateStressApplied() {
        this.lastStressApplied = impact;
        return impact;
    }

    public void setStressApplied(float impact) {
        this.impact = impact / this.getTheoreticalSpeed();
    }

    public float getNetworkCapacity() {
        return capacity;
    }

    public void applyNewSpeed(float prevSpeed, float speed) {
        if(speed == 0.0F) {
            if(this.hasSource()) {
                this.notifyStressCapacityChange(0.0F);
                this.getOrCreateNetwork().updateStressFor(this, this.calculateAddedStressCapacity());
            } else {
                this.detachKinetics();
                this.setSpeed(0.0F);
                this.setNetwork(null);
            }
        } else if(prevSpeed == 0.0F) {
            this.setSpeed(speed);
            this.setNetwork(this.createNetworkId());
            this.attachKinetics();
        } else if(this.hasSource()) {
            if(Math.abs(prevSpeed) > Math.abs(speed)) {
                if(Math.signum(prevSpeed) != Math.signum(speed)) {
                    this.level.destroyBlock(this.worldPosition, true);
                }
            } else {
                this.detachKinetics();
                this.setSpeed(speed);
                this.source = null;
                this.setNetwork(this.createNetworkId());
                this.attachKinetics();
            }
        } else {
            this.detachKinetics();
            this.setSpeed(speed);
            this.attachKinetics();
        }
    }

    public Long createNetworkId() {
        return this.worldPosition.asLong();
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putFloat("workingSpeed", workingSpeed);
        compound.putFloat("workingStress", workingStress);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        workingSpeed = compound.contains("workingSpeed") ? compound.getFloat("workingSpeed") : 0;
        workingStress = compound.contains("workingStress") ? compound.getFloat("workingStress") : 0;
    }
}
