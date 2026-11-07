package electrolyte.greate.compat.gtceu.api.recipe.gui;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.widget.WidgetTree;
import brachy.modularui.widgets.TextWidget;
import brachy.modularui.widgets.layout.Flow;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.gui.CapabilityContentBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;

import java.util.List;

public interface GreateCapabilityContentBuilder {

    CapabilityContentBuilder STRESS = (widget, content, io, perTick,
                                            recipeType, recipe, chanceTier, recipeTier) -> {
        if (!(widget instanceof Flow flow)) return;

        var existingStressWidgetIn = WidgetTree.findFirstWithNameNullable(flow, "stress_in_0");
        var existingStressWidgetOut = WidgetTree.findFirstWithNameNullable(flow, "stress_out_0");

        float stressPerTick = (float) getStressCap(recipe, io).stream().map(Content::content)
                .mapToDouble(StressRecipeCapability.STRESS_CAPABILITY::of).sum();
        int tierDiff = recipeTier - GreateValues.convertStressToTier(getStressCap(recipe, io));
        if(GreateValues.convertStressToTier(getStressCap(recipe, io)) == GTValues.ULV) tierDiff = tierDiff - 1;
        float adjustedStress = (float) (stressPerTick * Math.pow(4, Math.max(0, tierDiff)));
        var text = Text.lang("greate.recipe.stress_" + (io == IO.IN ? "input" : "output"), FormattingUtil.formatNumbers(adjustedStress));

        if ((io == IO.IN ? existingStressWidgetIn : existingStressWidgetOut) != null) ((TextWidget<?>) existingStressWidgetIn).value(text);
        else flow.child(text.asWidget().name("stress_" + (io == IO.IN ? "in_0" : "out_0")));
    };

    private static List<Content> getStressCap(GTRecipe recipe, IO io) {
        return io == IO.IN ? recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY) : recipe.getTickOutputContents(StressRecipeCapability.STRESS_CAPABILITY);
    }
}
