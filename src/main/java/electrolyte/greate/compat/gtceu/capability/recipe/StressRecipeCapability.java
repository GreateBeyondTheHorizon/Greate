package electrolyte.greate.compat.gtceu.capability.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerFloat;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import electrolyte.greate.GreateValues;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

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

    @Override
    public void addXEIInfo(WidgetGroup group, int xOffset, GTRecipe recipe, List<Content> contents, boolean perTick, boolean isInput, MutableInt yOffset) {
        String key = isInput ? "input" : "output";
        float stress = (float) contents.stream().map(Content::getContent).mapToDouble(StressRecipeCapability.STRESS_CAPABILITY::of).sum();
        int recipeTier = GreateValues.convertStressToTier(contents);
        group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10), LocalizationUtils.format("greate.recipe.stress_" + key, stress, GreateValues.SNF[recipeTier])));
    }
}
