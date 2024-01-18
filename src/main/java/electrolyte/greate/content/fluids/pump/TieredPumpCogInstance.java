package electrolyte.greate.content.fluids.pump;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TieredPumpCogInstance extends TieredSingleRotatingInstance<TieredPumpBlockEntity> implements DynamicInstance {

	public TieredPumpCogInstance(MaterialManager materialManager, TieredPumpBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	public void beginFrame() {}

	@Override
	protected Instancer<RotatingData> getModel() {
		BlockState referenceState = blockEntity.getBlockState();
		Direction facing = referenceState.getValue(BlockStateProperties.FACING);
		return getRotatingMaterial().getModel(((ITieredPartialModel) blockState.getBlock()).getPartialModel(), referenceState, facing);
	}
}
