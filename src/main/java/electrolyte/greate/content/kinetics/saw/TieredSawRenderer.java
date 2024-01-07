package electrolyte.greate.content.kinetics.saw;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class TieredSawRenderer extends SawRenderer {

    private Block shaftBlock;
    private PartialModel halfShaftModel;
    private PartialModel[] sawModels;

    public TieredSawRenderer(Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SawBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        halfShaftModel = ((ITieredPartialModel) be.getBlockState().getBlock()).getPartialModel();
        sawModels = ((TieredSawBlock) be.getBlockState().getBlock()).getSawModels();
        shaftBlock = ((TieredSawBlock) be.getBlockState().getBlock()).getShaft();
        renderBlade(be, ms, bufferSource, light);
        renderItems(be, partialTicks, ms, bufferSource, light, overlay);
        if(be.getBlockState().getValue(SawBlock.FACING) == Direction.UP) {
            renderFluid((TieredSawBlockEntity) be, partialTicks, ms, bufferSource, light);
        }
        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, bufferSource, light, overlay);

        if(Backend.canUseInstancing(be.getLevel())) return;
        renderShaft(be, ms, bufferSource, light, overlay);
    }

    @Override
    protected void renderBlade(SawBlockEntity be, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        BlockState state = be.getBlockState();
        PartialModel sawModel;
        float speed = be.getSpeed();
        boolean rotate = false;
        if(SawBlock.isHorizontal(state)) {
            if(speed > 0) {
                sawModel = sawModels[0];
            } else if(speed < 0) {
                sawModel = sawModels[1];
            } else {
                sawModel = sawModels[2];
            }
        } else {
            if(speed > 0) {
                sawModel = sawModels[3];
            } else if(speed < 0) {
                sawModel = sawModels[4];
            } else {
                sawModel = sawModels[5];
            }

            if(state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE)) rotate = true;
        }

        SuperByteBuffer superByteBuffer = CachedBufferer.partialFacing(sawModel, state);
        if(rotate) {
            superByteBuffer.rotateCentered(Direction.UP, AngleHelper.rad(90));
        }
        superByteBuffer.light(light).renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticBlockEntity be) {
        BlockState state = be.getBlockState();
        if(state.getValue(FACING).getAxis().isHorizontal()) {
            return CachedBufferer.partialFacing(halfShaftModel, state.rotate(be.getLevel(), be.getBlockPos(), Rotation.CLOCKWISE_180));
        }
        return CachedBufferer.block(KineticBlockEntityRenderer.KINETIC_BLOCK, getRenderedBlockState(be));
    }

    @Override
    protected BlockState getRenderedBlockState(KineticBlockEntity be) {
        return shaftBlock.defaultBlockState().setValue(ShaftBlock.AXIS, KineticBlockEntityRenderer.getRotationAxisOf(be));
    }

    public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld, ContraptionMatrices matrices, MultiBufferSource bufferSource) {
        BlockState state = context.state;
        Direction facing = state.getValue(SawBlock.FACING);
        Vec3 facingVec = Vec3.atLowerCornerOf(context.state.getValue(SawBlock.FACING).getNormal());
        facingVec = context.rotation.apply(facingVec);

        Direction closestToFacing = Direction.getNearest(facingVec.x, facingVec.y, facingVec.z);
        boolean horizontal = closestToFacing.getAxis().isHorizontal();
        boolean backwards = VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite());
        boolean moving = context.getAnimationSpeed() != 0;
        boolean shouldAnimate = (context.contraption.stalled && horizontal) || (!context.contraption.stalled && !backwards && moving);

        SuperByteBuffer superBuffer;
        PartialModel[] sawModels = ((TieredSawBlock) context.state.getBlock()).getSawModels();
        if (SawBlock.isHorizontal(state)) {
            if (shouldAnimate)
                superBuffer = CachedBufferer.partial(sawModels[0], state);
            else
                superBuffer = CachedBufferer.partial(sawModels[2], state);
        } else {
            if (shouldAnimate)
                superBuffer = CachedBufferer.partial(sawModels[3], state);
            else
                superBuffer = CachedBufferer.partial(sawModels[5], state);
        }

        superBuffer.transform(matrices.getModel()).centre().rotateY(AngleHelper.horizontalAngle(facing)).rotateX(AngleHelper.verticalAngle(facing));

        if (!SawBlock.isHorizontal(state)) {
            superBuffer.rotateZ(state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE) ? 90 : 0);
        }

        superBuffer.unCentre().light(matrices.getWorld(), ContraptionRenderDispatcher.getContraptionWorldLight(context, renderWorld)).renderInto(matrices.getViewProjection(), bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    private void renderFluid(TieredSawBlockEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        SmartFluidTankBehaviour behaviour = be.getBehaviour(SmartFluidTankBehaviour.INPUT);
        float units = behaviour.getTanks()[0].getTotalUnits(partialTicks);
        if(units < 1) return;

        final float xMin = 7 / 16f;
        final float xMax = 9 / 16f;
        final float yMin = 13 / 16f;
        final float yMax = 13.01f / 16f;
        final float zMin = 2 / 16f;
        final float zMax = 14 / 16f;
        FluidStack renderedFluid = behaviour.getTanks()[0].getRenderedFluid();
        if(be.getBlockState().getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE)) {
            FluidRenderer.renderFluidBox(renderedFluid, xMin, yMin, zMin, xMax, yMax, zMax, bufferSource, poseStack, light, false);
        } else {
            FluidRenderer.renderFluidBox(renderedFluid, zMin, yMin, xMin, zMax, yMax, xMax, bufferSource, poseStack, light, false);
        }
    }
}
