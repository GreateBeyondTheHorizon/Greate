package electrolyte.greate.registry;

import static electrolyte.greate.Greate.REGISTRATE;

public class GreateLang {

    static {
        REGISTRATE.addRawLang("itemGroup.greate", "Greate");
        REGISTRATE.addRawLang("greate.tooltip.overcapacity", "Overcapacity");
        REGISTRATE.addRawLang("greate.tooltip.overcapacity.description", "It appears that a shaft on this network has reached §fovercapacity.§7 Reduce the amount of su to an amount that the shaft can support.");
        REGISTRATE.addRawLang("greate.tooltip.capacity", "Kinetic Capacity: ");
        REGISTRATE.addRawLang("greate.jei.recipe_tier", "Recipe Tier: ");
    }

    public static void register() {}
}
