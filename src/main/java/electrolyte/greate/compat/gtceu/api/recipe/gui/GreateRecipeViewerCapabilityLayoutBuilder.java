package electrolyte.greate.compat.gtceu.api.recipe.gui;

import brachy.modularui.utils.Alignment;
import brachy.modularui.widgets.layout.Flow;
import com.gregtechceu.gtceu.api.recipe.gui.GTRecipeViewerWidget;
import com.gregtechceu.gtceu.api.recipe.gui.RecipeViewerCapabilityLayoutBuilder;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;

public interface GreateRecipeViewerCapabilityLayoutBuilder extends RecipeViewerCapabilityLayoutBuilder {

    RecipeViewerCapabilityLayoutBuilder STRESS = (layout, widget, io) -> {
        if (layout.getRecipeType().getMaxSlots(StressRecipeCapability.STRESS_CAPABILITY, io) == 0) return;

        var stressInfo = Flow.col().childPadding(2)
                .coverChildrenHeight()
                .widthRel(1f)
                .crossAxisAlignment(Alignment.CrossAxis.START)
                .name(GTRecipeViewerWidget.capabilityWidgetName(StressRecipeCapability.STRESS_CAPABILITY, io, 0));

        widget.textComponents.child(stressInfo);
    };
}
