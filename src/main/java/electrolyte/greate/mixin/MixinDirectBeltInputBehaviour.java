package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirectBeltInputBehaviour.class)
public abstract class MixinDirectBeltInputBehaviour extends MixinBlockEntityBehaviour {

    @Inject(method = "defaultInsertionCallback(Lcom/simibubi/create/content/kinetics/belt/transport/TransportedItemStack;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "RETURN", ordinal = 0), remap = false, cancellable = true)
    private void greate$defaultInsertionCallback(TransportedItemStack inserted, Direction side, boolean simulate, CallbackInfoReturnable<ItemStack> cir) {
        if(blockEntity.getBlockState().getBlock() instanceof MetaMachineBlock mmb) {
            BlockEntity be = blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos());
            if(!(be instanceof TieredKineticWorkableMachine tkwm)) return;
            LazyOptional<IItemHandler> cap = tkwm.getCapability(ForgeCapabilities.ITEM_HANDLER);
            if(!cap.isPresent()) return;
            cir.setReturnValue(ItemHandlerHelper.insertItemStacked(cap.orElse(null), inserted.stack.copy(), simulate));
            cir.cancel();
            return;
        }
    }
}
