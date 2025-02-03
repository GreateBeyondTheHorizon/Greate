package electrolyte.greate.content.kinetics.belt;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.ShadowRenderHelper;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.worldWrappers.WrappedWorld;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import electrolyte.greate.registry.GreatePartialModels;
import electrolyte.greate.registry.GreateSpriteShifts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.function.Supplier;

public class TieredBeltRenderer extends SafeBlockEntityRenderer<TieredBeltBlockEntity> implements IBeltRenderHelper {

    public TieredBeltRenderer(Context context) {}

    @Override
    public boolean shouldRenderOffScreen(TieredBeltBlockEntity pBlockEntity) {
        return pBlockEntity.isController();
    }

    @Override
    protected void renderSafe(TieredBeltBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (! VisualizationManager.supportsVisualization(be.getLevel())) {

            BlockState blockState = be.getBlockState();
            if (!(blockState.getBlock() instanceof TieredBeltBlock)) return;

            BeltSlope beltSlope = blockState.getValue(BeltBlock.SLOPE);
            BeltPart part = blockState.getValue(BeltBlock.PART);
            Direction facing = blockState.getValue(BeltBlock.HORIZONTAL_FACING);
            AxisDirection axisDirection = facing.getAxisDirection();

            boolean downward = beltSlope == BeltSlope.DOWNWARD;
            boolean upward = beltSlope == BeltSlope.UPWARD;
            boolean diagonal = downward || upward;
            boolean start = part == BeltPart.START;
            boolean end = part == BeltPart.END;
            boolean sideways = beltSlope == BeltSlope.SIDEWAYS;
            boolean alongX = facing.getAxis() == Direction.Axis.X;

            PoseStack localTransforms = new PoseStack();
            TransformStack<PoseTransformStack> msr = TransformStack.of(localTransforms);
            VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
            float renderTick = AnimationTickHolder.getRenderTime(be.getLevel());

            msr.center()
                    .rotateY(AngleHelper.horizontalAngle(facing) + (upward ? 180 : 0) + (sideways ? 270 : 0))
                    .rotateZ(sideways ? 90 : 0)
                    .rotateX(!diagonal && beltSlope != BeltSlope.HORIZONTAL ? 90 : 0)
                    .uncenter();

            if (downward || beltSlope == BeltSlope.VERTICAL && axisDirection == AxisDirection.POSITIVE) {
                boolean b = start;
                start = end;
                end = b;
            }

            DyeColor color = be.color.orElse(null);

            for (boolean bottom : Iterate.trueAndFalse) {

                PartialModel beltPartial = getBeltPartial((TieredBeltBlock) blockState.getBlock(), diagonal, start, end, bottom);
                PartialModel overlayPartial = getOverlayPartial(diagonal, start, end, bottom);

                SuperByteBuffer beltBuffer = CachedBufferer.partial(beltPartial, blockState).light(light);
                SuperByteBuffer overlayBuffer = CachedBufferer.partial(overlayPartial, blockState).light(light);

                SpriteShiftEntry spriteShift = getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), diagonal, bottom);
                SpriteShiftEntry overlayShift = getDyeOverlayEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal);
                float speed = be.getSpeed();
                if (speed != 0 || be.color.isPresent()) {
                    float time = renderTick * axisDirection.getStep();
                    if (diagonal && (downward ^ alongX) || !sideways && !diagonal && alongX || sideways && axisDirection == AxisDirection.NEGATIVE)
                        speed = -speed;

                    float scrollMult = diagonal ? 3f / 8f : 0.5f;

                    float spriteSize = spriteShift.getTarget().getV1() - spriteShift.getTarget().getV0();

                    double scroll = speed * time / (31.5 * 16) + (bottom ? 0.5 : 0.0);
                    scroll = scroll - Math.floor(scroll);
                    scroll = scroll * spriteSize * scrollMult;

                    beltBuffer.shiftUVScrolling(spriteShift, (float) scroll);
                    overlayBuffer.shiftUVScrolling(overlayShift, (float) scroll);
                    }

                beltBuffer.transform(localTransforms).renderInto(ms, vb);
                overlayBuffer.transform(localTransforms).renderInto(ms, vb);

