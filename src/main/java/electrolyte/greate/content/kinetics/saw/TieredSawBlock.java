package electrolyte.greate.content.kinetics.saw;

import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.fluid.FluidHelper;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class TieredSawBlock extends SawBlock implements ITieredBlock, ITieredShaftBlock {

    private int tier;
    private Supplier<Block> shaftBlock;

    private static final int PLACEMENT_HELPER_ID = PlacementHelpers.register(new PlacementHelper());

    public TieredSawBlock(Properties properties, Material mat) {
        super(properties);
        this.shaftBlock = () -> ChemicalHelper.getBlock(shaft, mat);
    }

    @Override
    public BlockEntityType<? extends SawBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_SAW.get();
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
        return shaftBlock.get();
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        IPlacementHelper placementHelper = PlacementHelpers.get(PLACEMENT_HELPER_ID);
        if(!player.isShiftKeyDown() && player.mayBuild()) {
            if(placementHelper.matchesItem(stack) && placementHelper.getOffset(player, worldIn, state, pos, hit)
                    .placeInWorld(worldIn, (BlockItem) stack.getItem(), player, handIn, hit).consumesAction()) {
                return ItemInteractionResult.SUCCESS;
            }
        }
        if(player.isSpectator()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(state.getOptionalValue(FACING).orElse(Direction.WEST) != Direction.UP) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        return onBlockEntityUseItemOn(worldIn, pos, be -> {
            if(!stack.isEmpty()) {
                if(FluidHelper.tryEmptyItemIntoBE(worldIn, player, handIn, stack, be)) {
                    player.playSound(SoundEvents.BUCKET_EMPTY);
                    return ItemInteractionResult.SUCCESS;
                }
                if(FluidHelper.tryFillItemFromBE(worldIn, player, handIn, stack, be)) {
                    player.playSound(SoundEvents.BUCKET_FILL);
                    return ItemInteractionResult.SUCCESS;
                }
                if(GenericItemEmptying.canItemBeEmptied(worldIn, stack) ||
                        GenericItemFilling.canItemBeFilled(worldIn, stack)) return ItemInteractionResult.SUCCESS;
                if(stack.getItem().equals(Items.SPONGE)) {
                    IFluidHandler fluidHandler = worldIn.getCapability(FluidHandler.BLOCK, pos, null);
                    if(fluidHandler != null) {
                        FluidStack drained = fluidHandler.drain(Integer.MAX_VALUE, FluidAction.EXECUTE);
                        if(!drained.isEmpty()) {
                            return ItemInteractionResult.SUCCESS;
                        }
                    }
                    player.playSound(SoundEvents.BOTTLE_EMPTY);
                    return ItemInteractionResult.SUCCESS;
                }
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            for(int i = 0; i < be.inventory.getSlots(); i++) {
                ItemStack stackStack = be.inventory.getStackInSlot(i);
                if(!worldIn.isClientSide && !stackStack.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(stackStack);
                }
            }
            be.inventory.clear();
            be.notifyUpdate();
            return ItemInteractionResult.SUCCESS;
        });
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
