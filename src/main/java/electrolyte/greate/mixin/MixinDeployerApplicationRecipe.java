package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DeployerApplicationRecipe.class)
public abstract class MixinDeployerApplicationRecipe extends ItemApplicationRecipe implements IAssemblyRecipe {

    public MixinDeployerApplicationRecipe(AllRecipeTypes type, ProcessingRecipeParams params) {
        super(type, params);
    }

    @WrapOperation(method = "getDescriptionForAssembly", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent greate_getDescriptionForAssembly(String pKey, Operation<MutableComponent> original) {
        ItemStack[] matchingStacks = ingredients.get(1).getItems();
        return Component.translatable(matchingStacks[0].getHoverName().getString());
    }
}
