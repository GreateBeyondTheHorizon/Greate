package electrolyte.greate.compat.gtceu.common.data;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.drawable.GuiTextures;
import brachy.modularui.integration.recipeviewer.RecipeViewerSlotWidget;
import brachy.modularui.integration.recipeviewer.entry.item.ItemStackList;
import brachy.modularui.widget.ParentWidget;
import brachy.modularui.widgets.SlotGroupWidget;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.gui.GTRecipeViewerWidget;
import com.gregtechceu.gtceu.api.recipe.gui.RecipeUIModifier;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.machine.trait.customlogic.MaceratorLogic;
import com.gregtechceu.gtceu.common.mui.GTGuiTextures;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateCapabilityContentBuilder;
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateRecipeViewerCapabilityLayoutBuilder;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import net.minecraft.world.item.ItemStack;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC;

public class GreateRecipeTypes {

    public static final String KINETIC = "kinetic";

    public static final GTRecipeType WIRE_COATING_RECIPES = GTRecipeTypes.register("wire_coating", ELECTRIC)
            .setMaxIOSize(3, 1, 1, 0)
            .setEUIO(IO.IN)
            .UI(builder -> builder.setProgressBar(GTGuiTextures.PROGRESS_WIREMILL)
                    .setItemSlotOverlay(IO.IN, 0, GTGuiTextures.WIREMILL_OVERLAY))
            .setSound(GTSoundEntries.CHEMICAL);

    public static final GTRecipeType MILLSTONE = GTRecipeTypes.register("millstone", KINETIC)
            .setMaxIOSize(1, 4, 0, 0)
            .setMaxSize(IO.IN, StressRecipeCapability.STRESS_CAPABILITY, 1)
            .setEUIO(IO.NONE)
            .addCustomRecipeLogic(MaceratorLogic.INSTANCE)
            .setSound(GTSoundEntries.MACERATOR)  //todo:change
            .UI(uiBuilder -> uiBuilder
                    .setItemSlotOverlay(IO.OUT, 1, GTGuiTextures.DUST_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 2, GTGuiTextures.DUST_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 3, GTGuiTextures.DUST_OVERLAY)
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(StressRecipeCapability.STRESS_CAPABILITY, GreateRecipeViewerCapabilityLayoutBuilder.STRESS)
                    .setCapabilityContentBuilder(StressRecipeCapability.STRESS_CAPABILITY, GreateCapabilityContentBuilder.STRESS)
                    .setProgressBarSupplier((layout, value, machine) -> new ParentWidget<>().size(layout.getProgressBar().progressSize()))
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(
                    ItemRecipeCapability.CAP, (layout, widget, io) -> {
                        if(layout.getRecipeType().getMaxSlots(ItemRecipeCapability.CAP, io) == 0)
                            return;

                        var slotGroupWidget = SlotGroupWidget.builder()
                                .matrix(layout.capabilityInfo(ItemRecipeCapability.CAP).getRecipeViewerGrid(io))
                                .key('s', i -> RecipeViewerSlotWidget.create()
                                        .value(ItemStackList.of(ItemStack.EMPTY))
                                        .background(GuiTextures.SLOT_ITEM,
                                                layout.capabilityInfo(ItemRecipeCapability.CAP).getOverlay(io, i))
                                        .name(GTRecipeViewerWidget.capabilityWidgetName(ItemRecipeCapability.CAP, io, i)))
                                .build()
                                .coverChildren(18, 18)
                                .pos(io == IO.IN ? 5 : 90, 5);

                        widget.child(slotGroupWidget);
                    })
                    .addRecipeUIModifier((recipe, widget) -> widget.textComponents.paddingTop(25))
                    .addRecipeUIModifier(RecipeUIModifier.textLine(Text.lang("gtceu.recipe.byproduct_tier", GreateValues.SNF[GTValues.HV]))));

    public static final GTRecipeType SAW = GTRecipeTypes.register("saw", KINETIC)
            .setMaxIOSize(1, 2, 1, 0)
            .setMaxSize(IO.IN, StressRecipeCapability.STRESS_CAPABILITY, 1)
            .setEUIO(IO.NONE)
            .setSound(GTSoundEntries.CUT)
            .UI(uiBuilder -> uiBuilder
                    .setItemSlotOverlay(IO.OUT, 1, GTGuiTextures.DUST_OVERLAY)
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(StressRecipeCapability.STRESS_CAPABILITY, GreateRecipeViewerCapabilityLayoutBuilder.STRESS)
                    .setCapabilityContentBuilder(StressRecipeCapability.STRESS_CAPABILITY, GreateCapabilityContentBuilder.STRESS)
                    .setProgressBarSupplier((layout, value, machine) -> new ParentWidget<>().size(layout.getProgressBar().progressSize()))
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(
                    ItemRecipeCapability.CAP, (layout, widget, io) -> {
                        if(layout.getRecipeType().getMaxSlots(ItemRecipeCapability.CAP, io) == 0)
                            return;

                        var slotGroupWidget = SlotGroupWidget.builder()
                                .matrix(layout.capabilityInfo(ItemRecipeCapability.CAP).getRecipeViewerGrid(io))
                                .key('s', i -> RecipeViewerSlotWidget.create()
                                        .value(ItemStackList.of(ItemStack.EMPTY))
                                        .background(GuiTextures.SLOT_ITEM,
                                                layout.capabilityInfo(ItemRecipeCapability.CAP).getOverlay(io, i))
                                        .name(GTRecipeViewerWidget.capabilityWidgetName(ItemRecipeCapability.CAP, io, i)))
                                .build()
                                .coverChildren(18, 18)
                                .pos(io == IO.IN ? 5 : 90, 5);

                        widget.child(slotGroupWidget);
                        //TODO: fix fluid slot
                    })
                    .addRecipeUIModifier((recipe, widget) -> widget.textComponents.paddingTop(25)));



    public static void register() {
        GTRecipeTypes.MACERATOR_RECIPES.onRecipeBuild((gtRecipeBuilder, finishedRecipeConsumer) -> {
            long euT = gtRecipeBuilder.buildRawRecipe().getInputEUt().voltage();
            GTRecipeBuilder copiedRecipe = MILLSTONE
                    .copyFrom(gtRecipeBuilder)
                    .perTick(true)
                    .input(StressRecipeCapability.STRESS_CAPABILITY, (float) euT)
                    .perTick(false);
            copiedRecipe.save(finishedRecipeConsumer);
        });
        GTRecipeTypes.CUTTER_RECIPES.onRecipeBuild((gtRecipeBuilder, finishedRecipeConsumer) -> {
            long euT = gtRecipeBuilder.buildRawRecipe().getInputEUt().voltage();
            GTRecipeBuilder copiedRecipe = SAW
                    .copyFrom(gtRecipeBuilder)
                    .perTick(true)
                    .input(StressRecipeCapability.STRESS_CAPABILITY, (float) euT)
                    .perTick(false);
            copiedRecipe.save(finishedRecipeConsumer);
        });
    }
}
