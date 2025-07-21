package electrolyte.greate.mixin;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipeParams;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeployerApplicationRecipe.class)
public abstract class MixinDeployerApplicationRecipe extends ItemApplicationRecipe implements IAssemblyRecipe {

    public MixinDeployerApplicationRecipe(AllRecipeTypes type, ItemApplicationRecipeParams params) {
        super(type, params);
    }

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "getDescriptionForAssembly", at = @At("HEAD"), remap = false, cancellable = true)
    private void greate$getDescriptionForAssembly(CallbackInfoReturnable<Component> cir) {
        ItemStack[] matchingStacks = ingredients.get(1).getItems();
        if(matchingStacks.length == 0) cir.setReturnValue(Component.literal("Invalid"));
        cir.setReturnValue(CreateLang.translateDirect("recipe.assembly.deploying_item",
                Component.translatable(matchingStacks[0].getHoverName().getString())));
    }
}
