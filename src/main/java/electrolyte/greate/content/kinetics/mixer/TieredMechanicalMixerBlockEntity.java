package electrolyte.greate.content.kinetics.mixer;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.potion.PotionMixingRecipes;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.base.ICircuitHolder;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TieredMechanicalMixerBlockEntity extends MechanicalMixerBlockEntity implements ITieredKineticBlockEntity, ICircuitHolder {

    private int tier;
    private ScrollValueBehaviour targetCircuit;
    private static final Object SHAPELESS_OR_MIXING_RECIPES_KEY = new Object();

    public TieredMechanicalMixerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.tier = ((TieredMechanicalMixerBlock) state.getBlock()).getTier();
    }

    @Override
    public void tick() {
        if(this.getSpeed() == 0) running = false;
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickAudio() {
        if(this.getSpeed() == 0) return;
        super.tickAudio();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        targetCircuit = new ScrollValueBehaviour(Lang.builder(Greate.MOD_ID).translate("tooltip.circuit_number")
                .component(), this, new CircuitValueBoxTransform());
        targetCircuit.between(0, 32);
        behaviours.add(targetCircuit);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> r) {
        return ((r.value() instanceof CraftingRecipe && !(r.value() instanceof ShapedRecipe)
                && AllConfigs.server().recipes.allowShapelessInMixer.get() && r.value().getIngredients().size() > 1
                && !MechanicalPressBlockEntity.canCompress(r.value())) && !AllRecipeTypes.shouldIgnoreInAutomation(r)
                || r.value().getType() == ModRecipeTypes.MIXING.getType()
                || (AllConfigs.server().recipes.allowBrewingInMixer.get() && r.value().getType() == ModRecipeTypes.BREWING.getType()));
    }

    @Override
    protected Object getRecipeCacheKey() {
        return SHAPELESS_OR_MIXING_RECIPES_KEY;
    }

    @Override
    public int getCircuitNumber() {
        return targetCircuit.getValue();
    }

    @Override
    protected void applyBasinRecipe() {
        if(currentRecipe == null) return;
        if(this.getSpeed() == 0) return;

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

    public int getTier() {
        return tier;
    }

    @Override
    protected List<Recipe<?>> getMatchingRecipes() {
        List<Recipe<?>> matchingRecipes = new ArrayList<>();
        if (getBasin().map(BasinBlockEntity::isEmpty)
                .orElse(true))
            return matchingRecipes;

        List<Recipe<?>> recipes = new ArrayList<>();
        for(RecipeHolder<? extends Recipe<?>> r : RecipeFinder.get(getRecipeCacheKey(), level, this::matchStaticFilters)) {
            recipes.add(r.value());
        }
        recipes.sort((r1, r2) -> r2.getIngredients().size() - r1.getIngredients().size());

        if (!AllConfigs.server().recipes.allowBrewingInMixer.get())
            return matchingRecipes;

        Optional<BasinBlockEntity> basin = getBasin();
        if (!basin.isPresent())
            return matchingRecipes;

        BasinBlockEntity basinBlockEntity = basin.get();
        if (basin.isEmpty())
            return matchingRecipes;

        IItemHandler availableItems = level.getCapability(ItemHandler.BLOCK, basinBlockEntity.getBlockPos(), null);
        if (availableItems == null)
            return matchingRecipes;

        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty())
                continue;

            List<MixingRecipe> list = PotionMixingRecipes.sortRecipesByItem(level).get(stack.getItem());
            if (list == null) continue;
            for (MixingRecipe mixingRecipe : list)
                if (matchBasinRecipe(mixingRecipe)) {
                    matchingRecipes.add(mixingRecipe);
                }
        }

        return matchingRecipes;
    }

    @Override
    protected <I extends RecipeInput> boolean matchBasinRecipe(Recipe<I> recipe) {
        if(recipe == null) return false;
        Optional<BasinBlockEntity> basin = getBasin();
        return basin.filter(basinBlockEntity -> TieredBasinRecipe.match(basinBlockEntity, recipe, this.tier)).isPresent();
    }

    private class CircuitValueBoxTransform extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 12f, 15.8f);
        }

        @Override
        public float getScale() {
            return 0.25f;
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return !direction.getAxis().isVertical();
        }
    }
}
