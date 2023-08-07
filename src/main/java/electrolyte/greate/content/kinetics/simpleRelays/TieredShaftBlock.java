package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TieredShaftBlock extends ShaftBlock {

    private TIER tier;

    public TieredShaftBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_SHAFT.get();
    }

    public TIER getTier() {
        return this.tier;
    }

    public void setTier(TIER tier) {
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.shaft_capacity").append(Component.literal(String.valueOf(tier.getStressCapacity())).withStyle(tier.getTierColor())).append(" (").append(Component.literal(tier.getName()).withStyle(tier.getTierColor())).append(")").withStyle(ChatFormatting.DARK_GRAY));
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
        InteractionResult result = tryEncase(pState, pLevel, pPos, heldItem, pPlayer, pHand, pHit);
        if (result.consumesAction())
            return result;

        if (AllBlocks.METAL_GIRDER.isIn(heldItem) && pState.getValue(AXIS) != Direction.Axis.Y) {
            KineticBlockEntity.switchToBlockState(pLevel, pPos, AllBlocks.METAL_GIRDER_ENCASED_SHAFT.getDefaultState()
                    .setValue(WATERLOGGED, pState.getValue(WATERLOGGED))
                    .setValue(GirderEncasedShaftBlock.HORIZONTAL_AXIS, pState.getValue(AXIS) == Direction.Axis.Z ? Direction.Axis.Z : Direction.Axis.X));
            if (!pLevel.isClientSide && !pPlayer.isCreative()) {
                heldItem.shrink(1);
                if (heldItem.isEmpty())
                    pPlayer.setItemInHand(pHand, ItemStack.EMPTY);
            }
            return InteractionResult.SUCCESS;
        }

        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        if (helper.matchesItem(heldItem))
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) heldItem.getItem(), pPlayer, pHand, pHit);

        return InteractionResult.PASS;
    }
}
