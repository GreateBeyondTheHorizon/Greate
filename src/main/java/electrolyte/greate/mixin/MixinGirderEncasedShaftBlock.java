package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasedShaftBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GirderEncasedShaftBlock.class)
public class MixinGirderEncasedShaftBlock {

    @Inject(method = "onWrenched", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V"), remap = false, cancellable = true)
    private void greate$onWrenched(BlockState state, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local InteractionResult arg1) {
        if(state.getBlock() instanceof TieredGirderEncasedShaftBlock tgesb) {
            context.getPlayer().getInventory().placeItemBackInInventory(new ItemStack(tgesb.getShaft()));
            cir.setReturnValue(arg1);
        }
    }
}
