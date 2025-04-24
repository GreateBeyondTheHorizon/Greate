package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.integration.kjs.recipe.GTRecipeSchema.GTRecipeJS;
import electrolyte.greate.compat.gtceu.api.capability.recipe.RPMRecipeCapability;
import electrolyte.greate.compat.gtceu.api.capability.recipe.StressRecipeCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GTRecipeJS.class)
public abstract class MixinGTRecipeJS { //We love load order issues!!!!

    @Shadow(remap = false) public abstract <T> GTRecipeJS input(RecipeCapability<T> capability, Object... obj);
    @Shadow(remap = false) public abstract <T> GTRecipeJS output(RecipeCapability<T> capability, Object... obj);

    @Unique
    public GTRecipeJS inputStress(float stress) {
        return this.inputStress(stress, 16);
    }

    @Unique
    public GTRecipeJS inputStress(float stress, float rpm) {
        return this.input(StressRecipeCapability.STRESS_CAPABILITY, stress).perTick(true)
                .input(RPMRecipeCapability.RPM_CAPABILITY, rpm).perTick(true);
    }

    @Unique
    public GTRecipeJS outputStress(float stress) {
        return this.outputStress(stress, 16);
    }

    @Unique
    public GTRecipeJS outputStress(float stress, float rpm) {
        return this.output(StressRecipeCapability.STRESS_CAPABILITY, stress).perTick(true)
                .output(RPMRecipeCapability.RPM_CAPABILITY, rpm).perTick(true);
    }
}
