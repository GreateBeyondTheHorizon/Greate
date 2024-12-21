package electrolyte.greate.compat.gtceu.common.block;

import com.gregtechceu.gtceu.api.block.BlockProperties;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.common.blockentity.TieredKineticMachineBlockEntity;
import electrolyte.greate.compat.gtceu.common.machine.TieredKineticMachineDefinition;
import electrolyte.greate.compat.gtceu.common.machine.kinetic.IKineticMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TieredKineticMachineBlock extends MetaMachineBlock implements IRotate {

    private int tier;

    public TieredKineticMachineBlock(Properties properties, TieredKineticMachineDefinition definition) {
        super(properties, definition);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        if(MetaMachine.getMachine(world, pos) instanceof IKineticMachine km) {
            return km.hasShaftTowards(face);
        }
        return false;
    }

    public Direction getRotationFacing(BlockState state) {
        Direction frontFacing = getFrontFacing(state);
        return ((TieredKineticMachineDefinition) definition).isFrontRotation() ? frontFacing :
                (frontFacing.getAxis() == Axis.Y ? Direction.NORTH : frontFacing.getClockWise());
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return getRotationFacing(state).getAxis();
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof KineticBlockEntity kbe) {
            kbe.preventSpeedUpdate = 0;
            if(oldState.getBlock() != state.getBlock()) return;
            if(oldState.hasBlockEntity() != state.hasBlockEntity()) return;
            if(!areStatesKineticallyEquivalent(oldState, state)) return;
            kbe.preventSpeedUpdate = 2;
        }
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(this.rotationState.property, pRotation.rotate(pState.getValue(this.rotationState.property)));
    }

    public boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
        if(oldState.getBlock() != newState.getBlock()) return false;
        return getRotationAxis(oldState) == getRotationAxis(newState);
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int pFlags, int pRecursionLeft) {
        if(pLevel.isClientSide()) return;
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if(!(be instanceof KineticBlockEntity kbe)) return;
        if(kbe.preventSpeedUpdate > 0) return;

        kbe.warnOfMovement();
        kbe.clearKineticInformation();
        kbe.updateSpeed = true;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(blockEntityType == getDefinition().getBlockEntityType()) {
            if(!level.isClientSide) {
                return (pLevel, pPos, pState, pBlockEntity) -> {
                    if(pState.getValue(BlockProperties.SERVER_TICK) && pBlockEntity instanceof IMachineBlockEntity metaMachine) {
                        metaMachine.getMetaMachine().serverTick();
                    }
                    if(pBlockEntity instanceof TieredKineticMachineBlockEntity tkbe) {
                        tkbe.tick();
                    }
                };
            } else {
                return (pLevel, pPos, pState, pBlockEntity) -> {
                    if(pBlockEntity instanceof IMachineBlockEntity metaMachine) {
                        metaMachine.getMetaMachine().clientTick();
                    }
                    if(pBlockEntity instanceof TieredKineticMachineBlockEntity tkbe) {
                        tkbe.tick();
                    }
                };
            }
        }
        return null;
    }
}
