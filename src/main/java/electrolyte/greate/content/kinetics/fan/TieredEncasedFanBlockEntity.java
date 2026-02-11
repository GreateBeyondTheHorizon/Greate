package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;
import static electrolyte.greate.GreateValues.TM;

public class TieredEncasedFanBlockEntity extends EncasedFanBlockEntity implements IAirCurrentSource, ITieredKineticBlockEntity {

    private int tier;
    private ScrollValueBehaviour targetCircuit;
    private SmartFluidTankBehaviour inputTank;
    private LazyOptional<IFluidHandler> fluidCapability;

    public TieredEncasedFanBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.airCurrent = new TieredAirCurrent(this, ((TieredEncasedFanBlock) state.getBlock()).getTier());
        this.tier = ((ITieredBlock) state.getBlock()).getTier();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, 16000, false);
        behaviours.add(inputTank);

        fluidCapability = LazyOptional.of(() -> {
            LazyOptional<? extends IFluidHandler> inputCap = inputTank.getCapability();
            return new CombinedTankWrapper(inputCap.orElse(null));
        });
        targetCircuit = new ScrollValueBehaviour(Lang.builder(Greate.MOD_ID).translate("tooltip.circuit_number").component(), this, new CircuitValueBoxTransform());
        targetCircuit.between(0, 32);
        behaviours.add(targetCircuit);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if(fluidCapability != null) {
            fluidCapability.invalidate();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return fluidCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, TM[tier], capacity, stress);
        containedFluidTooltip(tooltip, isPlayerSneaking, fluidCapability);
        return true;
    }

    private class CircuitValueBoxTransform extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 9f, 15.5f);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            boolean shaftDir = ((TieredEncasedFanBlock) state.getBlock()).hasShaftTowards(level, getBlockPos(), state, direction);
            return !shaftDir && direction != state.getValue(FACING);
        }
    }

    @Override
    public boolean renderNormally() {
        return false;
    }

    public ScrollValueBehaviour getTargetCircuit() {
        return targetCircuit;
    }

    @Nullable
    public FluidStack getFluidInTank() {
        IFluidHandler handler = this.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
        if(handler != null) {
            return handler.getFluidInTank(0);
        }
        return null;
    }
}
