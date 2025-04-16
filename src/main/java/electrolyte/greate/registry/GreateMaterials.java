package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.PropertyKeys;
import electrolyte.greate.content.gtceu.material.WhiskProperty;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.item.tool.GTToolType.BUZZSAW;
import static com.gregtechceu.gtceu.common.data.GTElements.Ma;
import static com.gregtechceu.gtceu.common.data.GTElements.Sp;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

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
				.components(Andesite, 9, Iron, 1)
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
		Aluminium.addFlags(GENERATE_ROTOR);
		Neutronium.addFlags(GENERATE_ROTOR);
		Copper.addFlags(GENERATE_BOLT_SCREW);

		// Add whisks to Greate tier materials
		AndesiteAlloy.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		Steel.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		Aluminium.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		StainlessSteel.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		Titanium.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		TungstenSteel.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		RhodiumPlatedPalladium.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		NaquadahAlloy.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		Darmstadtium.setProperty(PropertyKeys.WHISK, new WhiskProperty());
		Neutronium.setProperty(PropertyKeys.WHISK, new WhiskProperty());

		Darmstadtium.setProperty(PropertyKey.TOOL, ToolProperty.Builder.of(50.0F, 15.0F, 5120, 5, BUZZSAW).build());
		RhodiumPlatedPalladium.setProperty(PropertyKey.TOOL, ToolProperty.Builder.of(35.0F, 10.0F, 2560, 4, BUZZSAW).build());
	}

	public static void modifyMaterials() {
	}

	public static Material.Builder Builder(String id) {
		return new Material.Builder(Greate.id(id));
	}
}
