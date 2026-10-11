package electrolyte.greate.compat.gtceu.api.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class GreateRecipeType extends GTRecipeType {
    public GreateRecipeType(ResourceLocation registryName, String group, RecipeType<?>... proxyRecipes) {
        super(registryName, group, proxyRecipes);
    }

    @Override
    public GTRecipe toGTrecipe(ResourceLocation id, Recipe<?> recipe) {
        if(recipe instanceof ProcessingRecipe<?> processingRecipe) {
            String recipePath = id.withPrefix("/integration/" + id.getNamespace() + "/").getPath();
            ResourceLocation recipeId = Greate.id(recipePath);
            GTRecipeBuilder builder = recipeBuilder(recipeId);
            builder.perTick(true);
            builder.input(StressRecipeCapability.STRESS_CAPABILITY, 7.0f);
            builder.perTick(false);
            builder.duration(processingRecipe.getProcessingDuration());
            for(var ingredient : processingRecipe.getIngredients()) {
                builder.inputItems(ingredient);
            }
            for(var ingredient : processingRecipe.getFluidIngredients()) {
                for(var fluidIngredient : ingredient.getMatchingFluidStacks()) {
                    builder.inputFluids(FluidIngredient.of(fluidIngredient.getFluid(), ingredient.getRequiredAmount())); //todo: check
                }
            }
            //builder.outputItems(processingRecipe.getResultItem(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY))); //todo:check if needed
            for(ProcessingOutput output : processingRecipe.getRollableResults()) {
                builder.chancedOutput(output.getStack(), (int) (output.getChance() * 10000));
            }
            for(var fluidOutput : processingRecipe.getFluidResults()) {
                builder.chancedOutput(new FluidStack(fluidOutput.getFluid(), fluidOutput.getAmount()), 10000); //todo:check (needs 100%)
            }
            return GTRecipeSerializer.SERIALIZER.fromJson(recipeId, builder.build().serializeRecipe());
        }
        return super.toGTrecipe(id, recipe);
    }

    public void addProxyRecipeType(RecipeType<?> recipeType) {
        this.proxyRecipes.put(recipeType, new ArrayList<>());
    }
}
