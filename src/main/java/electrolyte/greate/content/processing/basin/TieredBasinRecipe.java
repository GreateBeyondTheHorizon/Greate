package electrolyte.greate.content.processing.basin;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour.TankSegment;
import com.simibubi.create.foundation.recipe.DummyCraftingContainer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.content.kinetics.base.ICircuitHolder;
import electrolyte.greate.content.kinetics.mixer.TieredBrewingRecipe;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeParams;
import electrolyte.greate.content.processing.recipe.TieredStandardProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TieredBasinRecipe extends TieredStandardProcessingRecipe<RecipeInput> {

    public TieredBasinRecipe(TieredProcessingRecipeParams params) {
        this(ModRecipeTypes.BASIN, params);
    }

    protected TieredBasinRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    public static boolean match(BasinBlockEntity basin, Recipe<?> recipe, int machineTier) {
        FilteringBehaviour filter = basin.getFilter();
        int recipeTier = 0;
        if (filter == null) return false;

        boolean filterTest = filter.test(recipe.getResultItem(basin.getLevel().registryAccess()));
        if(recipe instanceof TieredBasinRecipe tpr) {
                recipeTier = tpr.getRecipeTier();
        }
        if (recipe instanceof BasinRecipe basinRecipe) {
            if (basinRecipe.getRollableResults().isEmpty()
                    && !basinRecipe.getFluidResults().isEmpty())
                filterTest = filter.test(basinRecipe.getFluidResults().get(0));
        }

        if (!filterTest) return false;
        if(machineTier < recipeTier) return false;
        return apply(basin, recipe, true);
    }

    public static boolean apply(BasinBlockEntity basin, Recipe<?> recipe) {
        return apply(basin, recipe, false);
    }

    private static boolean apply(BasinBlockEntity basin, Recipe<?> recipe, boolean test) {
        if(recipe instanceof BasinRecipe) return false;
        if(!AllConfigs.server().recipes.allowBrewingInMixer.get() && recipe instanceof TieredBrewingRecipe) return false;
        boolean isBasinRecipe = recipe instanceof BasinRecipe || recipe instanceof TieredBasinRecipe;
        boolean isTieredBasinRecipe = recipe instanceof TieredBasinRecipe;
        IItemHandler availableItems = basin.getLevel().getCapability(ItemHandler.BLOCK, basin.getBlockPos(), null);
        IFluidHandler availableFluids = basin.getLevel().getCapability(FluidHandler.BLOCK, basin.getBlockPos(), null);

        if (availableItems == null || availableFluids == null) return false;

        HeatLevel heat = BasinBlockEntity.getHeatLevelOf(basin.getLevel().getBlockState(basin.getBlockPos().below(1)));
        if ((isBasinRecipe) && !((ProcessingRecipe<?,?>) recipe).getRequiredHeat().testBlazeBurner(heat)) {
            return false;
        }

        if(isTieredBasinRecipe && basin.getLevel().getBlockState(basin.getBlockPos().above(2)).getBlock() instanceof ITieredBlock tieredBlock) {
            if(tieredBlock.getTier() < ((TieredBasinRecipe) recipe).getRecipeTier()) {
                return false;
            }
        }

        if(isTieredBasinRecipe && basin.getLevel().getBlockEntity(basin.getBlockPos().above(2)) instanceof ICircuitHolder circuitHolder) {
            if(((TieredBasinRecipe) recipe).getCircuitNumber() != -1 && circuitHolder.getCircuitNumber() != ((TieredBasinRecipe) recipe).getCircuitNumber()) {
                return false;
            }
        }

        List<ItemStack> recipeOutputItems = new ArrayList<>();
        List<FluidStack> recipeOutputFluids = new ArrayList<>();

        List<Ingredient> ingredients = new LinkedList<>(recipe.getIngredients());
        List<SizedFluidIngredient> fluidIngredients = isBasinRecipe
                ? ((ProcessingRecipe<?, ?>) recipe).getFluidIngredients()
                : Collections.emptyList();

        for (boolean simulate : Iterate.trueAndFalse) {

            if (!simulate && test) return true;

            int[] extractedItemsFromSlot = new int[availableItems.getSlots()];
            int[] extractedFluidsFromTank = new int[availableFluids.getTanks()];

            Ingredients: for(Ingredient ingredient : ingredients) {
                int amountRequired = ingredient.getItems()[0].getCount();
                for(int slot = 0; slot < availableItems.getSlots(); slot++) {
                    if(simulate && availableItems.getStackInSlot(slot).getCount() <= extractedItemsFromSlot[slot]) {
                        continue;
                    }
                    ItemStack extracted = availableItems.extractItem(slot, amountRequired, true);
                    if(!ingredient.test(extracted)) continue;
                    int removedAmount = Math.min(amountRequired, availableItems.getStackInSlot(slot).getCount());
                    if(!simulate) availableItems.extractItem(slot, amountRequired, false);
                    amountRequired -= removedAmount;
                    if(amountRequired != 0) continue;
                    extractedItemsFromSlot[slot]++;
                    continue Ingredients;
                }
                return false;
            }

            boolean fluidsAffected = false;
            FluidIngredients: for(SizedFluidIngredient fluidIngredient : fluidIngredients) {
                int amountRequired = fluidIngredient.amount(); //todo: check

                for(int tank = 0; tank < availableFluids.getTanks(); tank++) {
                    FluidStack fluidStack = availableFluids.getFluidInTank(tank);
                    if(simulate && fluidStack.getAmount() <= extractedFluidsFromTank[tank]) continue;
                    if(!fluidIngredient.test(fluidStack)) continue;
                    int drainedAmount = Math.min(amountRequired, fluidStack.getAmount());
                    if(!simulate) {
                        fluidStack.shrink(drainedAmount);
                        fluidsAffected = true;
                    }
                    amountRequired -= drainedAmount;
                    if(amountRequired != 0) continue;
                    extractedFluidsFromTank[tank] += drainedAmount;
                    continue FluidIngredients;
                }
                return false;
            }

            if (fluidsAffected) {
                basin.getBehaviour(SmartFluidTankBehaviour.INPUT).forEach(TankSegment::onFluidStackChanged);
                basin.getBehaviour(SmartFluidTankBehaviour.OUTPUT).forEach(TankSegment::onFluidStackChanged);
            }

            if (simulate) {
                CraftingInput remainderContainer = new DummyCraftingContainer(availableItems, extractedItemsFromSlot).asCraftInput();
                if(recipe instanceof TieredBasinRecipe basinRecipe) {
                    recipeOutputItems.addAll(basinRecipe.rollResults(basin.getLevel().random));

                    for(FluidStack stack : basinRecipe.getFluidResults()) {
                        if(!stack.isEmpty()) {
                            recipeOutputFluids.add(stack);
                        }
                    }
                    for(ItemStack stack : basinRecipe.getRemainingItems(remainderContainer)) {
                        if(!stack.isEmpty()) {
                            recipeOutputItems.add(stack);
                        }
                    }
                } else if(recipe instanceof BasinRecipe basinRecipe) {
                    recipeOutputItems.addAll(basinRecipe.rollResults(basin.getLevel().random));

                    for(FluidStack stack : basinRecipe.getFluidResults()) {
                        if(!stack.isEmpty()) {
                            recipeOutputFluids.add(stack);
                        }
                    }
                    for(ItemStack stack : basinRecipe.getRemainingItems(remainderContainer)) {
                        if(!stack.isEmpty()) {
                            recipeOutputItems.add(stack);
                        }
                    }
                } else {
                    recipeOutputItems.add(recipe.getResultItem(basin.getLevel().registryAccess()));

                    if (recipe instanceof CraftingRecipe craftingRecipe) {
                        for(ItemStack stack : craftingRecipe.getRemainingItems(remainderContainer)) {
                            if(!stack.isEmpty()) {
                                recipeOutputItems.add(stack);
                            }
                        }
                    }
                }
            }
            if (!basin.acceptOutputs(recipeOutputItems, recipeOutputFluids, simulate))
                return false;
        }
        return true;
    }

    public static RecipeHolder<TieredBasinRecipe> convertShapeless(RecipeHolder<?> recipe) {
        TieredBasinRecipe basinRecipe = new Builder<>(TieredBasinRecipe::new, recipe.id())
                        .withItemIngredients(recipe.value().getIngredients())
                        .withSingleItemOutput(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess()))
                        .build();
        return new RecipeHolder<>(recipe.id(), basinRecipe);
    }

    @Override
    protected int getMaxInputCount() {
        return 9;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 2;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 2;
    }

    @Override
    protected boolean canRequireHeat() {
        return true;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }

    @Override
    public boolean matches(RecipeInput pInput, @Nonnull Level pLevel) {
        return false;
    }
}
