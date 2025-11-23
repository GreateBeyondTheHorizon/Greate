package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.schematics.cannon.MaterialChecklist;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MaterialChecklist.class)
public class MixinMaterialChecklist {

    @WrapOperation(method = "entry", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDescriptionId()Ljava/lang/String;"), remap = false)
    private String greate_entry(ItemStack instance, Operation<String> original) {
        return instance.getHoverName().getString();
    }
}
