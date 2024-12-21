package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import electrolyte.greate.compat.gtceu.api.capability.recipe.RPMRecipeCapability;
import electrolyte.greate.compat.gtceu.api.capability.recipe.StressRecipeCapability;

public class GreateRecipeCapabilities {

    public static final RecipeCapability<Float> STRESS = StressRecipeCapability.STRESS_CAPABILITY;
    public static final RecipeCapability<Float> RPM = RPMRecipeCapability.RPM_CAPABILITY;

    public static void register() {
        GTRegistries.RECIPE_CAPABILITIES.register(STRESS.name, STRESS);
        GTRegistries.RECIPE_CAPABILITIES.register(RPM.name, RPM);
    }
}
