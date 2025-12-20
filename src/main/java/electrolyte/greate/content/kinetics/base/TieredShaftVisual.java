package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.foundation.client.models.GreateModelUtils;

public class TieredShaftVisual<T extends KineticBlockEntity> extends SingleAxisRotatingVisual<T> {
    public TieredShaftVisual(VisualizationContext context, T blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.partial(GreateModelUtils.getPartialModel(blockEntity.getBlockState().getBlock(), "/shaft")));
    }
}
