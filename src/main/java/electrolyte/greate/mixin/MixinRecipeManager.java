package electrolyte.greate.mixin;

import com.google.gson.JsonElement;
import electrolyte.greate.Greate;
import electrolyte.greate.foundation.data.recipe.GreateRecipeRemoval;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void greate_apply(Map<ResourceLocation, JsonElement> pMap, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        GreateRecipeRemoval.init(recipe -> {
            if(pMap.remove(recipe) == null) {
                Greate.LOGGER.error("Unable to remove recipe {}.", recipe);
            }
        });
    }
}
