package electrolyte.greate.foundation.data.recipe;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class GreateRuntimeRecipes {

    public static final Object2ObjectOpenHashMap<ResourceLocation, JsonObject> JSON_FILES = new Object2ObjectOpenHashMap<>();
    public static final ResourceManagerReloadListener LISTENER = r -> {
        JSON_FILES.clear();
        JSON_FILES.trim();
    };

    /*public static void convertGTRecipe(TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> factory, ResourceLocation recipeId, JsonElement recipeJson, boolean supportsDuration) {
        GTRecipe recipe = GTRecipeSerializer.SERIALIZER.fromJson(recipeId, recipeJson.getAsJsonObject());
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        int recipeCircuit = TieredProcessingRecipe.getCircuitFromGTRecipe(recipe.getInputContents(ItemRecipeCapability.CAP));
        TieredProcessingRecipeBuilder<TieredProcessingRecipe<?>> builder = new Builder<>(factory, recipe.getId())
                    .withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP))
                    .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP))
                    .recipeTier(recipeTier);
        if(recipe.getType() == GTRecipeTypes.ORE_WASHER_RECIPES) {
            if(recipeCircuit != 2) return;
            builder.build();
        } else {
            builder
                    .withFluidIngredientsGT(recipe.getInputContents(FluidRecipeCapability.CAP))
                    .withFluidOutputsGT(recipe.getOutputContents(FluidRecipeCapability.CAP))
                    .duration(supportsDuration ? recipe.duration : 0)
                    .recipeCircuit(recipeCircuit)
                    .build();
        }
    }

    public static void convertCreateRecipe(Factory<ProcessingRecipeParams, ProcessingRecipe<?, ProcessingRecipeParams>> factory, ResourceLocation recipeId, JsonElement recipeJson) {
        TieredProcessingRecipeSerializer<TieredProcessingRecipe<?>> serializer = new Serializer<>(factory);
        ProcessingRecipe<?> recipe = serializer.fromJson(recipeId, recipeJson.getAsJsonObject());
        new Builder<>(factory, recipe.getId())
                .withItemIngredients(recipe.getIngredients())
                .withFluidIngredients(recipe.getFluidIngredients())
                .withItemOutputs(recipe.getRollableResults())
                .withFluidOutputs(recipe.getFluidResults())
                .duration(recipe.getProcessingDuration())
                .requiresHeat(recipe.getRequiredHeat())
                .recipeTier(ULV)
                .build();
    }

    public static void convertPotionRecipe(MixingRecipe recipe) {
        new Builder<>(TieredBrewingRecipe::new, recipe.getId())
                .withItemIngredients(recipe.getIngredients())
                .withFluidIngredients(recipe.getFluidIngredients())
                .withFluidOutputs(recipe.getFluidResults())
                .duration(recipe.getProcessingDuration())
                .requiresHeat(recipe.getRequiredHeat())
                .recipeTier(ULV)
                .build();
    }*/

    /*private static class Builder<T extends TieredProcessingRecipe<?>> extends TieredProcessingRecipeBuilder<T> {

        public Builder(TieredProcessingRecipeFactory<T> factory, ResourceLocation recipeId) {
            super(factory, Greate.id("integration/" + recipeId.toString().replace(":", "/")));
        }

        @Override
        public T build() {
            T t = super.build();
            DataGenResult<T> result = new DataGenResult<>(t, Collections.emptyList());
            JSON_FILES.put(result.getId(), result.serializeRecipe());
            return t;
        }
    }*/
}
