package electrolyte.greate.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.potion.PotionMixingRecipes;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.kubejs.GreateKubeJSHelper;
import electrolyte.greate.compat.kubejs.KubeJSGreatePlugin;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory;
import electrolyte.greate.foundation.data.recipe.GreateRuntimeRecipes;
import electrolyte.greate.foundation.data.recipe.removal.GreateRecipeRemoval;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(value = RecipeManager.class, priority = 1099)
public class MixinRecipeManager {

    @Shadow public Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
    @Shadow(remap = false) @Final private IContext context;

    /**
     * <!!! [WARNING: JANK BELOW] !!!>
     * TODO: these recipes can be removed with kube using id or targeting an output, but cannot be removed by targeting the input
     * @see GreateRuntimeRecipes
     * @see KubeJSGreatePlugin#injectRuntimeRecipes(RecipesEventJS, RecipeManager, Map)
     **/
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "HEAD"))
    private void greate_apply(Map<ResourceLocation, JsonElement> pMap, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        GreateRecipeRemoval.register(recipe -> {
            if(pMap.remove(recipe) == null) {
                Greate.LOGGER.error("Unable to remove recipe {}.", recipe);
            }
        });


        long currentTime = System.currentTimeMillis();
        Greate.LOGGER.info("Converting GT & Create recipes...");
        if(ModList.get().isLoaded("kubejs")) GreateKubeJSHelper.kubeStuff();
        AtomicInteger recipeCount = new AtomicInteger();
        pMap.forEach((resourceLocation, jsonElement) -> {
            try {
                if(jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("type") &&
                        (jsonElement.getAsJsonObject().get("type").getAsString().startsWith(Create.ID) ||
                         jsonElement.getAsJsonObject().get("type").getAsString().startsWith(GTCEu.MOD_ID))) {
                    if(CraftingHelper.processConditions(jsonElement.getAsJsonObject(), "conditions", this.context)) {
                        TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> factory = GreateValues.getFactory(resourceLocation);
                        if(factory != null) {
                            String type = jsonElement.getAsJsonObject().get("type").getAsString();
                            if(type.startsWith(GTCEu.MOD_ID)) {
                                GTRecipe recipe = GTRecipeSerializer.SERIALIZER.fromJson(resourceLocation, jsonElement.getAsJsonObject());
                                GreateRuntimeRecipes.convertGTRecipe(factory, recipe, ! type.startsWith(GTRecipeTypes.BENDER_RECIPES.registryName.toString()));
                            } else if(type.startsWith(Create.ID)) {
                                GreateRuntimeRecipes.convertCreateRecipe(factory, resourceLocation, jsonElement);
                            }
                            recipeCount.getAndIncrement();
                        }
                    }
                }
            } catch (JsonSyntaxException e) {
                Greate.LOGGER.error("Unable to parse recipe {}, will be skipped.", resourceLocation);
            }
        });
        PotionMixingRecipes.ALL.forEach(potionRecipe -> {
            GreateRuntimeRecipes.convertPotionRecipe(potionRecipe);
            recipeCount.getAndIncrement();
        });
        Greate.LOGGER.info("Finished processing {} recipes in {} ms", recipeCount, System.currentTimeMillis() - currentTime);
        pMap.putAll(GreateRuntimeRecipes.JSON_FILES);
    }
}
