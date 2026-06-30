package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.integration.recipeviewer.emi.GTEMIPlugin;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GTEMIPlugin.class)
public class MixinGTEMIPlugin {

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/recipe/category/GTRecipeCategory;shouldRegisterDisplays()Z"), remap = false)
    private boolean greate$shouldRegisterDisplays(GTRecipeCategory cat) {
        if(cat == GreateRecipeTypes.MILLSTONE.getCategory()) return false;
        return cat.shouldRegisterDisplays();
    }
}
