package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredSawBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(SawBlockEntity.class)
public abstract class MixinSawBlockEntity extends BlockBreakingKineticBlockEntity {
    @Shadow public ProcessingInventory inventory;

    @Shadow private FilteringBehaviour filtering;

    @Shadow @Final public static Supplier<RecipeType<?>> woodcuttingRecipeType;

    @Shadow @Final private static Object cuttingRecipesKey;

    @Shadow private int recipeIndex;

    @Shadow protected abstract List<? extends Recipe<?>> getRecipes();

    @Shadow protected abstract boolean canProcess();

    public MixinSawBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "getRecipes", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate_getRecipes(CallbackInfoReturnable<List<? extends Recipe<?>>> cir) {
        if(this.getBlockState().getBlock() instanceof TieredSawBlock tsb) {
            TieredSawBlockEntity be = (TieredSawBlockEntity) level.getBlockEntity(this.getBlockPos());
            Predicate<Recipe<?>> recipeTypes = RecipeConditions.isOfType(AllRecipeTypes.CUTTING.getType(), ModRecipeTypes.CUTTING.getType(), GTRecipeTypes.CUTTER_RECIPES,
                    AllConfigs.server().recipes.allowStonecuttingOnSaw.get() ? RecipeType.STONECUTTING : null,
                    AllConfigs.server().recipes.allowWoodcuttingOnSaw.get() ? woodcuttingRecipeType.get() : null);
            List<Recipe<?>> startedSearch = RecipeFinder.get(cuttingRecipesKey, level, recipeTypes);
            IFluidHandler availableFluid = be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
            if(availableFluid == null) cir.cancel();
            List<Recipe<?>> valid = startedSearch.stream()
                    .filter(TieredRecipeConditions.outputMatchesFilter(filtering))
                    .filter(TieredRecipeConditions.firstIngredientMatches(inventory.getStackInSlot(0)))
                    .filter(TieredRecipeConditions.firstFluidMatches(availableFluid.getFluidInTank(0)))
                    .filter(TieredRecipeConditions.isEqualOrAboveTier(tsb.getTier()))
                    .filter(r -> !AllRecipeTypes.shouldIgnoreInAutomation(r))
                    .filter(r -> !ModRecipeTypes.shouldIgnoreInAutomation(r))
                    .collect(Collectors.toList());
            cir.setReturnValue(valid);
            cir.cancel();
        }
    }

    @Inject(method = "applyRecipe", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate_applyRecipe(CallbackInfo ci) {
        if(this.getBlockState().getBlock() instanceof TieredSawBlock tsb) {
            TieredSawBlockEntity be = (TieredSawBlockEntity) level.getBlockEntity(this.getBlockPos());
            List<? extends Recipe<?>> recipes = getRecipes();
            if(recipes.isEmpty()) return;
            if(recipeIndex >= recipes.size()) recipeIndex = 0;

            Recipe<?> recipe = recipes.get(recipeIndex);
            if(recipe instanceof GTRecipe gtr) {
                recipe = TieredCuttingRecipe.convertGTCutter(gtr, tsb.getTier());
            }
            int rolls = inventory.getStackInSlot(0).getCount();
            IFluidHandler availableFluid = be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
            if(availableFluid == null) ci.cancel();
            inventory.clear();
            List<ItemStack> list = new ArrayList<>();
            for(int roll = 0; roll < rolls; roll++) {
                List<ItemStack> results = new LinkedList<>();
                if(recipe instanceof ProcessingRecipe<?> pr) {
                    results = pr.rollResults();
                    if(!pr.getFluidIngredients().isEmpty()) {
                        availableFluid.drain(pr.getFluidIngredients().get(0).getRequiredAmount(), FluidAction.EXECUTE);
                    }
                } else if(recipe instanceof StonecutterRecipe || recipe.getType() == woodcuttingRecipeType.get()) {
                    results.add(recipe.getResultItem(level.registryAccess()).copy());
                }

                for(ItemStack stack : results) {
                    ItemHelper.addToList(stack, list);
                }
            }

            for(int slot = 0; slot < list.size() && slot + 1 < inventory.getSlots(); slot++) {
                inventory.setStackInSlot(slot + 1, list.get(slot));
            }
            award(AllAdvancements.SAW_PROCESSING);
            ci.cancel();
        }
    }

    @Inject(method = "start", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate_start(ItemStack inserted, CallbackInfo ci) {
        if(this.getBlockState().getBlock() instanceof TieredSawBlock) {
            if(!canProcess()) return;
            if(inventory.isEmpty()) return;
            if(level.isClientSide && !isVirtual()) return;

            List<? extends Recipe<?>> recipes = getRecipes();
            boolean valid = !recipes.isEmpty();
            int time = 50;

            if(recipes.isEmpty()) {
                inventory.remainingTime = inventory.recipeDuration = 10;
                inventory.appliedRecipe = false;
                sendData();
                return;
            }

            if(valid) {
                recipeIndex++;
                if(recipeIndex >= recipes.size()) {
                    recipeIndex = 0;
                }
            }

            Recipe<?> recipe = recipes.get(recipeIndex);
            if(recipe instanceof ProcessingRecipe<?> pr) {
                time = pr.getProcessingDuration();
            } else if(recipe instanceof GTRecipe gtr) {
                time = gtr.duration;
            }

            inventory.remainingTime = time * Math.max(1, (inserted.getCount() / 5));
            inventory.recipeDuration = inventory.remainingTime;
            inventory.appliedRecipe = false;
            sendData();
            ci.cancel();
        }
    }
}
