package electrolyte.greate.content.kinetics.millstone;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.GreateEnums;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.registry.Millstones;
import electrolyte.greate.registry.ModBlockEntityTypes;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TieredMillstoneBlock extends KineticBlock implements IBE<TieredMillstoneBlockEntity>, ICogWheel, ITieredBlock, ITieredPartialModel {

    TIER tier;
    PartialModel model;

    public TieredMillstoneBlock(Properties properties, PartialModel model) {
        super(properties);
        this.model = model;
        Millstones.MILLSTONES.add(this);
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
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.capacity").append(Component.literal(String.valueOf(tier.getStressCapacity())).withStyle(tier.getTierColor())).append(" (").append(Component.literal(tier.getName()).withStyle(tier.getTierColor())).append(")").withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pPlayer.getItemInHand(pHand).isEmpty()) return InteractionResult.PASS;
        if(pLevel.isClientSide) return InteractionResult.SUCCESS;

        withBlockEntityDo(pLevel, pPos, millstone -> {
            boolean emptyOutput = true;
            ItemStackHandler inv = millstone.outputInv;
            for(int slot = 0; slot < inv.getSlotCount(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if(!stackInSlot.isEmpty()) emptyOutput = false;
                pPlayer.getInventory().placeItemBackInInventory(stackInSlot);
                inv.setStackInSlot(slot, ItemStack.EMPTY);
            }

            if(emptyOutput) {
                inv = millstone.inputInv;
                for(int slot = 0; slot < inv.getSlotCount(); slot++) {
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

        if(pEntity.level().isClientSide) return;
        if(!(pEntity instanceof ItemEntity itemEntity)) return;
        if(!pEntity.isAlive()) return;

        TieredMillstoneBlockEntity millstone = null;
        for(BlockPos pos : Iterate.hereAndBelow(pEntity.blockPosition()))
            if(millstone == null) {
                millstone = getBlockEntity(pLevel, pos);
            }
        if(millstone == null) return;

        Storage<ItemVariant> handler = millstone.getItemStorage(null);
        if(handler == null) return;
        try (Transaction t = TransferUtil.getTransaction()) {
           ItemStack stack = itemEntity.getItem();
           long inserted = handler.insert(ItemVariant.of(stack), stack.getCount(), t);
           if(inserted == stack.getCount()) itemEntity.discard();
           else itemEntity.setItem(ItemHandlerHelper.copyStackWithSize(stack, (int) (stack.getCount() - inserted)));
           t.commit();
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

    @Override
    public PartialModel getPartialModel() {
        return this.model;
    }
}
