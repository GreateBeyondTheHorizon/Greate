package electrolyte.greate.content.kinetics.simpleRelays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingVisual;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;

import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.LARGE_COGWHEEL_SHAFTLESS_MODELS;

public class TieredBracketedKineticBlockEntityVisual extends TieredSingleRotatingVisual<TieredBracketedKineticBlockEntity> {

    protected RotatingInstance additionalShaft;
    public TieredBracketedKineticBlockEntityVisual(VisualizationContext context, TieredBracketedKineticBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        if(ICogWheel.isLargeCog(blockEntity.getBlockState())) {
            int tier = ((TieredCogwheelBlock) blockEntity.getBlockState().getBlock()).getTier();
            float speed = blockEntity.getSpeed();
            Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
            BlockPos pos = blockEntity.getBlockPos();
            float offset = BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos);
            var model = Models.partial(LARGE_COGWHEEL_SHAFTLESS_MODELS[tier], axis, TieredBracketedKineticBlockEntityVisual::rotateToAxis);
            Instancer<RotatingInstance> half = instancerProvider().instancer(AllInstanceTypes.ROTATING, model);

            additionalShaft = setup(half.createInstance(), speed);
            additionalShaft.setRotationOffset(offset).setChanged();
        }
    }

    @Override
    protected Model getModel() {
        if(!ICogWheel.isLargeCog(blockEntity.getBlockState())) return super.getModel();
        Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        return Models.partial(LARGE_COGWHEEL_SHAFTLESS_MODELS[blockEntity.getTier()], axis, TieredBracketedKineticBlockEntityVisual::rotateToAxis);
    }

    private static void rotateToAxis(Axis axis, PoseStack poseStack) {
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        TransformStack.of(poseStack)
                .center()
                .rotateToFace(facing)
                .rotate(com.mojang.math.Axis.XN.rotationDegrees(-90))
                .uncenter();
    }

    @Override
    public void update(float partialTick) {
        super.update(partialTick);
        if(additionalShaft != null) {
            updateRotation(additionalShaft);
            additionalShaft.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos)).setChanged();
        }
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        if(additionalShaft != null) {
            relight(additionalShaft);
        }
    }

    @Override
    public void _delete() {
        super._delete();
        if(additionalShaft != null) {
            additionalShaft.delete();
        }
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        if(additionalShaft != null) {
            consumer.accept(additionalShaft);
        }
    }
}
