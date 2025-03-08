package electrolyte.greate.content.kinetics.belt;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.processing.burner.ScrollInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import org.joml.Quaternionf;

import java.util.function.Consumer;

import static com.simibubi.create.content.kinetics.belt.BeltVisual.*;

public class TieredBeltVisual extends KineticBlockEntityVisual<TieredBeltBlockEntity> implements IBeltRenderHelper {

    protected final ScrollInstance[] keys;
    protected final ScrollInstance[] overlayKeys;
    protected final RotatingInstance pulleyKey;

    public TieredBeltVisual(VisualizationContext context, TieredBeltBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        BeltPart part = blockState.getValue(BeltBlock.PART);
        boolean start = part == BeltPart.START;
        boolean end = part == BeltPart.END;
        DyeColor color = blockEntity.color.orElse(null);

        boolean diagonal = blockState.getValue(BeltBlock.SLOPE).isDiagonal();
        keys = new ScrollInstance[diagonal ? 1 : 2];
        overlayKeys = new ScrollInstance[diagonal ? 1 : 2];

        for(boolean bottom : Iterate.trueAndFalse) {
            PartialModel beltPartial = TieredBeltRenderer.getBeltPartial(((TieredBeltBlock) blockState.getBlock()), diagonal, start, end, bottom);
            PartialModel overlayPartial = TieredBeltRenderer.getOverlayPartial(diagonal, start, end, bottom);
            SpriteShiftEntry spriteShift = TieredBeltRenderer.getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), diagonal, bottom);
            SpriteShiftEntry overlayShift = TieredBeltRenderer.getDyeOverlayEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal);
            Instancer<ScrollInstance> beltModel = instancerProvider().instancer(AllInstanceTypes.SCROLLING, Models.partial(beltPartial));
            Instancer<ScrollInstance> overlayModel = instancerProvider().instancer(AllInstanceTypes.SCROLLING, Models.partial(overlayPartial));

            keys[bottom ? 0 : 1] = setup(beltModel.createInstance(), bottom, spriteShift);
            overlayKeys[bottom ? 0 : 1] = setup(overlayModel.createInstance(), bottom, overlayShift);

            if(diagonal) break;
        }

        if(blockEntity.hasPulley()) {
            pulleyKey = instancerProvider().instancer(AllInstanceTypes.ROTATING, getPulleyModel()).createInstance();
            pulleyKey.setup(TieredBeltVisual.this.blockEntity).setPosition(getVisualPosition()).setChanged();
        } else {
            pulleyKey = null;
        }
    }

    @Override
    public void update(float partialTick) {
        DyeColor color = blockEntity.color.orElse(null);
        boolean diagonal = blockState.getValue(BeltBlock.SLOPE).isDiagonal();
        boolean bottom = true;
        for(ScrollInstance key : keys) {
            SpriteShiftEntry spriteShiftEntry = TieredBeltRenderer.getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), diagonal, bottom);
            setup(key, bottom, spriteShiftEntry);
            bottom = false;
        }

        for(ScrollInstance key : overlayKeys) {
            SpriteShiftEntry overlayEntry = TieredBeltRenderer.getDyeOverlayEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal);
            setup(key, bottom, overlayEntry);
        }
        if(pulleyKey != null) pulleyKey.setup(blockEntity).setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(keys);
        relight(overlayKeys);
        if(pulleyKey != null) relight(pulleyKey);
    }

    @Override
    protected void _delete() {
        for(ScrollInstance key : keys) key.delete();
        for(ScrollInstance key : overlayKeys) key.delete();
        if(pulleyKey != null) pulleyKey.delete();
    }

    private Model getPulleyModel() {
        Direction dir = getOrientation();

        return Models.partial(getBeltPulleyModel(blockState), dir.getAxis(), (axis, poseStack) -> {
            TransformStack<PoseTransformStack> msr = TransformStack.of(poseStack);
            msr.center();
            if(axis == Axis.X) msr.rotateYDegrees(90);
            if(axis == Axis.Y) msr.rotateXDegrees(90);
            msr.rotateXDegrees(90);
            msr.uncenter();
        });
    }

    private Direction getOrientation() {
        Direction dir = blockState.getValue(BeltBlock.HORIZONTAL_FACING).getClockWise();
        if(blockState.getValue(BeltBlock.SLOPE) == BeltSlope.SIDEWAYS) dir = Direction.UP;
        return dir;
    }

    private ScrollInstance setup(ScrollInstance key, boolean bottom, SpriteShiftEntry spriteShift) {
        BeltSlope beltSlope = blockState.getValue(BeltBlock.SLOPE);
        Direction facing = blockState.getValue(BeltBlock.HORIZONTAL_FACING);
        boolean diagonal = beltSlope.isDiagonal();
        boolean sideways = beltSlope == BeltSlope.SIDEWAYS;
        boolean vertical = beltSlope == BeltSlope.VERTICAL;
        boolean upward = beltSlope == BeltSlope.UPWARD;
        boolean alongX = facing.getAxis() == Direction.Axis.X;
        boolean alongZ = facing.getAxis() == Direction.Axis.Z;
        boolean downward = beltSlope == BeltSlope.DOWNWARD;

        float speed = blockEntity.getSpeed();
        if(((facing.getAxisDirection() == AxisDirection.NEGATIVE) ^ upward) ^ ((alongX && !diagonal) || (alongZ && diagonal))) {
            speed =- speed;
        }
        if(sideways && (facing == Direction.SOUTH || facing == Direction.WEST) || (vertical && facing == Direction.EAST)) {
            speed =- speed;
        }

        float rotX = (!diagonal && beltSlope != BeltSlope.HORIZONTAL ? 90 : 0) + (downward ? 180 : 0) + (sideways ? 90 : 0) + (vertical && alongZ ? 180 : 0);
        float rotY = facing.toYRot() + ((diagonal ^ alongX) && !downward ? 180 : 0) + (sideways && alongZ ? 180 : 0) + (vertical && alongX ? 90 : 0);
        float rotZ = (sideways ? 90 : 0) + (vertical && alongX ? 90 : 0);
        Quaternionf q = new Quaternionf().rotationXYZ(rotX * Mth.DEG_TO_RAD, rotY * Mth.DEG_TO_RAD, rotZ * Mth.DEG_TO_RAD);
        key.setSpriteShift(spriteShift, 1f, (diagonal ? SCROLL_FACTOR_DIAGONAL : SCROLL_FACTOR_OTHERWISE))
                .position(getVisualPosition())
                .rotation(q)
                .speed(0, speed * MAGIC_SCROLL_MULTIPLIER)
                .offset(0, bottom ? SCROLL_OFFSET_BOTTOM : SCROLL_OFFSET_OTHERWISE)
                .colorRgb(RotatingInstance.colorFromBE(blockEntity))
                .setChanged();
        return key;
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        if(pulleyKey != null) {
            consumer.accept(pulleyKey);
        }
        for(ScrollInstance key : keys) consumer.accept(key);
        for(ScrollInstance key : overlayKeys) consumer.accept(key);
    }
}
