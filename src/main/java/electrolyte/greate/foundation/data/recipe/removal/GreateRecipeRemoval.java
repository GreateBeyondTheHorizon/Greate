package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.config.ConfigHolder;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.ConfigurableRecipeRemoval.disableArmorToolRecipes;
import static electrolyte.greate.foundation.data.recipe.removal.ConfigurableRecipeRemoval.disableDyeRecipes;
import static electrolyte.greate.foundation.data.recipe.removal.CreateRecipeRemoval.disableConfigurableCreateRecipes;
import static electrolyte.greate.foundation.data.recipe.removal.CreateRecipeRemoval.disableCreateRecipes;
import static electrolyte.greate.foundation.data.recipe.removal.GTRecipeRemoval.disableGTRecipes;

public class GreateRecipeRemoval {

    public static void register(Consumer<ResourceLocation> recipe) {
        if(ConfigHolder.INSTANCE.recipes.hardDyeRecipes) disableDyeRecipes(recipe);
        if(ConfigHolder.INSTANCE.recipes.hardToolArmorRecipes) disableArmorToolRecipes(recipe);
        if(Greate.CONFIG.enableHardCreateRecipes) disableConfigurableCreateRecipes(recipe);
        disableCreateRecipes(recipe);
        disableGTRecipes(recipe);
    }
}
