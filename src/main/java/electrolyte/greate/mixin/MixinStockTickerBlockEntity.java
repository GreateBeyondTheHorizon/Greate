package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StockTickerBlockEntity.class)
public class MixinStockTickerBlockEntity {

    @WrapOperation(method = "addToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDescriptionId()Ljava/lang/String;"))
    private String greate_addToTooltip(ItemStack instance, Operation<String> original) {
        return instance.getHoverName().getString();
    }
}
