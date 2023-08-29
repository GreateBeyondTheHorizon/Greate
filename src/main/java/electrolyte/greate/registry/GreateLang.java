package electrolyte.greate.registry;

import static electrolyte.greate.Greate.REGISTRATE;

public class GreateLang {

    static {
        REGISTRATE.addRawLang("itemGroup.greate", "Greate");
        REGISTRATE.addRawLang("greate.tooltip.overcapacity", "Overcapacity");
        REGISTRATE.addRawLang("greate.tooltip.overcapacity.description", "It appears that a shaft on this network has reached §fovercapacity.§7 Reduce the amount of su to an amount that the shaft can support.");
        REGISTRATE.addRawLang("greate.tooltip.capacity", "Kinetic Capacity: ");
        REGISTRATE.addRawLang("greate.recipe.milling", "Milling");
        REGISTRATE.addRawLang("greate.recipe.crushing", "Crushing");
        REGISTRATE.addRawLang("greate.jei.recipe_tier", "Recipe Tier: ");
        REGISTRATE.addRawLang("greate.recipe.processing.extra_chance", "% per tier");
        REGISTRATE.addRawLang("greate.old_create_items_warning", "[Greate]: The conversion recipe for this item will be removed in a future version, once all recipes have been properly updated!");
    }

    public static void register() {}
}
