package electrolyte.greate.content.kinetics.simpleRelays.encased;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.content.kinetics.simpleRelays.TieredSimpleKineticBlockEntity;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TieredEncasedCogRenderer extends KineticBlockEntityRenderer<TieredSimpleKineticBlockEntity> {

    private boolean large;

    public static TieredEncasedCogRenderer small(Context context) {
        return new TieredEncasedCogRenderer(context, false);
    }

    public static TieredEncasedCogRenderer large(Context context) {
        return new TieredEncasedCogRenderer(context, true);
    }

    public TieredEncasedCogRenderer(Context context, boolean large) {
        super(context);
        this.large = large;
    }

    @Override
    protected void renderSafe(TieredSimpleKineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        int tier = ((TieredEncasedCogwheelBlock) be.getBlockState().getBlock()).getTier();
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        BlockState blockState = be.getBlockState();
        Block block = blockState.getBlock();
        if (!(block instanceof IRotate rotate)) return;

        Axis axis = getRotationAxisOf(be);
        BlockPos pos = be.getBlockPos();
        float angle = large ? BracketedKineticBlockEntityRenderer.getAngleForLargeCogShaft(be, axis) : getAngleForBe(be, pos, axis);

        for (Direction d : Iterate.directionsInAxis(getRotationAxisOf(be))) {
            if (!rotate.hasShaftTowards(be.getLevel(), be.getBlockPos(), blockState, d))
                continue;
            SuperByteBuffer shaft = CachedBuffers.partialFacing(GreateModelUtils.getPartialModel(be.getBlockState().getBlock(), "/shaft_half"), be.getBlockState(), d);
            kineticRotationTransform(shaft, be, axis, angle, light);
            shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
        }
    }

    @Override
    protected SuperByteBuffer getRotatedModel(TieredSimpleKineticBlockEntity be, BlockState state) {
        PartialModel cogModel = large ? GreateModelUtils.getPartialModel(state.getBlock(), "/large_cogwheel_shaftless") : GreateModelUtils.getPartialModel(state.getBlock(), "/cogwheel_shaftless");
        return CachedBuffers.partialFacingVertical(cogModel, state, Direction.fromAxisAndDirection(state.getValue(EncasedCogwheelBlock.AXIS), AxisDirection.POSITIVE));
    }
}
