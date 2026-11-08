package electrolyte.greate.compat.gtceu.common.data;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.widget.ParentWidget;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
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
import electrolyte.greate.compat.gtceu.api.recipe.gui.GreateRecipeViewerCapabilityLayoutBuilder;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.api.GTValues.VA;

public class GreateRecipeTypes {

    public static final String KINETIC = "kinetic";

    public static final GreateRecipeType MILLING = (GreateRecipeType) GreateRecipeTypes.register(Greate.id("millstone"), KINETIC)//todo:rename to milling after removing old machines
            .setMaxIOSize(1, 4, 0, 0)
            .setMaxSize(IO.IN, StressRecipeCapability.STRESS_CAPABILITY, 1)
            .setEUIO(IO.NONE)
            .addCustomRecipeLogic(MaceratorLogic.INSTANCE)
            .setSound(GTSoundEntries.MACERATOR)  //todo:change
            .setXEIVisible(false)
            .UI(uiBuilder -> uiBuilder
                    .setItemSlotOverlay(IO.OUT, 1, GTGuiTextures.DUST_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 2, GTGuiTextures.DUST_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 3, GTGuiTextures.DUST_OVERLAY)
                    .setProgressBarSupplier((layout, value, machine) -> new ParentWidget<>().size(layout.getProgressBar().progressSize()))
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(ItemRecipeCapability.CAP, GreateRecipeViewerCapabilityLayoutBuilder.MILLING_ITEM)
                    .addRecipeUIModifier((recipe, widget) -> widget.textComponents.paddingTop(30))
                    .addRecipeUIModifier(RecipeUIModifier.textLine(Text.lang("gtceu.recipe.byproduct_tier", GreateValues.SNF[GTValues.LV]))));

    public static final GTRecipeType CUTTING = GTRecipeTypes.register(Greate.id("saw"), KINETIC)
            .setMaxIOSize(2, 2, 1, 0)
            .setMaxSize(IO.IN, StressRecipeCapability.STRESS_CAPABILITY, 1)
            .setEUIO(IO.NONE)
            .setSound(GTSoundEntries.CUT)
            .setXEIVisible(false)
            .UI(uiBuilder -> uiBuilder
                    .setItemSlotOverlay(IO.IN, 0, GTGuiTextures.SAWBLADE_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 0, GTGuiTextures.CUTTER_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 1, GTGuiTextures.DUST_OVERLAY)
                    .setProgressBarSupplier((layout, value, machine) -> new ParentWidget<>().size(layout.getProgressBar().progressSize()))
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(ItemRecipeCapability.CAP, GreateRecipeViewerCapabilityLayoutBuilder.CUTTING_ITEM)
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(FluidRecipeCapability.CAP, GreateRecipeViewerCapabilityLayoutBuilder.CUTTING_FLUID)
                    .addRecipeUIModifier((recipe, widget) -> widget.textComponents.paddingTop(30)));

    public static final GTRecipeType KINETIC_STONECUTTING = GreateRecipeTypes.register(Greate.id("stonecutting"), KINETIC, RecipeType.STONECUTTING)
            .setMaxIOSize(2, 1, 0, 0)
            .setMaxSize(IO.IN, StressRecipeCapability.STRESS_CAPABILITY, 1)
            .setEUIO(IO.NONE)
            .setSound(GTSoundEntries.CUT)
            .setXEIVisible(false)
            .prepareBuilder(b -> b
                    .duration(100)
                    .perTick(true)
                    .input(StressRecipeCapability.STRESS_CAPABILITY, (float) VA[ULV])
                    .perTick(false))
            .UI(uiBuilder -> uiBuilder
                    .setItemSlotOverlay(IO.IN, 0, GTGuiTextures.SAWBLADE_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 0, GTGuiTextures.CUTTER_OVERLAY)
                    .setItemSlotOverlay(IO.OUT, 1, GTGuiTextures.DUST_OVERLAY)
                    .setProgressBarSupplier((layout, value, machine) -> new ParentWidget<>().size(layout.getProgressBar().progressSize()))
                    .setRecipeViewerLayoutCapabilityLayoutBuilder(ItemRecipeCapability.CAP, GreateRecipeViewerCapabilityLayoutBuilder.KINETIC_STONECUTTING_ITEM)
                    .addRecipeUIModifier((recipe, widget) -> widget.textComponents.paddingTop(30)));

    public static void register() {
        GTRecipeTypes.MACERATOR_RECIPES.onRecipeBuild((gtRecipeBuilder, provider) -> {
            GTRecipeBuilder copiedRecipe = MILLING
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
                GTRecipeBuilder copiedRecipe = CUTTING
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
