package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

public class GreatePartialModels {

    public static final PartialModel

    ANDESITE_COGWHEEL_SHAFTLESS = block("andesite_cogwheel_shaftless"), SHAFTLESS_LARGE_ANDESITE_COGWHEEL = block("large_andesite_cogwheel_shaftless"), ANDESITE_SHAFT_HALF = block("andesite_shaft_half"),
    STEEL_COGWHEEL_SHAFTLESS = block("steel_cogwheel_shaftless"), SHAFTLESS_LARGE_STEEL_COGWHEEL = block("large_steel_cogwheel_shaftless"), STEEL_SHAFT_HALF = block("steel_shaft_half"),
    ALUMINIUM_COGWHEEL_SHAFTLESS = block("aluminium_cogwheel_shaftless"), SHAFTLESS_LARGE_ALUMINIUM_COGWHEEL = block("large_aluminium_cogwheel_shaftless"), ALUMINIUM_SHAFT_HALF = block("aluminium_shaft_half"),
    STAINLESS_STEEL_COGWHEEL_SHAFTLESS = block("stainless_steel_cogwheel_shaftless"), SHAFTLESS_LARGE_STAINLESS_STEEL_COGWHEEL = block("large_stainless_steel_cogwheel_shaftless"), STAINLESS_STEEL_SHAFT_HALF = block("stainless_steel_shaft_half"),
    TITANIUM_COGWHEEL_SHAFTLESS = block("titanium_cogwheel_shaftless"), SHAFTLESS_LARGE_TITANIUM_COGWHEEL = block("large_titanium_cogwheel_shaftless"), TITANIUM_SHAFT_HALF = block("titanium_shaft_half"),
    TUNGSTEN_STEEL_COGWHEEL_SHAFTLESS = block("tungsten_steel_cogwheel_shaftless"), SHAFTLESS_LARGE_TUNGSTEN_STEEL_COGWHEEL = block("large_tungsten_steel_cogwheel_shaftless"), TUNGSTEN_STEEL_SHAFT_HALF = block("tungsten_steel_shaft_half"),
    PALLADIUM_COGWHEEL_SHAFTLESS = block("palladium_cogwheel_shaftless"), SHAFTLESS_LARGE_PALLADIUM_COGWHEEL = block("large_palladium_cogwheel_shaftless"), PALLADIUM_SHAFT_HALF = block("palladium_shaft_half"),
    NAQUADAH_COGWHEEL_SHAFTLESS = block("naquadah_cogwheel_shaftless"), SHAFTLESS_LARGE_NAQUADAH_COGWHEEL = block("large_naquadah_cogwheel_shaftless"), NAQUADAH_SHAFT_HALF = block("naquadah_shaft_half"),
    DARMSTADTIUM_COGWHEEL_SHAFTLESS = block("darmstadtium_cogwheel_shaftless"), SHAFTLESS_LARGE_DARMSTADTIUM_COGWHEEL = block("large_darmstadtium_cogwheel_shaftless"), DARMSTADTIUM_SHAFT_HALF = block("darmstadtium_shaft_half"),
    NEUTRONIUM_COGWHEEL_SHAFTLESS = block("neutronium_cogwheel_shaftless"), SHAFTLESS_LARGE_NEUTRONIUM_COGWHEEL = block("large_neutronium_cogwheel_shaftless"), NEUTRONIUM_SHAFT_HALF = block("neutronium_shaft_half"),

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
