package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.BeltProperty;
import electrolyte.greate.content.gtceu.material.CogwheelProperty;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;

import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.M;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
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

		Rubber.setProperty(GreatePropertyKeys.BELT, new BeltProperty(List.of(AndesiteAlloy, Steel)));
		SiliconeRubber.setProperty(GreatePropertyKeys.BELT, new BeltProperty(List.of(Aluminium, StainlessSteel)));
		Polyethylene.setProperty(GreatePropertyKeys.BELT, new BeltProperty(List.of(Titanium, TungstenSteel)));
		Polytetrafluoroethylene.setProperty(GreatePropertyKeys.BELT, new BeltProperty(List.of(RhodiumPlatedPalladium, NaquadahAlloy)));
		Polybenzimidazole.setProperty(GreatePropertyKeys.BELT, new BeltProperty(List.of(Darmstadtium, Neutronium)));

		alloy.addSecondaryMaterial(new MaterialStack(Andesite, M));
		alloy.setIgnored(WroughtIron, () -> AllItems.ANDESITE_ALLOY);
		block.setIgnored(AndesiteAlloy, () -> AllBlocks.ANDESITE_ALLOY_BLOCK);
		ingot.setIgnored(AndesiteAlloy, () -> AllItems.ANDESITE_ALLOY);
		gem.setIgnored(RoseQuartz, () -> AllItems.ROSE_QUARTZ);

		if(Greate.CONFIG.useCreateItemsInRecipes) {
			block.setIgnored(Brass, () -> AllBlocks.BRASS_BLOCK);
			block.setIgnored(Zinc, () -> AllBlocks.ZINC_BLOCK);

			dust.setIgnored(Netherrack, () -> AllItems.CINDER_FLOUR);
			dust.setIgnored(Obsidian, () -> AllItems.POWDERED_OBSIDIAN);
			dust.setIgnored(Wheat, () -> AllItems.WHEAT_FLOUR);

			ingot.setIgnored(Brass, () -> AllItems.BRASS_INGOT);
			ingot.setIgnored(Zinc, () -> AllItems.ZINC_INGOT);

			nugget.setIgnored(Brass, () -> AllItems.BRASS_NUGGET);
			nugget.setIgnored(Copper, () -> AllItems.COPPER_NUGGET);
			nugget.setIgnored(Zinc, () -> AllItems.ZINC_NUGGET);

			plate.setIgnored(Brass, () -> AllItems.BRASS_SHEET);
			plate.setIgnored(Copper, () -> AllItems.COPPER_SHEET);
			plate.setIgnored(Iron, () -> AllItems.IRON_SHEET);
			plate.setIgnored(Gold, () -> AllItems.GOLDEN_SHEET);
		}
	}

	public static Material.Builder Builder(String id) {
		return new Material.Builder(Greate.id(id));
	}
}
