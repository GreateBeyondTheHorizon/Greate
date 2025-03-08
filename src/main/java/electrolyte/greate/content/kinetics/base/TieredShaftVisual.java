package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;

import static electrolyte.greate.registry.GreatePartialModels.SHAFT_MODELS;

public class TieredShaftVisual<T extends KineticBlockEntity> extends SingleAxisRotatingVisual<T> {
    public TieredShaftVisual(VisualizationContext context, T blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(SHAFT_MODELS[((ITieredBlock) blockEntity.getBlockState().getBlock()).getTier()]));
    }
}
