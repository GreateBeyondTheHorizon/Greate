package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import net.minecraft.data.recipes.RecipeOutput;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.data.material.GTMaterials.WroughtIron;
import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.CUTTER_RECIPES;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class GreateCuttingMachineRecipes {

    public static void register(RecipeOutput provider) {
        CUTTER_RECIPES
                .recipeBuilder(AndesiteAlloy.getName() + "_shaft")
                .inputItems(ChemicalHelper.get(alloy, WroughtIron))
                .outputItems(ChemicalHelper.get(shaft, AndesiteAlloy), 6)
                .duration(300)
                .EUt(VA[LV])
                .save(provider);
    }

    public static void registerMaterialRecipes(RecipeOutput provider, Material material) {
        if(material.hasProperty(GreatePropertyKeys.KINETIC)) {
            if(!material.getName().equals(AndesiteAlloy.getName())) {
                CUTTER_RECIPES
                        .recipeBuilder(material.getName() + "_shaft")
                        .inputItems(ChemicalHelper.get(alloy, material))
                        .outputItems(ChemicalHelper.get(shaft, material), 6)
                        .duration(300)
                        .EUt(VA[LV])
                        .save(provider);
            }
        }
    }
}
