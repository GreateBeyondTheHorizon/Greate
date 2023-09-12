/*
package electrolyte.greate.compat.kubejs;

import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import electrolyte.greate.GreateEnums.TIER;

import java.util.Locale;

public interface TieredProcessingRecipeSchema extends ProcessingRecipeSchema {

    RecipeKey<String> RECIPE_TIER = new StringComponent("Invalid Recipe Tier!", s -> {
        for(TIER tier : TIER.values()) {
            if(tier.name().equalsIgnoreCase(s)) return true;
        }
        return false;
    }).key("recipeTier").optional("ulv");

    class TieredProcessingRecipeJS extends ProcessingRecipeJS {
        public RecipeJS recipeTier(Object from) {
            return setValue(RECIPE_TIER, ((String) from).toLowerCase(Locale.ROOT));
        }
    }

    RecipeSchema PROCESSING_DEFAULT = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME, HEAT_REQUIREMENT, RECIPE_TIER);
    RecipeSchema PROCESSING_WITH_TIME = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME_REQUIRED, HEAT_REQUIREMENT, RECIPE_TIER);
    RecipeSchema PROCESSING_UNWRAPPED = new RecipeSchema(TieredProcessingRecipeJS.class, TieredProcessingRecipeJS::new, RESULTS, INGREDIENTS_UNWRAPPED, PROCESSING_TIME, HEAT_REQUIREMENT, RECIPE_TIER);
}
*/