                if (diagonal) break;
            }

            if (be.hasPulley()) {
                Direction dir = sideways ? Direction.UP : blockState.getValue(BeltBlock.HORIZONTAL_FACING).getClockWise();

                Supplier<PoseStack> matrixStackSupplier = () -> {
                    PoseStack stack = new PoseStack();
                    TransformStack<PoseTransformStack> stacker = TransformStack.of(stack);
                    stacker.center();
                    if (dir.getAxis() == Direction.Axis.X) stacker.rotateYDegrees(90);
                    if (dir.getAxis() == Direction.Axis.Y) stacker.rotateXDegrees(90);
                    stacker.rotateXDegrees(90);
                    stacker.uncenter();
                    return stack;
                };

                SuperByteBuffer superBuffer = CachedBufferer.partialDirectional(getBeltPulleyModel(blockState), blockState, dir, matrixStackSupplier);
                KineticBlockEntityRenderer.standardKineticRotationTransform(superBuffer, be, light).renderInto(ms, vb);
            }
        }

        renderItems(be, partialTicks, ms, buffer, light, overlay);
    }

    public static SpriteShiftEntry getSpriteShiftEntry(TieredBeltBlock block, boolean diagonal, boolean bottom) {
        return diagonal ? GreateSpriteShifts.BELT_SPRITES.get(block).get(2) :
                bottom ? GreateSpriteShifts.BELT_SPRITES.get(block).get(1) : GreateSpriteShifts.BELT_SPRITES.get(block).get(0);
    }

    public static SpriteShiftEntry getDyeOverlayEntry(TieredBeltBlock block, DyeColor color, boolean diagonal) {
        if(color != null) {
            return diagonal ? GreateSpriteShifts.DYED_DIAGONAL_BELTS.get(block).get(color) : GreateSpriteShifts.DYED_BELTS.get(block).get(color);
        } else {
            return GreateSpriteShifts.BELT_SPRITES.get(block).get(3);
        }
    }

    public static PartialModel getBeltPartial(TieredBeltBlock block, boolean diagonal, boolean start, boolean end, boolean bottom) {
        Material beltMaterial = block.getBeltMaterial();
        if (diagonal) {
            if(start) return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(8);
            if(end) return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(10);
            return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(9);
        } else if (bottom) {
            if(start) return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(5);
            if(end) return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(7);
            return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(6);
        } else {
            if(start) return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(2);
            if(end) return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(4);
            return GreatePartialModels.NEW_BELT_MODELS.get(beltMaterial).get(3);
        }
    }

    public static PartialModel getOverlayPartial(boolean diagonal, boolean start, boolean end, boolean bottom) {
        if(diagonal) {
            if(start) return GreatePartialModels.BELT_OVERLAY_DIAGONAL_START;
            if(end) return GreatePartialModels.BELT_OVERLAY_DIAGONAL_END;
            return GreatePartialModels.BELT_OVERLAY_DIAGONAL_MIDDLE;
        } else if (bottom) {
            if(start) return GreatePartialModels.BELT_OVERLAY_START_BOTTOM;
            if(end) return GreatePartialModels.BELT_OVERLAY_END_BOTTOM;
            return GreatePartialModels.BELT_OVERLAY_MIDDLE_BOTTOM;
        } else {
            if(start) return GreatePartialModels.BELT_OVERLAY_START;
            if(end) return GreatePartialModels.BELT_OVERLAY_END;
            return GreatePartialModels.BELT_OVERLAY_MIDDLE;
        }
    }

    protected void renderItems(TieredBeltBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (!be.isController()) return;
        if (be.beltLength == 0) return;

        ms.pushPose();

        Direction beltFacing = be.getBeltFacing();
        Vec3i directionVec = beltFacing.getNormal();
        Vec3 beltStartOffset = Vec3.atLowerCornerOf(directionVec).scale(-.5).add(.5, 15 / 16f, .5);
        ms.translate(beltStartOffset.x, beltStartOffset.y, beltStartOffset.z);
        BeltSlope slope = be.getBlockState().getValue(BeltBlock.SLOPE);
        int verticality = slope == BeltSlope.DOWNWARD ? -1 : slope == BeltSlope.UPWARD ? 1 : 0;
        boolean slopeAlongX = beltFacing.getAxis() == Direction.Axis.X;
        MutableBlockPos mutableBlockPos = new MutableBlockPos();

        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();
        boolean onContraption = be.getLevel() instanceof WrappedWorld;

        for (TransportedItemStack transported : be.getInventory().getTransportedItems()) {
            float offset;
            float sideOffset;
            float verticalMovement;

            if (be.getSpeed() == 0) {
                offset = transported.beltPosition;
                sideOffset = transported.sideOffset;
            } else {
                offset = Mth.lerp(partialTicks, transported.prevBeltPosition, transported.beltPosition);
                sideOffset = Mth.lerp(partialTicks, transported.prevSideOffset, transported.sideOffset);
            }

            if (offset < .5) verticalMovement = 0;
            else verticalMovement = verticality * (Math.min(offset, be.beltLength - .5f) - .5f);
            Vec3 offsetVec = Vec3.atLowerCornerOf(directionVec).scale(offset);
            if (verticalMovement != 0) offsetVec = offsetVec.add(0, verticalMovement, 0);
            boolean onSlope = slope != BeltSlope.HORIZONTAL && Mth.clamp(offset, .5f, be.beltLength - .5f) == offset;
            boolean tiltForward = (slope == BeltSlope.DOWNWARD ^ beltFacing
                    .getAxisDirection() == AxisDirection.POSITIVE) == (beltFacing
                    .getAxis() == Direction.Axis.Z);
            float slopeAngle = onSlope ? tiltForward ? -45 : 45 : 0;

            Vec3 itemPos = beltStartOffset.add(be.getBlockPos().getX(), be.getBlockPos().getY(), be.getBlockPos().getZ()).add(offsetVec);

            if(this.shouldCullItem(itemPos, be.getLevel())) continue;

            ms.pushPose();
            TransformStack.of(ms).nudge(transported.angle);
            ms.translate(offsetVec.x, offsetVec.y, offsetVec.z);

            boolean alongX = beltFacing.getClockWise().getAxis() == Direction.Axis.X;
            if (!alongX) sideOffset *= -1;
            ms.translate(alongX ? sideOffset : 0, 0, alongX ? 0 : sideOffset);

            int stackLight;
            if(onContraption) {
                stackLight = light;
            } else {
                int segment = (int) Math.floor(offset);
                mutableBlockPos.set(be.getBlockPos()).move(directionVec.getX() * segment, verticality * segment, directionVec.getZ() * segment);
                stackLight = LevelRenderer.getLightColor(be.getLevel(), mutableBlockPos);
            }
            boolean renderUpright = BeltHelper.isItemUpright(transported.stack);
            BakedModel bakedModel = itemRenderer.getModel(transported.stack, be.getLevel(), null, 0);
            boolean blockItem = bakedModel.isGui3d();
            int count = Mth.log2(transported.stack.getCount()) / 2;
            if(mc.player.getEyePosition(1.0F).distanceTo(itemPos) < 16) {
                count = Mth.log2(transported.stack.getCount()) / 2;
            }
            Random r = new Random(transported.angle);

            boolean slopeShadowOnly = renderUpright && onSlope;
            float slopeOffset = 1 / 8f;
            if (slopeShadowOnly) ms.pushPose();
            if (!renderUpright || slopeShadowOnly)
                ms.mulPose((slopeAlongX ? Axis.ZP : Axis.XP).rotationDegrees(slopeAngle));
            if (onSlope) ms.translate(0, slopeOffset, 0);
            ms.pushPose();
            ms.translate(0, -1 / 8f + 0.005f, 0);
            ShadowRenderHelper.renderShadow(ms, buffer, .75f, .2f);
            ms.popPose();
            if (slopeShadowOnly) {
                ms.popPose();
                ms.translate(0, slopeOffset, 0);
            }

            if (renderUpright) {
                Entity renderViewEntity = Minecraft.getInstance().cameraEntity;
                if (renderViewEntity != null) {
                    Vec3 positionVec = renderViewEntity.position();
                    Vec3 vectorForOffset = BeltHelper.getVectorForOffset(be, offset);
                    Vec3 diff = vectorForOffset.subtract(positionVec);
                    float yRot = (float) (Mth.atan2(diff.x, diff.z) + Math.PI);
                    ms.mulPose(Axis.YP.rotation(yRot));
                }
                ms.translate(0, 3 / 32d, 1 / 16f);
            }

            for (int i = 0; i <= count; i++) {
                ms.pushPose();

                ms.mulPose(Axis.YP.rotationDegrees(transported.angle));
                if (!blockItem && !renderUpright) {
                    ms.translate(0, -.09375, 0);
                    ms.mulPose(Axis.XP.rotationDegrees(90));
                }

                if (blockItem) {
                    ms.translate(r.nextFloat() * .0625f * i, 0, r.nextFloat() * .0625f * i);
                }

                ms.scale(.5f, .5f, .5f);
                itemRenderer.render(transported.stack, ItemDisplayContext.FIXED, false, ms, buffer, stackLight, overlay, bakedModel);
                ms.popPose();

                if (!renderUpright) {
                    if (!blockItem) ms.mulPose(Axis.YP.rotationDegrees(10));
                    ms.translate(0, blockItem ? 1 / 64d : 1 / 16d, 0);
                } else
                    ms.translate(0, 0, -1 / 16f);

            }

            ms.popPose();
        }
        ms.popPose();
    }
}
