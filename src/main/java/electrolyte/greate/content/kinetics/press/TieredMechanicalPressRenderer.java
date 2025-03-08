package electrolyte.greate.content.kinetics.press;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_PRESS_HEAD_MODELS;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TieredMechanicalPressRenderer extends KineticBlockEntityRenderer<TieredMechanicalPressBlockEntity> {
    public TieredMechanicalPressRenderer(Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(TieredMechanicalPressBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    protected void renderSafe(TieredMechanicalPressBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if(VisualizationManager.supportsVisualization(be.getLevel())) return;
        BlockState blockState = be.getBlockState();
        PressingBehaviour pressingBehaviour = be.getPressingBehaviour();
        float headOffset = pressingBehaviour.getRenderedHeadOffset(partialTicks) * pressingBehaviour.mode.headOffset;
        int tier = ((TieredMechanicalPressBlock) blockState.getBlock()).getTier();
        SuperByteBuffer headRender = CachedBuffers.partialFacing(MECHANICAL_PRESS_HEAD_MODELS[tier], blockState, blockState.getValue(HORIZONTAL_FACING));
        headRender.translate(0, -headOffset, 0).light(light).renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(TieredMechanicalPressBlockEntity be) {
        return ((TieredMechanicalPressBlock) be.getBlockState().getBlock()).getShaft().defaultBlockState().setValue(BlockStateProperties.AXIS, getRotationAxisOf(be));
    }
}
