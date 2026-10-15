package electrolyte.greate.compat.gtceu.common.data;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.drawable.GuiTextures;
import brachy.modularui.integration.recipeviewer.RecipeViewerSlotWidget;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidStackList;
import brachy.modularui.integration.recipeviewer.entry.item.ItemStackList;
import brachy.modularui.widget.ParentWidget;
import brachy.modularui.widgets.SlotGroupWidget;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.gui.GTRecipeViewerWidget;
import com.gregtechceu.gtceu.api.recipe.gui.RecipeUIModifier;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.machine.trait.customlogic.MaceratorLogic;
import com.gregtechceu.gtceu.common.mui.GTGuiTextures;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.api.recipe.GreateRecipeType;
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateCapabilityContentBuilder;
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateRecipeViewerCapabilityLayoutBuilder;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GreateRecipeTypes {

    public static final String KINETIC = "kinetic";

    public static final GreateRecipeType MILLSTONE = (GreateRecipeType) GreateRecipeTypes.register(Greate.id("millstone"), KINETIC)
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
                                .key('s', i -> RecipeViewerSlotWidget.create(ItemStack.class)
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
                    .addRecipeUIModifier(RecipeUIModifier.textLine(Text.lang("gtceu.recipe.byproduct_tier", GreateValues.SNF[GTValues.LV]))));

    public static final GTRecipeType SAW = GTRecipeTypes.register(Greate.id("saw"), KINETIC)
            .setMaxIOSize(2, 2, 1, 0)
            .setMaxSize(IO.IN, StressRecipeCapability.STRESS_CAPABILITY, 1)
            .setEUIO(IO.NONE)
            .setSound(GTSoundEntries.CUT)
            .UI(uiBuilder -> uiBuilder
                    .setItemSlotOverlay(IO.IN, 0, GTGuiTextures.SAWBLADE_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 0, GTGuiTextures.CUTTER_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 1, GTGuiTextures.DUST_OVERLAY)
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(StressRecipeCapability.STRESS_CAPABILITY, GreateRecipeViewerCapabilityLayoutBuilder.STRESS)
                    .setCapabilityContentBuilder(StressRecipeCapability.STRESS_CAPABILITY, GreateCapabilityContentBuilder.STRESS)
                    .setProgressBarSupplier((layout, value, machine) -> new ParentWidget<>().size(layout.getProgressBar().progressSize()))
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(
                    ItemRecipeCapability.CAP, (layout, widget, io) -> {
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
                                .pos(io == IO.IN ? 5 : 90, 5);

                        widget.child(slotGroupWidget);
                    })
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(
                            FluidRecipeCapability.CAP, (layout, widget, io) -> {
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
                            })
                    .addRecipeUIModifier((recipe, widget) -> widget.textComponents.paddingTop(25)));

    public static void register() {
        GTRecipeTypes.MACERATOR_RECIPES.onRecipeBuild((gtRecipeBuilder, provider) -> {
            GTRecipeBuilder copiedRecipe = MILLSTONE
                    .copyFrom(gtRecipeBuilder)
                    .perTick(true)
                    .input(StressRecipeCapability.STRESS_CAPABILITY, (float) gtRecipeBuilder.EUt().voltage())
                    .perTick(false);
            copiedRecipe.tickInput.remove(EURecipeCapability.CAP);
            copiedRecipe.save(provider);
        });
        GTRecipeTypes.CUTTER_RECIPES.prepareBuilder(builder -> {
            BiConsumer<GTRecipeBuilder, Consumer<FinishedRecipe>> original = builder.onSave;
            builder.onSave((gtRecipeBuilder, provider) -> {
                if(original != null) {
                    original.accept(gtRecipeBuilder, provider); //for water/dist water/lube addition
                }
                GTRecipeBuilder copiedRecipe = SAW
                        .copyFrom(gtRecipeBuilder)
                        .perTick(true)
                        .input(StressRecipeCapability.STRESS_CAPABILITY, (float) gtRecipeBuilder.EUt().voltage())
                        .perTick(false);
                copiedRecipe.tickInput.remove(EURecipeCapability.CAP);
                copiedRecipe.save(provider);
            });
        });
    }

    public static GreateRecipeType register(ResourceLocation id, String group, RecipeType<?>... proxyRecipes) {
        GreateRecipeType recipeType = new GreateRecipeType(id, group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }
}
