package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visual.BlockEntityVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction.Axis;

import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.*;

public class TieredBracketedKineticBlockEntityVisual {
    public static BlockEntityVisual<TieredBracketedKineticBlockEntity> create(VisualizationContext context, TieredBracketedKineticBlockEntity be, float partialTick) {
        if(ICogWheel.isLargeCog(be.getBlockState())) {
            return new TieredLargeCogVisual(context, be, partialTick);
        } else {
            Model model;
            if(ICogWheel.isSmallCog(be.getBlockState())) {
                model = Models.partial(COGWHEEL_MODELS[be.getTier()]);
            } else {
                model = Models.partial(SHAFT_MODELS[be.getTier()]);
            }
            return new SingleAxisRotatingVisual<>(context, be, partialTick, model);
        }
    }

    public static class TieredLargeCogVisual extends SingleAxisRotatingVisual<TieredBracketedKineticBlockEntity> {

        protected RotatingInstance additionalShaft;
        public TieredLargeCogVisual(VisualizationContext context, TieredBracketedKineticBlockEntity be, float partialTick) {
            super(context, be, partialTick, Models.partial(LARGE_COGWHEEL_SHAFTLESS_MODELS[be.getTier()]));

            Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(be);

            additionalShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(COGWHEEL_SHAFT_MODELS[be.getTier()])).createInstance();
            additionalShaft.rotateToFace(axis)
                    .setup(be)
                    .setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos))
                    .setPosition(getVisualPosition())
                    .setChanged();
        }

        @Override
        public void update(float partialTick) {
            super.update(partialTick);
            additionalShaft.setup(blockEntity)
                    .setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(rotationAxis(), pos))
                    .setChanged();
        }

        @Override
        public void updateLight(float partialTick) {
            super.updateLight(partialTick);
            relight(additionalShaft);
        }

        @Override
        public void _delete() {
            super._delete();
            additionalShaft.delete();
        }

        @Override
        public void collectCrumblingInstances(Consumer<Instance> consumer) {
            super.collectCrumblingInstances(consumer);
            consumer.accept(additionalShaft);
        }
    }
}
