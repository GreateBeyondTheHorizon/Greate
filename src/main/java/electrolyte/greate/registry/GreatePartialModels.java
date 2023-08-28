package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

public class GreatePartialModels {

    public static final PartialModel

    ANDESITE_SHAFTLESS_COGWHEEL = block("andesite_cogwheel_shaftless"), LARGE_ANDESITE_SHAFTLESS_COGWHEEL = block("large_andesite_cogwheel_shaftless"), ANDESITE_HALF_SHAFT = block("andesite_shaft_half"),
    STEEL_SHAFTLESS_COGWHEEL = block("steel_cogwheel_shaftless"), LARGE_STEEL_SHAFTLESS_COGWHEEL = block("large_steel_cogwheel_shaftless"), STEEL_HALF_SHAFT = block("steel_shaft_half"),
    ALUMINIUM_SHAFTLESS_COGWHEEL = block("aluminium_cogwheel_shaftless"), LARGE_ALUMINIUM_SHAFTLESS_COGWHEEL = block("large_aluminium_cogwheel_shaftless"), ALUMINIUM_HALF_SHAFT = block("aluminium_shaft_half"),
    STAINLESS_STEEL_SHAFTLESS_COGWHEEL = block("stainless_steel_cogwheel_shaftless"), LARGE_STAINLESS_STEEL_SHAFTLESS_COGWHEEL = block("large_stainless_steel_cogwheel_shaftless"), STAINLESS_STEEL_HALF_SHAFT = block("stainless_steel_shaft_half"),
    TITANIUM_SHAFTLESS_COGWHEEL = block("titanium_cogwheel_shaftless"), LARGE_TITANIUM_SHAFTLESS_COGWHEEL = block("large_titanium_cogwheel_shaftless"), TITANIUM_HALF_SHAFT = block("titanium_shaft_half"),
    TUNGSTEN_STEEL_SHAFTLESS_COGWHEEL = block("tungsten_steel_cogwheel_shaftless"), LARGE_TUNGSTEN_STEEL_SHAFTLESS_COGWHEEL = block("large_tungsten_steel_cogwheel_shaftless"), TUNGSTEN_STEEL_HALF_SHAFT = block("tungsten_steel_shaft_half"),
    PALLADIUM_SHAFTLESS_COGWHEEL = block("palladium_cogwheel_shaftless"), LARGE_PALLADIUM_SHAFTLESS_COGWHEEL = block("large_palladium_cogwheel_shaftless"), PALLADIUM_HALF_SHAFT = block("palladium_shaft_half"),
    NAQUADAH_SHAFTLESS_COGWHEEL = block("naquadah_cogwheel_shaftless"), LARGE_NAQUADAH_SHAFTLESS_COGWHEEL = block("large_naquadah_cogwheel_shaftless"), NAQUADAH_HALF_SHAFT = block("naquadah_shaft_half"),
    DARMSTADTIUM_SHAFTLESS_COGWHEEL = block("darmstadtium_cogwheel_shaftless"), LARGE_DARMSTADTIUM_SHAFTLESS_COGWHEEL = block("large_darmstadtium_cogwheel_shaftless"), DARMSTADTIUM_HALF_SHAFT = block("darmstadtium_shaft_half"),
    NEUTRONIUM_SHAFTLESS_COGWHEEL = block("neutronium_cogwheel_shaftless"), LARGE_NEUTRONIUM_SHAFTLESS_COGWHEEL = block("large_neutronium_cogwheel_shaftless"), NEUTRONIUM_HALF_SHAFT = block("neutronium_shaft_half"),

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
