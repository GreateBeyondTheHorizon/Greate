package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingInventory;
import com.simibubi.create.content.kinetics.crafter.RecipeGridHandler;
import com.simibubi.create.content.kinetics.crafter.RecipeGridHandler.GroupedItems;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;

@Mixin(RecipeGridHandler.class)
public abstract class MixinRecipeGridHandler {

    @Shadow public static boolean isRecipeAllowed(CraftingRecipe recipe, CraftingContainer inventory) {throw new MixinApplyError("Mixin did not apply!");}

    @Inject(method = "tryToApplyRecipe", at = @At("TAIL"), remap = false, cancellable = true)
    private static void greate_tryToApplyRecipe(Level world, GroupedItems items, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack result = null;
        CraftingContainer craftingInventory = new MechanicalCraftingInventory(items);
        result = ModRecipeTypes.MECHANICAL_CRAFTING.find(craftingInventory, world, 0/*TODO: get machine tier here!*/)
                .map(r -> r.assemble(craftingInventory, world.registryAccess()))
                .orElse(null);
        cir.setReturnValue(result);
    }
}
