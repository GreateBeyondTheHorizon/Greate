package electrolyte.greate.content.kinetics.saw;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ActorVisual;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockAndTintGetter;

public class TieredSawActorVisual extends ActorVisual {

    private final RotatingInstance shaft;

    public TieredSawActorVisual(VisualizationContext visualizationContext, BlockAndTintGetter world, MovementContext context) {
        super(visualizationContext, world, context);
        shaft = TieredSawVisual.shaft(visualizationContext.instancerProvider(), context.state);

        Axis axis = KineticBlockEntityVisual.rotationAxis(context.state);
        shaft.setRotationAxis(axis)
                .setRotationOffset(KineticBlockEntityVisual.rotationOffset(context.state, axis, context.localPos))
                .setPosition(context.localPos)
                .light(localBlockLight(), 0)
                .setChanged();
    }

    @Override
    protected void _delete() {
        shaft.delete();
    }
}
