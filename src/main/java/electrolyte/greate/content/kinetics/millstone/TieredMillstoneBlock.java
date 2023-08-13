package electrolyte.greate.content.kinetics.millstone;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.GreateEnums;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TieredMillstoneBlock extends KineticBlock implements IBE<TieredMillstoneBlockEntity>, ICogWheel, ITieredBlock {

    TIER tier;

    public TieredMillstoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.MILLSTONE;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pPlayer.getItemInHand(pHand).isEmpty()) return InteractionResult.PASS;
        if(pLevel.isClientSide) return InteractionResult.SUCCESS;

        withBlockEntityDo(pLevel, pPos, millstone -> {
            boolean emptyOutput = true;
            IItemHandlerModifiable inv = millstone.outputInv;
            for(int slot = 0; slot < inv.getSlots(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if(!stackInSlot.isEmpty()) emptyOutput = false;
                pPlayer.getInventory().placeItemBackInInventory(stackInSlot);
                inv.setStackInSlot(slot, ItemStack.EMPTY);
            }

            if(emptyOutput) {
                inv = millstone.inputInv;
                for(int slot = 0; slot < inv.getSlots(); slot++) {
                    pPlayer.getInventory().placeItemBackInInventory(inv.getStackInSlot(slot));
                    inv.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }

            millstone.setChanged();
            millstone.sendData();
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        super.updateEntityAfterFallOn(pLevel, pEntity);

        if(pEntity.level.isClientSide) return;
        if(!(pEntity instanceof ItemEntity)) return;
        if(!pEntity.isAlive()) return;

        TieredMillstoneBlockEntity millstone = null;
        for(BlockPos pos : Iterate.hereAndBelow(pEntity.blockPosition()))
            if(millstone == null) {
                millstone = getBlockEntity(pLevel, pos);
            }
        if(millstone == null) return;

        ItemEntity itemEntity = (ItemEntity) pEntity;
        LazyOptional<IItemHandler> capability = millstone.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        if(!capability.isPresent()) return;

        ItemStack remainder = capability.orElse(new ItemStackHandler()).insertItem(0, itemEntity.getItem(), false);
        if(remainder.isEmpty()) itemEntity.discard();
        if(remainder.getCount() < itemEntity.getItem().getCount()) {
            itemEntity.setItem(remainder);
        }
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }

    @Override
    public Class<TieredMillstoneBlockEntity> getBlockEntityClass() {
        return TieredMillstoneBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TieredMillstoneBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MILLSTONE.get();
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public GreateEnums.TIER getTier() {
        return tier;
    }

    @Override
    public void setTier(GreateEnums.TIER tier) {
        this.tier = tier;
    }
}
