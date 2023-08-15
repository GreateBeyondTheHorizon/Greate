package electrolyte.greate.content.kinetics.gearbox;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.content.kinetics.base.TieredKineticBlockEntityInstance;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredHalfShaft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.EnumMap;
import java.util.Map;

public class TieredGearboxInstance extends TieredKineticBlockEntityInstance<TieredGearboxBlockEntity> {

    protected final EnumMap<Direction, RotatingData> keys;
    protected Direction sourceFacing;
    private PartialModel halfShaftModel;

    public TieredGearboxInstance(MaterialManager materialManager, TieredGearboxBlockEntity blockEntity) {
        super(materialManager, blockEntity);
        halfShaftModel = ((ITieredHalfShaft) blockState.getBlock()).getHalfShaft();
        keys = new EnumMap<>(Direction.class);
        final Axis boxAxis = blockState.getValue(BlockStateProperties.AXIS);
        int blockLight = world.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = world.getBrightness(LightLayer.SKY, pos);
        updateSourceFacing();
        Material<RotatingData> rotatingMaterial = getRotatingMaterial();
        for(Direction direction : Iterate.directions) {
            final Axis axis = direction.getAxis();
            if(boxAxis == axis) continue;
            Instancer<RotatingData> shaft = rotatingMaterial.getModel(halfShaftModel, blockState, direction);
            RotatingData key = shaft.createInstance();
            key.setRotationAxis(Direction.get(AxisDirection.POSITIVE, axis).step())
                    .setRotationalSpeed(getSpeed(direction))
                    .setRotationOffset(getRotationOffset(axis)).setColor(blockEntity)
                    .setPosition(getInstancePosition())
                    .setBlockLight(blockLight)
                    .setSkyLight(skyLight);

            keys.put(direction, key);
        }
    }

    private float getSpeed(Direction direction) {
        float speed = blockEntity.getSpeed();
        if(speed != 0 && sourceFacing != null) {
            if(sourceFacing.getAxis() == direction.getAxis()) {
                speed *= sourceFacing == direction ? 1 : -1;
            } else if(sourceFacing.getAxisDirection() == direction.getAxisDirection()) {
                speed *= -1;
            }
        }
        return speed;
    }

    protected void updateSourceFacing() {
        if(blockEntity.hasSource()) {
            BlockPos source = blockEntity.source.subtract(pos);
            sourceFacing = Direction.getNearest(source.getX(), source.getY(), source.getZ());
        } else {
            sourceFacing = null;
        }
    }

    @Override
    public void update() {
        updateSourceFacing();
        for(Map.Entry<Direction, RotatingData> key : keys.entrySet()) {
            Direction direction = key.getKey();
            Axis axis = direction.getAxis();
            updateRotation(key.getValue(), axis, getSpeed(direction));
        }
    }

    @Override
    public void updateLight() {
        relight(pos, keys.values().stream());
    }

    @Override
    protected void remove() {
        keys.values().forEach(InstanceData::delete);
        keys.clear();
    }
}
