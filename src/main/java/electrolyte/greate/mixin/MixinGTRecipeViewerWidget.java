package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.gui.GTRecipeViewerWidget;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GTRecipeViewerWidget.class)
public class MixinGTRecipeViewerWidget {

    @WrapOperation(method = "applyOverclock", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/recipe/GTRecipe;getInputEUt()Lcom/gregtechceu/gtceu/api/recipe/ingredient/EnergyStack;"), remap = false)
    private EnergyStack greate$applyOverclock(GTRecipe recipe, Operation<EnergyStack> original) {
        if(!recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).isEmpty()) {
            Content c = recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).get(0);
            return new EnergyStack((long) (float)c.content(), 1);
        }
        return original.call(recipe);
    }
}
