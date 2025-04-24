package electrolyte.greate.compat.kubejs;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.integration.kjs.recipe.GTRecipeSchema;
import com.gregtechceu.gtceu.integration.kjs.recipe.GTRecipeSchema.GTRecipeJS;
import com.gregtechceu.gtceu.integration.kjs.recipe.components.GTRecipeComponents;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.kubejs.item.TieredOutputItem;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KubeJSGreatePlugin extends KubeJSPlugin {

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        if(!ModList.get().isLoaded("kubejs_create")) return;
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
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("TieredOutputItem", TieredOutputItem.class);
        event.add("GreateValues", GreateValues.class);
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        typeWrappers.registerSimple(TieredOutputItem.class, TieredOutputItem::of);
    }

    @Override
    public void injectRuntimeRecipes(RecipesEventJS event, RecipeManager manager, Map<ResourceLocation, Recipe<?>> recipesByName) {
        for(RecipeJS r : event.addedRecipes) {
            if(r instanceof GTRecipeJS gtRecipeJS) {
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
        }
    }
}
