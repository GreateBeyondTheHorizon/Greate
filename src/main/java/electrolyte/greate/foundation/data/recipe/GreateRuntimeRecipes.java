package electrolyte.greate.foundation.data.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.kinetics.mixer.TieredBrewingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.material.Fluids;

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
}
