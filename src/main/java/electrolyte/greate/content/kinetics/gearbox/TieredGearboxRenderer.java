package electrolyte.greate.content.kinetics.gearbox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class TieredGearboxRenderer extends KineticBlockEntityRenderer<TieredGearboxBlockEntity> {
    public TieredGearboxRenderer(Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(TieredGearboxBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if(VisualizationManager.supportsVisualization(be.getLevel())) return;

        final Axis boxAxis = be.getBlockState().getValue(BlockStateProperties.AXIS);
        final BlockPos pos = be.getBlockPos();
        float time = AnimationTickHolder.getRenderTime(be.getLevel());

        for (Direction direction : Iterate.directions) {
            final Axis axis = direction.getAxis();
            if (boxAxis == axis)
                continue;

            int tier = ((TieredGearboxBlock) be.getBlockState().getBlock()).getTier();
            SuperByteBuffer shaft = CachedBuffers.partialFacing(SHAFT_HALF_MODELS[tier], be.getBlockState(), direction);
            float offset = getRotationOffsetForPosition(be, pos, axis);
            float angle = (time * be.getSpeed() * 3f / 10) % 360;

            if (be.getSpeed() != 0 && be.hasSource()) {
                BlockPos source = be.source.subtract(be.getBlockPos());
                Direction sourceFacing = Direction.getNearest(source.getX(), source.getY(), source.getZ());
                if (sourceFacing.getAxis() == direction.getAxis())
                    angle *= sourceFacing == direction ? 1 : -1;
                else if (sourceFacing.getAxisDirection() == direction.getAxisDirection())
                    angle *= -1;
            }

            angle += offset;
            angle = angle / 180f * (float) Math.PI;

            kineticRotationTransform(shaft, be, axis, angle, light);
            shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
        }
    }
}
