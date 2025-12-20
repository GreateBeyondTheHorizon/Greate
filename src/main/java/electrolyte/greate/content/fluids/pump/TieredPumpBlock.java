package electrolyte.greate.content.fluids.pump;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static electrolyte.greate.GreateValues.TM;

public class TieredPumpBlock extends PumpBlock implements ITieredBlock {

	private int tier;
	private float pressure;

	public TieredPumpBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntityType<? extends TieredPumpBlockEntity> getBlockEntityType() {
		return ModBlockEntityTypes.TIERED_PUMP.get();
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public void setTier(int tier) {
		this.tier = tier;
	}

	@Override
	public Material getMaterial() {
		return TM[tier];
	}
}
