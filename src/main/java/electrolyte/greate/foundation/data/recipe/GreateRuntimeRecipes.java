package electrolyte.greate.foundation.data.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeFactory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.content.kinetics.mixer.TieredBrewingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

public class GreateRuntimeRecipes {

    public static final Object2ObjectOpenHashMap<ResourceLocation, JsonObject> JSON_FILES = new Object2ObjectOpenHashMap<>();
    public static final ResourceManagerReloadListener LISTENER = r -> {
        JSON_FILES.clear();
        JSON_FILES.trim();
    };

    public static void convertGTRecipe(TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> factory, GTRecipe recipe, boolean supportsDuration) {
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        int recipeCircuit = TieredProcessingRecipe.getCircuitFromGTRecipe(recipe.getInputContents(ItemRecipeCapability.CAP));
        new Builder<>(factory, recipe.getId())
                .withItemIngredientsGT(recipe.getInputContents(ItemRecipeCapability.CAP))
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP))
                .withFluidIngredientsGT(recipe.getInputContents(FluidRecipeCapability.CAP))
                .withFluidOutputsGT(recipe.getOutputContents(FluidRecipeCapability.CAP))
                .duration(supportsDuration ? recipe.duration : 0)
                .recipeTier(recipeTier)
                .recipeCircuit(recipeCircuit)
                .build();
    }

    public static void convertCreateRecipe(TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> factory, ResourceLocation recipeId, JsonElement recipeJson, String type) {
        TieredProcessingRecipeSerializer<TieredProcessingRecipe<?>> serializer = new TieredProcessingRecipeSerializer<>(factory);
        ProcessingRecipe<?> recipe = serializer.fromJson(recipeId, recipeJson.getAsJsonObject());
        TieredProcessingRecipeBuilder<?> builder = new Builder<>(factory, recipe.getId())
                .withItemIngredients(recipe.getIngredients())
                .withFluidIngredients(recipe.getFluidIngredients())
                .withItemOutputs(recipe.getRollableResults())
                .withFluidOutputs(recipe.getFluidResults())
                .duration(recipe.getProcessingDuration())
                .requiresHeat(recipe.getRequiredHeat())
                .recipeTier(ULV);

        if(type.equals(AllRecipeTypes.SPLASHING.getId().toString())) {
            //add water if no fluid specified to mimic default create
            builder.withFluidIngredients(FluidIngredient.fromFluid(Fluids.WATER, 1));
        }

        builder.build();
    }

    public static void convertCreateToGT(ProcessingRecipeFactory<ProcessingRecipe<?>> factory, GTRecipeType type, ResourceLocation recipeId, JsonElement recipeJson) {
        ProcessingRecipeSerializer<ProcessingRecipe<?>> serializer = new ProcessingRecipeSerializer<>(factory);
        ProcessingRecipe<?> recipe = serializer.fromJson(recipeId, recipeJson.getAsJsonObject());
        new GTBuilder(type.recipeBuilder(recipeId), recipe).build();
    }

    public static void convertPotionRecipe(MixingRecipe recipe) {
        new Builder<>(TieredBrewingRecipe::new, recipe.getId())
                .withItemIngredients(recipe.getIngredients())
                .withFluidIngredients(recipe.getFluidIngredients())
                .withFluidOutputs(recipe.getFluidResults())
                .duration(recipe.getProcessingDuration())
                .requiresHeat(recipe.getRequiredHeat())
                .recipeTier(ULV)
                .build();
    }

    private static class Builder<T extends TieredProcessingRecipe<?>> extends TieredProcessingRecipeBuilder<T> {

        public Builder(TieredProcessingRecipeFactory<T> factory, ResourceLocation recipeId) {
            super(factory, Greate.id("integration/" + recipeId.toString().replace(":", "/")));
        }

        @Override
        public T build() {
            T t = super.build();
            DataGenResult<T> result = new DataGenResult<>(t, Collections.emptyList());
            JSON_FILES.put(result.getId(), result.serializeRecipe());
            return t;
        }
    }

    private static class GTBuilder {

        private final GTRecipeBuilder builder;
        private final ResourceLocation recipeId;
        private final ProcessingRecipe<?> recipe;

        public GTBuilder(GTRecipeBuilder builder, ProcessingRecipe<?> recipe) {
            this.builder = builder;
            this.recipe = recipe;
            this.recipeId = Greate.id("integration/" + builder.id.toString().replace(":", "/"));
        }


        private void assembleComponents() {
            builder.duration(recipe.getProcessingDuration());
            builder.perTick(true);
            builder.input(StressRecipeCapability.STRESS_CAPABILITY, 2.0f);
            builder.perTick(false);
            for(Ingredient ing : recipe.getIngredients()) {
                builder.inputItems(ing);
            }
            for(FluidIngredient ing : recipe.getFluidIngredients()) {
                //TODO: check correct amount (and for output)
                builder.inputFluids(com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient.of(ing.getMatchingFluidStacks()));
            }
            for(ProcessingOutput result : recipe.getRollableResults()) {
                if(result.getChance() == 1.0) builder.outputItems(result.getStack());
                else builder.chancedOutput(result.getStack(), (int) (result.getChance() * 10000.0f));
            }
            for(FluidStack result : recipe.getFluidResults()) {
                builder.outputFluids(result);
            }
        }

        public void build() {
            assembleComponents();
            JSON_FILES.put(recipeId, builder.build().serializeRecipe());
        }
    }
}
