package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import electrolyte.greate.GreateValues;

import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Predicate;

public class TieredRecipeConditions {

    public static Predicate<Recipe<?>> firstIngredientMatches(ItemStack stack) {
        return r -> {
            if(r instanceof ProcessingRecipe<?>) {
                return !r.getIngredients().isEmpty() && r.getIngredients().get(0).test(stack);
            } else if(r instanceof GTRecipe gtr) {
                List<Content> inputIngredients = gtr.getInputContents(ItemRecipeCapability.CAP);
                Ingredient ing = (Ingredient) inputIngredients.get(0).getContent();
                return !inputIngredients.isEmpty() && ing.test(stack);
            }
            return false;
        };
    }

    public static Predicate<Recipe<?>> firstFluidMatches(FluidStack stack) {
        return r -> {
            if(r instanceof ProcessingRecipe<?> pr) {
                if(!pr.getFluidIngredients().isEmpty()) {
                    boolean testFluid = pr.getFluidIngredients().get(0).test(stack);
                    return testFluid && pr.getFluidIngredients().get(0).getRequiredAmount() <= stack.getAmount();
                } else {
                    return true;
                }
            } else if(r instanceof GTRecipe gtr) {
                List<Content> fluidInputIngredients = gtr.getInputContents(FluidRecipeCapability.CAP);
                FluidIngredient ing = (FluidIngredient) fluidInputIngredients.get(0).getContent();
                if(!ing.isEmpty()) {
                    boolean testFluid = ing.test(com.lowdragmc.lowdraglib.side.fluid.FluidStack.create(stack.getFluid(), stack.getAmount()));
                    return testFluid && ing.getAmount() <= stack.getAmount();
                } else {
                    return true;
                }
            }
            return false;
        };
    }

    public static Predicate<Recipe<?>> outputMatchesFilter(FilteringBehaviour filter) {
        return r -> {
            if(r instanceof ProcessingRecipe<?> pr) {
                return filter.test(pr.getResultItem(filter.getWorld().registryAccess()));
            } else if(r instanceof GTRecipe gtr) {
                List<Content> output = gtr.getOutputContents(ItemRecipeCapability.CAP);
                Ingredient ing = (Ingredient) output.get(0).getContent();
                return filter.test(ing.getItems()[0]);
            }
            return false;
        };
    }

    public static Predicate<Recipe<?>> isEqualOrAboveTier(int machineTier) {
        return r -> {
            if(r instanceof TieredProcessingRecipe<?> pr) {
                return pr.getRecipeTier() <= machineTier;
            } else if(r instanceof GTRecipe gtr) {
                return GreateValues.convertGTEUToTier(gtr.getTickInputContents(EURecipeCapability.CAP)) <= machineTier;
            }
            return true; //todo: check default create recipes
        };
    }

    public static Predicate<Recipe<?>> circuitMatches(int machineCircuitNumber) {
        return r -> {
            if(r instanceof TieredProcessingRecipe<?> pr) {
                if(pr.getCircuitNumber() != -1) {
                    return pr.getCircuitNumber() == machineCircuitNumber;
                }
            } else if(r instanceof GTRecipe gtr) {
                for(Content c : gtr.getInputContents(ItemRecipeCapability.CAP)) {
                    Ingredient ing = ((Ingredient) c.getContent());
                    if(ing.getItems()[0].is(GTItems.INTEGRATED_CIRCUIT.get())) {
                        int circuit = IntCircuitBehaviour.getCircuitConfiguration(ing.getItems()[0]);
                        return circuit == machineCircuitNumber;
                    }
                }
            }
            return true;
        };
    }
}
