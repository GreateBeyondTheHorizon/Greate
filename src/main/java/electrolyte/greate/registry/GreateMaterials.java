package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.BeltProperty;
import electrolyte.greate.content.gtceu.material.CogwheelProperty;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;

import java.util.ArrayList;
import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.M;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.api.item.tool.GTToolType.BUZZSAW;
import static com.gregtechceu.gtceu.common.data.GTElements.Ma;
import static com.gregtechceu.gtceu.common.data.GTElements.Sp;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.content.gtceu.material.GreateMaterialFlags.GENERATE_ALLOY;
import static electrolyte.greate.content.gtceu.material.GreateMaterialFlags.GENERATE_WHISK;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;

public class GreateMaterials {

	public static Material AndesiteAlloy;
	public static Material RoseQuartz;
	public static Material ChromaticCompound;
	public static Material RefinedRadiance;
	public static Material ShadowSteel;

	public static void register() {
		AndesiteAlloy = Builder("andesite_alloy")
				.ingot().fluid()
				.appendFlags(STD_METAL, GENERATE_BOLT_SCREW, GENERATE_ROTOR)
				.color(0xDADBCA).secondaryColor(0xABC7B5).iconSet(DULL)
				.toolStats(ToolProperty.Builder.of(1, 1, 64, 0, BUZZSAW).build())
				.components(Andesite, 1, WroughtIron, 1)
				.buildAndRegister();
		RoseQuartz = Builder("rose_quartz")
				.gem()
				.color(0xF44471).secondaryColor(0xC63163).iconSet(QUARTZ)
				.flags(NO_SMELTING, CRYSTALLIZABLE, DISABLE_DECOMPOSITION)
				.components(NetherQuartz, 1, Redstone, 8)
				.buildAndRegister();
		ChromaticCompound = Builder("chromatic_compound")
				.ingot().fluid()
				.color(0x744B71).iconSet(DULL)
				.components(Glowstone, 3, Obsidian, 3, RoseQuartz, 1)
				.buildAndRegister();
		RefinedRadiance = Builder("refined_radiance")
				.ingot().fluid()
				.color(0xffffff).secondaryColor(0xffffff).iconSet(METALLIC)
				.appendFlags(EXT2_METAL)
				.buildAndRegister()
				.setFormula(ChromaticCompound.getChemicalFormula() + Ma.symbol());
		ShadowSteel = Builder("shadow_steel")
				.ingot().fluid()
				.color(0x35333c).iconSet(METALLIC)
				.appendFlags(EXT2_METAL)
				.buildAndRegister()
				.setFormula(ChromaticCompound.getChemicalFormula() + Sp.symbol());

		WroughtIron.addFlags(GENERATE_ROTOR);

		AndesiteAlloy.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(0, 8));
		Steel.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(1, 32));
		Aluminium.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(2, 128));
		StainlessSteel.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(3, 512));
		Titanium.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(4, 2048));
		TungstenSteel.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(5, 8192));
		RhodiumPlatedPalladium.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(6, 32768));
		NaquadahAlloy.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(7, 131072));
		Darmstadtium.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(8, 524288));
		Neutronium.setProperty(GreatePropertyKeys.KINETIC, new KineticProperty(9, 2097152));

		AndesiteAlloy.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(Wood));
		Steel.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(AndesiteAlloy));
		Aluminium.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(Steel));
		StainlessSteel.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(Aluminium));
		Titanium.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(StainlessSteel));
		TungstenSteel.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(Titanium));
		RhodiumPlatedPalladium.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(TungstenSteel));
		NaquadahAlloy.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(RhodiumPlatedPalladium));
		Darmstadtium.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(NaquadahAlloy));
		Neutronium.setProperty(GreatePropertyKeys.COGWHEEL, new CogwheelProperty(Darmstadtium));

		AndesiteAlloy.addFlags(GENERATE_WHISK, DISABLE_DECOMPOSITION);
		WroughtIron.addFlags(GENERATE_ALLOY);
		Steel.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		Aluminium.addFlags(GENERATE_ROTOR, GENERATE_WHISK, GENERATE_ALLOY);
		StainlessSteel.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		Titanium.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		TungstenSteel.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		RhodiumPlatedPalladium.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		NaquadahAlloy.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		Darmstadtium.addFlags(GENERATE_WHISK, GENERATE_ALLOY);
		Neutronium.addFlags(GENERATE_ROTOR, GENERATE_WHISK, GENERATE_ALLOY);

		Darmstadtium.setProperty(PropertyKey.TOOL, ToolProperty.Builder.of(50.0F, 15.0F, 5120, 5, BUZZSAW).build());
		RhodiumPlatedPalladium.setProperty(PropertyKey.TOOL, ToolProperty.Builder.of(35.0F, 10.0F, 2560, 4, BUZZSAW).build());

		Rubber.setProperty(GreatePropertyKeys.BELT, new BeltProperty(new ArrayList<>(List.of(AndesiteAlloy, Steel)), 20));
		SiliconeRubber.setProperty(GreatePropertyKeys.BELT, new BeltProperty(new ArrayList<>(List.of(Aluminium, StainlessSteel)), 20));
		Polyethylene.setProperty(GreatePropertyKeys.BELT, new BeltProperty(new ArrayList<>(List.of(Titanium, TungstenSteel)), 20));
		Polytetrafluoroethylene.setProperty(GreatePropertyKeys.BELT, new BeltProperty(new ArrayList<>(List.of(RhodiumPlatedPalladium, NaquadahAlloy)), 20));
		Polybenzimidazole.setProperty(GreatePropertyKeys.BELT, new BeltProperty(new ArrayList<>(List.of(Darmstadtium, Neutronium)), 20));

		alloy.addSecondaryMaterial(new MaterialStack(Andesite, M));
		alloy.setIgnored(WroughtIron, () -> AllItems.ANDESITE_ALLOY);
		block.setIgnored(AndesiteAlloy, () -> AllBlocks.ANDESITE_ALLOY_BLOCK);
		ingot.setIgnored(AndesiteAlloy, () -> AllItems.ANDESITE_ALLOY);
		gem.setIgnored(RoseQuartz, () -> AllItems.ROSE_QUARTZ);
	}

	public static Material.Builder Builder(String id) {
		return new Material.Builder(Greate.id(id));
	}
}
