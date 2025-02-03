package electrolyte.greate.content.kinetics.belt;

import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltInstance;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import com.simibubi.create.foundation.utility.Iterate;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.AbstractInstance;
import dev.engine_room.flywheel.lib.instance.FlatLit;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import electrolyte.greate.content.kinetics.base.TieredKineticBlockEntityVisual;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LightLayer;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TieredBeltVisual extends TieredKineticBlockEntityVisual<TieredBeltBlockEntity> implements IBeltRenderHelper {

    boolean upward;
    boolean diagonal;
    boolean sideways;
    boolean vertical;
    boolean alongX;
    boolean alongZ;
    BeltSlope beltSlope;
    Direction facing;
    protected ArrayList<BeltInstance> keys;
    protected ArrayList<BeltInstance> overlayKeys;
    protected RotatingInstance pulleyKey;

    public TieredBeltVisual(VisualizationContext context, TieredBeltBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        if(!(blockState.getBlock() instanceof TieredBeltBlock)) return;

        keys = new ArrayList<>(2);
        overlayKeys = new ArrayList<>(2);
        beltSlope = blockState.getValue(BeltBlock.SLOPE);
        facing = blockState.getValue(BeltBlock.HORIZONTAL_FACING);
        upward = beltSlope == BeltSlope.UPWARD;
        diagonal = beltSlope.isDiagonal();
        sideways = beltSlope == BeltSlope.SIDEWAYS;
        vertical = beltSlope == BeltSlope.VERTICAL;
        alongX = facing.getAxis() == Axis.X;
        alongZ = facing.getAxis() == Axis.Z;
        BeltPart part = blockState.getValue(BeltBlock.PART);
        boolean start = part == BeltPart.START;
        boolean end = part == BeltPart.END;
        DyeColor color = blockEntity.color.orElse(null);

        for(boolean bottom : Iterate.trueAndFalse) {
            PartialModel beltPartial = TieredBeltRenderer.getBeltPartial(((TieredBeltBlock) blockState.getBlock()), diagonal, start, end, bottom);
            PartialModel overlayPartial = TieredBeltRenderer.getOverlayPartial(diagonal, start, end, bottom);
            SpriteShiftEntry spriteShift = TieredBeltRenderer.getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), diagonal, bottom);
            SpriteShiftEntry overlayShift = TieredBeltRenderer.getDyeOverlayEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal);
            Instancer<BeltInstance> beltModel = instancerProvider().instancer(AllInstanceTypes.BELT, Models.partial(beltPartial)); //todo:render types
            Instancer<BeltInstance> overlayModel = instancerProvider().instancer(AllInstanceTypes.BELT, Models.partial(overlayPartial));
            keys.add(setup(beltModel.createInstance(), bottom, spriteShift));
            overlayKeys.add(setup(overlayModel.createInstance(), bottom, overlayShift));
            if(diagonal) break;
        }

        if(blockEntity.hasPulley()) {
            Instancer<RotatingInstance> pulleyModel = getPulleyModel();
            pulleyKey = setup(pulleyModel.createInstance());
        }
    }

    @Override
    public void update(float partialTick) {
        DyeColor color = blockEntity.color.orElse(null);
        boolean bottom = true;
        for(BeltInstance key : keys) {
            SpriteShiftEntry spriteShiftEntry = TieredBeltRenderer.getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), diagonal, bottom);
            key.setScrollTexture(spriteShiftEntry).setColor(blockEntity).setRotationalSpeed(getScrollSpeed()).setChanged();
            bottom = false;
        }

        for(BeltInstance key : overlayKeys) {
            SpriteShiftEntry overlayEntry = TieredBeltRenderer.getDyeOverlayEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal);
            key.setScrollTexture(overlayEntry).setColor(blockEntity).setRotationalSpeed(getScrollSpeed()).setChanged();
        }

        if(pulleyKey != null) updateRotation(pulleyKey);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(keys.toArray(FlatLit[]::new));
        relight(overlayKeys.toArray(FlatLit[]::new));
        if(pulleyKey != null) relight(pulleyKey);
    }

    @Override
    protected void _delete() {
        keys.forEach(AbstractInstance::delete);
        keys.clear();
        overlayKeys.forEach(AbstractInstance::delete);
        overlayKeys.clear();
        if(pulleyKey != null) pulleyKey.delete();
        pulleyKey = null;
    }

    private float getScrollSpeed() {
        float speed = blockEntity.getSpeed();
        if(((facing.getAxisDirection() == AxisDirection.NEGATIVE) ^ upward) ^ ((alongX && !diagonal) || (alongZ && diagonal))) {
            speed = -speed;
        }

        if(sideways && (facing == Direction.SOUTH || facing == Direction.WEST) || (vertical && facing == Direction.EAST)) {
            speed = -speed;
        }
        return speed;
    }

    private Instancer<RotatingInstance> getPulleyModel() {
        Direction dir = getOrientation();

        Model model = Models.partial(getBeltPulleyModel(blockState), dir.getAxis(), (axis, poseStack) -> {
            TransformStack<PoseTransformStack> msr = TransformStack.of(poseStack);
            msr.center();
            if(axis == Axis.Y) msr.rotateYDegrees(90);
            if(axis == Axis.Y) msr.rotateXDegrees(90);
            msr.rotateXDegrees(90);
            msr.uncenter();
        });

        return instancerProvider().instancer(AllInstanceTypes.ROTATING, model);
    }

    private Direction getOrientation() {
        Direction dir = blockState.getValue(BeltBlock.HORIZONTAL_FACING).getClockWise();
        if(beltSlope == BeltSlope.SIDEWAYS) dir = Direction.UP;
        return dir;
    }

    private BeltInstance setup(BeltInstance key, boolean bottom, SpriteShiftEntry spriteShift) {
        boolean downward = beltSlope == BeltSlope.DOWNWARD;
        float rotX = (!diagonal && beltSlope != BeltSlope.HORIZONTAL ? 90 : 0) + (downward ? 180 : 0) + (sideways ? 90 : 0) + (vertical && alongZ ? 180 : 0);
        float rotY = facing.toYRot() + ((diagonal ^ alongX) && !downward ? 180 : 0) + (sideways && alongZ ? 180 : 0) + (vertical && alongX ? 90 : 0);
        float rotZ = (sideways ? 90 : 0) + (vertical && alongX ? 90 : 0);
        Quaternionf q = new Quaternionf().rotationXYZ(rotX * Mth.DEG_TO_RAD, rotY * Mth.DEG_TO_RAD, rotZ * Mth.DEG_TO_RAD);
        key.setScrollTexture(spriteShift)
                .setScrollMult(diagonal ? 3f / 8f : 0.5f)
                .setRotation(q)
                .setRotationalSpeed(getScrollSpeed())
                .setRotationOffset(bottom ? 0.5f : 0f)
                .setColor(blockEntity)
                .setPosition(getVisualPosition())
                .light(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos))
                .setChanged();
        return key;
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        if(pulleyKey != null) {
            consumer.accept(pulleyKey);
        }
        keys.forEach(consumer);
        overlayKeys.forEach(consumer);
    }
}
