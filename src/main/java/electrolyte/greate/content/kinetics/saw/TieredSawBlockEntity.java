package electrolyte.greate.content.kinetics.saw;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.foundation.utility.CreateLang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.mixin.MixinSawBlockEntityAccessor;
import electrolyte.greate.registry.ModBlockEntityTypes;
import electrolyte.greate.registry.ModRecipeTypes;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TieredSawBlockEntity extends SawBlockEntity implements ITieredKineticBlockEntity {

    private int tier;
    private SmartFluidTankBehaviour inputTank;
    private IFluidHandler fluidCapability;

    public TieredSawBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tier = ((ITieredBlock) state.getBlock()).getTier();
        inventory = new ProcessingInventory(this::start);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(FluidHandler.BLOCK, ModBlockEntityTypes.TIERED_SAW.get(),
                (be, ctx) -> be.fluidCapability);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, 16000, false);
        behaviours.add(inputTank);

        fluidCapability = new CombinedTankWrapper(inputTank.getCapability());
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
        if(canProcess()) {
            if(fluidCapability == null) {
                fluidCapability = new FluidTank(0);
            }
            LangBuilder mb = CreateLang.translate("generic.unit.millibuckets");
            FluidStack fluidStack = fluidCapability.getFluidInTank(0);
            if(!fluidStack.isEmpty()) {
                Lang.builder(Greate.MOD_ID).translate("gui.goggles.saw_contents").style(ChatFormatting.GRAY).forGoggles(tooltip);
                CreateLang.text("")
                        .add(CreateLang.fluidName(fluidStack)
                                .add(CreateLang.text(" ")).style(ChatFormatting.GRAY)
                                .add(CreateLang.number(fluidStack.getAmount()).add(mb).style(ChatFormatting.BLUE)))
                        .forGoggles(tooltip, 1);
            } else {
                tooltip.remove(0);
            }
        }
        return true;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }

    public List<RecipeHolder<? extends Recipe<?>>> getValidRecipes() {
        TieredSawBlockEntity be = (TieredSawBlockEntity) level.getBlockEntity(this.getBlockPos());
        Optional<RecipeHolder<CuttingRecipe>> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, inventory.getStackInSlot(0), AllRecipeTypes.CUTTING.getType(), CuttingRecipe.class);
        Optional<RecipeHolder<TieredCuttingRecipe>> tieredAssemblyRecipe = SequencedAssemblyRecipe.getRecipe(level, inventory.getStackInSlot(0), ModRecipeTypes.CUTTING.getType(), TieredCuttingRecipe.class);
        FilteringBehaviour filtering = ((MixinSawBlockEntityAccessor) this).getFilteringBehaviour();
        Object cuttingRecipesKey = ((MixinSawBlockEntityAccessor) this).getCuttingRecipesKey();
        if(assemblyRecipe.isPresent() && filtering.test(assemblyRecipe.get().value().getResultItem(level.registryAccess()))) {
            return ImmutableList.of(assemblyRecipe.get());
        }
        if(tieredAssemblyRecipe.isPresent() && filtering.test(tieredAssemblyRecipe.get().value().getResultItem(level.registryAccess()))) {
            Predicate<RecipeHolder<? extends Recipe<?>>> predicate = TieredRecipeConditions.isEqualOrAboveTier(tier);
            if(predicate.test(tieredAssemblyRecipe.get())) {
                return ImmutableList.of(tieredAssemblyRecipe.get());
            }
        }
        Predicate<RecipeHolder<? extends Recipe<?>>> recipeTypes = RecipeConditions.isOfType(AllRecipeTypes.CUTTING.getType(), ModRecipeTypes.CUTTING.getType(),
                AllConfigs.server().recipes.allowStonecuttingOnSaw.get() ? RecipeType.STONECUTTING : null);
        List<RecipeHolder<? extends Recipe<?>>> startedSearch = RecipeFinder.get(cuttingRecipesKey, level, recipeTypes);
        IFluidHandler availableFluid = inputTank.getCapability();
        if(availableFluid == null) return List.of();
        return startedSearch.stream()
                .filter(TieredRecipeConditions.outputMatchesFilter(filtering))
                .filter(TieredRecipeConditions.firstIngredientMatches(inventory.getStackInSlot(0)))
                .filter(TieredRecipeConditions.firstIngredientCountMatches(inventory.getStackInSlot(0)))
                .filter(TieredRecipeConditions.firstFluidMatches(availableFluid.getFluidInTank(0)))
                .filter(TieredRecipeConditions.isEqualOrAboveTier(tier))
                .filter(r -> !AllRecipeTypes.shouldIgnoreInAutomation(r))
                .filter(r -> !ModRecipeTypes.shouldIgnoreInAutomation(r))
                .collect(Collectors.toList());
    }

    public void applyValidRecipe() {
        ItemStack input = inventory.getStackInSlot(0);
        List<ItemStack> list = new ArrayList<>();
        if(PackageItem.isPackage(input)) {
            inventory.clear();
            ItemStackHandler results = PackageItem.getContents(input);
            for(int i = 0; i < results.getSlots(); i++) {
                ItemStack stack = results.getStackInSlot(i);
                if(!stack.isEmpty()) {
                    ItemHelper.addToList(stack, list);
                }
            }
            for(int slot = 0; slot < list.size() && slot + 1 < inventory.getSlots(); slot++) {
                inventory.setStackInSlot(slot + 1, list.get(slot));
            }
            return;
        }
        List<RecipeHolder<? extends Recipe<?>>> recipes = getValidRecipes();
        int recipeIndex = ((MixinSawBlockEntityAccessor) this).getRecipeIndex();
        if(recipes.isEmpty()) return;
        if(recipeIndex >= recipes.size()) recipeIndex = 0;

        RecipeHolder<? extends Recipe<?>> recipe = recipes.get(recipeIndex);
        int rolls = inventory.getStackInSlot(0).getCount();
        int requiredAmount = 1;
        if(recipe.value() instanceof TieredProcessingRecipe<?,?> tpr) {
            requiredAmount = tpr.getIngredients().get(0).getItems()[0].getCount();
            rolls /= requiredAmount;
        }
        IFluidHandler availableFluid = inputTank.getCapability();
        if(availableFluid == null) return;
        if(recipe.value() instanceof TieredProcessingRecipe<?,?> tpr) {
            inventory.setStackInSlot(0, input.copyWithCount(input.getCount() - (rolls * requiredAmount)));
        } else inventory.clear();
        for(int roll = 0; roll < rolls; roll++) {
            List<ItemStack> results = new LinkedList<>();
            if(recipe.value() instanceof ProcessingRecipe<?,?> pr) {
                results = pr.rollResults(this.level.random);
                if(!pr.getFluidIngredients().isEmpty()) {
                    //todo: check
                    availableFluid.drain(pr.getFluidIngredients().get(0).amount(), FluidAction.EXECUTE);
                }
            } else if(recipe.value() instanceof StonecutterRecipe || recipe.value().getType() == woodcuttingRecipeType.get()) {
                results.add(recipe.value().getResultItem(level.registryAccess()).copy());
            }

            for(ItemStack stack : results) {
                ItemHelper.addToList(stack, list);
            }
        }

        for(int slot = 0; slot < list.size() && slot + 1 < inventory.getSlots(); slot++) {
            inventory.setStackInSlot(slot + 1, list.get(slot));
        }
        award(AllAdvancements.SAW_PROCESSING);
    }

    @Override
    public void start(ItemStack inserted) {
        if(!canProcess()) return;
        if(inventory.isEmpty()) return;
        if(level.isClientSide && !isVirtual()) return;

        List<RecipeHolder<? extends Recipe<?>>> recipes = getValidRecipes();
        boolean valid = !recipes.isEmpty();
        int time = 50;

        if(recipes.isEmpty()) {
            inventory.remainingTime = inventory.recipeDuration = 10;
            inventory.appliedRecipe = false;
            sendData();
            return;
        }

        int recipeIndex = ((MixinSawBlockEntityAccessor) this).getRecipeIndex();
        if(valid) {
            recipeIndex++;
            if(recipeIndex >= recipes.size()) {
                ((MixinSawBlockEntityAccessor) this).setRecipeIndex(0);
            }
        }

        RecipeHolder<? extends Recipe<?>> recipe = recipes.get(((MixinSawBlockEntityAccessor) this).getRecipeIndex());
        if(recipe.value() instanceof ProcessingRecipe<?,?> pr) {
            time = pr.getProcessingDuration();
        }

        int timePer = inserted.getCount();
        if(recipe.value() instanceof TieredProcessingRecipe<?,?> tpr) {
            timePer /= tpr.getIngredients().get(0).getItems()[0].getCount();
        }
        inventory.remainingTime = time * Math.max(1, (timePer / 5));
        inventory.recipeDuration = inventory.remainingTime;
        inventory.appliedRecipe = false;
        sendData();
    }
}
