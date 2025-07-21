/*
package electrolyte.greate.compat.kubejs;

import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent.IntRange;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface TieredProcessingRecipeSchema extends ProcessingRecipeSchema {

    RecipeKey<Integer> RECIPE_TIER = new IntRange(0, 10).key("recipeTier").optional(0);

    RecipeKey<Integer> RECIPE_CIRCUIT = new IntRange(0, 32).key("circuitNumber").optional(-1);

    class TieredProcessingRecipeJS extends ProcessingRecipeJS {

        public RecipeJS recipeTier(Object from) {
            return setValue(RECIPE_TIER, (int) (double) from);
        }

        public RecipeJS circuitNumber(Object from) {
            return setValue(RECIPE_CIRCUIT, (int) (double) from);
        }
    }

    RecipeSchema PROCESSING_DEFAULT = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME, HEAT_REQUIREMENT, RECIPE_TIER);
    RecipeSchema PROCESSING_WITH_CIRCUIT = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME, HEAT_REQUIREMENT, RECIPE_TIER, RECIPE_CIRCUIT);
    RecipeSchema PROCESSING_WITH_TIME = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME_REQUIRED, HEAT_REQUIREMENT, RECIPE_TIER);
    RecipeSchema PROCESSING_UNWRAPPED = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS_UNWRAPPED, PROCESSING_TIME, HEAT_REQUIREMENT, RECIPE_TIER);
}
*/
