package electrolyte.greate.mixin;

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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GirderEncasedShaftBlock.class)
public class MixinGirderEncasedShaftBlock {

    @Inject(method = "onWrenched", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;)V"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void greate_onWrenched(BlockState state, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, InteractionResult arg1) {
        if(state.getBlock() instanceof TieredGirderEncasedShaftBlock tgesb) {
            context.getPlayer().getInventory().placeItemBackInInventory(new ItemStack(tgesb.getShaft()));
            cir.setReturnValue(arg1);
        }
    }
}
