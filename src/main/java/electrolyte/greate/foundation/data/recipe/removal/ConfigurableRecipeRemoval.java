package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterial;
import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterials.Color;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.data.recipe.WoodTypeEntry;
import com.simibubi.create.Create;
import electrolyte.greate.mixin.MixinWoodMachineRecipesAccessor;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class ConfigurableRecipeRemoval {

    public static void disableDyeRecipes(Consumer<ResourceLocation> recipe) {
        for(MarkerMaterial dyeColor : Color.VALUES) {
            recipe.accept(Create.asResource(String.format("crafting/kinetics/%s_seat_from_other_seat", dyeColor.getName())));
            recipe.accept(Create.asResource(String.format("crafting/kinetics/%s_valve_handle_from_other_valve_handle", dyeColor.getName())));
            recipe.accept(Create.asResource(String.format("crafting/logistics/%s_postbox_from_other_postbox", dyeColor.getName())));
        }
        recipe.accept(Create.asResource("crafting/kinetics/copper_valve_handle_from_others"));
    }

    public static void disableArmorToolRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crafting/appliances/copper_diving_helmet"));
        recipe.accept(Create.asResource("crafting/appliances/copper_backtank"));
        recipe.accept(Create.asResource("crafting/appliances/copper_diving_boots"));
    }

    public static void disableCompressionRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("crafting/materials/raw_zinc"));
        recipe.accept(Create.asResource("crafting/materials/raw_zinc_block"));
        recipe.accept(new ResourceLocation("raw_copper"));
        recipe.accept(new ResourceLocation("raw_copper_block"));
        recipe.accept(new ResourceLocation("raw_gold"));
        recipe.accept(new ResourceLocation("raw_gold_block"));
        recipe.accept(new ResourceLocation("raw_iron"));
        recipe.accept(new ResourceLocation("raw_iron_block"));
    }

    public static void disableGlassRecipes(Consumer<ResourceLocation> recipe) {
        recipe.accept(Create.asResource("tiled_glass_pane"));
        recipe.accept(Create.asResource("framed_glass_pane"));
        recipe.accept(Create.asResource("horizontal_framed_glass_pane"));
        recipe.accept(Create.asResource("vertical_framed_glass_pane"));

        recipe.accept(Create.asResource("ornate_iron_window_pane"));
        recipe.accept(Create.asResource("industrial_iron_window_pane"));
        recipe.accept(Create.asResource("weathered_iron_window_pane"));

        for(WoodTypeEntry woodType : MixinWoodMachineRecipesAccessor.getDefaultEntries()) {
            if(GTBlocks.TREATED_WOOD_TYPE.name().contains(woodType.woodName) || GTBlocks.RUBBER_TYPE.name().contains(woodType.woodName)) continue;
            recipe.accept(Create.asResource(woodType.woodName + "_window_pane"));
        }
    }
}
