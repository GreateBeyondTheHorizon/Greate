package electrolyte.greate.content.kinetics.saw;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

import static electrolyte.greate.registry.GreatePartialModels.*;
import static electrolyte.greate.registry.Shafts.SHAFTS;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class TieredSawRenderer extends SawRenderer {

    private int tier;

    public TieredSawRenderer(Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SawBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        tier = ((TieredSawBlock) be.getBlockState().getBlock()).getTier();
        renderBlade(be, ms, bufferSource, light);
        renderItems(be, partialTicks, ms, bufferSource, light, overlay);
        if(be.getBlockState().getValue(SawBlock.FACING) == Direction.UP) {
            renderFluid((TieredSawBlockEntity) be, partialTicks, ms, bufferSource, light);
        }
        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, bufferSource, light, overlay);

        if(VisualizationManager.supportsVisualization(be.getLevel())) return;
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
                sawModel = MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE_MODELS[tier];
            } else if(speed < 0) {
                sawModel = MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED_MODELS[tier];
            } else {
                sawModel = MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE_MODELS[tier];
            }
        } else {
            if(speed > 0) {
                sawModel = MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE_MODELS[tier];
            } else if(speed < 0) {
                sawModel = MECHANICAL_SAW_BLADE_VERTICAL_REVERSED_MODELS[tier];
            } else {
                sawModel = MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE_MODELS[tier];
            }

            if(state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE)) rotate = true;
        }

        SuperByteBuffer superByteBuffer = CachedBuffers.partialFacing(sawModel, state);
        if(rotate) {
            superByteBuffer.rotateCentered(AngleHelper.rad(90), Direction.UP);
        }
        superByteBuffer.color(0xFFFFFF)
                .light(light)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticBlockEntity be) {
        BlockState state = be.getBlockState();
        if(state.getValue(FACING).getAxis().isHorizontal()) {
            return CachedBuffers.partialFacing(SHAFT_HALF_MODELS[tier], state.rotate(be.getLevel(), be.getBlockPos(), Rotation.CLOCKWISE_180));
        }
        return CachedBuffers.block(KineticBlockEntityRenderer.KINETIC_BLOCK, getRenderedBlockState(be));
    }

    @Override
    protected BlockState getRenderedBlockState(KineticBlockEntity be) {
        return SHAFTS[tier].getDefaultState().setValue(BlockStateProperties.AXIS, KineticBlockEntityRenderer.getRotationAxisOf(be));
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
        int tier = ((TieredSawBlock) state.getBlock()).getTier();

        SuperByteBuffer buffer = CachedBuffers.partial(SHAFT_HALF_MODELS[tier], state);
        SuperByteBuffer superBuffer;
        if (SawBlock.isHorizontal(state)) {
            if (shouldAnimate)
                superBuffer = CachedBuffers.partial(MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE_MODELS[tier], state);
            else
                superBuffer = CachedBuffers.partial(MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE_MODELS[tier], state);
        } else {
            if (shouldAnimate)
                superBuffer = CachedBuffers.partial(MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE_MODELS[tier], state);
            else
                superBuffer = CachedBuffers.partial(MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE_MODELS[tier], state);
        }

        buffer.transform(matrices.getModel())
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing.getOpposite()))
                .rotateXDegrees(AngleHelper.verticalAngle(facing.getOpposite()));

        superBuffer.transform(matrices.getModel())
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXDegrees(AngleHelper.verticalAngle(facing));

        if (!SawBlock.isHorizontal(state)) {
            buffer.rotateZDegrees(state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE) ? 90 : 0);
            superBuffer.rotateZDegrees(state.getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE) ? 90 : 0);
        }

        if(!VisualizationManager.supportsVisualization(renderWorld)) {
            buffer.uncenter()
                    .light(LevelRenderer.getLightColor(renderWorld, context.localPos))
                    .useLevelLight(context.world, matrices.getWorld())
                    .renderInto(matrices.getViewProjection(), bufferSource.getBuffer(RenderType.solid()));
        }

        superBuffer.uncenter()
                .light(LevelRenderer.getLightColor(renderWorld, context.localPos))
                .useLevelLight(context.world, matrices.getWorld())
                .renderInto(matrices.getViewProjection(), bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    private void renderFluid(TieredSawBlockEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        SmartFluidTankBehaviour behaviour = be.getBehaviour(SmartFluidTankBehaviour.INPUT);
        if(behaviour == null) return;
        float units = behaviour.getTanks()[0].getTotalUnits(partialTicks);
        if(units < 1) return;

        final float xMin = 7 / 16f;
        final float xMax = 9 / 16f;
        final float yMin = 13 / 16f;
        final float yMax = 13.01f / 16f;
        final float zMin = 2 / 16f;
        final float zMax = 14 / 16f;
        FluidStack renderedFluid = behaviour.getTanks()[0].getRenderedFluid();
        if(renderedFluid.isEmpty()) return;
        if(be.getBlockState().getValue(SawBlock.AXIS_ALONG_FIRST_COORDINATE)) {
            FluidRenderer.renderFluidBox(renderedFluid.getFluid(), renderedFluid.getAmount(), xMin, yMin, zMin, xMax, yMax, zMax, bufferSource, poseStack, light, false, false, renderedFluid.getTag());
        } else {
            FluidRenderer.renderFluidBox(renderedFluid.getFluid(), renderedFluid.getAmount(), zMin, yMin, xMin, zMax, yMax, xMax, bufferSource, poseStack, light, false, false, renderedFluid.getTag());
        }
    }
}
