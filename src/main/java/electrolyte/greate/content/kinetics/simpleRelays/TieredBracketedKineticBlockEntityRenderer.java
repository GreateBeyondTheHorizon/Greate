package electrolyte.greate.content.kinetics.simpleRelays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;

import static com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer.getAngleForLargeCogShaft;

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

        Axis axis = getRotationAxisOf(be);
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        renderRotatingBuffer(be,
                CachedBuffers.partialFacingVertical(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/large_cogwheel_shaftless"), be.getBlockState(), facing),
                ms, buffer.getBuffer(RenderType.solid()), light);

        float angle = getAngleForLargeCogShaft(be, axis);
        SuperByteBuffer shaft =
                CachedBuffers.partialFacingVertical(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/cogwheel_shaft"), be.getBlockState(), facing);
        kineticRotationTransform(shaft, be, axis, angle, light);
        shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}
