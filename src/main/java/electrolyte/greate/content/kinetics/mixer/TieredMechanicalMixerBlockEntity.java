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
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static electrolyte.greate.GreateValues.TM;

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
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, TM[tier], capacity, stress);
    }

    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> r) {
        return ((r instanceof CraftingRecipe && !(r instanceof IShapedRecipe<?>)
                && AllConfigs.server().recipes.allowShapelessInMixer.get() && r.getIngredients().size() > 1
                && !MechanicalPressBlockEntity.canCompress(r)) && !AllRecipeTypes.shouldIgnoreInAutomation(r)
                || r.getType() == ModRecipeTypes.MIXING.getType()
                || (AllConfigs.server().recipes.allowBrewingInMixer.get() && r.getType() == ModRecipeTypes.BREWING.getType()));
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

        List<Recipe<?>> recipes = RecipeFinder.get(getRecipeCacheKey(), level, this::matchStaticFilters);
        matchingRecipes = recipes.stream()
                .filter(this::matchBasinRecipe)
                .sorted((r1, r2) -> r2.getIngredients()
                        .size()
                        - r1.getIngredients()
                        .size())
                .collect(Collectors.toList());

        if (!AllConfigs.server().recipes.allowBrewingInMixer.get())
            return matchingRecipes;

        Optional<BasinBlockEntity> basin = getBasin();
        if (!basin.isPresent())
            return matchingRecipes;

        BasinBlockEntity basinBlockEntity = basin.get();
        if (basin.isEmpty())
            return matchingRecipes;

        IItemHandler availableItems = basinBlockEntity
                .getCapability(ForgeCapabilities.ITEM_HANDLER)
                .orElse(null);
        if (availableItems == null)
            return matchingRecipes;

        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty())
                continue;

            List<MixingRecipe> list = PotionMixingRecipes.BY_ITEM.get(stack.getItem());
            if (list == null)
                continue;
            for (MixingRecipe mixingRecipe : list)
                if (matchBasinRecipe(mixingRecipe))
                    matchingRecipes.add(mixingRecipe);
        }

        return matchingRecipes;
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
