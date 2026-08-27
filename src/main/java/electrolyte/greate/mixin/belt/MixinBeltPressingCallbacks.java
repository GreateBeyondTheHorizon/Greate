package electrolyte.greate.mixin.belt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.press.BeltPressingCallbacks;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlockEntity;
import electrolyte.greate.foundation.item.GreateItemHelper;
import net.createmod.catnip.data.Pair;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(BeltPressingCallbacks.class)
public class MixinBeltPressingCallbacks {

    @WrapOperation(method = "whenItemHeld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"), remap = false)
    private static void greate$whenItemHeld(ItemStack stack, int pDecrement, Operation<Void> original, @Local(argsOnly = true) PressingBehaviour pressingBehaviour) {
        if(pressingBehaviour.blockEntity instanceof TieredMechanicalPressBlockEntity tmpbe) {
            if(tmpbe.getRecipe() != null) {
                List<Pair<Ingredient, MutableInt>> condensedIngredients = GreateItemHelper.condenseIngredients(tmpbe.getRecipe().getIngredients());
                stack.shrink(condensedIngredients.get(0).getSecond().getValue());
            }
        } else original.call(stack, pDecrement);
    }
}
