package electrolyte.greate.compat.gtceu.api.capability.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerFloat;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Collection;
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
    public List<Object> compressIngredients(Collection<Object> ingredients) {
        return List.of(ingredients.stream().map(Float.class::cast).reduce(0f, Float::sum));
    }

    @Override
    public void addXEIInfo(WidgetGroup group, int xOffset, GTRecipe recipe, List<Content> contents, boolean perTick, boolean isInput, MutableInt yOffset) {
        String key = isInput ? "input" : "output";
        float rpm = isInput ? (float) recipe.getTickInputContents(RPMRecipeCapability.RPM_CAPABILITY).get(0).getContent() :
                (float) recipe.getTickOutputContents(RPMRecipeCapability.RPM_CAPABILITY).get(0).getContent();
        float stress = (float) contents.stream().map(Content::getContent).mapToDouble(StressRecipeCapability.STRESS_CAPABILITY::of).sum();
        group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10), LocalizationUtils.format("greate.recipe.stress_" + key, stress, rpm)));
    }
}
