package electrolyte.greate.compat.kubejs;

import dev.latvian.mods.kubejs.core.RecipeManagerKJS;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.gtceu.material.GreateMaterialFlags;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.foundation.data.recipe.GreateCraftingComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Map;

public class KubeJSGreatePlugin implements KubeJSPlugin {

    /*@Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        if(! ModList.get().isLoaded("kubejs_create")) return;
        final Map<ModRecipeTypes, RecipeSchema> RECIPE_SCHEMAS = Map.of(
                ModRecipeTypes.MILLING, TieredProcessingRecipeSchema.PROCESSING_WITH_TIME,
                ModRecipeTypes.CRUSHING, TieredProcessingRecipeSchema.PROCESSING_WITH_TIME,
                ModRecipeTypes.PRESSING, TieredProcessingRecipeSchema.PROCESSING_WITH_CIRCUIT,
                ModRecipeTypes.COMPACTING, TieredProcessingRecipeSchema.PROCESSING_WITH_CIRCUIT,
                ModRecipeTypes.MIXING, TieredProcessingRecipeSchema.PROCESSING_WITH_CIRCUIT,
                ModRecipeTypes.BASIN, TieredProcessingRecipeSchema.PROCESSING_WITH_CIRCUIT
        );
        for(ModRecipeTypes recipeType : ModRecipeTypes.values()) {
            if(recipeType.getSerializer() instanceof TieredProcessingRecipeSerializer<?>) {
                RecipeSchema schema = RECIPE_SCHEMAS.getOrDefault(recipeType, TieredProcessingRecipeSchema.PROCESSING_DEFAULT);
                event.register(recipeType.getId(), schema);
            }
        }
    }*/

    @Override
    public void registerBindings(BindingRegistry event) {
        event.add("GreateValues", GreateValues.class);
        event.add("GreateCraftingComponents", GreateCraftingComponents.class);
        event.add("GreateMaterialFlags", GreateMaterialFlags.class);
        event.add("GreatePropertyKeys", GreatePropertyKeys.class);
    }

    @Override
    public void injectRuntimeRecipes(RecipesKubeEvent event, RecipeManagerKJS manager, Map<ResourceLocation, RecipeHolder<?>> recipesByName) {
        /*for(RecipeJS r : event.addedRecipes) {
            if(r instanceof GTRecipeJS gtRecipeJS) {
                if(r.getId().endsWith("_manual_only")) continue;
                if(r.getId().endsWith("_electric_only")) continue;
                TieredProcessingRecipeFactory<TieredProcessingRecipe<?>> factory = GreateValues.getFactory(gtRecipeJS.getType());
                if(factory == null) continue;
                TieredProcessingRecipeBuilder<TieredProcessingRecipe<?>> builder = new TieredProcessingRecipeBuilder<>(factory, Greate.id("integration/" + gtRecipeJS.idWithoutType().toString().replace(":", "/")));
                if(gtRecipeJS.getValue(GTRecipeSchema.DURATION) != null) {
                    builder.duration(gtRecipeJS.getValue(GTRecipeSchema.DURATION).intValue());
                } else {
                    builder.averageProcessingDuration();
                }
                if(gtRecipeJS.getValue(GTRecipeSchema.ALL_INPUTS) != null) {
                    Map<RecipeCapability<?>, List<Content>> inputs = gtRecipeJS.getValue(GTRecipeSchema.ALL_INPUTS).entrySet().stream()
                            .map(e -> Map.entry(e.getKey(), Arrays.stream(e.getValue())
                                    .map(c -> e.getKey().serializer
                                            .fromJsonContent(GTRecipeComponents.VALID_CAPS.get(e.getKey())
                                                    .getFirst().write(gtRecipeJS, c)))
                                    .toList()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    inputs.forEach((recipeCapability, contents) -> {
                        if(recipeCapability instanceof ItemRecipeCapability) {
                            builder.withItemIngredientsGT(contents);
                            builder.recipeCircuit(TieredProcessingRecipe.getCircuitFromGTRecipe(contents));
                        }
                        if(recipeCapability instanceof FluidRecipeCapability) {
                            builder.withFluidIngredientsGT(contents);
                        }
                    });
                }
                if(gtRecipeJS.getValue(GTRecipeSchema.ALL_OUTPUTS) != null) {
                    Map<RecipeCapability<?>, List<Content>> outputs = gtRecipeJS.getValue(GTRecipeSchema.ALL_OUTPUTS).entrySet().stream()
                            .map(e -> Map.entry(e.getKey(), Arrays.stream(e.getValue())
                                    .map(c -> e.getKey().serializer
                                            .fromJsonContent(GTRecipeComponents.VALID_CAPS.get(e.getKey())
                                                    .getFirst().write(gtRecipeJS, c)))
                                    .toList()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    outputs.forEach((recipeCapability, contents) -> {
                        if(recipeCapability instanceof ItemRecipeCapability) {
                            builder.withItemOutputsGT(contents);
                        }
                        if(recipeCapability instanceof FluidRecipeCapability) {
                            builder.withFluidOutputsGT(contents);
                        }
                    });
                }
                if(gtRecipeJS.getValue(GTRecipeSchema.ALL_TICK_INPUTS) != null) {
                    Map<RecipeCapability<?>, List<Content>> inputs = gtRecipeJS.getValue(GTRecipeSchema.ALL_TICK_INPUTS).entrySet().stream()
                            .map(e -> Map.entry(e.getKey(), Arrays.stream(e.getValue())
                                    .map(c -> e.getKey().serializer
                                            .fromJsonContent(GTRecipeComponents.VALID_CAPS.get(e.getKey())
                                                    .getFirst().write(gtRecipeJS, c)))
                                    .toList()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    inputs.forEach((recipeCapability, contents) -> {
                        if(recipeCapability instanceof EURecipeCapability) {
                            builder.recipeTier(GreateValues.convertGTEUToTier(contents));
                        }
                    });
                }
                builder.build(b -> recipesByName.put(b.getId(), b.getType().fromJson(b.getId(), b.serializeRecipe())));
            }
        }*/
    }
}
