package electrolyte.greate.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import net.minecraft.world.item.Item;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.simibubi.create.AllItems.ANDESITE_ALLOY;
import static com.simibubi.create.AllItems.WHISK;
import static electrolyte.greate.Greate.REGISTRATE;

public class ModItems {

    // Alloy
    public static final ItemEntry<Item>[] ALLOYS = new ItemEntry[10];
    public static ItemEntry<Item>
            STEEL_ALLOY,
            ALUMINIUM_ALLOY,
            STAINLESS_STEEL_ALLOY,
            TITANIUM_ALLOY,
            TUNGSTENSTEEL_ALLOY,
            PALLADIUM_ALLOY,
            NAQUADAH_ALLOY,
            DARMSTADTIUM_ALLOY,
            NEUTRONIUM_ALLOY;

    // Whisk
    public static final ItemEntry<Item>[] WHISKS = new ItemEntry[10];
    public static ItemEntry<Item>
            STEEL_WHISK,
            ALUMINIUM_WHISK,
            STAINLESS_STEEL_WHISK,
            TITANIUM_WHISK,
            TUNGSTENSTEEL_WHISK,
            PALLADIUM_WHISK,
            NAQUADAH_WHISK,
            DARMSTADTIUM_WHISK,
            NEUTRONIUM_WHISK;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        // Alloy
        ALLOYS[ULV] = ANDESITE_ALLOY;
        ALLOYS[LV] = STEEL_ALLOY = alloy("steel");
        ALLOYS[MV] = ALUMINIUM_ALLOY = alloy("aluminium");
        ALLOYS[HV] = STAINLESS_STEEL_ALLOY = alloy("stainless_steel");
        ALLOYS[EV] = TITANIUM_ALLOY = alloy("titanium");
        ALLOYS[IV] = TUNGSTENSTEEL_ALLOY = alloy("tungstensteel");
        ALLOYS[LuV] = PALLADIUM_ALLOY = alloy("palladium");
        ALLOYS[ZPM] = NAQUADAH_ALLOY = alloy("naquadah");
        ALLOYS[UV] = DARMSTADTIUM_ALLOY = alloy("darmstadtium");
        ALLOYS[UHV] = NEUTRONIUM_ALLOY = alloy("neutronium");

        // Whisk
        WHISKS[ULV] = WHISK;
        WHISKS[LV] = STEEL_WHISK = whisk("steel");
        WHISKS[MV] = ALUMINIUM_WHISK = whisk("aluminium");
        WHISKS[HV] = STAINLESS_STEEL_WHISK = whisk("stainless_steel");
        WHISKS[EV] = TITANIUM_WHISK = whisk("titanium");
        WHISKS[IV] = TUNGSTENSTEEL_WHISK = whisk("tungstensteel");
        WHISKS[LuV] = PALLADIUM_WHISK = whisk("palladium");
        WHISKS[ZPM] = NAQUADAH_WHISK = whisk("naquadah");
        WHISKS[UV] = DARMSTADTIUM_WHISK = whisk("darmstadtium");
        WHISKS[UHV] = NEUTRONIUM_WHISK = whisk("neutronium");
    }

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
}
