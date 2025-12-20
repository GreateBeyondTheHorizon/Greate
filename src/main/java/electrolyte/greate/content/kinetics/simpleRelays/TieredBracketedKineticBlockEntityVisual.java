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
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.minecraft.core.Direction.Axis;

import java.util.function.Consumer;

public class TieredBracketedKineticBlockEntityVisual {
    public static BlockEntityVisual<TieredBracketedKineticBlockEntity> create(VisualizationContext context, TieredBracketedKineticBlockEntity be, float partialTick) {
        if(ICogWheel.isLargeCog(be.getBlockState())) {
            return new TieredLargeCogVisual(context, be, partialTick);
        } else {
            Model model;
            if(ICogWheel.isSmallCog(be.getBlockState())) {
                model = Models.partial(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/cogwheel"));
            } else {
                model = Models.partial(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/shaft"));
            }
            return new SingleAxisRotatingVisual<>(context, be, partialTick, model);
        }
    }

    public static class TieredLargeCogVisual extends SingleAxisRotatingVisual<TieredBracketedKineticBlockEntity> {

        protected RotatingInstance additionalShaft;
        public TieredLargeCogVisual(VisualizationContext context, TieredBracketedKineticBlockEntity be, float partialTick) {
            super(context, be, partialTick, Models.partial(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/large_cogwheel_shaftless")));

            Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(be);

            additionalShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/cogwheel_shaft"))).createInstance();
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
