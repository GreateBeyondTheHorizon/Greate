package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import electrolyte.greate.compat.gtceu.common.recipe.condition.RPMRecipeCondition;
import electrolyte.greate.compat.gtceu.common.recipe.condition.StressRecipeCondition;

public class GreateRecipeConditions {

    public static void register() {}

    public static final RecipeConditionType<StressRecipeCondition> STRESS = GTRegistries.RECIPE_CONDITIONS.register("stress", new RecipeConditionType<>(StressRecipeCondition::new, StressRecipeCondition.CODEC));
    public static final RecipeConditionType<RPMRecipeCondition> RPM = GTRegistries.RECIPE_CONDITIONS.register("rpm", new RecipeConditionType<>(RPMRecipeCondition::new, RPMRecipeCondition.CODEC));
}
