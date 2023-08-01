package electrolyte.greate.client;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import electrolyte.greate.be.AndesiteBracketedKineticBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class AndesiteBracketedKineticBlockEntityRenderer extends KineticBlockEntityRenderer<AndesiteBracketedKineticBlockEntity> {
    public AndesiteBracketedKineticBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(AndesiteBracketedKineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
            if (Backend.canUseInstancing(be.getLevel()))
                return;

            if (!AllBlocks.LARGE_COGWHEEL.has(be.getBlockState())) {
                super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
                return;
            }

            // Large cogs sometimes have to offset their teeth by 11.25 degrees in order to
            // mesh properly

            Direction.Axis axis = getRotationAxisOf(be);
            Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
            renderRotatingBuffer(be,
                    CachedBufferer.partialFacingVertical(AllPartialModels.SHAFTLESS_LARGE_COGWHEEL, be.getBlockState(), facing),
                    ms, buffer.getBuffer(RenderType.solid()), light);

            float angle = getAngleForLargeCogShaft(be, axis);
            SuperByteBuffer shaft =
                    CachedBufferer.partialFacingVertical(AllPartialModels.COGWHEEL_SHAFT, be.getBlockState(), facing);
            kineticRotationTransform(shaft, be, axis, angle, light);
            shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

        public static float getAngleForLargeCogShaft(SimpleKineticBlockEntity
        be, Direction.Axis axis) {
            BlockPos pos = be.getBlockPos();
            float offset = getShaftAngleOffset(axis, pos);
            float time = AnimationTickHolder.getRenderTime(be.getLevel());
            float angle = ((time * be.getSpeed() * 3f / 10 + offset) % 360) / 180 * (float) Math.PI;
            return angle;
        }

        public static float getShaftAngleOffset(Direction.Axis axis, BlockPos pos) {
            float offset = 0;
            double d = (((axis == Direction.Axis.X) ? 0 : pos.getX()) + ((axis == Direction.Axis.Y) ? 0 : pos.getY())
                    + ((axis == Direction.Axis.Z) ? 0 : pos.getZ())) % 2;
            if (d == 0)
                offset = 22.5f;
            return offset;
        }
}
