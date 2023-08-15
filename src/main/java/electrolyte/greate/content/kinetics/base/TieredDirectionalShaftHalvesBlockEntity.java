package electrolyte.greate.content.kinetics.base;

import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TieredDirectionalShaftHalvesBlockEntity extends TieredKineticBlockEntity {
    public TieredDirectionalShaftHalvesBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    public Direction getSourceFacing() {
        BlockPos pos = source.subtract(getBlockPos());
        return Direction.getNearest(pos.getX(), pos.getY(), pos.getZ());
    }
}
