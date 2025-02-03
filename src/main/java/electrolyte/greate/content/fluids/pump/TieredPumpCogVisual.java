package electrolyte.greate.content.fluids.pump;

import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingVisual;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_PUMP_COG_MODELS;

public class TieredPumpCogVisual extends TieredSingleRotatingVisual<TieredPumpBlockEntity> implements SimpleDynamicVisual {

	public TieredPumpCogVisual(VisualizationContext context, TieredPumpBlockEntity blockEntity, float partialTick) {
		super(context, blockEntity, partialTick);
	}

	@Override
	protected Model getModel() {
		BlockState referenceState = blockEntity.getBlockState();
		Direction facing = referenceState.getValue(BlockStateProperties.FACING);
		int tier = ((TieredPumpBlock) blockState.getBlock()).getTier();
		return Models.partial(MECHANICAL_PUMP_COG_MODELS[tier], facing);
	}

	@Override
	public void beginFrame(Context ctx) {}
}
