package electrolyte.greate.content.fluids.pump;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.registry.Pumps;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TieredPumpBlock extends PumpBlock implements ITieredBlock, ITieredPartialModel {

	private TIER tier;
	private PartialModel model;
	private float pressure;

	public TieredPumpBlock(Properties properties, PartialModel model) {
		super(properties);
		this.model = model;
		Pumps.PUMPS.add(this);
	}

	@Override
	public BlockEntityType<? extends TieredPumpBlockEntity> getBlockEntityType() {
		return ModBlockEntityTypes.TIERED_PUMP.get();
	}

	@Override
	public TIER getTier() {
		return tier;
	}

	@Override
	public void setTier(TIER tier) {
		this.tier = tier;
	}

	@Override
	public PartialModel getPartialModel() {
		return model;
	}
}
