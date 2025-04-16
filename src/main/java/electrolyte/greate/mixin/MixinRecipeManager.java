package electrolyte.greate.mixin;

import com.google.gson.JsonElement;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.potion.PotionMixingRecipes;
import electrolyte.greate.Greate;
import electrolyte.greate.compat.kubejs.GreateKubeJSHelper;
import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import electrolyte.greate.content.kinetics.millstone.TieredMillingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredCompactingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
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
    @Shadow @Final private IContext context;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "HEAD"))
    private void greate_apply(Map<ResourceLocation, JsonElement> pMap, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        GreateRecipeRemoval.init(recipe -> {
            if(pMap.remove(recipe) == null) {
                Greate.LOGGER.error("Unable to remove recipe {}.", recipe);
            }
        });

        /**
         * <!!! [WARNING: JANK BELOW] !!!>
         * TODO: these recipes can be removed with kube using id or targeting an output, but cannot be removed by targeting the input
         * @see GreateRuntimeRecipes
         */
        long currentTime = System.currentTimeMillis();
        Greate.LOGGER.info("Converting GT & Create recipes...");
        if(ModList.get().isLoaded("kubejs")) GreateKubeJSHelper.kubeStuff();
        AtomicInteger recipeCount = new AtomicInteger();
        pMap.forEach((resourceLocation, jsonElement) -> {
            if(jsonElement.isJsonObject() && CraftingHelper.processConditions(jsonElement.getAsJsonObject(), "conditions", this.context)) {
                if(resourceLocation.toString().startsWith(GTRecipeTypes.MACERATOR_RECIPES.registryName.toString())) {
                    GreateRuntimeRecipes.convertGTRecipe(TieredMillingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                } else if(resourceLocation.toString().startsWith(AllRecipeTypes.MILLING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredMillingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                }
                else if(resourceLocation.toString().startsWith(AllRecipeTypes.SPLASHING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredSplashingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                } else if(resourceLocation.toString().startsWith(AllRecipeTypes.HAUNTING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredHauntingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                }
                else if(resourceLocation.toString().startsWith(GTRecipeTypes.BENDER_RECIPES.registryName.toString())) {
                    GreateRuntimeRecipes.convertGTRecipe(TieredPressingRecipe::new, resourceLocation, jsonElement, false);
                    recipeCount.getAndIncrement();
                } else if(resourceLocation.toString().startsWith(AllRecipeTypes.PRESSING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredPressingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                }
                else if(resourceLocation.toString().startsWith(GTRecipeTypes.MIXER_RECIPES.registryName.toString())) {
                    GreateRuntimeRecipes.convertGTRecipe(TieredMixingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                } else if(resourceLocation.toString().startsWith(AllRecipeTypes.MIXING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredMixingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                }
                else if(resourceLocation.toString().startsWith(AllRecipeTypes.COMPACTING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredCompactingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                }
                else if(resourceLocation.toString().startsWith(GTRecipeTypes.CUTTER_RECIPES.registryName.toString())) {
                    GreateRuntimeRecipes.convertGTRecipe(TieredCuttingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                } else if(resourceLocation.toString().startsWith(AllRecipeTypes.CUTTING.getId().toString())) {
                    GreateRuntimeRecipes.convertCreateRecipe(TieredCuttingRecipe::new, resourceLocation, jsonElement);
                    recipeCount.getAndIncrement();
                }
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
