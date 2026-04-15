package electrolyte.greate.compat.gtceu.machine.kinetic;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.simibubi.create.content.fluids.FlowSource.Blocked;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.mixin.MixinKineticBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static electrolyte.greate.GreateValues.TM;

public abstract class TieredKineticMachineBlock extends MetaMachineBlock implements ITieredBlock, IRotate {
    public TieredKineticMachineBlock(Properties properties, MachineDefinition definition) {
        super(properties, definition);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.TYPE).isPresent()) {
                KineticBlockEntity kbe = mm.getTraitHolder().getTrait(KineticMachineTrait.TYPE).getKineticBlockEntity();
                kbe.preventSpeedUpdate = 0;
                if(oldState.getBlock() != state.getBlock()) return;
                if(state.hasBlockEntity() != oldState.hasBlockEntity()) return;
                if(!areStatesKineticallyEquivalent(oldState, state)) return;

                kbe.preventSpeedUpdate = 2;
            }
        }
    }

    protected boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
        if(oldState.getBlock() != newState.getBlock()) return false;
        return getRotationAxis(newState) == getRotationAxis(oldState);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int pFlags, int pRecursionLeft) {
        if(pLevel.isClientSide()) return;
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(!(blockEntity instanceof MetaMachine mm)) return;
        if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.TYPE).isEmpty()) return;
        KineticBlockEntity kbe = mm.getTraitHolder().getTrait(KineticMachineTrait.TYPE).getKineticBlockEntity();
        if(kbe.preventSpeedUpdate > 0) return;

        kbe.warnOfMovement();
        kbe.clearKineticInformation();
        kbe.updateSpeed = true;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity player, ItemStack pStack) {
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, player);
        if(pLevel.isClientSide) return;
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(!(blockEntity instanceof MetaMachine mm)) return;
        if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.TYPE).isEmpty()) return;
        KineticBlockEntity kbe = mm.getTraitHolder().getTrait(KineticMachineTrait.TYPE).getKineticBlockEntity();
        ((MixinKineticBlockEntityAccessor) kbe).getEffects().queueRotationIndicators();
    }

    @Override
    public int getTier() {
        return getDefinition().getTier();
    }

    @Override
    public void setTier(int tier) {
        getDefinition().setTier(tier);
    }

    @Override
    public Material getMaterial() {
        //todo: fix for tiers past uhv
        if(getTier() <= TM.length - 1) return TM[getTier()];
        return TM[TM.length - 1];
    }
}
