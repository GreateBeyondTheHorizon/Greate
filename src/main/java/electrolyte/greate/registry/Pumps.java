package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class Pumps {

	public static BlockEntry<TieredPumpBlock>[] MECHANICAL_PUMPS = new BlockEntry[10];
	public static BlockEntry<TieredPumpBlock>
			ANDESITE_MECHANICAL_PUMP,
			STEEL_MECHANICAL_PUMP,
			ALUMINIUM_MECHANICAL_PUMP,
			STAINLESS_STEEL_MECHANICAL_PUMP,
			TITANIUM_MECHANICAL_PUMP,
			TUNGSTENSTEEL_MECHANICAL_PUMP,
			PALLADIUM_MECHANICAL_PUMP,
			NAQUADAH_MECHANICAL_PUMP,
			DARMSTADTIUM_MECHANICAL_PUMP,
			NEUTRONIUM_MECHANICAL_PUMP;

	public static void register() {
		REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

		MECHANICAL_PUMPS[ULV] = ANDESITE_MECHANICAL_PUMP = pump(ULV, GreatePartialModels.ANDESITE_PUMP_COG, 0.5);
		MECHANICAL_PUMPS[LV] = STEEL_MECHANICAL_PUMP = pump(LV, GreatePartialModels.STEEL_PUMP_COG, 1.0);
		MECHANICAL_PUMPS[MV] = ALUMINIUM_MECHANICAL_PUMP = pump(MV, GreatePartialModels.ALUMINIUM_PUMP_COG, 1.5);
		MECHANICAL_PUMPS[HV] = STAINLESS_STEEL_MECHANICAL_PUMP = pump(HV, GreatePartialModels.STAINLESS_STEEL_PUMP_COG, 2.0);
		MECHANICAL_PUMPS[EV] = TITANIUM_MECHANICAL_PUMP = pump(EV, GreatePartialModels.TITANIUM_PUMP_COG, 2.5);
		MECHANICAL_PUMPS[IV] = TUNGSTENSTEEL_MECHANICAL_PUMP = pump(IV, GreatePartialModels.TUNGSTENSTEEL_PUMP_COG, 3.0);
		MECHANICAL_PUMPS[LuV] = PALLADIUM_MECHANICAL_PUMP = pump(LuV, GreatePartialModels.PALLADIUM_PUMP_COG, 3.5);
		MECHANICAL_PUMPS[ZPM] = NAQUADAH_MECHANICAL_PUMP = pump(ZPM, GreatePartialModels.NAQUADAH_PUMP_COG, 4.0);
		MECHANICAL_PUMPS[UV] = DARMSTADTIUM_MECHANICAL_PUMP = pump(UV, GreatePartialModels.DARMSTADTIUM_PUMP_COG, 4.5);
		MECHANICAL_PUMPS[UHV] = NEUTRONIUM_MECHANICAL_PUMP = pump(UHV, GreatePartialModels.NEUTRONIUM_PUMP_COG, 5.0);
	}

	private static BlockEntry<TieredPumpBlock> pump(int tier, PartialModel mechanicalPumpCogModel, double pumpImpact) {
		return pump(tier, TM[tier], mechanicalPumpCogModel, pumpImpact);
	}

	public static BlockEntry<TieredPumpBlock> pump(int tier, Material material, PartialModel mechanicalPumpCogModel, double pumpImpact) {
		return REGISTRATE
				.block(material.getName() + "_mechanical_pump", p -> new TieredPumpBlock(p, mechanicalPumpCogModel))
				.initialProperties(SharedProperties::copperMetal)
				.properties(p -> p.mapColor(MapColor.STONE))
				.transform(pickaxeOnly())
				.transform(GreateBuilderTransformers.tieredMechanicalPump())
				.transform(BlockStressDefaults.setImpact(pumpImpact))
				.transform(TieredBlockMaterials.setMaterialForBlock(material))
				.onRegister(c -> c.setTier(tier))
				.register();
	}
}
