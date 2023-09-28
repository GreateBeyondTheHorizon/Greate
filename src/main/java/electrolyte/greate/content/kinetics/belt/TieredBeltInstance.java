package electrolyte.greate.content.kinetics.belt;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.flwdata.BeltData;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.base.TieredKineticBlockEntityInstance;
import electrolyte.greate.registry.GreatePartialModels;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TieredBeltInstance extends TieredKineticBlockEntityInstance<TieredBeltBlockEntity> {

    boolean upward;
    boolean diagonal;
    boolean sideways;
    boolean vertical;
    boolean alongX;
    boolean alongZ;
    BeltSlope beltSlope;
    Direction facing;
    protected ArrayList<BeltData> keys;
    protected RotatingData pulleyKey;

    public TieredBeltInstance(MaterialManager materialManager, TieredBeltBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        if(!(blockState.getBlock() instanceof TieredBeltBlock)) return;

        keys = new ArrayList<>(2);
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
            SpriteShiftEntry spriteShift = TieredBeltRenderer.getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal, bottom);
            Instancer<BeltData> beltModel = materialManager.defaultSolid().material(AllMaterialSpecs.BELTS).getModel(beltPartial, blockState);
            keys.add(setup(beltModel.createInstance(), bottom, spriteShift));
            if(diagonal) break;
        }

        if(blockEntity.hasPulley()) {
            Instancer<RotatingData> pulleyModel = getPulleyModel();
            pulleyKey = setup(pulleyModel.createInstance());
        }
    }

    @Override
    public void update() {
        DyeColor color = blockEntity.color.orElse(null);
        boolean bottom = true;
        for(BeltData key : keys) {
            SpriteShiftEntry spriteShiftEntry = TieredBeltRenderer.getSpriteShiftEntry((TieredBeltBlock) blockState.getBlock(), color, diagonal, bottom);
            key.setScrollTexture(spriteShiftEntry).setColor(blockEntity).setRotationalSpeed(getScrollSpeed());
            bottom = false;
        }

        if(pulleyKey != null) updateRotation(pulleyKey);
    }

    @Override
    public void updateLight() {
        relight(pos, keys.stream());
        if(pulleyKey != null) relight(pos, pulleyKey);
    }

    @Override
    protected void remove() {
        keys.forEach(InstanceData::delete);
        keys.clear();
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

    private Instancer<RotatingData> getPulleyModel() {
        Direction dir = getOrientation();
        Axis axis = dir.getAxis();
        Supplier<PoseStack> ms = () -> {
            PoseStack modelTransform = new PoseStack();
            TransformStack msr = TransformStack.cast(modelTransform);
            msr.centre();
            if(axis == Axis.X) msr.rotateY(90);
            if(axis == Axis.Y) msr.rotateX(90);
            msr.rotateX(90);
            msr.unCentre();
            return modelTransform;
        };

        String beltMaterial = ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString().substring(Greate.MOD_ID.length() + 1);
        String shaftMaterial;
        if(((TieredBeltBlock) blockState.getBlock()).getShaftType() != null) {
            shaftMaterial = ((TieredBeltBlock) blockState.getBlock()).getShaftType().toString().substring(2, ((TieredBeltBlock) blockState.getBlock()).getShaftType().toString().length() - 5);
        } else {
            shaftMaterial = blockEntity.getShaftType().toString().substring(2, blockEntity.getShaftType().toString().length() - 5);
        }
        PartialModel model = GreatePartialModels.PARTIAL_MODELS.stream().filter(p -> p.getLocation().equals(new ResourceLocation(Greate.MOD_ID, "block/" + beltMaterial + "_" + shaftMaterial + "pulley"))).findFirst().orElse(AllPartialModels.BELT_PULLEY);
        if(model == AllPartialModels.BELT_PULLEY) Greate.LOGGER.error("Unable to find {} pulley model for {}, using default instead.", shaftMaterial, beltMaterial);
        return getRotatingMaterial().getModel(model, blockState, dir, ms);
    }

    private Direction getOrientation() {
        Direction dir = blockState.getValue(BeltBlock.HORIZONTAL_FACING).getClockWise();
        if(beltSlope == BeltSlope.SIDEWAYS) dir = Direction.UP;
        return dir;
    }

    private BeltData setup(BeltData key, boolean bottom, SpriteShiftEntry spriteShift) {
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
                .setPosition(getInstancePosition())
                .setBlockLight(world.getBrightness(LightLayer.BLOCK, pos))
                .setSkyLight(world.getBrightness(LightLayer.SKY, pos));
        return key;
    }
}
