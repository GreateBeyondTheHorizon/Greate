package electrolyte.greate.content.kinetics.simpleRelays;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.PoleHelper;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import electrolyte.greate.registry.Shafts;
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

import static electrolyte.greate.registry.GreateTagPrefixes.girderEncasedShaft;
import static electrolyte.greate.registry.GreateTagPrefixes.poweredShaft;

public class TieredShaftBlock extends ShaftBlock implements ITieredBlock, ITieredShaftBlock {

    public static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());
    private int tier;
    private Material material;

    public TieredShaftBlock(Properties properties, Material material) {
        super(properties);
        this.material = material;
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
        return pickShaftType(stateForPlacement, context.getLevel(), context.getClickedPos());
    }

    public BlockState pickShaftType(BlockState stateForPlacement, Level level, BlockPos offsetPos) {
        return PoweredShaftBlock.stillValid(stateForPlacement, level, offsetPos) ?
                ChemicalHelper.getBlock(poweredShaft, material).defaultBlockState()
                        .setValue(PoweredShaftBlock.AXIS, stateForPlacement.getValue(ShaftBlock.AXIS))
                        .setValue(WATERLOGGED, stateForPlacement.getValue(WATERLOGGED))
                : stateForPlacement;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isShiftKeyDown() || !pPlayer.mayBuild())
            return InteractionResult.PASS;

        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        InteractionResult resultEncase = tryEncase(pState, pLevel, pPos, heldItem, pPlayer, pHand, pHit);
        if (resultEncase.consumesAction()) return resultEncase;

        if(AllBlocks.METAL_GIRDER.isIn(heldItem) && pState.getValue(AXIS) != Axis.Y) {
            KineticBlockEntity.switchToBlockState(pLevel, pPos, ChemicalHelper.getBlock(girderEncasedShaft, getMaterial()).defaultBlockState()
                    .setValue(WATERLOGGED, pState.getValue(WATERLOGGED))
                    .setValue(GirderEncasedShaftBlock.HORIZONTAL_AXIS, pState.getValue(AXIS) == Axis.Z ? Axis.Z : Axis.X));
            if(!pLevel.isClientSide && !pPlayer.isCreative()) {
                heldItem.shrink(1);
                if(heldItem.isEmpty()) pPlayer.setItemInHand(pHand, ItemStack.EMPTY);
            }
            return InteractionResult.SUCCESS;
        }

        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        if (helper.matchesItem(heldItem))
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) heldItem.getItem(), pPlayer, pHand, pHit);

        return InteractionResult.PASS;
    }

    @Override
    public Block getShaft() {
        return this;
    }

    @Override
    public Material getMaterial() {
        return material;
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
            Block shaftBlock;
            if(Block.byItem(player.getMainHandItem().getItem()) instanceof TieredShaftBlock tsb) {
                shaftBlock = Shafts.POWERED_SHAFTS.get(poweredShaft, tsb.getMaterial()).get();
            } else shaftBlock = AllBlocks.POWERED_SHAFT.get();
            if (offset.isSuccessful())
                offset.withTransform(offset.getTransform()
                        .andThen(stateForPlacement -> PoweredShaftBlock.stillValid(stateForPlacement, world, offset.getBlockPos()) ?
                                shaftBlock.defaultBlockState()
                                        .setValue(PoweredShaftBlock.AXIS, stateForPlacement.getValue(ShaftBlock.AXIS))
                                        .setValue(WATERLOGGED, stateForPlacement.getValue(WATERLOGGED))
                                : stateForPlacement));
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
