package electrolyte.greate.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import electrolyte.greate.registry.ModRecipeTypes;

import java.util.Map;

public class GreateKubeJSPlugin extends KubeJSPlugin {

    private static final Map<ModRecipeTypes, RecipeSchema> RECIPE_SCHEMAS = Map.of(
            ModRecipeTypes.MILLING, TieredProcessingRecipeSchema.PROCESSING_WITH_TIME,
            ModRecipeTypes.CRUSHING, TieredProcessingRecipeSchema.PROCESSING_WITH_TIME
    );

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        for(ModRecipeTypes recipeType : ModRecipeTypes.values()) {
            if(recipeType.getSerializer() instanceof TieredProcessingRecipeSerializer<?>) {
                RecipeSchema schema = RECIPE_SCHEMAS.getOrDefault(recipeType, TieredProcessingRecipeSchema.PROCESSING_DEFAULT);
                event.register(recipeType.getId(), schema);
            }
        }
    }
}
