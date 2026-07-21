package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.integration.recipeviewer.emi.recipe.GTRecipeEMICategory;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GTRecipeEMICategory.class)
public class MixinGTRecipeEMICategory {

    @Redirect(method = "registerDisplays", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/recipe/category/GTRecipeCategory;shouldRegisterDisplays()Z"), remap = false)
    private static boolean greate$shouldRegisterDisplays(GTRecipeCategory cat) {
        if(cat == GreateRecipeTypes.MILLSTONE.getCategory() ||
            cat == GreateRecipeTypes.SAW.getCategory()) return false;
        return cat.shouldRegisterDisplays();
    }

    @Redirect(method = "registerWorkStations", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/recipe/category/GTRecipeCategory;isXEIVisible()Z"), remap = false)
    private static boolean greate$isXEIVisible(GTRecipeCategory cat) {
        if(cat == GreateRecipeTypes.MILLSTONE.getCategory() ||
            cat == GreateRecipeTypes.SAW.getCategory()) return false;
        return cat.shouldRegisterDisplays();
    }
}
