package electrolyte.greate.mixin;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import electrolyte.greate.compat.kubejs.KubeJSGreatePlugin;
import electrolyte.greate.foundation.data.recipe.GreateRuntimeRecipes;
import electrolyte.greate.foundation.data.recipe.removal.GreateRecipeRemoval;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = RecipeManager.class, priority = 1099)
public abstract class MixinRecipeManager {

    /**
     * <!!! [WARNING: JANK BELOW] !!!>
     * TODO: these recipes can be removed with kube using id or targeting an output, but cannot be removed by targeting the input
     * @see GreateRuntimeRecipes
     * @see KubeJSGreatePlugin#injectRuntimeRecipes(RecipesKubeEvent, RecipeManager, Map)
     **/
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "HEAD"), remap = false)
    private void greate$apply(Map<ResourceLocation, JsonElement> pMap, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        GreateRecipeRemoval.register(pMap::remove);

        //TODO: fix
        /*long currentTime = System.currentTimeMillis();
        Greate.LOGGER.info("Converting GT & Create recipes...");
        if(ModList.get().isLoaded("kubejs")) GreateKubeJSHelper.kubeStuff();
        int recipeCount = 0;
        for(Map.Entry<ResourceLocation, JsonElement> recipeEntry : pMap.entrySet()) {
            ResourceLocation resourceLocation = recipeEntry.getKey();
            JsonElement jsonElement = recipeEntry.getValue();
            try {
                if(!jsonElement.isJsonObject()) continue;
                JsonObject recipeJson = jsonElement.getAsJsonObject();
                if(!recipeJson.has("type") || recipeJson.get("type").getAsString() == null) continue;
                String type = recipeJson.get("type").getAsString();
                if(!type.startsWith(Create.ID) && !type.startsWith(GTCEu.MOD_ID)) continue;
                if(!CraftingHelper.processConditions(recipeJson, "conditions", this.context)) continue;
                TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> factory = GreateValues.getFactory(resourceLocation);
                if(factory != null) {
                    if(type.startsWith(GTCEu.MOD_ID)) {
                        GTRecipe recipe = GTRecipeSerializer.SERIALIZER.fromJson(resourceLocation, recipeJson);
                        GreateRuntimeRecipes.convertGTRecipe(factory, recipe, !type.startsWith(GTRecipeTypes.BENDER_RECIPES.registryName.toString()));
                    } else if(type.startsWith(Create.ID)) {
                        GreateRuntimeRecipes.convertCreateRecipe(factory, resourceLocation, jsonElement);
                    }
                    recipeCount++;
                }
            } catch (JsonSyntaxException e) {
                Greate.LOGGER.warn("Unable to parse recipe {}, it will be skipped. Check debug.log for erroring JSON.)", resourceLocation);
                Greate.LOGGER.debug("Unable to parse recipe {}, erroring JSON is: {}", resourceLocation, jsonElement);
            }
        }
        for(MixingRecipe potionRecipe : PotionMixingRecipes.ALL) {
            GreateRuntimeRecipes.convertPotionRecipe(potionRecipe);
            recipeCount++;
        }
        Greate.LOGGER.info("Finished processing {} recipes in {} ms", recipeCount, System.currentTimeMillis() - currentTime);
        pMap.putAll(GreateRuntimeRecipes.JSON_FILES);*/
    }
}
