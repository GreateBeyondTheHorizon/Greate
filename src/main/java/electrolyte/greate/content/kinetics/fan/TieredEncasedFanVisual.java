package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static electrolyte.greate.registry.GreatePartialModels.FAN_INNER_MODELS;

public class TieredEncasedFanVisual extends KineticBlockEntityVisual<TieredEncasedFanBlockEntity> {

    protected final RotatingInstance halfShaft;
    protected final RotatingInstance fanInner;
    private final Direction dir;
    private final Direction opposite;

    public TieredEncasedFanVisual(VisualizationContext context, TieredEncasedFanBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        dir = blockState.getValue(EncasedFanBlock.FACING);
        opposite = dir.getOpposite();
        TieredEncasedFanBlock encasedFanBlock = (TieredEncasedFanBlock) blockState.getBlock();
        int tier = encasedFanBlock.getTier();
        halfShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(GreateModelUtils.getPartialModel(blockState.getBlock(), "/shaft_half"))).createInstance();
        fanInner = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(FAN_INNER_MODELS[tier])).createInstance();

        halfShaft.setup(blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(Direction.SOUTH, opposite)
                .setChanged();

        fanInner.setup(blockEntity, getFanSpeed())
                .setPosition(getVisualPosition())
                .rotateToFace(Direction.SOUTH, opposite)
                .setChanged();
    }

    private float getFanSpeed() {
        float speed = blockEntity.getSpeed() * 5;
        if(speed > 0) {
            speed = Mth.clamp(speed, 80, 64 * 20);
        }
        if (speed < 0) {
            speed = Mth.clamp(speed, -64 * 20, -80);
        }
        return speed;
    }

    @Override
    public void update(float partialTick) {
        halfShaft.setup(blockEntity).setChanged();
        fanInner.setup(blockEntity).setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(pos.relative(opposite), halfShaft);
        relight(pos.relative(dir), fanInner);
    }

    @Override
    protected void _delete() {
        halfShaft.delete();
        fanInner.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(halfShaft);
        consumer.accept(fanInner);
    }
}
