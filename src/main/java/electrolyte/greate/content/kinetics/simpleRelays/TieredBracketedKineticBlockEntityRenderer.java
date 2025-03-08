package electrolyte.greate.content.kinetics.simpleRelays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;

import static com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer.getAngleForLargeCogShaft;
import static electrolyte.greate.registry.GreatePartialModels.COGWHEEL_SHAFT_MODELS;
import static electrolyte.greate.registry.GreatePartialModels.LARGE_COGWHEEL_SHAFTLESS_MODELS;

public class TieredBracketedKineticBlockEntityRenderer extends KineticBlockEntityRenderer<TieredBracketedKineticBlockEntity> {
    public TieredBracketedKineticBlockEntityRenderer(Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(TieredBracketedKineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        if (!(be.getBlockState().getBlock() instanceof TieredCogwheelBlock tcb && tcb.isLargeCog())) {
            super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
            return;
        }

        int tier = ((TieredCogwheelBlock) be.getBlockState().getBlock()).getTier();
        Axis axis = getRotationAxisOf(be);
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        renderRotatingBuffer(be,
                CachedBuffers.partialFacingVertical(LARGE_COGWHEEL_SHAFTLESS_MODELS[tier], be.getBlockState(), facing),
                ms, buffer.getBuffer(RenderType.solid()), light);

        float angle = getAngleForLargeCogShaft(be, axis);
        SuperByteBuffer shaft =
                CachedBuffers.partialFacingVertical(COGWHEEL_SHAFT_MODELS[tier], be.getBlockState(), facing);
        kineticRotationTransform(shaft, be, axis, angle, light);
        shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}
