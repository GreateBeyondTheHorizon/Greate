package electrolyte.greate.content.kinetics.simpleRelays.encased;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.content.kinetics.simpleRelays.TieredBracketedKineticBlockEntityRenderer;
import electrolyte.greate.content.kinetics.simpleRelays.TieredSimpleKineticBlockEntity;
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
        PartialModel shaftHalf = ((TieredEncasedCogwheelBlock) be.getBlockState().getBlock()).getPartialModel();
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (Backend.canUseInstancing(be.getLevel())) return;

        BlockState blockState = be.getBlockState();
        Block block = blockState.getBlock();
        if (!(block instanceof IRotate rotate)) return;

        Axis axis = getRotationAxisOf(be);
        BlockPos pos = be.getBlockPos();
        float angle = large ? TieredBracketedKineticBlockEntityRenderer.getAngleForLargeCogShaft(be, axis) : getAngleForTe(be, pos, axis);

        for (Direction d : Iterate.directionsInAxis(getRotationAxisOf(be))) {
            if (!rotate.hasShaftTowards(be.getLevel(), be.getBlockPos(), blockState, d))
                continue;
            SuperByteBuffer shaft = CachedBufferer.partialFacing(shaftHalf, be.getBlockState(), d);
            kineticRotationTransform(shaft, be, axis, angle, light);
            shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
        }
    }

    @Override
    protected SuperByteBuffer getRotatedModel(TieredSimpleKineticBlockEntity be, BlockState state) {
        PartialModel shaftlessCogwheel = ((TieredEncasedCogwheelBlock) be.getBlockState().getBlock()).getCogwheelModel();
        return CachedBufferer.partialFacingVertical(shaftlessCogwheel, state, Direction.fromAxisAndDirection(state.getValue(EncasedCogwheelBlock.AXIS), AxisDirection.POSITIVE));
    }
}
