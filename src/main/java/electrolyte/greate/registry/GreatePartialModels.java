package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

public class GreatePartialModels {

    public static final PartialModel

    ANDESITE_COGWHEEL_SHAFTLESS = block("andesite_cogwheel_shaftless"), LARGE_ANDESITE_COGWHEEL_SHAFTLESS = block("large_andesite_cogwheel_shaftless"),
    STEEL_COGWHEEL_SHAFTLESS = block("steel_cogwheel_shaftless"), LARGE_STEEL_COGWHEEL_SHAFTLESS = block("large_steel_cogwheel_shaftless"),
    ALUMINIUM_COGWHEEL_SHAFTLESS = block("aluminium_cogwheel_shaftless"), LARGE_ALUMINIUM_COGWHEEL_SHAFTLESS = block("large_aluminium_cogwheel_shaftless"),
    STAINLESS_STEEL_COGWHEEL_SHAFTLESS = block("stainless_steel_cogwheel_shaftless"), LARGE_STAINLESS_STEEL_COGWHEEL_SHAFTLESS = block("large_stainless_steel_cogwheel_shaftless"),
    TITANIUM_COGWHEEL_SHAFTLESS = block("titanium_cogwheel_shaftless"), LARGE_TITANIUM_COGWHEEL_SHAFTLESS = block("large_titanium_cogwheel_shaftless"),
    TUNGSTEN_STEEL_COGWHEEL_SHAFTLESS = block("tungsten_steel_cogwheel_shaftless"), LARGE_TUNGSTEN_STEEL_COGWHEEL_SHAFTLESS = block("large_tungsten_steel_cogwheel_shaftless"),
    PALLADIUM_COGWHEEL_SHAFTLESS = block("palladium_cogwheel_shaftless"), LARGE_PALLADIUM_COGWHEEL_SHAFTLESS = block("large_palladium_cogwheel_shaftless"),
    NAQUADAH_COGWHEEL_SHAFTLESS = block("naquadah_cogwheel_shaftless"), LARGE_NAQUADAH_COGWHEEL_SHAFTLESS = block("large_naquadah_cogwheel_shaftless"),
    DARMSTADTIUM_COGWHEEL_SHAFTLESS = block("darmstadtium_cogwheel_shaftless"), LARGE_DARMSTADTIUM_COGWHEEL_SHAFTLESS = block("large_darmstadtium_cogwheel_shaftless"),
    NEUTRONIUM_COGWHEEL_SHAFTLESS = block("neutronium_cogwheel_shaftless"), LARGE_NEUTRONIUM_COGWHEEL_SHAFTLESS = block("large_neutronium_cogwheel_shaftless"),
    ANDESITE_COGWHEEL_SHAFT = block("andesite_cogwheel_shaft"), ANDESITE_SHAFT_HALF = block("andesite_shaft_half"),
    STEEL_COGWHEEL_SHAFT = block("steel_cogwheel_shaft"), STEEL_SHAFT_HALF = block("steel_shaft_half"),
    ALUMINIUM_COGWHEEL_SHAFT = block("aluminium_cogwheel_shaft"), ALUMINIUM_SHAFT_HALF = block("aluminium_shaft_half"),
    STAINLESS_STEEL_COGWHEEL_SHAFT = block("stainless_steel_cogwheel_shaft"), STAINLESS_STEEL_SHAFT_HALF = block("stainless_steel_shaft_half"),
    TITANIUM_COGWHEEL_SHAFT = block("titanium_cogwheel_shaft"), TITANIUM_SHAFT_HALF = block("titanium_shaft_half"),
    TUNGSTEN_STEEL_COGWHEEL_SHAFT = block("tungsten_steel_cogwheel_shaft"), TUNGSTEN_STEEL_SHAFT_HALF = block("tungsten_steel_shaft_half"),
    PALLADIUM_COGWHEEL_SHAFT = block("palladium_cogwheel_shaft"), PALLADIUM_SHAFT_HALF = block("palladium_shaft_half"),
    NAQUADAH_COGWHEEL_SHAFT = block("naquadah_cogwheel_shaft"), NAQUADAH_SHAFT_HALF = block("naquadah_shaft_half"),
    DARMSTADTIUM_COGWHEEL_SHAFT = block("darmstadtium_cogwheel_shaft"), DARMSTADTIUM_SHAFT_HALF = block("darmstadtium_shaft_half"),
    NEUTRONIUM_COGWHEEL_SHAFT = block("neutronium_cogwheel_shaft"), NEUTRONIUM_SHAFT_HALF = block("neutronium_shaft_half"),
    ANDESITE_MILLSTONE_INNER = block("andesite_millstone_inner"),
    STEEL_MILLSTONE_INNER = block("steel_millstone_inner"),
    ALUMINIUM_MILLSTONE_INNER = block("aluminium_millstone_inner"),
    STAINLESS_STEEL_MILLSTONE_INNER = block("stainless_steel_millstone_inner"),
    TITANIUM_MILLSTONE_INNER = block("titanium_millstone_inner"),
    TUNGSTEN_STEEL_MILLSTONE_INNER = block("tungsten_steel_millstone_inner"),
    PALLADIUM_MILLSTONE_INNER = block("palladium_millstone_inner"),
    NAQUADAH_MILLSTONE_INNER = block("naquadah_millstone_inner"),
    DARMSTADTIUM_MILLSTONE_INNER = block("darmstadtium_millstone_inner"),
    NEUTRONIUM_MILLSTONE_INNER = block("neutronium_millstone_inner");

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(Greate.MOD_ID, "block/" + path));
    }

    public static void register() {}
}
