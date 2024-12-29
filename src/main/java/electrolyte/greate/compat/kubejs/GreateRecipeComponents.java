package electrolyte.greate.compat.kubejs;

import com.gregtechceu.gtceu.integration.kjs.recipe.components.ContentJS;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import electrolyte.greate.compat.gtceu.api.capability.recipe.RPMRecipeCapability;
import electrolyte.greate.compat.gtceu.api.capability.recipe.StressRecipeCapability;

public class GreateRecipeComponents {

    public static final ContentJS<Float> STRESS_IN = new ContentJS<>(NumberComponent.FLOAT, StressRecipeCapability.STRESS_CAPABILITY, false);
    public static final ContentJS<Float> STRESS_OUT = new ContentJS<>(NumberComponent.FLOAT, StressRecipeCapability.STRESS_CAPABILITY, true);
    public static final ContentJS<Float> RPM_IN = new ContentJS<>(NumberComponent.FLOAT, RPMRecipeCapability.RPM_CAPABILITY, false);
    public static final ContentJS<Float> RPM_OUT = new ContentJS<>(NumberComponent.FLOAT, RPMRecipeCapability.RPM_CAPABILITY, true);
}
