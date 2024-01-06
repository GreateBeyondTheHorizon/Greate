package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.fluids.PipeAttachmentModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.GreateEnums.MATERIAL_TYPE;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.pump.TieredPumpBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class Pumps {

	static {
		REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
	}

	public static ArrayList<TieredPumpBlock> PUMPS = new ArrayList<>();

	public static final BlockEntry<TieredPumpBlock> ANDESITE_MECHANICAL_PUMP = pump("andesite_mechanical_pump", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.ANDESITE_PUMP_COG, 0.5);
	public static final BlockEntry<TieredPumpBlock> STEEL_MECHANICAL_PUMP = pump("steel_mechanical_pump", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.STEEL_PUMP_COG, 1.0);
	public static final BlockEntry<TieredPumpBlock> ALUMINIUM_MECHANICAL_PUMP = pump("aluminium_mechanical_pump", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.ALUMINIUM_PUMP_COG, 1.5);
	public static final BlockEntry<TieredPumpBlock> STAINLESS_STEEL_MECHANICAL_PUMP = pump("stainless_steel_mechanical_pump", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.STAINLESS_STEEL_PUMP_COG, 2.0);
	public static final BlockEntry<TieredPumpBlock> TITANIUM_MECHANICAL_PUMP = pump("titanium_mechanical_pump", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.TITANIUM_PUMP_COG, 2.5);
	public static final BlockEntry<TieredPumpBlock> TUNGSTENSTEEL_MECHANICAL_PUMP = pump("tungstensteel_mechanical_pump", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.TUNGSTENSTEEL_PUMP_COG, 3.0);
	public static final BlockEntry<TieredPumpBlock> PALLADIUM_MECHANICAL_PUMP = pump("palladium_mechanical_pump", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.PALLADIUM_PUMP_COG, 3.5);
	public static final BlockEntry<TieredPumpBlock> NAQUADAH_MECHANICAL_PUMP = pump("naquadah_mechanical_pump", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.NAQUADAH_PUMP_COG, 4.0);
	public static final BlockEntry<TieredPumpBlock> DARMSTADTIUM_MECHANICAL_PUMP = pump("darmstadtium_mechanical_pump", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.DARMSTADTIUM_PUMP_COG, 4.5);
	public static final BlockEntry<TieredPumpBlock> NEUTRONIUM_MECHANICAL_PUMP = pump("neutronium_mechanical_pump", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.NEUTRONIUM_PUMP_COG, 5.0);

	public static BlockEntry<TieredPumpBlock> pump(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel mechanicalPumpCogModel, double pumpImpact) {
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
