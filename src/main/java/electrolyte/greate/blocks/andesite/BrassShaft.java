package electrolyte.greate.blocks.andesite;

import com.google.common.base.Predicates;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.decoration.encasing.EncasableBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.placement.PoleHelper;
import electrolyte.greate.registry.ModBlockEntityTypes;
import electrolyte.greate.registry.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

public class BrassShaft extends ShaftBlock implements EncasableBlock {

    public BrassShaft(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        return pickCorrectShaftType(stateForPlacement, context.getLevel(), context.getClickedPos());
    }

    public static BlockState pickCorrectShaftType(BlockState stateForPlacement, Level level, BlockPos pos) {
        if(PoweredAndesiteShaft.stillValid(stateForPlacement, level, pos)) {
            return PoweredAndesiteShaft.getEquivalent(stateForPlacement);
        }
        return stateForPlacement;
    }
    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.BRASS_SHAFT.get();
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.isShiftKeyDown() || !pPlayer.mayBuild()) {
            return InteractionResult.PASS;
        }

        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        InteractionResult result = tryEncase(pState, pLevel, pPos, heldItem, pPlayer, pHand, pHit);
        if(result.consumesAction()) {
            return result;
        }
        if(AllBlocks.METAL_GIRDER.isIn(heldItem) && pState.getValue(AXIS) != Axis.Y) {
            KineticBlockEntity.switchToBlockState(pLevel, pPos, ModBlocks.GIRDER_ENCASED_ANDESITE_SHAFT.getDefaultState()
                    .setValue(WATERLOGGED, pState.getValue(WATERLOGGED))
                    .setValue(GirderEncasedAndesiteShaft.HORIZONTAL_AXIS, pState.getValue(AXIS) == Axis.Z ? Axis.Z : Axis.X));
            if(!pLevel.isClientSide && !pPlayer.isCreative()) {
                heldItem.shrink(1);
                if(heldItem.isEmpty()) {
                    pPlayer.setItemInHand(pHand, ItemStack.EMPTY);
                }
                return InteractionResult.SUCCESS;
            }
            IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
            if(helper.matchesItem(heldItem))
                return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit).placeInWorld(pLevel, (BlockItem) heldItem.getItem(), pPlayer, pHand, pHit);
        }
        return InteractionResult.PASS;
    }
}