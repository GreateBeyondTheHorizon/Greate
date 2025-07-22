package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.logistics.tunnel.BrassTunnelBlockEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrassTunnelBlockEntity.class)
public class MixinBrassTunnelBlockEntity {

    @WrapOperation(method = "addToGoggleTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDescriptionId()Ljava/lang/String;"))
    private String greate$addToGoggleTooltip(ItemStack instance, Operation<String> original) {
        return instance.getHoverName().getString();
    }
}
