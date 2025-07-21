package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.block.Blocks;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.api.tag.TagPrefix.ingot;
import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.MIXER_RECIPES;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;

public class GreateMixingRecipes {

    public static void registerMaterialRecipes(RecipeOutput provider, Material material) {
        if(material.hasFlag(GreateMaterialFlags.GENERATE_ALLOY)) {
            MIXER_RECIPES
                    .recipeBuilder(material.getName() + "_alloy")
                    .inputItems(ChemicalHelper.get(ingot, material))
                    .inputItems(Blocks.ANDESITE.asItem())
                    .outputItems(alloy, material)
                    .duration(300)
                    .EUt(VA[LV])
                    .save(provider);
        }
    }
}
