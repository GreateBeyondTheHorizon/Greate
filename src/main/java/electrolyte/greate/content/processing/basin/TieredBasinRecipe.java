package electrolyte.greate.content.processing.basin;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour.TankSegment;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.DummyCraftingContainer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.base.ICircuitHolder;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TieredBasinRecipe extends TieredProcessingRecipe<SmartInventory> {

    public TieredBasinRecipe(TieredProcessingRecipeParams params) {
        this(ModRecipeTypes.BASIN, params);
    }

    protected TieredBasinRecipe(IRecipeTypeInfo typeInfo, TieredProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    public static boolean match(BasinBlockEntity basin, Recipe<?> recipe, TIER machineTier) {
        FilteringBehaviour filter = basin.getFilter();
        if (filter == null) return false;

        boolean filterTest = filter.test(recipe.getResultItem(basin.getLevel().registryAccess()));
        if (recipe instanceof ProcessingRecipe<?> basinRecipe) {
            if (basinRecipe.getRollableResults().isEmpty()
                    && !basinRecipe.getFluidResults().isEmpty())
                filterTest = filter.test(basinRecipe.getFluidResults().get(0));
        } else if(recipe instanceof GTRecipe basinRecipe) {
            if(!basinRecipe.getOutputContents(ItemRecipeCapability.CAP).isEmpty()) {
                Ingredient ing = (Ingredient) basinRecipe.getOutputContents(ItemRecipeCapability.CAP).get(0).getContent();
                filterTest = filter.test(ing.getItems()[0]);
        }
            if(basinRecipe.getOutputContents(ItemRecipeCapability.CAP).isEmpty()
                && !basinRecipe.getOutputContents(FluidRecipeCapability.CAP).isEmpty()) {
                com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient fluidStack = (com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient) basinRecipe.getOutputContents(FluidRecipeCapability.CAP).get(0).getContent();
                filterTest = filter.test(new FluidStack(fluidStack.getStacks()[0].getFluid(), (int) fluidStack.getAmount()));
            }
            if(!filterTest) return false;
            return apply(basin, basinRecipe, true, machineTier);
        }

        if (!filterTest) return false;
        return apply(basin, recipe, true);
    }

    public static boolean apply(BasinBlockEntity basin, Recipe<?> recipe) {
        return apply(basin, recipe, false);
    }

    public static boolean applyGT(BasinBlockEntity basin, Recipe<?> recipe, TIER machineTier) {
        if(!(recipe instanceof GTRecipe gtRecipe)) return false;
        return apply(basin, gtRecipe, false, machineTier);
    }

    private static boolean apply(BasinBlockEntity basin, Recipe<?> recipe, boolean test) {
        boolean isBasinRecipe = recipe instanceof BasinRecipe;
        boolean isTieredBasinRecipe = recipe instanceof TieredBasinRecipe;
        if(recipe instanceof GTRecipe) return false;
        IItemHandler availableItems = basin.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        IFluidHandler availableFluids = basin.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);

        if (availableItems == null || availableFluids == null) return false;

        HeatLevel heat = BasinBlockEntity.getHeatLevelOf(basin.getLevel().getBlockState(basin.getBlockPos().below(1)));
        if ((isBasinRecipe || isTieredBasinRecipe) && !((ProcessingRecipe<?>) recipe).getRequiredHeat().testBlazeBurner(heat)) {
            return false;
        }

        if(isTieredBasinRecipe && basin.getLevel().getBlockState(basin.getBlockPos().above(2)).getBlock() instanceof ITieredBlock tieredBlock) {
            if(tieredBlock.getTier().compareTo(((TieredBasinRecipe) recipe).getRecipeTier()) < 0) {
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
        List<FluidIngredient> fluidIngredients = isBasinRecipe
                ? ((ProcessingRecipe<?>) recipe).getFluidIngredients()
                : Collections.emptyList();

        for (boolean simulate : Iterate.trueAndFalse) {

            if (!simulate && test) return true;

            int[] extractedItemsFromSlot = new int[availableItems.getSlots()];
            int[] extractedFluidsFromTank = new int[availableFluids.getTanks()];

            Ingredients: for(Ingredient ingredient : ingredients) {
                for(int slot = 0; slot < availableItems.getSlots(); slot++) {
                    if(simulate && availableItems.getStackInSlot(slot).getCount() <= extractedItemsFromSlot[slot]) {
                        continue;
                    }
                    ItemStack extracted = availableItems.extractItem(slot, 1, true);
                    if(!ingredient.test(extracted)) continue;
                    if(!simulate) availableItems.extractItem(slot, 1, false);
                    extractedItemsFromSlot[slot]++;
                    continue Ingredients;
                }
                return false;
            }

            boolean fluidsAffected = false;
            FluidIngredients: for(FluidIngredient fluidIngredient : fluidIngredients) {
                int amountRequired = fluidIngredient.getRequiredAmount();

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
                if (recipe instanceof TieredBasinRecipe basinRecipe) {
                    recipeOutputItems.addAll(basinRecipe.rollResults());
                    recipeOutputFluids.addAll(basinRecipe.getFluidResults());
                    recipeOutputItems.addAll(basinRecipe.getRemainingItems(basin.getInputInventory()));
                } else if (recipe instanceof BasinRecipe basinRecipe) {
                    recipeOutputItems.addAll(basinRecipe.rollResults());
                    recipeOutputFluids.addAll(basinRecipe.getFluidResults());
                    recipeOutputItems.addAll(basinRecipe.getRemainingItems(basin.getInputInventory()));
                } else {
                    recipeOutputItems.add(recipe.getResultItem(basin.getLevel().registryAccess()));

                    if (recipe instanceof CraftingRecipe craftingRecipe) {
                        recipeOutputItems.addAll(craftingRecipe.getRemainingItems(new DummyCraftingContainer(availableItems, extractedItemsFromSlot)));
                    }
                }
            }
            if (!basin.acceptOutputs(recipeOutputItems, recipeOutputFluids, simulate))
                return false;
        }
        return true;
    }

    private static boolean apply(BasinBlockEntity basin, GTRecipe recipe, boolean test, TIER machineTier) {
        TieredBasinRecipe basinRecipe;
        if(Minecraft.getInstance().level != null) {
            basinRecipe = TieredMixingRecipe.convertGTMixing(recipe, machineTier);
        } else {
            return false;
        }
        IItemHandler availableItems = basin.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        IFluidHandler availableFluids = basin.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);

        if (availableItems == null || availableFluids == null) return false;

        HeatLevel heat = BasinBlockEntity.getHeatLevelOf(basin.getLevel().getBlockState(basin.getBlockPos().below(1)));
        if (!(basinRecipe.getRequiredHeat().testBlazeBurner(heat))) return false;

        if(basin.getLevel().getBlockState(basin.getBlockPos().above(2)).getBlock() instanceof ITieredBlock tieredBlock) {
            if(tieredBlock.getTier().compareTo(basinRecipe.getRecipeTier()) < 0) {
                return false;
            }
        }

        if(basin.getLevel().getBlockEntity(basin.getBlockPos().above(2)) instanceof ICircuitHolder circuitHolder) {
            if(basinRecipe.getCircuitNumber() != -1 && circuitHolder.getCircuitNumber() != basinRecipe.getCircuitNumber()) {
                return false;
            }
        }

        List<ItemStack> recipeOutputItems = new ArrayList<>();
        List<FluidStack> recipeOutputFluids = new ArrayList<>();

        List<Ingredient> ingredients = basinRecipe.getIngredients();
        List<FluidIngredient> fluidIngredients = basinRecipe.getFluidIngredients();

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
                    if(!simulate) {
                        availableItems.extractItem(slot, amountRequired, false);
                    }
                    amountRequired -= removedAmount;
                    if(amountRequired != 0) continue;
                    extractedItemsFromSlot[slot]++;
                    continue Ingredients;
                }
                return false;
            }

            boolean fluidsAffected = false;
            FluidIngredients: for(FluidIngredient fluidIngredient : fluidIngredients) {
                int amountRequired = fluidIngredient.getRequiredAmount();

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
                recipeOutputItems.addAll(basinRecipe.rollResults());
                recipeOutputFluids.addAll(basinRecipe.getFluidResults());
                recipeOutputItems.addAll(basinRecipe.getRemainingItems(basin.getInputInventory()));
            }
            if (!basin.acceptOutputs(recipeOutputItems, recipeOutputFluids, simulate)) return false;
        }
        return true;
    }

    public static TieredBasinRecipe convertShapeless(Recipe<?> recipe) {
        return new TieredProcessingRecipeBuilder<>(TieredBasinRecipe::new, recipe.getId())
                        .withItemIngredients(recipe.getIngredients())
                        .withSingleItemOutput(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                        .build();
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
    public boolean matches(SmartInventory pContainer, Level pLevel) {
        return false;
    }
}
