package electrolyte.greate.content.kinetics.simpleRelays;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
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
        if (Backend.canUseInstancing(be.getLevel())) return;

        if (!(be.getBlockState().getBlock() instanceof TieredCogwheelBlock tcb && tcb.isLargeCog())) {
            super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
            return;
        }

        PartialModel[] models = (((TieredCogwheelBlock) be.getBlockState().getBlock()).getPartialModels());
        Axis axis = getRotationAxisOf(be);
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        renderRotatingBuffer(be,
                CachedBufferer.partialFacingVertical(models[0], be.getBlockState(), facing),
                ms, buffer.getBuffer(RenderType.solid()), light);

        float angle = getAngleForLargeCogShaft(be, axis);
        SuperByteBuffer shaft =
                CachedBufferer.partialFacingVertical(models[1], be.getBlockState(), facing);
        kineticRotationTransform(shaft, be, axis, angle, light);
        shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}
