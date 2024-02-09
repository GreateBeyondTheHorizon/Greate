package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.fluids.pump.TieredPumpBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TMS;

public class Pumps {

	static {
		REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
	}

	public static ArrayList<TieredPumpBlock> PUMPS = new ArrayList<>();

	public static final BlockEntry<TieredPumpBlock> ANDESITE_MECHANICAL_PUMP = pump("andesite_mechanical_pump", ULV, TMS[0], GreatePartialModels.ANDESITE_PUMP_COG, 0.5);
	public static final BlockEntry<TieredPumpBlock> STEEL_MECHANICAL_PUMP = pump("steel_mechanical_pump", LV, TMS[1], GreatePartialModels.STEEL_PUMP_COG, 1.0);
	public static final BlockEntry<TieredPumpBlock> ALUMINIUM_MECHANICAL_PUMP = pump("aluminium_mechanical_pump", MV, TMS[2], GreatePartialModels.ALUMINIUM_PUMP_COG, 1.5);
	public static final BlockEntry<TieredPumpBlock> STAINLESS_STEEL_MECHANICAL_PUMP = pump("stainless_steel_mechanical_pump", HV, TMS[3], GreatePartialModels.STAINLESS_STEEL_PUMP_COG, 2.0);
	public static final BlockEntry<TieredPumpBlock> TITANIUM_MECHANICAL_PUMP = pump("titanium_mechanical_pump", EV, TMS[4], GreatePartialModels.TITANIUM_PUMP_COG, 2.5);
	public static final BlockEntry<TieredPumpBlock> TUNGSTENSTEEL_MECHANICAL_PUMP = pump("tungstensteel_mechanical_pump", IV, TMS[5], GreatePartialModels.TUNGSTENSTEEL_PUMP_COG, 3.0);
	public static final BlockEntry<TieredPumpBlock> PALLADIUM_MECHANICAL_PUMP = pump("palladium_mechanical_pump", LuV, TMS[6], GreatePartialModels.PALLADIUM_PUMP_COG, 3.5);
	public static final BlockEntry<TieredPumpBlock> NAQUADAH_MECHANICAL_PUMP = pump("naquadah_mechanical_pump", ZPM, TMS[7], GreatePartialModels.NAQUADAH_PUMP_COG, 4.0);
	public static final BlockEntry<TieredPumpBlock> DARMSTADTIUM_MECHANICAL_PUMP = pump("darmstadtium_mechanical_pump", UV, TMS[8], GreatePartialModels.DARMSTADTIUM_PUMP_COG, 4.5);
	public static final BlockEntry<TieredPumpBlock> NEUTRONIUM_MECHANICAL_PUMP = pump("neutronium_mechanical_pump", UHV, TMS[9], GreatePartialModels.NEUTRONIUM_PUMP_COG, 5.0);

	public static BlockEntry<TieredPumpBlock> pump(String name, int tier, String materialType, PartialModel mechanicalPumpCogModel, double pumpImpact) {
		return REGISTRATE
				.block(name, p -> new TieredPumpBlock(p, mechanicalPumpCogModel))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(MapColor.STONE))
				.transform(pickaxeOnly())
				.transform(GreateBuilderTransformers.tieredMechanicalPump())
				.transform(BlockStressDefaults.setImpact(pumpImpact))
				.transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
				.onRegister(c -> c.setTier(tier))
				.register();
	}

	public static void register() {}
}
