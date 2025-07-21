package electrolyte.greate.foundation.data.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.kind.GTRecipe;
import com.gregtechceu.gtceu.common.item.behavior.IntCircuitBehaviour;
import com.gregtechceu.gtceu.data.item.GTItems;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Predicate;

public class TieredRecipeConditions {

    public static Predicate<RecipeHolder<? extends Recipe<?>>> firstIngredientMatches(ItemStack stack) {
        return r -> {
            if(r.value() instanceof ProcessingRecipe<?, ?> || r.value() instanceof StonecutterRecipe) {
                return !r.value().getIngredients().isEmpty() && r.value().getIngredients().get(0).test(stack);
            }
            return false;
        };
    }

    public static Predicate<RecipeHolder<? extends Recipe<?>>> firstIngredientCountMatches(ItemStack stack) {
        return r -> {
            if(r.value() instanceof TieredProcessingRecipe<?, ?>) {
                return (r.value().getIngredients().get(0).getItems()[0].getCount() <= stack.getCount());
            }
            //TODO: check if needed
            else if(r.value() instanceof GTRecipe gtr) {
                List<Content> inputIngredients = gtr.getInputContents(ItemRecipeCapability.CAP);
                Ingredient ing = (Ingredient) inputIngredients.get(0).getContent();
                return ing.getItems()[0].getCount() <= stack.getCount();
            }
            return true;
        };
    }

    public static Predicate<RecipeHolder<? extends Recipe<?>>> firstFluidMatches(FluidStack stack) {
        return r -> {
            if(r.value() instanceof StonecutterRecipe) return true;
            if(r.value() instanceof ProcessingRecipe<?, ?> pr) {
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

    public static Predicate<RecipeHolder<? extends Recipe<?>>> outputMatchesFilter(FilteringBehaviour filter) {
        return r -> {
             if(r.value() instanceof StonecutterRecipe) {
                return filter.test(r.value().getResultItem(filter.getWorld().registryAccess()));
            } else if(r.value() instanceof ProcessingRecipe<?, ?> pr) {
                return filter.test(pr.getResultItem(filter.getWorld().registryAccess()));
            }
            return false;
        };
    }

    public static Predicate<RecipeHolder<? extends Recipe<?>>> isEqualOrAboveTier(int machineTier) {
        return r -> {
            if(r.value() instanceof TieredProcessingRecipe<?, ?> pr) {
                return pr.getRecipeTier() <= machineTier;
            }
            return true;
        };
    }

    public static Predicate<RecipeHolder<? extends Recipe<?>>> circuitMatches(int machineCircuitNumber) {
        return r -> {
            if(r.value() instanceof TieredProcessingRecipe<?, ?> pr) {
                if(pr.getCircuitNumber() != -1) {
                    return pr.getCircuitNumber() == machineCircuitNumber;
                }
                //TODO: check if needed
            } else if(r.value() instanceof GTRecipe gtr) {
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
