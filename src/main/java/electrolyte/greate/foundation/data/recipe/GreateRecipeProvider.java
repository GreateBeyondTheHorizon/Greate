package electrolyte.greate.foundation.data.recipe;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import electrolyte.greate.Greate;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GreateRecipeProvider extends FabricRecipeProvider {

    protected final List<GeneratedRecipe> ALL_RECIPES = new ArrayList<>();

    public GreateRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ALL_RECIPES.forEach(r -> r.register(pFinishedRecipeConsumer));
        Greate.LOGGER.info(getName() + " registered " + ALL_RECIPES.size() + " recipe " + (ALL_RECIPES.size() == 1 ? "" : "s"));
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        ALL_RECIPES.add(recipe);
        return recipe;
    }

    protected static class Marker {}
}
