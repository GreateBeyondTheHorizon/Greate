package electrolyte.greate.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import net.minecraft.world.item.Item;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModItems {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final ItemEntry<Item> STEEL_ALLOY = alloy("steel_alloy");
    public static final ItemEntry<Item> ALUMINIUM_ALLOY = alloy("aluminium_alloy");
    public static final ItemEntry<Item> STAINLESS_STEEL_ALLOY = alloy("stainless_steel_alloy");
    public static final ItemEntry<Item> TITANIUM_ALLOY = alloy("titanium_alloy");
    public static final ItemEntry<Item> TUNGSTENSTEEL_ALLOY = alloy("tungstensteel_alloy");
    public static final ItemEntry<Item> PALLADIUM_ALLOY = alloy("palladium_alloy");
    public static final ItemEntry<Item> NAQUADAH_ALLOY = alloy("naquadah_alloy");
    public static final ItemEntry<Item> DARMSTADTIUM_ALLOY = alloy("darmstadtium_alloy");
    public static final ItemEntry<Item> NEUTRONIUM_ALLOY = alloy("neutronium_alloy");

    public static ItemEntry<Item> alloy(String name) {
        return REGISTRATE
                .item(name, Item::new)
                .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                        .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
                .register();
    }

    public static void register() {}
}
