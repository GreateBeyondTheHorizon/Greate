package electrolyte.greate.compat.gtceu.api.recipe.gui;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.drawable.text.ModularComponent;
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
        int generatedRPM = recipe.data.getInt("generatedRPM");

        float stressPerTick = (float) getStressCap(recipe, io).stream().map(Content::content)
                .mapToDouble(StressRecipeCapability.STRESS_CAPABILITY::of).sum();
        int tierDiff = recipeTier - GreateValues.convertStressToTier(getStressCap(recipe, io));
        if(GreateValues.convertStressToTier(getStressCap(recipe, io)) == GTValues.ULV) tierDiff = tierDiff - 1;
        float adjustedStress = (float) (stressPerTick * Math.pow(4, Math.max(0, tierDiff)));
        ModularComponent text;
        if(io == IO.IN) {
            text = Text.lang("greate.recipe.stress_input", FormattingUtil.formatNumbers(adjustedStress));
            if(existingStressWidgetIn != null) ((TextWidget<?>) existingStressWidgetIn).value(text);
            else flow.child(text.asWidget().paddingTop(1).name("stress_in_0"));
        } else {
            if(generatedRPM == 0) generatedRPM = 16; //default to 16 rpm if none specified
            text = Text.lang("greate.recipe.stress_output", FormattingUtil.formatNumbers(adjustedStress), generatedRPM);
            if(existingStressWidgetOut != null) ((TextWidget<?>) existingStressWidgetOut).value(text);
            else flow.child(text.asWidget().paddingTop(1).name("stress_out_0"));
        }

        var existingRPMWidgetInMin = WidgetTree.findFirstWithNameNullable(flow, "rpm_in_min_0");
        var existingRPMWidgetInMax = WidgetTree.findFirstWithNameNullable(flow, "rpm_in_max_0");

        int minRPM = recipe.data.getInt("minRPM");
        int maxRPM = recipe.data.getInt("maxRPM");

        if(minRPM != 0 && io == IO.IN) {
            text = Text.lang("greate.recipe.rpm_input_min", minRPM);
            if(existingRPMWidgetInMin != null) ((TextWidget<?>) existingRPMWidgetInMin).value(text);
            else flow.child(text.asWidget().name("rpm_in_min_0"));
        }

        if(maxRPM != 0 && io == IO.IN) {
            text = Text.lang("greate.recipe.rpm_input_max", maxRPM);
            if(existingRPMWidgetInMax != null) ((TextWidget<?>) existingRPMWidgetInMax).value(text);
            else flow.child(text.asWidget().name("rpm_in_max_0"));
        }
    };

    private static List<Content> getStressCap(GTRecipe recipe, IO io) {
        return io == IO.IN ? recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY) : recipe.getTickOutputContents(StressRecipeCapability.STRESS_CAPABILITY);
    }
}
