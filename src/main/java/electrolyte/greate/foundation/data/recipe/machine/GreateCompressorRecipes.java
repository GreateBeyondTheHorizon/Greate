package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.COMPRESSOR_RECIPES;

public class GreateCompressorRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        COMPRESSOR_RECIPES.recipeBuilder("compress_zinc_to_raw_ore_block")
                .inputItems(AllItems.RAW_ZINC, 9)
                .outputItems(AllBlocks.RAW_ZINC_BLOCK.asStack())
                .duration(20 * 15)
                .EUt(2)
                .save(provider);
    }
}
