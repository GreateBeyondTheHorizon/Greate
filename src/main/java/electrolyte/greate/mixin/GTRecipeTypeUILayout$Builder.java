package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.gui.GTRecipeTypeUILayout.CapabilityUIInfo;
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateCapabilityContentBuilder;
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateRecipeViewerCapabilityLayoutBuilder;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com/gregtechceu/gtceu/api/recipe/gui/GTRecipeTypeUILayout$Builder")
public abstract class GTRecipeTypeUILayout$Builder {

    @Shadow
    protected abstract CapabilityUIInfo getCapInfo(RecipeCapability<?> cap);

    @Inject(method = "<init>", at = @At(value = "RETURN"), remap = false)
    private void greate$init(GTRecipeType recipeType, CallbackInfo ci) {
        getCapInfo(StressRecipeCapability.STRESS_CAPABILITY).recipeViewerLayoutBuilder = GreateRecipeViewerCapabilityLayoutBuilder.STRESS;
        getCapInfo(StressRecipeCapability.STRESS_CAPABILITY).capabilityWidgetBuilder = GreateCapabilityContentBuilder.STRESS;
    }
}
