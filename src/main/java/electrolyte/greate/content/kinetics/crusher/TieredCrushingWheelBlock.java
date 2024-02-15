package electrolyte.greate.content.kinetics.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.CrushingWheels;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock.VALID;

public class TieredCrushingWheelBlock extends CrushingWheelBlock implements ITieredBlock {

    private int tier;

    public TieredCrushingWheelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        for(Direction dir : Iterate.directions) {
            if(dir.getAxis() == pState.getValue(AXIS)) continue;
            if(TieredCrushingWheelControllerBlock.MAP.get(this) == pLevel.getBlockState(pPos.relative(dir)).getBlock()) {
                pLevel.removeBlock(pPos.relative(dir), pIsMoving);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public void updateControllers(BlockState state, Level level, BlockPos pos, Direction direction) {
        if(direction.getAxis() == state.getValue(AXIS)) return;
        if(level == null) return;
        BlockPos controllerPos = pos.relative(direction);
        BlockPos otherWheelPos = pos.relative(direction, 2);
        boolean controllerExists = TieredCrushingWheelControllerBlock.MAP.get(this) == level.getBlockState(controllerPos).getBlock();
        boolean controllerIsValid = controllerExists && level.getBlockState(controllerPos).getValue(VALID);
        Direction controllerOldDirection = controllerExists ? level.getBlockState(controllerPos).getValue(CrushingWheelControllerBlock.FACING) : null;
        boolean controllerShouldExist = false;
        boolean controllerShouldBeValid = false;
        Direction controllerNewDirection = Direction.DOWN;
        BlockState otherState = level.getBlockState(otherWheelPos);
        if(this == otherState.getBlock()) {
            controllerShouldExist = true;
            CrushingWheelBlockEntity be = getBlockEntity(level, pos);
            CrushingWheelBlockEntity otherBE = getBlockEntity(level, otherWheelPos);

            if(be != null && otherBE != null && (be.getSpeed() > 0) != (otherBE.getSpeed() > 0) && be.getSpeed() != 0) {
                Axis wheelAxis = state.getValue(AXIS);
                Axis sideAxis = direction.getAxis();
                int controllerADO = Math.round(Math.signum(be.getSpeed()))  * direction.getAxisDirection().getStep();
                Vec3 controllerDirVec = new Vec3(wheelAxis == Axis.X ? 1 : 0, wheelAxis == Axis.Y ? 1 : 0, wheelAxis == Axis.Z ? 1 : 0).cross(
                        new Vec3(sideAxis == Axis.X ? 1 : 0, sideAxis == Axis.Y ? 1 : 0, sideAxis == Axis.Z ? 1 : 0));
                controllerNewDirection = Direction.getNearest(controllerDirVec.x * controllerADO, controllerDirVec.y * controllerADO, controllerDirVec.z * controllerADO);
                controllerShouldBeValid = true;
            }
            if(otherState.getValue(AXIS) != state.getValue(AXIS)) controllerShouldExist = false;
        }
        if(!controllerShouldExist) {
            if(controllerExists) level.setBlockAndUpdate(controllerPos, Blocks.AIR.defaultBlockState());
            return;
        }

        if(!controllerExists) {
            if(!level.getBlockState(controllerPos).canBeReplaced()) return;
            level.setBlockAndUpdate(controllerPos, TieredCrushingWheelControllerBlock.MAP.get(this).defaultBlockState()
                    .setValue(VALID, controllerShouldBeValid)
                    .setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
        } else if(controllerIsValid != controllerShouldBeValid || controllerOldDirection != controllerNewDirection) {
            level.setBlockAndUpdate(controllerPos, level.getBlockState(controllerPos)
                    .setValue(VALID, controllerShouldBeValid)
                    .setValue(CrushingWheelControllerBlock.FACING, controllerNewDirection));
        }

        ((TieredCrushingWheelControllerBlock) TieredCrushingWheelControllerBlock.MAP.get(this)).updateSpeed(level.getBlockState(controllerPos), level, controllerPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        for (Direction direction : Iterate.directions) {
            BlockPos pos = pPos.relative(direction);
            BlockState state = pLevel.getBlockState(pos);
            Axis stateAxis = pState.getValue(AXIS);
            if (TieredCrushingWheelControllerBlock.MAP.get(this) == state.getBlock() && direction.getAxis() != stateAxis) return false;
            if (this != state.getBlock()) continue;
            if (state.getValue(AXIS) != stateAxis || stateAxis != direction.getAxis()) return false;
        }
        return true;
    }

    @Override
    public BlockEntityType<? extends CrushingWheelBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_CRUSHING_WHEEL.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
