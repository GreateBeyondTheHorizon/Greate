package electrolyte.greate.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeCapabilities;
import electrolyte.greate.compat.kubejs.item.TieredOutputItem;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraftforge.fml.ModList;

import java.util.Map;

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
        event.add("GreateRecipeCapabilties", GreateRecipeCapabilities.class);
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        typeWrappers.registerSimple(TieredOutputItem.class, TieredOutputItem::of);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
        event.register("inputStress", GreateRecipeComponents.STRESS_IN);
        event.register("greateOutputStress", GreateRecipeComponents.STRESS_OUT);
        event.register("greateInputRPM", GreateRecipeComponents.RPM_IN);
        event.register("greateOutputRPM", GreateRecipeComponents.RPM_OUT);
    }
}
