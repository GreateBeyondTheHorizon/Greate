package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.PoleHelper;
import electrolyte.greate.content.decoration.encasing.IGirderEncasableBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Predicate;

import static electrolyte.greate.registry.Shafts.POWERED_SHAFTS;

public class TieredShaftBlock extends ShaftBlock implements ITieredBlock, ITieredShaftBlock, IGirderEncasableBlock {

    public static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    private int tier;

    public TieredShaftBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_BRACKETED_KINETIC.get();
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        return pickCorrectShaftType(stateForPlacement, context.getLevel(), context.getClickedPos());
    }

    public static BlockState pickCorrectShaftType(BlockState stateForPlacement, Level level, BlockPos offsetPos, ItemStack shaft) {
        return PoweredShaftBlock.stillValid(stateForPlacement, level, offsetPos) ?
            TieredPoweredShaftBlock.getEquivalent(POWERED_SHAFTS[((ITieredBlock) ((BlockItem) shaft.getItem()).getBlock()).getTier()], stateForPlacement) : stateForPlacement;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isShiftKeyDown() || !pPlayer.mayBuild())
            return InteractionResult.PASS;

        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        InteractionResult resultEncase = tryEncase(pState, pLevel, pPos, heldItem, pPlayer, pHand, pHit);
        InteractionResult resultGirderEncase = tryGirderEncase(pState, pLevel, pPos, heldItem, pPlayer, pHand, pHit);
        if (resultEncase.consumesAction()) return resultEncase;
        if (resultGirderEncase.consumesAction()) return resultGirderEncase;

        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        if (Block.byItem(heldItem.getItem()) == pState.getBlock())
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) heldItem.getItem(), pPlayer, pHand, pHit);

        return InteractionResult.PASS;
    }

    @Override
    public Block getShaft() {
        return this;
    }

    private static class PlacementHelper extends PoleHelper<Axis> {
        private PlacementHelper() {
            super(state -> state.getBlock() instanceof AbstractSimpleShaftBlock
                    || state.getBlock() instanceof TieredPoweredShaftBlock, state -> state.getValue(AXIS), AXIS);
        }

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem
                    && ((BlockItem) i.getItem()).getBlock() instanceof AbstractSimpleShaftBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return ((Predicate<BlockState>) this::checkBlock).or(this::checkBlockPowered);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            PlacementOffset offset = super.getOffset(player, world, state, pos, ray);
            if (offset.isSuccessful())
                offset.withTransform(offset.getTransform()
                        .andThen(s -> TieredShaftBlock.pickCorrectShaftType(s, world, offset.getBlockPos(), player.getMainHandItem())));
            return offset;
        }

        private boolean checkBlock(BlockState state) {
            return state.getBlock() instanceof TieredShaftBlock;
        }

        private boolean checkBlockPowered(BlockState state) {
            return state.getBlock() instanceof TieredPoweredShaftBlock;
        }
    }
}
