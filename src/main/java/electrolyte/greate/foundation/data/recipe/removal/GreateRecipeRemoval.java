package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.config.ConfigHolder;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static electrolyte.greate.foundation.data.recipe.removal.ConfigurableRecipeRemoval.*;
import static electrolyte.greate.foundation.data.recipe.removal.CreateRecipeRemoval.*;
import static electrolyte.greate.foundation.data.recipe.removal.GTRecipeRemoval.disableGTRecipes;

public class GreateRecipeRemoval {

    public static void register(Consumer<ResourceLocation> recipe) {
        if(ConfigHolder.INSTANCE.recipes.hardDyeRecipes) disableDyeRecipes(recipe);
        if(ConfigHolder.INSTANCE.recipes.hardToolArmorRecipes) disableArmorToolRecipes(recipe);
        if(ConfigHolder.INSTANCE.recipes.hardGlassRecipes) disableGlassRecipes(recipe);
        if(ConfigHolder.INSTANCE.recipes.disableManualCompression) disableCompressionRecipes(recipe);
        if(Greate.CONFIG.enableHardCreateRecipes) disableConfigurableCreateRecipes(recipe);
        if(Greate.CONFIG.disableConflictingRecipes) disableConflictingCreateRecipes(recipe);
        disableCreateRecipes(recipe);
        disableGTRecipes(recipe);
    }
}
