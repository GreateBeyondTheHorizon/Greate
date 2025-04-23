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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;

public class TieredMillstoneBlockEntity extends MillstoneBlockEntity implements ITieredKineticBlockEntity {
    public int timer;
    private Recipe<? extends Container> lastRecipe;
    private int tier;
    private static final Object MILLING_RECIPE_CACHE_KEY = new Object();

    public TieredMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        capability = LazyOptional.of(TieredMillstoneInventoryHandler::new);
        tier = ((TieredMillstoneBlock) state.getBlock()).getTier();
    }

    @Override
    public void tick() {
        super.tick();

        if(getSpeed() == 0) return;

        for(int i = 0; i < outputInv.getSlots(); i++) {
            if(outputInv.getStackInSlot(i).getCount() == outputInv.getSlotLimit(i)) return;
        }

        if(timer > 0) {
            timer -= getProcessingSpeed();

            if(level.isClientSide) {
                spawnParticles();
                return;
            }

            if(timer <= 0) {
                process();
            }
            return;
        }

        if(inputInv.getStackInSlot(0).isEmpty()) return;

        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);
        if(lastRecipe == null || !TieredRecipeHelper.INSTANCE.firstIngredientMatches(lastRecipe, inventoryIn)) {
            Optional<Recipe<?>> recipe = findRecipe(inventoryIn);
            if(recipe.isEmpty()) {
                timer = 100;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = TieredRecipeHelper.INSTANCE.findDuration(recipe.get());
                sendData();
            }
            return;
        }
        timer = TieredRecipeHelper.INSTANCE.findDuration(lastRecipe);
        sendData();
    }

    private void process() {
        if(inputInv.getStackInSlot(0).isEmpty()) return;
        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);

        if(lastRecipe == null || !TieredRecipeHelper.INSTANCE.firstIngredientMatches(lastRecipe, inventoryIn)) {
            Optional<Recipe<?>> recipe = findRecipe(inventoryIn);

            if(recipe.isEmpty()) return;
            lastRecipe = recipe.get();
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

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        timer = compound.getInt("Timer");
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("Timer", timer);
    }

    private boolean canProcess(ItemStack stack) {
        ItemStackHandler tester = new ItemStackHandler(1);
        tester.setStackInSlot(0, stack);
        RecipeWrapper inventoryIn = new RecipeWrapper(tester);

        if(lastRecipe != null && TieredRecipeFinder.shouldRefreshRecipe()) {
            lastRecipe = null;
        }
        if(lastRecipe != null && TieredRecipeHelper.INSTANCE.firstIngredientMatches(lastRecipe, inventoryIn)) return true;
        return findRecipe(inventoryIn).isPresent();
    }

    private Optional<Recipe<?>> findRecipe(RecipeWrapper wrapper) {
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

    private class TieredMillstoneInventoryHandler extends CombinedInvWrapper {

        public TieredMillstoneInventoryHandler() {
            super(inputInv, outputInv);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if(outputInv == getHandlerFromIndex(getIndexForSlot(slot))) return false;
            return canProcess(stack) && super.isItemValid(slot, stack);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if(outputInv == getHandlerFromIndex(getIndexForSlot(slot))) return stack;
            if(!isItemValid(slot, stack)) return stack;
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if(inputInv == getHandlerFromIndex(getIndexForSlot(slot))) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
    }
}
