package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RecipeHelper.class)
public class MixinRecipeHelper {

    @WrapOperation(method = "getRealEUt", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/recipe/GTRecipe;getInputEUt()Lcom/gregtechceu/gtceu/api/recipe/ingredient/EnergyStack;"), remap = false)
    private static EnergyStack greate$getRealEUt(GTRecipe recipe, Operation<EnergyStack> original) {
        if(!recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).isEmpty()) {
            Content c = recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).get(0);
            return new EnergyStack((long) (float)c.content(), 1);
        }
        return original.call(recipe);
    }

    @WrapOperation(method = "getRealEUtWithIO", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/recipe/GTRecipe;getInputEUt()Lcom/gregtechceu/gtceu/api/recipe/ingredient/EnergyStack;"), remap = false)
    private static EnergyStack greate$getRealEUtWithIO(GTRecipe recipe, Operation<EnergyStack> original) {
        if(!recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).isEmpty()) {
            Content c = recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).get(0);
            return new EnergyStack((long) (float)c.content(), 1);
        }
        return original.call(recipe);
    }
}
