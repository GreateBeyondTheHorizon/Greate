package electrolyte.greate.compat.gtceu.machine.kinetic.saw;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic.Status;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.drill.DrillBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.damageTypes.CreateDamageSources;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredDirectionalAxisKineticMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Predicate;

import static com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;
import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;
import static com.simibubi.create.content.kinetics.saw.SawBlock.FLIPPED;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TieredSawMachineBlock extends TieredDirectionalAxisKineticMachineBlock implements IBE<TieredSawMachine> {

    public static final int PLACEMENT_HELPER_ID = PlacementHelpers.register(new PlacementHelper());

    public TieredSawMachineBlock(Properties properties, MachineDefinition definition) {
        super(Properties.copy(AllBlocks.MECHANICAL_SAW.get()), definition, AllBlocks.MECHANICAL_SAW.get());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
		Direction facing = stateForPlacement.getValue(FACING);
		return stateForPlacement.setValue(FLIPPED, facing.getAxis() == Axis.Y && context.getHorizontalDirection()
			.getAxisDirection() == AxisDirection.POSITIVE);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FLIPPED);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.NORMAL;
    }

    @Override
    public Class<TieredSawMachine> getBlockEntityClass() {
        return TieredSawMachine.class;
    }

    @Override
    public BlockEntityType<? extends TieredSawMachine> getBlockEntityType() {
        return (BlockEntityType<? extends TieredSawMachine>) this.getDefinition().getBlockEntityType();
    }

    @Override
    public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
        MetaMachine machine = MetaMachine.getMachine(context.getLevel(), context.getClickedPos());
        if (machine instanceof TieredSawMachine sawMachine) {
            KineticBlockEntity kbe = sawMachine.kineticMachineTrait.getKineticBlockEntity();
            BlockState kbeState = kbe.getBlockState()
                    .setValue(FACING, newState.getValue(FACING))
                    .setValue(FLIPPED, newState.getValue(FLIPPED))
                    .setValue(AXIS_ALONG_FIRST_COORDINATE, newState.getValue(AXIS_ALONG_FIRST_COORDINATE));
            kbe.setBlockState(kbeState);
            KineticBlockEntity.switchToBlockState(kbe.getLevel(), kbe.getBlockPos(), kbeState);
            if(newState.getValue(FACING) != Direction.UP) {
                sawMachine.getRecipeLogic().setProgress(0);
                sawMachine.getRecipeLogic().setStatus(Status.SUSPEND);
            } else sawMachine.getRecipeLogic().setStatus(Status.IDLE);
        }
        return super.updateAfterWrenched(newState, context);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(pEntity instanceof ItemEntity) return;
        if(!new AABB(pPos).deflate(0.1F).intersects(pEntity.getBoundingBox())) return;
        withBlockEntityDo(pLevel, pPos, be -> {
            if(be.getSpeed() == 0) return;
            pEntity.hurt(CreateDamageSources.saw(pLevel), (float) DrillBlock.getDamage(be.getSpeed()));
        });
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        super.updateEntityAfterFallOn(pLevel, pEntity);
        if(!(pEntity instanceof ItemEntity itemEntity)) return;
        if(pEntity.level().isClientSide) return;

        TieredKineticWorkableMachine saw = null;
        for(BlockPos sawPos : Iterate.hereAndBelow(pEntity.blockPosition())) {
            if(saw == null) saw = getBlockEntity(pLevel, sawPos);
        }
        if(saw == null) return;
        LazyOptional<IItemHandler> capability = saw.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if(!capability.isPresent()) return;
        ItemStack remainder = capability.orElse(new ItemStackHandler())
                .insertItem(0, itemEntity.getItem(), false);
        if(remainder.isEmpty()) itemEntity.discard();
        if(remainder.getCount() < itemEntity.getItem().getCount()) itemEntity.setItem(remainder);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return super.getTicker(level, state, blockEntityType);
    }

    private static class PlacementHelper implements IPlacementHelper {

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem blockItem &&
                    blockItem.getBlock() instanceof TieredSawMachineBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof TieredSawMachineBlock;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            List<Direction> dirs = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), state.getValue(FACING).getAxis(), d -> world.getBlockState(pos.relative(d)).canBeReplaced());
            if(dirs.isEmpty()) return PlacementOffset.fail();
            else {
                return PlacementOffset.success(pos.relative(dirs.get(0)), s ->
                        s.setValue(FACING, state.getValue(FACING))
                                .setValue(AXIS_ALONG_FIRST_COORDINATE, state.getValue(AXIS_ALONG_FIRST_COORDINATE))
                                .setValue(FLIPPED, state.getValue(FLIPPED)));
            }
        }
    }
}
