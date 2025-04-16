package electrolyte.greate.content.kinetics.mixer;

import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;

public class TieredCompactingRecipe extends TieredBasinRecipe {
    public TieredCompactingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.COMPACTING, params);
    }
}
