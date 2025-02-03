package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import com.simibubi.create.foundation.render.VirtualRenderHelper;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class TieredSingleRotatingVisual<T extends KineticBlockEntity> extends TieredKineticBlockEntityVisual<T> {

    protected RotatingInstance rotatingModel;

    public TieredSingleRotatingVisual(VisualizationContext context, T blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        rotatingModel = instancerProvider().instancer(AllInstanceTypes.ROTATING, getModel()).createInstance();
        setup(rotatingModel);
    }

    @Override
    public void update(float partialTick) {
        updateRotation(rotatingModel);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(rotatingModel);
    }

    @Override
    protected void _delete() {
        rotatingModel.delete();
    }

    protected BlockState getRenderedBlockState() {
        return blockState;
    }

    protected Model getModel() {
        return VirtualRenderHelper.blockModel(getRenderedBlockState());
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(rotatingModel);
    }
}
