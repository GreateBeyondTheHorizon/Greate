package electrolyte.greate.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import net.minecraft.world.item.Item;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModItems {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final ItemEntry<Item> STEEL_ALLOY = alloy("steel");
    public static final ItemEntry<Item> ALUMINIUM_ALLOY = alloy("aluminium");
    public static final ItemEntry<Item> STAINLESS_STEEL_ALLOY = alloy("stainless_steel");
    public static final ItemEntry<Item> TITANIUM_ALLOY = alloy("titanium");
    public static final ItemEntry<Item> TUNGSTENSTEEL_ALLOY = alloy("tungstensteel");
    public static final ItemEntry<Item> PALLADIUM_ALLOY = alloy("palladium");
    public static final ItemEntry<Item> NAQUADAH_ALLOY = alloy("naquadah");
    public static final ItemEntry<Item> DARMSTADTIUM_ALLOY = alloy("darmstadtium");
    public static final ItemEntry<Item> NEUTRONIUM_ALLOY = alloy("neutronium");

    public static final ItemEntry<Item> STEEL_WHISK = whisk("steel");
    public static final ItemEntry<Item> ALUMINIUM_WHISK = whisk("aluminium");
    public static final ItemEntry<Item> STAINLESS_STEEL_WHISK = whisk("stainless_steel");
    public static final ItemEntry<Item> TITANIUM_WHISK = whisk("titanium");
    public static final ItemEntry<Item> TUNGSTENSTEEL_WHISK = whisk("tungstensteel");
    public static final ItemEntry<Item> PALLADIUM_WHISK = whisk("palladium");
    public static final ItemEntry<Item> NAQUADAH_WHISK = whisk("naquadah");
    public static final ItemEntry<Item> DARMSTADTIUM_WHISK = whisk("darmstadtium");
    public static final ItemEntry<Item> NEUTRONIUM_WHISK = whisk("neutronium");

    public static ItemEntry<Item> alloy(String name) {
        return REGISTRATE
                .item(name + "_alloy", Item::new)
                .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                        .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
                .register();
    }

    public static ItemEntry<Item> whisk(String name) {
        return REGISTRATE
                .item(name + "_whisk", Item::new)
                .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                        .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/whisk")))
                .register();
    }



    public static void register() {}
}
