package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TieredKineticBlockEntityVisual<T extends KineticBlockEntity> extends KineticBlockEntityVisual<T> {


    public TieredKineticBlockEntityVisual(VisualizationContext context, T blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
    }

    protected BlockState shaft(BlockState state) {
        return shaft(state, getRotationAxis());
    }

    public static BlockState shaft(BlockState state, Direction.Axis axis) {
        return state.setValue(ShaftBlock.AXIS, axis);
    }
}
