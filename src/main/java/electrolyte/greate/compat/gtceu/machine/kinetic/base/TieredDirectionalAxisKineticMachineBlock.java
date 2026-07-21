package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.simibubi.create.api.contraption.transformable.TransformableBlock;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import net.createmod.catnip.data.Iterate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;
import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class TieredDirectionalAxisKineticMachineBlock extends TieredDirectionalKineticMachineBlock implements TransformableBlock {
    public TieredDirectionalAxisKineticMachineBlock(Properties properties, MachineDefinition definition, KineticBlock kineticBlock) {
        super(properties, definition, kineticBlock);
    }

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction facing = getFacingForPlacement(context);
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();
		boolean alongFirst = false;
		Axis faceAxis = facing.getAxis();

		if (faceAxis.isHorizontal()) {
			alongFirst = faceAxis == Axis.Z;
			Direction positivePerpendicular = faceAxis == Axis.X ? Direction.SOUTH : Direction.EAST;

			boolean shaftAbove = prefersConnectionTo(world, pos, Direction.UP, true);
			boolean shaftBelow = prefersConnectionTo(world, pos, Direction.DOWN, true);
			boolean preferLeft = prefersConnectionTo(world, pos, positivePerpendicular, false);
			boolean preferRight = prefersConnectionTo(world, pos, positivePerpendicular.getOpposite(), false);

			if (shaftAbove || shaftBelow || preferLeft || preferRight)
				alongFirst = faceAxis == Axis.X;
		}

		if (faceAxis.isVertical()) {
			alongFirst = getAxisAlignmentForPlacement(context);
			Direction prefferedSide = null;

			for (Direction side : Iterate.horizontalDirections) {
				if (!prefersConnectionTo(world, pos, side, true)
					&& !prefersConnectionTo(world, pos, side.getClockWise(), false))
					continue;
				if (prefferedSide != null && prefferedSide.getAxis() != side.getAxis()) {
					prefferedSide = null;
					break;
				}
				prefferedSide = side;
			}

			if (prefferedSide != null)
				alongFirst = prefferedSide.getAxis() == Axis.X;
		}

		return this.defaultBlockState()
			.setValue(FACING, facing)
			.setValue(AXIS_ALONG_FIRST_COORDINATE, alongFirst);
	}

    protected boolean prefersConnectionTo(LevelReader reader, BlockPos pos, Direction facing, boolean shaftAxis) {
        if (!shaftAxis)
            return false;
        BlockPos neighbourPos = pos.relative(facing);
        BlockState blockState = reader.getBlockState(neighbourPos);
        Block block = blockState.getBlock();
        return block instanceof IRotate
                && ((IRotate) block).hasShaftTowards(reader, neighbourPos, blockState, facing.getOpposite());
    }

    protected boolean getAxisAlignmentForPlacement(BlockPlaceContext context) {
		return context.getHorizontalDirection()
			.getAxis() == Axis.X;
	}

    protected Direction getFacingForPlacement(BlockPlaceContext context) {
		Direction facing = context.getNearestLookingDirection()
			.getOpposite();
		if (context.getPlayer() != null && context.getPlayer()
			.isShiftKeyDown())
			facing = facing.getOpposite();
		return facing;
	}

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS_ALONG_FIRST_COORDINATE);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public BlockState transform(BlockState state, StructureTransform transform) {
        return ((DirectionalAxisKineticBlock) kineticBlock).transform(state, transform);
    }
}
