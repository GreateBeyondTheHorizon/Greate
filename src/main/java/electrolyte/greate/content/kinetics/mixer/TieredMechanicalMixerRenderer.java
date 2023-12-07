package electrolyte.greate.content.kinetics.mixer;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TieredMechanicalMixerRenderer extends KineticBlockEntityRenderer<TieredMechanicalMixerBlockEntity> {
    public TieredMechanicalMixerRenderer(Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(TieredMechanicalMixerBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    protected void renderSafe(TieredMechanicalMixerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if(Backend.canUseInstancing(be.getLevel())) return;
        BlockState blockState = be.getBlockState();
        TieredMechanicalMixerBlock mixerBlock = (TieredMechanicalMixerBlock) blockState.getBlock();
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        SuperByteBuffer byteBuffer = CachedBufferer.partial(mixerBlock.getCogwheelModel(), blockState);
        standardKineticRotationTransform(byteBuffer, be, light).renderInto(ms, vb);

        float renderedHeadOffset = be.getRenderedHeadOffset(partialTicks);
        float speed = be.getRenderedHeadRotationSpeed(partialTicks);
        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        SuperByteBuffer poleRender = CachedBufferer.partial(AllPartialModels.MECHANICAL_MIXER_POLE, blockState);
        poleRender.translate(0, -renderedHeadOffset, 0).light(light).renderInto(ms, vb);

        VertexConsumer vbConsumer = buffer.getBuffer(RenderType.cutoutMipped());
        SuperByteBuffer headRender = CachedBufferer.partial(mixerBlock.getPartialModel(), blockState);
        headRender.rotateCentered(Direction.UP, angle).translate(0, -renderedHeadOffset, 0).light(light).renderInto(ms, vbConsumer);
    }
}
