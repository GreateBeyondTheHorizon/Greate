package electrolyte.greate.registry;

import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.Locale;

public class GreateTags {

    public static <T> TagKey<T> modTag(IForgeRegistry<T> registry, ResourceLocation tagLocation) {
        return registry.tags().createOptionalTagKey(tagLocation, Collections.emptySet());
    }

    public static TagKey<Block> mcBlockTag(String location) {
        return modTag(ForgeRegistries.BLOCKS, new ResourceLocation(location));
    }

    public static TagKey<Item> mcItemTag(String location) {
        return modTag(ForgeRegistries.ITEMS, new ResourceLocation(location));
    }

    public static TagKey<Fluid> mcFluidTag(String location) {
        return modTag(ForgeRegistries.FLUIDS, new ResourceLocation(location));
    }

    public static TagKey<Block> forgeBlockTag(String location) {
        return modTag(ForgeRegistries.BLOCKS, new ResourceLocation("forge", location));
    }

    public static TagKey<Item> forgeItemTag(String location) {
        return modTag(ForgeRegistries.ITEMS, new ResourceLocation("forge", location));
    }

    public static TagKey<Fluid> forgeFluidTag(String location) {
        return modTag(ForgeRegistries.FLUIDS, new ResourceLocation("forge", location));
    }

    public static TagKey<Block> greateBlockTag(String location) {
        return modTag(ForgeRegistries.BLOCKS, new ResourceLocation(Greate.MOD_ID, location));
    }

    public static TagKey<Item> greateItemTag(String location) {
        return modTag(ForgeRegistries.ITEMS, new ResourceLocation(Greate.MOD_ID, location));
    }

    public static TagKey<Fluid> greateFluidTag(String location) {
        return modTag(ForgeRegistries.FLUIDS, new ResourceLocation(Greate.MOD_ID, location));
    }

    public enum GreateItemTags {
        SHAFTS,
        SHAFTS_ANDESITE(Greate.MOD_ID, "shafts/andesite"),
        SHAFTS_STEEL(Greate.MOD_ID, "shafts/steel"),
        SHAFTS_ALUMINIUM(Greate.MOD_ID, "shafts/aluminium"),
        SHAFTS_STAINLESS_STEEL(Greate.MOD_ID, "shafts/stainless_steel"),
        SHAFTS_TITANIUM(Greate.MOD_ID, "shafts/titanium"),
        SHAFTS_TUNGSTEN_STEEL(Greate.MOD_ID, "shafts/tungsten_steel"),
        SHAFTS_PALLADIUM(Greate.MOD_ID, "shafts/palladium"),
        SHAFTS_NAQUADAH(Greate.MOD_ID, "shafts/naquadah"),
        SHAFTS_DARMSTADTIUM(Greate.MOD_ID, "shafts/darmstadtium"),
        SHAFTS_NEUTRONIUM(Greate.MOD_ID, "shafts/neutronium"),

        COGWHEELS,
        COGWHEELS_ANDESITE(Greate.MOD_ID, "cogwheels/andesite"),
        COGWHEELS_STEEL(Greate.MOD_ID, "cogwheels/steel"),
        COGWHEELS_ALUMINIUM(Greate.MOD_ID, "cogwheels/aluminium"),
        COGWHEELS_STAINLESS_STEEL(Greate.MOD_ID, "cogwheels/stainless_steel"),
        COGWHEELS_TITANIUM(Greate.MOD_ID, "cogwheels/titanium"),
        COGWHEELS_TUNGSTEN_STEEL(Greate.MOD_ID, "cogwheels/tungsten_steel"),
        COGWHEELS_PALLADIUM(Greate.MOD_ID, "cogwheels/palladium"),
        COGWHEELS_NAQUADAH(Greate.MOD_ID, "cogwheels/naquadah"),
        COGWHEELS_DARMSTADTIUM(Greate.MOD_ID, "cogwheels/darmstadtium"),
        COGWHEELS_NEUTRONIUM(Greate.MOD_ID, "cogwheels/neutronium"),

        LARGE_COGWHEELS,
        LARGE_COGWHEELS_ANDESITE(Greate.MOD_ID, "large_cogwheels/andesite"),
        LARGE_COGWHEELS_STEEL(Greate.MOD_ID, "large_cogwheels/steel"),
        LARGE_COGWHEELS_ALUMINIUM(Greate.MOD_ID, "large_cogwheels/aluminium"),
        LARGE_COGWHEELS_STAINLESS_STEEL(Greate.MOD_ID, "large_cogwheels/stainless_steel"),
        LARGE_COGWHEELS_TITANIUM(Greate.MOD_ID, "large_cogwheels/titanium"),
        LARGE_COGWHEELS_TUNGSTEN_STEEL(Greate.MOD_ID, "large_cogwheels/tungsten_steel"),
        LARGE_COGWHEELS_PALLADIUM(Greate.MOD_ID, "large_cogwheels/palladium"),
        LARGE_COGWHEELS_NAQUADAH(Greate.MOD_ID, "large_cogwheels/naquadah"),
        LARGE_COGWHEELS_DARMSTADTIUM(Greate.MOD_ID, "large_cogwheels/darmstadtium"),
        LARGE_COGWHEELS_NEUTRONIUM(Greate.MOD_ID, "large_cogwheels/neutronium");

        public final TagKey<Item> itemTag;

        GreateItemTags() {
            this(Greate.MOD_ID);
        }

        GreateItemTags(String name) {
            this(name, null);
        }

        GreateItemTags(String name, String path) {
            ResourceLocation loc = new ResourceLocation(name, path == null ? name().toLowerCase(Locale.ROOT) : path);
            itemTag = ItemTags.create(loc);
        }

        public boolean matches(Item item) {
            return item.getDefaultInstance().is(itemTag);
        }

        private static void init() {}
    }

    public static void init() {
        GreateItemTags.init();
    }
}
