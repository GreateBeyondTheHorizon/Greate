package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableItemStackHandler;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import net.createmod.catnip.data.Iterate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TieredMillstoneMachineBlock extends TieredKineticMachineBlock implements IBE<TieredMillstoneMachine>, ICogWheel {

    public TieredMillstoneMachineBlock(Properties properties, MachineDefinition definition) {
        super(Properties.copy(AllBlocks.MILLSTONE.get()), definition, AllBlocks.MILLSTONE.get());
    }

    @Override
    public Class<TieredMillstoneMachine> getBlockEntityClass() {
        return TieredMillstoneMachine.class;
    }

    @Override
    public BlockEntityType<? extends TieredMillstoneMachine> getBlockEntityType() {
        return (BlockEntityType<? extends TieredMillstoneMachine>) this.getDefinition().getBlockEntityType();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!player.getItemInHand(hand).isEmpty()) return super.use(state, world, pos, player, hand, hit);
        if(player.isCrouching()) return super.use(state, world, pos, player, hand, hit);
        if(world.isClientSide) return InteractionResult.SUCCESS;

        withBlockEntityDo(world, pos, millstone -> {
            boolean emptyOutput = true;
            NotifiableItemStackHandler inv = millstone.exportItems;
            for(int slot = 0; slot < inv.getSlots(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if(!stackInSlot.isEmpty()) emptyOutput = false;
                player.getInventory().placeItemBackInInventory(stackInSlot);
                inv.setStackInSlot(slot, ItemStack.EMPTY);
            }

            if(emptyOutput) {
                inv = millstone.importItems;
                for(int slot = 0; slot < inv.getSlots(); slot++) {
                    player.getInventory().placeItemBackInInventory(inv.getStackInSlot(slot));
                    inv.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
            millstone.setChanged();
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        super.updateEntityAfterFallOn(pLevel, pEntity);
        if(pEntity.level().isClientSide) return;
        if(!(pEntity instanceof ItemEntity itemEntity)) return;
        if(!pEntity.isAlive()) return;

        TieredKineticWorkableMachine millstone = null;
        for(BlockPos pos : Iterate.hereAndBelow(pEntity.blockPosition())) {
            if(millstone == null) millstone = getBlockEntity(pLevel, pos);
        }
        if(millstone == null) return;
        LazyOptional<IItemHandler> capability = millstone.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if(!capability.isPresent()) return;

        ItemStack remainder = capability.orElse(new ItemStackHandler())
                .insertItem(0, itemEntity.getItem(), false);
        if(remainder.isEmpty()) itemEntity.discard();
        if(remainder.getCount() < itemEntity.getItem().getCount()) itemEntity.setItem(remainder);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return super.getTicker(level, state, blockEntityType);
    }
}
