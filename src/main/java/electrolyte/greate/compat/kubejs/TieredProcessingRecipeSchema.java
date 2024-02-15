package electrolyte.greate.compat.kubejs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent.IntRange;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import electrolyte.greate.compat.kubejs.item.TieredOutputItem;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;

public interface TieredProcessingRecipeSchema extends ProcessingRecipeSchema {

    RecipeKey<Integer> RECIPE_TIER = new IntRange(0, 10).key("recipeTier").optional(0);

    RecipeKey<Integer> RECIPE_CIRCUIT = new IntRange(0, 32).key("circuitNumber").optional(-1);

    class TieredProcessingRecipeJS extends ProcessingRecipeJS {

        @Override
        public OutputItem readOutputItem(Object from) {
            if(from instanceof TieredProcessingOutput output) {
                return TieredOutputItem.of(output.getStack(), output.getChance(), output.getExtraTierChance());
            } else {
                OutputItem outputItem = super.readOutputItem(from);
                TieredOutputItem tieredOutputItem;
                if(outputItem instanceof TieredOutputItem toi) {
                    tieredOutputItem = TieredOutputItem.of(toi.item, toi.getChance(), toi.getExtraTierChance());
                } else {
                   tieredOutputItem = TieredOutputItem.of(outputItem.item, outputItem.getChance(), Double.NaN);
                }

                if(from instanceof JsonObject jsonObject) {
                    if(jsonObject.has("chance") && jsonObject.has("extraTierChance")) {
                        tieredOutputItem.withChance(jsonObject.get("chance").getAsFloat());
                        tieredOutputItem.withExtraTierChance(jsonObject.get("extraTierChance").getAsFloat());
                        return tieredOutputItem;
                    }
                }
                return tieredOutputItem;
            }
        }

        @Override
        public JsonElement writeOutputItem(OutputItem value) {
            JsonElement jsonElement = super.writeOutputItem(value);
            if(value instanceof TieredOutputItem tieredOutputItem) {
                if(tieredOutputItem.hasExtraTierChance()) {
                    jsonElement.getAsJsonObject().addProperty("extraTierChance", ((TieredOutputItem) value).getExtraTierChance());
                }
            }
            return jsonElement;
        }

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
