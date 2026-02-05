package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.common.data.GTRecipeCategories;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.FORGE_HAMMER_RECIPES;

public class GreateForgeHammerRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        FORGE_HAMMER_RECIPES.recipeBuilder("decompress_zinc_to_raw_ore")
                .category(GTRecipeCategories.ORE_FORGING)
                .inputItems(AllBlocks.RAW_ZINC_BLOCK.asStack())
                .outputItems(AllItems.RAW_ZINC, 9)
                .duration(20 * 15)
                .EUt(2)
                .save(provider);
    }
}
