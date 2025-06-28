package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.config.ConfigHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.CableRecipeRemoval.disableCableRecipes;

public class GTRecipeRemoval {

    public static void disableGTRecipes(Consumer<ResourceLocation> recipe) {
        if(ConfigHolder.INSTANCE.recipes.hardMiscRecipes) {
            disableCableRecipes(recipe);
        }
    }
}
