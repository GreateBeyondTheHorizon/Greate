package electrolyte.greate.content.kinetics.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import electrolyte.greate.GreateValues;

import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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

    private ProcessingRecipe<RecipeWrapper> lastRecipe;
    private int tier;
    private int maxItemsPerRecipe;

    public TieredMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inputInv = new ItemStackHandler(1);
        outputInv = new ItemStackHandler(1);
        capability = LazyOptional.of(TieredMillstoneInventoryHandler::new);
        tier = ((TieredMillstoneBlock) state.getBlock()).getTier();
        maxItemsPerRecipe = tier * 2;
    }

    @Override
    public void tick() {
        super.tick();

        if(getSpeed() == 0) return;

        for(int i = 0; i < outputInv.getSlots(); i++) {
            if(outputInv.getStackInSlot(i).getCount() == outputInv.getSlotLimit(i)) return;
            if(outputInv.getStackInSlot(i).getCount() + Math.min(inputInv.getStackInSlot(0).getCount(), maxItemsPerRecipe) > outputInv.getSlotLimit(i)) return;
        }

        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);

        if(timer > 0 && lastRecipe != null && lastRecipe.matches(inventoryIn, level)) {
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

        if(lastRecipe == null || !lastRecipe.matches(inventoryIn, level)) {
            Optional<ProcessingRecipe<RecipeWrapper>> recipe = findRecipe(inventoryIn);
            if(recipe.isEmpty()) {
                timer = 100;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingDuration();
                sendData();
            }
            return;
        }

        timer = lastRecipe.getProcessingDuration();
        sendData();
    }

    @Override
    public void spawnParticles() {
        if(lastRecipe != null && lastRecipe.matches(new RecipeWrapper(inputInv), level)) {
            super.spawnParticles();
        }
    }

    public Optional<ProcessingRecipe<RecipeWrapper>> findRecipe(RecipeWrapper wrapper) {
        Optional<ProcessingRecipe<RecipeWrapper>> millingRecipe = ModRecipeTypes.MILLING.find(wrapper, level, tier);
        if(millingRecipe.isEmpty()) {
            millingRecipe = AllRecipeTypes.MILLING.find(wrapper, level);
        }
        if (millingRecipe.isEmpty()) {
            Optional<GTRecipe> test = level.getRecipeManager().getAllRecipesFor(GTRecipeTypes.MACERATOR_RECIPES).stream().filter(r ->
                    ((Ingredient) r.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).test(wrapper.getItem(0))).findFirst();
            if(test.isPresent()) {
                TieredProcessingRecipe<RecipeWrapper> convertedRecipe = TieredMillingRecipe.convertGT(test.get(), tier);
                if(convertedRecipe.getRecipeTier() <= tier) {
                    return Optional.of(convertedRecipe);
                }
            }
        }
        return millingRecipe;
    }

    private void process() {
        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);

        if(lastRecipe == null || !lastRecipe.matches(inventoryIn, level)) {
            Optional<ProcessingRecipe<RecipeWrapper>> recipe = findRecipe(inventoryIn);

            if(recipe.isEmpty()) return;
            lastRecipe = recipe.get();
        }

        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        int amountInSlot = stackInSlot.getCount();
        stackInSlot.shrink(maxItemsPerRecipe);
        inputInv.setStackInSlot(0, stackInSlot);
        for(int i = 0; i < Math.min(amountInSlot, maxItemsPerRecipe); i++) {
            lastRecipe.rollResults().forEach(stack -> ItemHandlerHelper.insertItemStacked(outputInv, stack, false));
        }
        award(AllAdvancements.MILLSTONE);

        sendData();
        setChanged();
    }

    private boolean canProcess(ItemStack stack) {
        ItemStackHandler tester = new ItemStackHandler(1);
        tester.setStackInSlot(0, stack);
        RecipeWrapper inventoryIn = new RecipeWrapper(tester);

        if(lastRecipe != null && lastRecipe.matches(inventoryIn, level)) return true;
        return inputInv.getStackInSlot(0).isEmpty() && outputInv.getStackInSlot(0).isEmpty();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity);
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
