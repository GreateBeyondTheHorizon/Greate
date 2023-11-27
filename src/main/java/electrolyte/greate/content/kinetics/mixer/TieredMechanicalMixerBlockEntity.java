package electrolyte.greate.content.kinetics.mixer;

import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.base.ICircuitHolder;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.List;
import java.util.Optional;

public class TieredMechanicalMixerBlockEntity extends MechanicalMixerBlockEntity implements ITieredKineticBlockEntity, ICircuitHolder {

    private TIER tier;
    private double networkMaxCapacity;
    private ScrollValueBehaviour targetCircuit;
    private static final Object SHAPELESS_OR_MIXING_RECIPES_KEY = new Object();

    public TieredMechanicalMixerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.tier = ((TieredMechanicalMixerBlock) state.getBlock()).getTier();
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
    protected void read(CompoundTag compound, boolean clientPacket) {
        if(compound.contains("Network")) {
            CompoundTag networkTag = compound.getCompound("Network");
            this.networkMaxCapacity = networkTag.getDouble("MaxCapacity");
        }
        super.read(compound, clientPacket);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if(hasNetwork()) {
            CompoundTag networkTag = compound.getCompound("Network");
            networkTag.putDouble("MaxCapacity", this.networkMaxCapacity);
        }
    }

    @Override
    protected <C extends Container> boolean matchStaticFilters(Recipe<C> r) {
        return ((r instanceof CraftingRecipe && !(r instanceof IShapedRecipe<?>)
                && AllConfigs.server().recipes.allowShapelessInMixer.get() && r.getIngredients().size() > 1
                && !MechanicalPressBlockEntity.canCompress(r)) && ! AllRecipeTypes.shouldIgnoreInAutomation(r)
                || r.getType() == AllRecipeTypes.MIXING.getType()
                || r.getType() == ModRecipeTypes.MIXING.getType()
                || r.getType() == GTRecipeTypes.MIXER_RECIPES);
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
        if (currentRecipe == null) return;

        Optional<BasinBlockEntity> optionalBasin = getBasin();
        if (!optionalBasin.isPresent()) return;
        BasinBlockEntity basin = optionalBasin.get();
        boolean wasEmpty = basin.canContinueProcessing();
        if(!TieredBasinRecipe.apply(basin, currentRecipe) && !TieredBasinRecipe.applyGT(basin, currentRecipe, this.tier)) return;
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
        if(basin.isEmpty()) return false;
        return TieredBasinRecipe.match(basin.get(), recipe, this.tier);
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
