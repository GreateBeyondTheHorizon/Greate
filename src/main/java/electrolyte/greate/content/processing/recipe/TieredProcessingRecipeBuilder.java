package electrolyte.greate.content.processing.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import electrolyte.greate.GreateEnums.TIER;
import net.minecraft.resources.ResourceLocation;

public class TieredProcessingRecipeBuilder<T extends ProcessingRecipe<?>> extends ProcessingRecipeBuilder<T> {

    protected TieredProcessingRecipeParams params;

    public TieredProcessingRecipeBuilder(ProcessingRecipeFactory<T> factory, ResourceLocation recipeId) {
        super(factory, recipeId);
        params = new TieredProcessingRecipeParams(recipeId);
    }

    public TieredProcessingRecipeBuilder<T> requiresTier(TIER condition) {
        params.recipeTier = condition;
        return this;
    }

    @Override
    public T build() {
        return factory.create(params);
    }

    public static class TieredProcessingRecipeParams extends ProcessingRecipeParams {
        protected TIER recipeTier;

        public TieredProcessingRecipeParams(ResourceLocation id) {
            super(id);
            recipeTier = TIER.NONE;
        }
    }
}
