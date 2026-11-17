package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static electrolyte.greate.GreateValues.TM;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredKineticMachineBlock extends MetaMachineBlock implements ITieredBlock, IRotate {

    @Getter
    protected final KineticBlock kineticBlock;

    public TieredKineticMachineBlock(MachineDefinition definition, KineticBlock kineticBlock) {
        super(Properties.copy(kineticBlock), definition);
        this.kineticBlock = kineticBlock;
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return kineticBlock.getRotationAxis(state);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        kineticBlock.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return kineticBlock.hasShaftTowards(world, pos, state, face);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return kineticBlock.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return kineticBlock.isPathfindable(pState, pLevel, pPos, pType);
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int pFlags, int pRecursionLeft) {
        kineticBlock.updateIndirectNeighbourShapes(pState, pLevel, pPos, pFlags, pRecursionLeft);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity player, ItemStack pStack) {
        kineticBlock.setPlacedBy(pLevel, pPos, pState, player, pStack);
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
