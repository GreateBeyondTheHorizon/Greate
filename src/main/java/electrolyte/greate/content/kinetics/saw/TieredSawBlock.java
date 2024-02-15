package electrolyte.greate.content.kinetics.saw;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.placement.PlacementOffset;

import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import electrolyte.greate.registry.Saws;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import java.util.List;
import java.util.function.Predicate;

public class TieredSawBlock extends SawBlock implements ITieredBlock, ITieredShaftBlock, ITieredPartialModel {

    private int tier;
    private Block shaft;
    private PartialModel halfShaftModel;
    private PartialModel[] sawModels;

    private static final int PLACEMENT_HELPER_ID = PlacementHelpers.register(new PlacementHelper());

    public TieredSawBlock(Properties properties, Block shaft, PartialModel halfShaftModel, PartialModel... sawModels) {
        super(properties);
        this.shaft = shaft;
        this.halfShaftModel = halfShaftModel;
        this.sawModels = sawModels;
    }

    @Override
    public BlockEntityType<? extends SawBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_SAW.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
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
        return this.shaft;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        IPlacementHelper placementHelper = PlacementHelpers.get(PLACEMENT_HELPER_ID);
        if(!player.isShiftKeyDown() && player.mayBuild()) {
            if(placementHelper.matchesItem(heldItem) && placementHelper.getOffset(player, worldIn, state, pos, hit)
                    .placeInWorld(worldIn, (BlockItem) heldItem.getItem(), player, handIn, hit).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        if(player.isSpectator()) return InteractionResult.PASS;
        if(state.getOptionalValue(FACING).orElse(Direction.WEST) != Direction.UP) return InteractionResult.PASS;
        return onBlockEntityUse(worldIn, pos, be -> {
            if(!heldItem.isEmpty()) {
                if(FluidHelper.tryEmptyItemIntoBE(worldIn, player, handIn, heldItem, be)) return InteractionResult.SUCCESS;
                if(FluidHelper.tryFillItemFromBE(worldIn, player, handIn, heldItem, be)) return InteractionResult.SUCCESS;
                if(GenericItemEmptying.canItemBeEmptied(worldIn, heldItem) ||
                        GenericItemFilling.canItemBeFilled(worldIn, heldItem)) return InteractionResult.SUCCESS;
                if(heldItem.getItem().equals(Items.SPONGE) &&
                        !be.getCapability(ForgeCapabilities.FLUID_HANDLER).map(fh -> fh.drain(Integer.MAX_VALUE, FluidAction.EXECUTE))
                                .orElse(FluidStack.EMPTY)
                                .isEmpty()) return InteractionResult.SUCCESS;
                return InteractionResult.PASS;
            }
            for(int i = 0; i < be.inventory.getSlots(); i++) {
                ItemStack heldItemStack = be.inventory.getStackInSlot(i);
                if(!worldIn.isClientSide && !heldItemStack.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(heldItemStack);
                }
            }
            be.inventory.clear();
            be.notifyUpdate();
            return InteractionResult.SUCCESS;
        });
    }

    @Override
    public PartialModel getPartialModel() {
        return halfShaftModel;
    }

    public PartialModel[] getSawModels() {
        return sawModels;
    }

    @MethodsReturnNonnullByDefault
    private static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return i -> i.getItem() instanceof BlockItem &&
                    ((BlockItem) i.getItem()).getBlock() instanceof TieredSawBlock;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return s -> s.getBlock() instanceof TieredSawBlock;
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
