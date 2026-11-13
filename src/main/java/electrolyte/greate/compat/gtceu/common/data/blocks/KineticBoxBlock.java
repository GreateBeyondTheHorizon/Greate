package electrolyte.greate.compat.gtceu.common.data.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public class KineticBoxBlock extends KineticBlock {
    public KineticBoxBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(BlockStateProperties.FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }
}
