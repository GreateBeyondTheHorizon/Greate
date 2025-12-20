package electrolyte.greate;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import electrolyte.greate.content.kinetics.millstone.TieredMillingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredCompactingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static net.minecraft.ChatFormatting.*;

public class GreateValues {

    /**
     * The short names for the stress tiers
     */
    public static final String[] SN = new String[]{"ULS", "LS", "MS", "HS", "ES", "IS", "LuS", "ZPMS", "US", "UHS"};

    /**
     * The short names for the stress tiers, formatted for text
     */
    public static final String[] SNF = new String[]{
            DARK_GRAY + "ULS",
            GRAY + "LS",
            AQUA + "MS",
            GOLD + "HS",
            DARK_PURPLE + "ES",
            BLUE + "IS",
            LIGHT_PURPLE + "LuS",
            RED + "ZPMS",
            DARK_AQUA + "US",
            DARK_RED + "UHS",
    };

    /**
     * The long names for the stress tiers
     */
    public static final String[] STRESS_NAMES = new String[]{
            "Ultra Low Stress",
            "Low Stress",
            "Medium Stress",
            "High Stress",
            "Extreme Stress",
            "Insane Stress",
            "Ludicrous Stress",
            "ZPM Stress",
            "Ultimate Stress",
            "Ultra High Stress",
            "Ultra Excessive Stress",
            "Ultra Immense Stress",
            "Ultra Extreme Stress",
            "Overpowered Stress",
            "Maximum Stress"
    };

    @HideFromJS
    public static int convertGTEUToTier(List<Content> content) {
        if (content.isEmpty()) return GTValues.ULV;
        long eut = ((EnergyStack) content.get(0).getContent()).voltage();
        for (int i = 0; i < GTValues.V.length; i++) {
            long voltage = GTValues.V[i];
            if (eut <= voltage) return GTValues.ALL_TIERS[i];
        }
        return GTValues.MAX;
    }

    @HideFromJS
    public static TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> getFactory(ResourceLocation loc) {
        for(String s : Greate.CONFIG.ignoredRecipeTypes) {
            if(loc.toString().startsWith(s)) return null;
        }
        if(loc.toString().startsWith(GTRecipeTypes.MACERATOR_RECIPES.registryName.toString()) || loc.toString().startsWith(AllRecipeTypes.MILLING.getId().toString())) return TieredMillingRecipe::new;
        else if(loc.toString().startsWith(AllRecipeTypes.CRUSHING.getId().toString())) return TieredCrushingRecipe::new;
        else if(loc.toString().startsWith(GTRecipeTypes.BENDER_RECIPES.registryName.toString()) || loc.toString().startsWith(AllRecipeTypes.PRESSING.getId().toString())) return TieredPressingRecipe::new;
        else if(loc.toString().startsWith(GTRecipeTypes.MIXER_RECIPES.registryName.toString()) || loc.toString().startsWith(AllRecipeTypes.MIXING.getId().toString())) return TieredMixingRecipe::new;
        else if(loc.toString().startsWith(GTRecipeTypes.CUTTER_RECIPES.registryName.toString()) || loc.toString().startsWith(AllRecipeTypes.CUTTING.getId().toString())) return TieredCuttingRecipe::new;
        else if(loc.toString().startsWith(GTRecipeTypes.ORE_WASHER_RECIPES.registryName.toString()) || loc.toString().startsWith(AllRecipeTypes.SPLASHING.getId().toString())) return TieredSplashingRecipe::new;
        else if(loc.toString().startsWith(GTRecipeTypes.COMPRESSOR_RECIPES.registryName.toString())) return TieredCompactingRecipe::new;
        else if(loc.toString().startsWith(AllRecipeTypes.HAUNTING.getId().toString())) return TieredHauntingRecipe::new;
        else if(loc.toString().startsWith(AllRecipeTypes.COMPACTING.getId().toString())) return TieredCompactingRecipe::new;
        return null;
    }

    public static float getMaxCapacityFromMaterial(Material material) {
        if(material == null || material == GTMaterials.NULL) return Float.MAX_VALUE;
        if(!material.hasProperty(GreatePropertyKeys.KINETIC)) return Float.MAX_VALUE;
        return material.getProperty(GreatePropertyKeys.KINETIC).getMaxCapacity();
    }

    /**
     * Tier materials
     * Based on GTMaterials.VOLTAGE_COMMON_MATERIALS, but uses AndesiteAlloy instead of WroughtIron in the first tier.
     */
    public static Material[] TM = new Material[]{
            AndesiteAlloy,
            Steel,
            Aluminium,
            StainlessSteel,
            Titanium,
            TungstenSteel,
            RhodiumPlatedPalladium,
            NaquadahAlloy,
            Darmstadtium,
            Neutronium,
    };

    /**
     * Belt materials
     */
    public static Material[] BM = new Material[]{
            Rubber,
            SiliconeRubber,
            Polyethylene,
            Polytetrafluoroethylene,
            Polybenzimidazole,
    };
}
