package electrolyte.greate.content.kinetics.simpleRelays;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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

import static electrolyte.greate.registry.GreateTagPrefixes.poweredShaft;

public class TieredShaftBlock extends ShaftBlock implements ITieredBlock, ITieredShaftBlock, IGirderEncasableBlock {

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
            ItemStack shaft = player.getMainHandItem();
            if (offset.isSuccessful())
                offset.withTransform(offset.getTransform()
                        .andThen(stateForPlacement -> PoweredShaftBlock.stillValid(stateForPlacement, world, offset.getBlockPos()) ?
                                ChemicalHelper.getBlock(poweredShaft, ChemicalHelper.getMaterialEntry(shaft.getItem()).material()).defaultBlockState()
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
