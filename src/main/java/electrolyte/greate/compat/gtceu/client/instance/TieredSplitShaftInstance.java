package electrolyte.greate.compat.gtceu.client.instance;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.AbstractInstance;
import dev.engine_room.flywheel.lib.instance.FlatLit;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.compat.gtceu.common.blockentity.TieredKineticMachineBlockEntity;
import electrolyte.greate.compat.gtceu.common.machine.kinetic.IKineticMachine;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class TieredSplitShaftInstance extends KineticBlockEntityVisual<TieredKineticMachineBlockEntity> {
    protected final EnumMap<Direction, RotatingInstance> keys = new EnumMap<>(Direction.class);
    protected Direction sourceFacing;

    public TieredSplitShaftInstance(VisualizationContext context, TieredKineticMachineBlockEntity be, float partialTick) {
        super(context, be, partialTick);

        updateSourceFacing();
        Instancer<RotatingInstance> half = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(SHAFT_HALF_MODELS[be.getDefinition().getTier()]));

        for (Direction dir : Iterate.directions) {
            final Axis axis = dir.getAxis();

            RotatingInstance instance = half.createInstance();
            instance.setup(be, axis, getSpeed(dir))
                    .setPosition(getVisualPosition())
                    .rotateToFace(Direction.SOUTH, dir)
                    .setChanged();
            keys.put(dir, instance);
        }
    }

    private float getSpeed(Direction dir) {
        return blockEntity.getSpeed() * (blockEntity.getMetaMachine() instanceof IKineticMachine kineticMachine ?
                kineticMachine.getRotationSpeedModifier(dir) : 1);
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
            Direction dir = key.getKey();
            Axis axis = dir.getAxis();
            key.getValue().setup(blockEntity, axis, getSpeed(dir)).setChanged();
        }
    }

    @Override
    public void updateLight(float partialTick) {
        relight(keys.values().toArray(FlatLit[]::new));
    }

    @Override
    public void _delete() {
        keys.values().forEach(AbstractInstance::delete);
        keys.clear();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        keys.values().forEach(consumer);
    }
}
