package electrolyte.greate.content.kinetics.simpleRelays;

import com.google.common.base.Predicates;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.decoration.encasing.EncasableBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.placement.PoleHelper;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.decoration.encasing.IGirderEncasableBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.Predicate;

public class TieredShaftBlock extends AbstractSimpleShaftBlock implements EncasableBlock, ITieredBlock, IGirderEncasableBlock {

    public static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    private TIER tier;

    public TieredShaftBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_KINETIC.get();
    }

    @Override
    public TIER getTier() {
        return this.tier;
    }

    @Override
    public void setTier(TIER tier) {
        this.tier = tier;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.SIX_VOXEL_POLE.get(state.getValue(AXIS));
    }

    @Override
    public float getParticleTargetRadius() {
        return .35f;
    }

    @Override
    public float getParticleInitialRadius() {
        return .125f;
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.capacity").append(Component.literal(String.valueOf(tier.getStressCapacity())).withStyle(tier.getTierColor())).append(" (").append(Component.literal(tier.getName()).withStyle(tier.getTierColor())).append(")").withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        return pickCorrectShaftType(stateForPlacement, context.getLevel(), context.getClickedPos());
    }

    public static BlockState pickCorrectShaftType(BlockState stateForPlacement, Level level, BlockPos pos) {
        if (PoweredShaftBlock.stillValid(stateForPlacement, level, pos))
            return PoweredShaftBlock.getEquivalent(stateForPlacement);
        return stateForPlacement;
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
        if (helper.matchesItem(heldItem))
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) heldItem.getItem(), pPlayer, pHand, pHit);

        return InteractionResult.PASS;
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper extends PoleHelper<Axis> {
        private PlacementHelper() {
            super(state -> state.getBlock() instanceof AbstractSimpleShaftBlock
                    || state.getBlock() instanceof PoweredShaftBlock, state -> state.getValue(AXIS), AXIS);
        }

        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem
                    && ((BlockItem) i.getItem()).getBlock() instanceof AbstractSimpleShaftBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return Predicates.or(AllBlocks.SHAFT::has, AllBlocks.POWERED_SHAFT::has);
        }

        @Override
        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
                                         BlockHitResult ray) {
            PlacementOffset offset = super.getOffset(player, world, state, pos, ray);
            if (offset.isSuccessful())
                offset.withTransform(offset.getTransform().andThen(s -> ShaftBlock.pickCorrectShaftType(s, world, offset.getBlockPos())));
            return offset;
        }
    }
}
