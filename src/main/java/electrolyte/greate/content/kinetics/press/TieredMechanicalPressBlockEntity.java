package electrolyte.greate.content.kinetics.press;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
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
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.base.ICircuitHolder;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredProcessingRecipeHolder;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TieredMechanicalPressBlockEntity extends MechanicalPressBlockEntity implements ITieredKineticBlockEntity, ITieredProcessingRecipeHolder, ICircuitHolder {

    private TIER tier;
    private double networkMaxCapacity;
    private ScrollValueBehaviour targetCircuit;
    private static final Object PRESSING_RECIPE_CACHE_KEY = new Object();
    private int remainingTime;

    public TieredMechanicalPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tier = ((TieredMechanicalPressBlock) state.getBlock()).getTier();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        behaviours.remove(pressingBehaviour);
        pressingBehaviour = new TieredPressingBehaviour(this);
        behaviours.add(pressingBehaviour);
        targetCircuit = new ScrollValueBehaviour(Lang.builder(Greate.MOD_ID).translate("tooltip.circuit_number").component(),
                this, new CircuitValueBoxTransform());
        targetCircuit.between(0, 32);
        behaviours.add(targetCircuit);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        if(compound.contains("Network")) {
            CompoundTag networkTag = compound.getCompound("Network");
            this.networkMaxCapacity = networkTag.getDouble("MaxCapacity");
        }
        super.read(compound, clientPacket);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if(hasNetwork()) {
            CompoundTag networkTag = compound.getCompound("Network");
            networkTag.putDouble("MaxCapacity", this.networkMaxCapacity);
        }
    }

    @Override
    public double getMaxCapacity() {
        return GConfigUtility.getMaxCapacityFromTier(tier);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        this.networkMaxCapacity = networkMaxCapacity;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity);
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack stack = itemEntity.getItem();
        Optional<PressingRecipe> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, stack, AllRecipeTypes.PRESSING.getType(), PressingRecipe.class);
        Optional<TieredPressingRecipe> recipe = getValidRecipe(stack);
        if(assemblyRecipe.isEmpty() && recipe.isEmpty()) return false;
        if(simulate) return true;

        ItemStack createdStack = ItemStack.EMPTY;
        pressingBehaviour.particleItems.add(stack);
        if(canProcessInBulk() || stack.getCount() == 1) {
            RecipeApplier.applyRecipeOn(itemEntity, assemblyRecipe.isPresent() ? assemblyRecipe.get() : recipe.get());
            createdStack = itemEntity.getItem().copy();
        } else {
            for(ItemStack result : RecipeApplier.applyRecipeOn(level, ItemHandlerHelper.copyStackWithSize(stack, 1), assemblyRecipe.isPresent() ? assemblyRecipe.get() : recipe.get())) {
                if(createdStack.isEmpty()) {
                    createdStack = result.copy();
                }
                ItemEntity createdEntityStack = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                createdEntityStack.setDefaultPickUpDelay();
                createdEntityStack.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 0.05f));
                level.addFreshEntity(createdEntityStack);
            }

            stack.shrink((assemblyRecipe.isPresent() ? assemblyRecipe.get() : recipe.get()).getIngredients().get(0).getItems()[0].getCount());
        }

        if(!createdStack.isEmpty()) {
            onItemPressed(createdStack);
        }
        return true;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        //todo: setup tiered machines in sequenced assembly recipes
        Optional<PressingRecipe> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, input.stack, AllRecipeTypes.PRESSING.getType(), PressingRecipe.class);
        Optional<TieredPressingRecipe> recipe = getValidRecipe(input.stack);
        if(assemblyRecipe.isEmpty() && recipe.isEmpty()) return false;
        if(simulate) return true;
        pressingBehaviour.particleItems.add(input.stack);
        List<ItemStack> outputStacks = RecipeApplier.applyRecipeOn(level, canProcessInBulk() ?
                input.stack : ItemHandlerHelper.copyStackWithSize(input.stack, 1), assemblyRecipe.isPresent() ? assemblyRecipe.get() : recipe.get());
        for(ItemStack stack : outputStacks) {
            if(!stack.isEmpty()) {
                onItemPressed(stack);
                break;
            }
        }

        outputList.addAll(outputStacks);
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if(remainingTime > 0) {
            remainingTime--;
        }
    }

    public Optional<TieredPressingRecipe> getValidRecipe(ItemStack item) {
        currentRecipe = null;
        List<Recipe<?>> list = new ArrayList<>();
        if(remainingTime == 0) {
            list = RecipeFinder.get(PRESSING_RECIPE_CACHE_KEY, level, p ->
                    p.getType() == GTRecipeTypes.BENDER_RECIPES ||
                            p.getType() == ModRecipeTypes.PRESSING.getType() ||
                            p.getType() == AllRecipeTypes.PRESSING.getType()).stream().filter(r -> {
                if(r.getType() == GTRecipeTypes.BENDER_RECIPES) {
                    GTRecipe recipe = (GTRecipe) r;
                    for(Content c : recipe.getInputContents(ItemRecipeCapability.CAP)) {
                        if(ItemRecipeCapability.CAP.copyContent(c.getContent()).test(item)) {
                            if(ItemRecipeCapability.CAP.copyContent(c.getContent()).getItems()[0].getCount() <= item.getCount()) {
                                return true;
                            }
                        }
                    }
                } else {
                    for(Ingredient i : r.getIngredients()) {
                        if(i.test(item)) {
                            if(i.getItems()[0].getCount() <= item.getCount()) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }).collect(Collectors.toList());
        }
        if(list.isEmpty()) {
            remainingTime = 10;
        } else if(Minecraft.getInstance().level != null) {
            for(Recipe<?> recipe : list) {
                if(recipe.getType() == GTRecipeTypes.BENDER_RECIPES) {
                    for(Content c : ((GTRecipe) recipe).getInputContents(ItemRecipeCapability.CAP)) {
                        if(TIER.convertGTEUToTier(((GTRecipe) recipe).getTickInputContents(EURecipeCapability.CAP)).compareTo(this.tier) <= 0) {
                            if(((Ingredient) c.getContent()).getItems()[0].is(GTItems.INTEGRATED_CIRCUIT.get())) {
                                int circuit = IntCircuitBehaviour.getCircuitConfiguration(((Ingredient) c.getContent()).getItems()[0]);
                                if(circuit == targetCircuit.getValue()) {
                                    TieredPressingRecipe convertedRecipe = TieredPressingRecipe.convertGT((GTRecipe) recipe, this.tier);
                                    currentRecipe = convertedRecipe;
                                    return Optional.of(convertedRecipe);
                                }
                            }
                        }
                    }
                } else if(recipe.getType() == AllRecipeTypes.PRESSING.getType()) {
                    TieredPressingRecipe tpr = TieredPressingRecipe.convertNormalPressing(recipe);
                    currentRecipe = tpr;
                    return Optional.of(tpr);
                } else {
                    TieredPressingRecipe tpr = (TieredPressingRecipe) recipe;
                    if(tpr.getRecipeTier().compareTo(this.tier) <= 0) {
                        if(tpr.getCircuitNumber() == this.targetCircuit.getValue()) {
                            currentRecipe = tpr;
                            return Optional.of(tpr);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static <C extends Container> boolean canCompress(Recipe<C> recipe) {
        if(!(recipe instanceof CraftingRecipe) || !AllConfigs.server().recipes.allowShapedSquareInPress.get()) return false;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        return (ingredients.size() == 4 || ingredients.size() == 9) && ItemHelper.matchAllIngredients(ingredients);
    }

    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> recipe) {
        return (recipe instanceof CraftingRecipe && !(recipe instanceof MechanicalCraftingRecipe) && canCompress(recipe)
                && !AllRecipeTypes.shouldIgnoreInAutomation(recipe))
                || recipe.getType() == AllRecipeTypes.COMPACTING.getType()
                || recipe.getType() == ModRecipeTypes.COMPACTING.getType();
    }

    @Override
    public Recipe<?> getRecipe() {
        return this.currentRecipe;
    }

    @Override
    public void setRecipe(Recipe<?> recipe) {
        this.currentRecipe = recipe;
    }

    @Override
    public int getCircuitNumber() {
        return targetCircuit.getValue();
    }

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
}
