package electrolyte.greate.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import net.minecraft.world.item.Item;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModItems {

    static {
        REGISTRATE.useCreativeTab(Greate.GREATE_TAB);
    }

    public static final ItemEntry<Item> STEEL_ALLOY = REGISTRATE
            .item("steel_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> ALUMINIUM_ALLOY = REGISTRATE
            .item("aluminium_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> STAINLESS_STEEL_ALLOY = REGISTRATE
            .item("stainless_steel_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> TITANIUM_ALLOY = REGISTRATE
            .item("titanium_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> TUNGSTEN_STEEL_ALLOY = REGISTRATE
            .item("tungsten_steel_alloy", Item::new)
            .lang("Tungstensteel Alloy")
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> PALLADIUM_ALLOY = REGISTRATE
            .item("palladium_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> NAQUADAH_ALLOY = REGISTRATE
            .item("naquadah_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> DARMSTADTIUM_ALLOY = REGISTRATE
            .item("darmstadtium_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();
    public static final ItemEntry<Item> NEUTRONIUM_ALLOY = REGISTRATE
            .item("neutronium_alloy", Item::new)
            .model((c, p) -> p.withExistingParent(c.getName(), "item/generated")
                    .texture("layer0", p.modLoc("item/" + c.getName().substring(0, c.getName().length() - 6) + "/alloy")))
            .register();

    public static void register() {}
}
