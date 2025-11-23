package electrolyte.greate.content.kinetics.mixer;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.Direction.Axis;

import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.COGWHEEL_SHAFTLESS_MODELS;
import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_MIXER_HEAD_MODELS;

public class TieredMechanicalMixerVisual extends SingleAxisRotatingVisual<TieredMechanicalMixerBlockEntity> implements SimpleDynamicVisual {

    private final RotatingInstance mixerHead;
    private final OrientedInstance mixerPole;
    private final TieredMechanicalMixerBlockEntity mixer;

    public TieredMechanicalMixerVisual(VisualizationContext context, TieredMechanicalMixerBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(COGWHEEL_SHAFTLESS_MODELS[blockEntity.getTier()]));
        this.mixer = blockEntity;
        mixerHead = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(MECHANICAL_MIXER_HEAD_MODELS[blockEntity.getTier()]))
                .createInstance();
        mixerHead.setRotationAxis(Axis.Y);
        mixerPole = instancerProvider()
                .instancer(InstanceTypes.ORIENTED, Models.partial(AllPartialModels.MECHANICAL_MIXER_POLE))
                .createInstance();

        animate(partialTick);
    }

    @Override
    public void beginFrame(DynamicVisual.Context context) {
        animate(context.partialTick());
    }

    private void animate(float partialTick) {
        float renderedHeadOffset = mixer.getRenderedHeadOffset(partialTick);
        transformPole(renderedHeadOffset);
        transformHead(renderedHeadOffset, partialTick);
    }

    private void transformHead(float renderedHeadOffset, float partialTick) {
        float speed = mixer.getRenderedHeadRotationSpeed(partialTick);

        mixerHead.setPosition(getVisualPosition())
                .nudge(0, -renderedHeadOffset, 0)
                .setRotationalSpeed(speed * 2)
                .setChanged();
    }

    private void transformPole(float renderedHeadOffset) {
        mixerPole.position(getVisualPosition())
                .translatePosition(0, -renderedHeadOffset, 0)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);

        relight(pos.below(), mixerHead);
        relight(mixerPole);
    }

    @Override
    protected void _delete() {
        super._delete();
        mixerHead.delete();
        mixerPole.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(mixerHead);
        consumer.accept(mixerPole);
    }
}
