package electrolyte.greate.compat.gtceu.api.recipe.gui;

import brachy.modularui.drawable.GuiTextures;
import brachy.modularui.integration.recipeviewer.RecipeViewerSlotWidget;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidStackList;
import brachy.modularui.integration.recipeviewer.entry.item.ItemStackList;
import brachy.modularui.utils.Alignment;
import brachy.modularui.widgets.SlotGroupWidget;
import brachy.modularui.widgets.layout.Flow;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.gui.GTRecipeViewerWidget;
import com.gregtechceu.gtceu.api.recipe.gui.RecipeViewerCapabilityLayoutBuilder;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface GreateRecipeViewerCapabilityLayoutBuilder {

    RecipeViewerCapabilityLayoutBuilder STRESS = (layout, widget, io) -> {
        if (layout.getRecipeType().getMaxSlots(StressRecipeCapability.STRESS_CAPABILITY, io) == 0) return;

        var stressInfo = Flow.col().childPadding(2)
                .coverChildrenHeight()
                .widthRel(1f)
                .crossAxisAlignment(Alignment.CrossAxis.START)
                .name(GTRecipeViewerWidget.capabilityWidgetName(StressRecipeCapability.STRESS_CAPABILITY, io, 0));

        widget.textComponents.child(stressInfo);
    };

    RecipeViewerCapabilityLayoutBuilder MILLING_ITEM = (layout, widget, io) -> {
        if(layout.getRecipeType().getMaxSlots(ItemRecipeCapability.CAP, io) == 0)
            return;

        var slotGroupWidget = SlotGroupWidget.builder()
                .matrix(layout.capabilityInfo(ItemRecipeCapability.CAP).getRecipeViewerGrid(io))
                .key('s', i -> RecipeViewerSlotWidget.create(ItemStack.class)
                        .value(ItemStackList.of(ItemStack.EMPTY))
                        .background(GuiTextures.SLOT_ITEM,
                                layout.capabilityInfo(ItemRecipeCapability.CAP).getOverlay(io, i))
                        .name(GTRecipeViewerWidget.capabilityWidgetName(ItemRecipeCapability.CAP, io, i)))
                .build()
                .coverChildren(18, 18)
                .pos(io == IO.IN ? 5 : 90, 5);
        widget.child(slotGroupWidget);
    };

    RecipeViewerCapabilityLayoutBuilder CUTTING_ITEM = (layout, widget, io) -> {
        if(layout.getRecipeType().getMaxSlots(ItemRecipeCapability.CAP, io) == 0) return;

        var slotGroupWidget = SlotGroupWidget.builder()
                .matrix(layout.capabilityInfo(ItemRecipeCapability.CAP).getRecipeViewerGrid(io))
                .key('s', i -> RecipeViewerSlotWidget.create(ItemStack.class)
                        .value(ItemStackList.of(ItemStack.EMPTY))
                        .background(GuiTextures.SLOT_ITEM,
                                layout.capabilityInfo(ItemRecipeCapability.CAP).getOverlay(io, i))
                        .name(GTRecipeViewerWidget.capabilityWidgetName(ItemRecipeCapability.CAP, io, i)))
                .build()
                .coverChildren(18, 18)
                .pos(io == IO.IN ? 5 : 100, 5);
        widget.child(slotGroupWidget);
    };

    RecipeViewerCapabilityLayoutBuilder CUTTING_FLUID = (layout, widget, io) -> {
        if(layout.getRecipeType().getMaxSlots(FluidRecipeCapability.CAP, io) == 0) return;

        var slotGroupWidget = SlotGroupWidget.builder()
                .matrix(layout.capabilityInfo(FluidRecipeCapability.CAP).getRecipeViewerGrid(io))
                .key('s', i -> RecipeViewerSlotWidget.create(FluidStack.class)
                        .value(FluidStackList.of(FluidStack.EMPTY))
                        .background(GuiTextures.SLOT_FLUID,
                                layout.capabilityInfo(FluidRecipeCapability.CAP).getOverlay(io, i))
                        .name(GTRecipeViewerWidget.capabilityWidgetName(FluidRecipeCapability.CAP, io, i)))
                .build()
                .coverChildren(18, 18)
                .pos(io == IO.IN ? 5 : 90, 25);
        widget.child(slotGroupWidget);
    };

    RecipeViewerCapabilityLayoutBuilder KINETIC_STONECUTTING_ITEM = (layout, widget, io) -> {
        if(layout.getRecipeType().getMaxSlots(ItemRecipeCapability.CAP, io) == 0) return;

        var slotGroupWidget = SlotGroupWidget.builder()
                .matrix(layout.capabilityInfo(ItemRecipeCapability.CAP).getRecipeViewerGrid(io))
                .key('s', i -> RecipeViewerSlotWidget.create(ItemStack.class)
                        .value(ItemStackList.of(ItemStack.EMPTY))
                        .background(GuiTextures.SLOT_ITEM,
                                layout.capabilityInfo(ItemRecipeCapability.CAP).getOverlay(io, i))
                        .name(GTRecipeViewerWidget.capabilityWidgetName(ItemRecipeCapability.CAP, io, i)))
                .build()
                .coverChildren(18, 18)
                .pos(io == IO.IN ? 5 : 133, io == IO.IN ? 5 : 22);
        widget.child(slotGroupWidget);
    };
}
