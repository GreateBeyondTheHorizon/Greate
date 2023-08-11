package electrolyte.greate.registry;

import electrolyte.greate.Greate;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModItems {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }
/*

    public static final ItemEntry<Item> STEEL_ALLOY = REGISTRATE
            .item("steel_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> ALUMINIUM_ALLOY = REGISTRATE
            .item("aluminium_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> STAINLESS_STEEL_ALLOY = REGISTRATE
            .item("stainless_steel_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> TITANIUM_ALLOY = REGISTRATE
            .item("titanium_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> TUNGSTENSTEEL_ALLOY = REGISTRATE
            .item("tungstensteel_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> PALLADIUM_ALLOY = REGISTRATE
            .item("palladium_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> NAQUADAH_ALLOY = REGISTRATE
            .item("naquadah_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> DARMSTADTIUM_ALLOY = REGISTRATE
            .item("darmstadtium_alloy", Item::new)
            .register();
    public static final ItemEntry<Item> NEUTRONIUM_ALLOY = REGISTRATE
            .item("neutronium_alloy", Item::new)
            .register();
*/

    public static void register() {}
}
