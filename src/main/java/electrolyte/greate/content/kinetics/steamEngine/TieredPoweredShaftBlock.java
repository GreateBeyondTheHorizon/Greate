package electrolyte.greate.content.kinetics.steamEngine;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;

import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TieredPoweredShaftBlock extends PoweredShaftBlock implements ITieredBlock, ITieredShaftBlock {

    private int tier;
    private Block shaftType;
    public static Map<TieredShaftBlock, TieredPoweredShaftBlock> SHAFTS = new HashMap<>();

    public TieredPoweredShaftBlock(Properties properties, Supplier<Block> shaftType) {
        super(properties);
        this.shaftType = shaftType.get();
        SHAFTS.put((TieredShaftBlock) shaftType.get(), this);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_POWERED_SHAFT.get();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.isShiftKeyDown() || !pPlayer.mayBuild()) return InteractionResult.PASS;
        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        IPlacementHelper helper = PlacementHelpers.get(TieredShaftBlock.placementHelperId);
        if(helper.matchesItem(heldItem)) {
            return helper.getOffset(pPlayer, pLevel, pState, pPos, pHit)
                    .placeInWorld(pLevel, (BlockItem) heldItem.getItem(), pPlayer, pHand, pHit);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(!stillValid(pState, pLevel, pPos)) {
            pLevel.setBlock(pPos, getShaft().defaultBlockState()
                    .setValue(ShaftBlock.AXIS, pState.getValue(AXIS))
                    .setValue(WATERLOGGED, pState.getValue(WATERLOGGED)), 3);
        }
    }

    public static BlockState getEquivalent(BlockState stateForPlacement) {
        return stateForPlacement.getBlock().defaultBlockState()
                .setValue(PoweredShaftBlock.AXIS, stateForPlacement.getValue(ShaftBlock.AXIS))
                .setValue(WATERLOGGED, stateForPlacement.getValue(WATERLOGGED));
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return getShaft().asItem().getDefaultInstance();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public Block getShaft() {
        return shaftType;
    }
}
