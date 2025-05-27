package electrolyte.greate.content.kinetics.millstone;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import electrolyte.greate.foundation.recipe.TieredRecipeHelper;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;

public class TieredMillstoneBlockEntity extends MillstoneBlockEntity implements ITieredKineticBlockEntity {
    private TieredMillingRecipe lastRecipe;
    private int tier;
    private static final Object MILLING_RECIPE_CACHE_KEY = new Object();

    public TieredMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        tier = ((TieredMillstoneBlock) state.getBlock()).getTier();
    }

    public void setupRecipe() {
        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);
        if(lastRecipe == null || !lastRecipe.matches(inventoryIn, level)) {
            Optional<Recipe<?>> recipe = findRecipe(inventoryIn);
            if(recipe.isEmpty()) {
                timer = 100;
                sendData();
            } else {
                lastRecipe = (TieredMillingRecipe) recipe.get();
                timer = ((TieredMillingRecipe) recipe.get()).getProcessingDuration();
                sendData();
            }
            return;
        }
        timer = lastRecipe.getProcessingDuration();
        sendData();
    }

    public void processRecipe() {
        if(inputInv.getStackInSlot(0).isEmpty()) return;
        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);

        if(lastRecipe == null || !lastRecipe.matches(inventoryIn, level)) {
            Optional<Recipe<?>> recipe = findRecipe(inventoryIn);

            if(recipe.isEmpty()) return;
            lastRecipe = (TieredMillingRecipe) recipe.get();
        }

        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        stackInSlot.shrink(1);
        inputInv.setStackInSlot(0, stackInSlot);
        List<ItemStack> results = TieredRecipeHelper.INSTANCE.getItemResults(lastRecipe, tier);
        results.forEach(stack -> ItemHandlerHelper.insertItemStacked(outputInv, stack, false));

        award(AllAdvancements.MILLSTONE);

        sendData();
        setChanged();
    }

    public boolean canProcess(ItemStack stack) {
        ItemStackHandler tester = new ItemStackHandler(1);
        tester.setStackInSlot(0, stack);
        RecipeWrapper inventoryIn = new RecipeWrapper(tester);

        if(lastRecipe != null && TieredRecipeFinder.shouldRefreshRecipe()) {
            lastRecipe = null;
        }
        if(lastRecipe != null && lastRecipe.matches(inventoryIn, level)) return true;
        return findRecipe(inventoryIn).isPresent();
    }

    public Optional<Recipe<?>> findRecipe(RecipeWrapper wrapper) {
        return TieredRecipeFinder.findRecipe(MILLING_RECIPE_CACHE_KEY, level, wrapper,
                RecipeConditions.isOfType(ModRecipeTypes.MILLING.getType())
                        .and(TieredRecipeConditions.firstIngredientMatches(wrapper.getItem(0))),
                TieredRecipeConditions.isEqualOrAboveTier(tier));
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }
}
