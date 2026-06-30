package electrolyte.greate.compat.gtceu.capability.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerFloat;

public class StressRecipeCapability extends RecipeCapability<Float> {

    public static final StressRecipeCapability STRESS_CAPABILITY = new StressRecipeCapability();

    protected StressRecipeCapability() {
        super("stress", 0xAABBCC00, false, 4, SerializerFloat.INSTANCE);
    }

    @Override
    public Float copyInner(Float content) {
        return content;
    }

    @Override
    public Float copyWithModifier(Float content, ContentModifier modifier) {
        return modifier.apply(content);
    }
}
