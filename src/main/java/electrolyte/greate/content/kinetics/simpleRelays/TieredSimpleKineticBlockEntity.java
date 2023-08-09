package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TieredSimpleKineticBlockEntity extends TieredKineticBlockEntity {
    public TieredSimpleKineticBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1);
    }

    @Override
    public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
        if(!ICogWheel.isLargeCog(state)) {
            return super.addPropagationLocations(block, state, neighbours);
        }
        BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1))
                .forEach(offset -> {
                    if(offset.distSqr(BlockPos.ZERO) == 2) {
                        neighbours.add(worldPosition.offset(offset));
                    }
                });
        return neighbours;
    }

    @Override
    protected boolean isNoisy() {
        return false;
    }
}
