package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterial;
import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterials.Color;
import com.simibubi.create.Create;
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
}
