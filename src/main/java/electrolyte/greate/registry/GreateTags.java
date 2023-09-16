package electrolyte.greate.registry;

import electrolyte.greate.Greate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Locale;

public class GreateTags {

    public static <T> TagKey<T> modTag(Registry<T> registry, ResourceLocation tagLocation) {
        return TagKey.create(registry.key(), tagLocation);
    }

    public static TagKey<Block> mcBlockTag(String location) {
        return modTag(BuiltInRegistries.BLOCK, new ResourceLocation(location));
    }

    public static TagKey<Item> mcItemTag(String location) {
        return modTag(BuiltInRegistries.ITEM, new ResourceLocation(location));
    }

    public static TagKey<Fluid> mcFluidTag(String location) {
        return modTag(BuiltInRegistries.FLUID, new ResourceLocation(location));
    }

    public static TagKey<Block> forgeBlockTag(String location) {
        return modTag(BuiltInRegistries.BLOCK, new ResourceLocation("c", location));
    }

    public static TagKey<Item> forgeItemTag(String location) {
        return modTag(BuiltInRegistries.ITEM, new ResourceLocation("c", location));
    }

    public static TagKey<Fluid> forgeFluidTag(String location) {
        return modTag(BuiltInRegistries.FLUID, new ResourceLocation("c", location));
    }

    public static TagKey<Block> greateBlockTag(String location) {
        return modTag(BuiltInRegistries.BLOCK, new ResourceLocation(Greate.MOD_ID, location));
    }

    public static TagKey<Item> greateItemTag(String location) {
        return modTag(BuiltInRegistries.ITEM, new ResourceLocation(Greate.MOD_ID, location));
    }

    public static TagKey<Fluid> greateFluidTag(String location) {
        return modTag(BuiltInRegistries.FLUID, new ResourceLocation(Greate.MOD_ID, location));
    }

    public enum GreateItemTags {
        SHAFTS,
        SHAFTS_ANDESITE(Greate.MOD_ID, "andesite_shafts"),
        SHAFTS_STEEL(Greate.MOD_ID, "steel_shafts"),
        SHAFTS_ALUMINIUM(Greate.MOD_ID, "aluminium_shafts"),
        SHAFTS_STAINLESS_STEEL(Greate.MOD_ID, "stainless_steel_shafts"),
        SHAFTS_TITANIUM(Greate.MOD_ID, "titanium_shafts"),
        SHAFTS_TUNGSTENSTEEL(Greate.MOD_ID, "tungstensteel_shafts"),
        SHAFTS_PALLADIUM(Greate.MOD_ID, "palladium_shafts"),
        SHAFTS_NAQUADAH(Greate.MOD_ID, "naquadah_shafts"),
        SHAFTS_DARMSTADTIUM(Greate.MOD_ID, "darmstadtium_shafts"),
        SHAFTS_NEUTRONIUM(Greate.MOD_ID, "neutronium_shafts"),

        COGWHEELS,
        COGWHEELS_ANDESITE(Greate.MOD_ID, "andesite_cogwheels"),
        COGWHEELS_STEEL(Greate.MOD_ID, "steel_cogwheels"),
        COGWHEELS_ALUMINIUM(Greate.MOD_ID, "aluminium_cogwheels"),
        COGWHEELS_STAINLESS_STEEL(Greate.MOD_ID, "stainless_steel_cogwheels"),
        COGWHEELS_TITANIUM(Greate.MOD_ID, "titanium_cogwheels"),
        COGWHEELS_TUNGSTENSTEEL(Greate.MOD_ID, "tungstensteel_cogwheels"),
        COGWHEELS_PALLADIUM(Greate.MOD_ID, "palladium_cogwheels"),
        COGWHEELS_NAQUADAH(Greate.MOD_ID, "naquadah_cogwheels"),
        COGWHEELS_DARMSTADTIUM(Greate.MOD_ID, "darmstadtium_cogwheels"),
        COGWHEELS_NEUTRONIUM(Greate.MOD_ID, "neutronium_cogwheels"),

        LARGE_COGWHEELS,
        LARGE_COGWHEELS_ANDESITE(Greate.MOD_ID, "andesite_large_cogwheels"),
        LARGE_COGWHEELS_STEEL(Greate.MOD_ID, "steel_large_cogwheels"),
        LARGE_COGWHEELS_ALUMINIUM(Greate.MOD_ID, "aluminium_large_cogwheels"),
        LARGE_COGWHEELS_STAINLESS_STEEL(Greate.MOD_ID, "stainless_steel_large_cogwheels"),
        LARGE_COGWHEELS_TITANIUM(Greate.MOD_ID, "titanium_large_cogwheels"),
        LARGE_COGWHEELS_TUNGSTENSTEEL(Greate.MOD_ID, "tungstensteel_large_cogwheels"),
        LARGE_COGWHEELS_PALLADIUM(Greate.MOD_ID, "palladium_large_cogwheels"),
        LARGE_COGWHEELS_NAQUADAH(Greate.MOD_ID, "naquadah_large_cogwheels"),
        LARGE_COGWHEELS_DARMSTADTIUM(Greate.MOD_ID, "darmstadtium_large_cogwheels"),
        LARGE_COGWHEELS_NEUTRONIUM(Greate.MOD_ID, "neutronium_large_cogwheels");

        public final TagKey<Item> itemTag;

        GreateItemTags() {
            this(Greate.MOD_ID);
        }

        GreateItemTags(String name) {
            this(name, null);
        }

        GreateItemTags(String name, String path) {
            ResourceLocation loc = new ResourceLocation(name, path == null ? name().toLowerCase(Locale.ROOT) : path);
            itemTag = modTag(BuiltInRegistries.ITEM, loc);
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
