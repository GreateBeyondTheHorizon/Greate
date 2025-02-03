package electrolyte.greate.content.kinetics.millstone;

import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingVisual;

import static electrolyte.greate.registry.GreatePartialModels.MILLSTONE_INNER_MODELS;

public class TieredMillstoneCogVisual extends TieredSingleRotatingVisual<TieredMillstoneBlockEntity> {

    public TieredMillstoneCogVisual(VisualizationContext context, TieredMillstoneBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
    }

    @Override
    protected Model getModel() {
        int tier = ((TieredMillstoneBlock) blockEntity.getBlockState().getBlock()).getTier();
        return Models.partial(MILLSTONE_INNER_MODELS[tier]);
    }
}
