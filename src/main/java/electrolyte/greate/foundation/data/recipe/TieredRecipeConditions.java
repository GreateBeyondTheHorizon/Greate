package electrolyte.greate.foundation.data.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.neoforged.neoforge.fluids.FluidStack;

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
            if(r.value() instanceof TieredProcessingRecipe<?,?>) {
                return (r.value().getIngredients().get(0).getItems()[0].getCount() <= stack.getCount());
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
                    //TODO:check
                    return testFluid && pr.getFluidIngredients().get(0).amount() <= stack.getAmount();
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
            }
            return true;
        };
    }
}
