package electrolyte.greate.content.kinetics.mixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_MIXER_HEAD_MODELS;

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
        if(VisualizationManager.supportsVisualization(be.getLevel())) return;
        BlockState blockState = be.getBlockState();
        TieredMechanicalMixerBlock mixerBlock = (TieredMechanicalMixerBlock) blockState.getBlock();
        int tier = mixerBlock.getTier();
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        SuperByteBuffer byteBuffer = CachedBuffers.partial(GreateModelUtils.getPartialModel(blockState.getBlock(), "/cogwheel_shaftless"), blockState);
        standardKineticRotationTransform(byteBuffer, be, light).renderInto(ms, vb);

        float renderedHeadOffset = be.getRenderedHeadOffset(partialTicks);
        float speed = be.getRenderedHeadRotationSpeed(partialTicks);
        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * (float) Math.PI;

        SuperByteBuffer poleRender = CachedBuffers.partial(AllPartialModels.MECHANICAL_MIXER_POLE, blockState);
        poleRender.translate(0, -renderedHeadOffset, 0).light(light).renderInto(ms, vb);

        VertexConsumer vbConsumer = buffer.getBuffer(RenderType.cutoutMipped());
        SuperByteBuffer headRender = CachedBuffers.partial(MECHANICAL_MIXER_HEAD_MODELS[tier], blockState);
        headRender.rotateCentered(angle, Direction.UP).translate(0, -renderedHeadOffset, 0).light(light).renderInto(ms, vbConsumer);
    }
}
