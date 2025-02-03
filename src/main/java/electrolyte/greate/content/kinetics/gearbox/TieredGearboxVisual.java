package electrolyte.greate.content.kinetics.gearbox;

import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import com.simibubi.create.foundation.utility.Iterate;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.AbstractInstance;
import dev.engine_room.flywheel.lib.instance.FlatLit;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.content.kinetics.base.TieredKineticBlockEntityVisual;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class TieredGearboxVisual extends TieredKineticBlockEntityVisual<TieredGearboxBlockEntity> {

    protected final EnumMap<Direction, RotatingInstance> keys;
    protected Direction sourceFacing;

    public TieredGearboxVisual(VisualizationContext context, TieredGearboxBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        int tier = ((TieredGearboxBlock) blockState.getBlock()).getTier();
        keys = new EnumMap<>(Direction.class);
        final Axis boxAxis = blockState.getValue(BlockStateProperties.AXIS);
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        updateSourceFacing();

        for(Direction direction : Iterate.directions) {
            final Axis axis = direction.getAxis();
            if(boxAxis == axis) continue;
            RotatingInstance key = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(SHAFT_HALF_MODELS[tier], direction)).createInstance();
            key.setRotationAxis(Direction.get(AxisDirection.POSITIVE, axis).step())
                    .setRotationalSpeed(getSpeed(direction))
                    .setRotationOffset(getRotationOffset(axis)).setColor(blockEntity)
                    .setPosition(getVisualPosition())
                    .light(blockLight, skyLight)
                    .setChanged();

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
    public void update(float partialTick) {
        updateSourceFacing();
        for(Map.Entry<Direction, RotatingInstance> key : keys.entrySet()) {
            Direction direction = key.getKey();
            Axis axis = direction.getAxis();
            updateRotation(key.getValue(), axis, getSpeed(direction));
        }
    }

    @Override
    public void updateLight(float partialTick) {
        relight(keys.values().toArray(FlatLit[]::new));
    }

    @Override
    protected void _delete() {
        keys.values().forEach(AbstractInstance::delete);
        keys.clear();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        keys.values().forEach(consumer);
    }
}
