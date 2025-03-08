package electrolyte.greate.content.kinetics.press;

import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import electrolyte.greate.content.kinetics.base.TieredShaftVisual;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.createmod.catnip.math.AngleHelper;
import org.joml.Quaternionf;

import java.util.function.Consumer;

import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;
import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_PRESS_HEAD_MODELS;

public class TieredMechanicalPressVisual extends TieredShaftVisual<TieredMechanicalPressBlockEntity> implements SimpleDynamicVisual {

    private final OrientedInstance pressHead;

    public TieredMechanicalPressVisual(VisualizationContext context, TieredMechanicalPressBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        int tier = ((ITieredBlock) blockEntity.getBlockState().getBlock()).getTier();
        pressHead = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(MECHANICAL_PRESS_HEAD_MODELS[tier])).createInstance();
        Quaternionf q = Axis.YP.rotationDegrees(AngleHelper.horizontalAngle(blockState.getValue(HORIZONTAL_FACING)));
        pressHead.rotation(q);
        transformModels(partialTick);
    }

    @Override
    public void beginFrame(Context context) {
        transformModels(context.partialTick());
    }

    private void transformModels(float partialTick) {
        float renderedHeadOffset = getRenderedHeadOffset(partialTick);
        pressHead.position(getVisualPosition())
                .translatePosition(0, -renderedHeadOffset, 0)
                .setChanged();
    }

    private float getRenderedHeadOffset(float partialTick) {
        PressingBehaviour pressingBehaviour = blockEntity.getPressingBehaviour();
        return pressingBehaviour.getRenderedHeadOffset(partialTick * pressingBehaviour.mode.headOffset);
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        relight(pressHead);
    }

    @Override
    protected void _delete() {
        super._delete();
        pressHead.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(pressHead);
    }
}
