package electrolyte.greate.foundation.data.recipe.machine;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTMaterials.VOLTAGE_COMMON_MATERIALS;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.CUTTER_RECIPES;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class GreateCuttingMachineRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            CUTTER_RECIPES
                    .recipeBuilder(SHAFTS[tier].getId())
                    .inputItems(alloy, VOLTAGE_COMMON_MATERIALS[tier])
                    .outputItems(SHAFTS[tier], 4)
                    .duration(100)
                    .EUt(VA[tier])
                    .save(provider);
        }
    }
}
