package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.CUTTER_RECIPES;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class GreateCuttingMachineRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {}

    public static void registerMaterialRecipes(Consumer<FinishedRecipe> provider, Material material) {
        if(material.hasFlag(GreateMaterialFlags.GENERATE_SHAFT)) {
            CUTTER_RECIPES
                    .recipeBuilder(material.getName() + "_shaft")
                    .inputItems(alloy, material)
                    .outputItems(ChemicalHelper.get(shaft, material), 4)
                    .duration(300)
                    .EUt(VA[LV])
                    .save(provider);
        }
    }
}
