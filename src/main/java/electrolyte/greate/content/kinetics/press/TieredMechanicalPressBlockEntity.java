package electrolyte.greate.content.kinetics.press;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.base.ICircuitHolder;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredProcessingRecipeHolder;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.foundation.recipe.TieredRecipeApplier;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import electrolyte.greate.registry.ModRecipeTypes;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TieredMechanicalPressBlockEntity extends MechanicalPressBlockEntity implements ITieredKineticBlockEntity, ITieredProcessingRecipeHolder, ICircuitHolder {

    private int tier;
    private ScrollValueBehaviour targetCircuit;
    private static final Object PRESSING_RECIPE_CACHE_KEY = new Object();

    public TieredMechanicalPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tier = ((TieredMechanicalPressBlock) state.getBlock()).getTier();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        targetCircuit = new ScrollValueBehaviour(Lang.builder(Greate.MOD_ID).translate("tooltip.circuit_number").component(),
                this, new CircuitValueBoxTransform());
        targetCircuit.between(0, 32);
        behaviours.add(targetCircuit);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack stack = itemEntity.getItem();
        Optional<? extends Recipe<?>> recipe = getValidRecipe(stack);
        if(recipe.isEmpty()) return false;
        if(simulate) return true;

        ItemStack createdStack = ItemStack.EMPTY;
        pressingBehaviour.particleItems.add(stack.copyWithCount(1));
        if(canProcessInBulk() || stack.getCount() == 1) {
            TieredRecipeApplier.applyRecipeOn(itemEntity, recipe.get(), tier, true);
            createdStack = itemEntity.getItem().copy();
        } else {
            for(ItemStack result : TieredRecipeApplier.applyRecipeOn(level, ItemHandlerHelper.copyStackWithSize(stack, 1), recipe.get(), tier, true)) {
                if(createdStack.isEmpty()) {
                    createdStack = result.copy();
                }
                ItemEntity createdEntityStack = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                createdEntityStack.setDefaultPickUpDelay();
                createdEntityStack.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 0.05f));
                level.addFreshEntity(createdEntityStack);
            }
            if(recipe.get() instanceof TieredProcessingRecipe<?>) {
                stack.shrink(recipe.get().getIngredients().get(0).getItems()[0].getCount());
            } else {
                stack.shrink(1);
            }
        }

        if(!createdStack.isEmpty()) {
            onItemPressed(createdStack);
        }
        return true;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        Optional<? extends Recipe<?>> recipe = getValidRecipe(input.stack);
        if(recipe.isEmpty()) return false;
        if(simulate) return true;
        pressingBehaviour.particleItems.add(input.stack);
        List<ItemStack> outputStacks = TieredRecipeApplier.applyRecipeOn(level, canProcessInBulk() ?
                input.stack : ItemHandlerHelper.copyStackWithSize(input.stack, 1), recipe.get(), tier, true);
        for(ItemStack stack : outputStacks) {
            if(!stack.isEmpty()) {
                onItemPressed(stack);
                break;
            }
        }

        outputList.addAll(outputStacks);
        return true;
    }

    private static final RecipeWrapper pressingInv = new RecipeWrapper(new ItemStackHandler(1));

    public Optional<? extends Recipe<?>> getValidRecipe(ItemStack stack) {
        Optional<PressingRecipe> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, stack, AllRecipeTypes.PRESSING.getType(), PressingRecipe.class);
        Optional<TieredPressingRecipe> tieredAssemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, stack, ModRecipeTypes.PRESSING.getType(), TieredPressingRecipe.class);

        if(assemblyRecipe.isPresent()) {
            currentRecipe = assemblyRecipe.get();
            return assemblyRecipe;
        }
        if(tieredAssemblyRecipe.isPresent()) {
            Predicate<Recipe<?>> predicate = TieredRecipeConditions.isEqualOrAboveTier(tier).and(TieredRecipeConditions.circuitMatches(targetCircuit.getValue()));
            if(predicate.test(tieredAssemblyRecipe.get())) {
                currentRecipe = tieredAssemblyRecipe.get();
                return tieredAssemblyRecipe;
            }
        }

        pressingInv.setItem(0, stack);
        Optional<Recipe<?>> recipe = TieredRecipeFinder.findRecipe(PRESSING_RECIPE_CACHE_KEY, level, pressingInv,
                RecipeConditions.isOfType(ModRecipeTypes.PRESSING.getType())
                        .and(TieredRecipeConditions.firstIngredientMatches(stack)),
                TieredRecipeConditions.isEqualOrAboveTier(tier)
                        .and(TieredRecipeConditions.circuitMatches(targetCircuit.getValue()))
                        .and(TieredRecipeConditions.firstIngredientCountMatches(stack)));
        if(recipe.isPresent()) {
            currentRecipe = recipe.get();
            return recipe;
        }
        return Optional.empty();
    }

    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> recipe) {
        return (recipe instanceof CraftingRecipe && !(recipe instanceof MechanicalCraftingRecipe) && canCompress(recipe)
                && !AllRecipeTypes.shouldIgnoreInAutomation(recipe))
                || recipe.getType() == ModRecipeTypes.COMPACTING.getType();
    }

    @Override
    public Recipe<?> getRecipe() {
        return this.currentRecipe;
    }

    @Override
    public int getCircuitNumber() {
        return targetCircuit.getValue();
    }

    /*@Override
    protected boolean updateBasin() {
        if (!isSpeedRequirementFulfilled())
            return true;
        if (getSpeed() == 0)
            return true;
        if (isRunning())
            return true;
        if (level == null || level.isClientSide)
            return true;
        Optional<BasinBlockEntity> basin = getBasin();
        if (!basin.filter(BasinBlockEntity::canContinueProcessing).isPresent())
            return true;

        List<Recipe<?>> recipes = getMatchingRecipes();
        if (recipes.isEmpty())
            return true;
        currentRecipe = recipes.get(0);
        startProcessingBasin();
        sendData();
        return true;
    }*/

    @Override
    protected void applyBasinRecipe() {
        if (currentRecipe == null) return;
        Optional<BasinBlockEntity> optionalBasin = getBasin();
        if (!optionalBasin.isPresent()) return;
        BasinBlockEntity basin = optionalBasin.get();
        boolean wasEmpty = basin.canContinueProcessing();
        if(!TieredBasinRecipe.apply(basin, currentRecipe)) return;
        getProcessedRecipeTrigger().ifPresent(this::award);
        basin.inputTank.sendDataImmediately();

        if (wasEmpty && matchBasinRecipe(currentRecipe)) {
            continueWithPreviousRecipe();
            sendData();
        }

        basin.notifyChangeOfContents();
    }

    @Override
    protected <C extends Container> boolean matchBasinRecipe(Recipe<C> recipe) {
        if(recipe == null) return false;
        if(!(recipe instanceof TieredProcessingRecipe<C>) && !(recipe instanceof CraftingRecipe)) return false;
        Optional<BasinBlockEntity> basin = getBasin();
        return basin.filter(basinBlockEntity -> TieredBasinRecipe.match(basinBlockEntity, recipe, this.tier)).isPresent();
    }

    private class CircuitValueBoxTransform extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 9f, 15.5f);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            if(direction.getAxis().isVertical()) return false;
            return !((TieredMechanicalPressBlock) state.getBlock()).hasShaftTowards(level, getBlockPos(), state, direction);
        }
    }

    public int getTier() {
        return tier;
    }
}
