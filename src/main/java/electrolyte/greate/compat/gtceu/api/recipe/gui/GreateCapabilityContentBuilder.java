package electrolyte.greate.compat.gtceu.api.recipe.gui;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.widget.WidgetTree;
import brachy.modularui.widgets.TextWidget;
import brachy.modularui.widgets.layout.Flow;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.gui.CapabilityContentBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;

public interface GreateCapabilityContentBuilder extends CapabilityContentBuilder {

    CapabilityContentBuilder STRESS = (widget, content, io, perTick,
                                            recipeType, recipe, chanceTier, recipeTier) -> {
        if (!(widget instanceof Flow flow)) return;

        var existingStressWidget = WidgetTree.findFirstWithNameNullable(flow, "stress_in_0");

        if (recipe.tickInputs.get(StressRecipeCapability.STRESS_CAPABILITY) != null) {
            if (StressRecipeCapability.STRESS_CAPABILITY.isTickSlot(0, IO.IN, recipe)) {
                float stressPerTick = (float) recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).stream().map(Content::content)
                        .mapToDouble(StressRecipeCapability.STRESS_CAPABILITY::of).sum();
                int tierDiff = recipeTier - GreateValues.convertStressToTier(recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY));
                if(GreateValues.convertStressToTier(recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY)) == GTValues.ULV) tierDiff = tierDiff - 1;
                float adjustedStress = (float) (stressPerTick * Math.pow(4, Math.max(0, tierDiff)));
                var text = Text.lang("greate.recipe.stress_input", FormattingUtil.formatNumbers(adjustedStress));

                if (existingStressWidget != null) ((TextWidget<?>) existingStressWidget).value(text);
                else flow.child(text.asWidget().name("stress_in_0"));
            }
        }
    };
}
