package electrolyte.greate.foundation.recipe;

import com.lowdragmc.lowdraglib.misc.ItemHandlerHelper;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TieredRecipeApplier {

    public static void applyRecipeOn(ItemEntity entity, Recipe<?> recipe, int machineTier, boolean returnProcessingRemainder) {
        if(recipe instanceof ProcessingRecipe<?,?> || recipe instanceof SequencedAssemblyRecipe) {
            RecipeApplier.applyRecipeOn(entity, recipe, returnProcessingRemainder);
        } else {
            List<ItemStack> stacks = applyRecipeOn(entity.level(), entity.getItem(), recipe, machineTier, returnProcessingRemainder);
            if(stacks == null) return;
            if(stacks.isEmpty()) {
                entity.discard();
                return;
            }
            entity.setItem(stacks.remove(0));
            for(ItemStack stack : stacks) {
                ItemEntity entityIn = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                entityIn.setDeltaMovement(entity.getDeltaMovement());
                entity.level().addFreshEntity(entityIn);
            }
        }
    }

    public static List<ItemStack> applyRecipeOn(Level level, ItemStack stackIn, Recipe<?> recipe, int machineTier, boolean returnProcessingRemainder) {
        List<ItemStack> stacks;
        if(recipe instanceof TieredProcessingRecipe<?,?>) {
            stacks = new ArrayList<>();
            for(int i = 0; i < stackIn.getCount(); i++) {
                List<ItemStack> newOutputs = TieredRecipeHelper.INSTANCE.getItemResults(recipe, machineTier, level.random);
                for(ItemStack stack : newOutputs) {
                    for(ItemStack previouslyRolled : stacks) {
                        if(stack.isEmpty())
                            continue;
                        if(! ItemHandlerHelper.canItemStacksStack(stack, previouslyRolled))
                            continue;
                        int amount = Math.min(previouslyRolled.getMaxStackSize() - previouslyRolled.getCount(), stack.getCount());
                        previouslyRolled.grow(amount);
                        stack.shrink(amount);
                    }

                    if(stack.isEmpty())
                        continue;
                    stacks.add(stack);
                }
            }
            if(returnProcessingRemainder && stackIn.hasCraftingRemainingItem()) {
                ItemHelper.addToList(stackIn.getCraftingRemainingItem(), stacks);
            }
        } else {
            ItemStack out = recipe.getResultItem(level.registryAccess()).copy();
            stacks = ItemHelper.multipliedOutput(stackIn, out);
        }
        return stacks;
    }
}
