package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ingot;
import static electrolyte.greate.registry.GreateTagPrefixes.alloy;

public class GreateMechanicalMixingRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {}

    public static void registerMaterialRecipes(Consumer<FinishedRecipe> provider, Material material) {
        if(material.hasFlag(GreateMaterialFlags.GENERATE_ALLOY)) {
            new TieredProcessingRecipeBuilder<>(TieredMixingRecipe::new, Greate.id("mixing/" + material.getName()))
                    .withItemIngredients(Ingredient.of(ChemicalHelper.get(dust, material), ChemicalHelper.get(ingot, material)), Ingredient.of(Blocks.ANDESITE))
                    .withSingleItemOutput(ChemicalHelper.get(alloy, material))
                    .duration(300)
                    .recipeTier(LV)
                    .recipeCircuit(4)
                    .build(provider);
        }
    }
}
