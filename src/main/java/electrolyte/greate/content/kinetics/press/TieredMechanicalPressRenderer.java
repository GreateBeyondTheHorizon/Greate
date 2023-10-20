package electrolyte.greate.content.kinetics.press;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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
        if(Backend.canUseInstancing(be.getLevel())) return;
        BlockState blockState = be.getBlockState();
        PressingBehaviour pressingBehaviour = be.getPressingBehaviour();
        float headOffset = pressingBehaviour.getRenderedHeadOffset(partialTicks) * pressingBehaviour.mode.headOffset;
        SuperByteBuffer headRender = CachedBufferer.partialFacing(((TieredMechanicalPressBlock) be.getBlockState().getBlock()).getPartialModel(), blockState, blockState.getValue(HORIZONTAL_FACING));
        headRender.translate(0, -headOffset, 0).light(light).renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(TieredMechanicalPressBlockEntity be) {
        return ((TieredMechanicalPressBlock) be.getBlockState().getBlock()).getShaft().defaultBlockState().setValue(BlockStateProperties.AXIS, getRotationAxisOf(be));
    }
}
