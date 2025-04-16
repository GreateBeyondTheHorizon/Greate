package electrolyte.greate.content.kinetics.mixer;

import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;

public class TieredBrewingRecipe extends TieredBasinRecipe {

    public TieredBrewingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.BREWING, params);
    }
}
