package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Predicate;

public class TieredRecipeConditions {

    public static Predicate<Recipe<?>> firstIngredientMatches(ItemStack stack) {
        return r -> {
            if(r instanceof ProcessingRecipe<?> || r instanceof StonecutterRecipe) {
                return !r.getIngredients().isEmpty() && r.getIngredients().get(0).test(stack);
            }
            return false;
        };
    }

    public static Predicate<Recipe<?>> firstIngredientCountMatches(ItemStack stack) {
        return r -> {
            if(r instanceof TieredProcessingRecipe<?>) {
                return (r.getIngredients().get(0).getItems()[0].getCount() <= stack.getCount());
            } else if(r instanceof GTRecipe gtr) {
                List<Content> inputIngredients = gtr.getInputContents(ItemRecipeCapability.CAP);
                Ingredient ing = (Ingredient) inputIngredients.get(0).getContent();
                return ing.getItems()[0].getCount() <= stack.getCount();
            }
            return true;
        };
    }

    public static Predicate<Recipe<?>> firstFluidMatches(FluidStack stack) {
        return r -> {
            if(r instanceof StonecutterRecipe) return true;
            if(r instanceof ProcessingRecipe<?> pr) {
                if(!pr.getFluidIngredients().isEmpty()) {
                    boolean testFluid = pr.getFluidIngredients().get(0).test(stack);
                    return testFluid && pr.getFluidIngredients().get(0).getRequiredAmount() <= stack.getAmount();
                } else {
                    return true;
                }
            }
            return false;
        };
    }

    public static Predicate<Recipe<?>> outputMatchesFilter(FilteringBehaviour filter) {
        return r -> {
             if(r instanceof StonecutterRecipe) {
                return filter.test(r.getResultItem(filter.getWorld().registryAccess()));
            } else if(r instanceof ProcessingRecipe<?> pr) {
                return filter.test(pr.getResultItem(filter.getWorld().registryAccess()));
            }
            return false;
        };
    }

    public static Predicate<Recipe<?>> isEqualOrAboveTier(int machineTier) {
        return r -> {
            if(r instanceof TieredProcessingRecipe<?> pr) {
                return pr.getRecipeTier() <= machineTier;
            }
            return true;
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
                    if(ing.getItems()[0].is(GTItems.PROGRAMMED_CIRCUIT.get())) {
                        int circuit = IntCircuitBehaviour.getCircuitConfiguration(ing.getItems()[0]);
                        return circuit == machineCircuitNumber;
                    }
                }
            }
            return true;
        };
    }
}
