package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.BELT_TYPE;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreatePartialModels {

    public static final Map<BELT_TYPE, List<PartialModel>> NEW_BELT_MODELS = new HashMap<>();

    public static final PartialModel

    ANDESITE_COGWHEEL_SHAFTLESS = block("andesite_cogwheel_shaftless"), LARGE_ANDESITE_COGWHEEL_SHAFTLESS = block("large_andesite_cogwheel_shaftless"),
    STEEL_COGWHEEL_SHAFTLESS = block("steel_cogwheel_shaftless"), LARGE_STEEL_COGWHEEL_SHAFTLESS = block("large_steel_cogwheel_shaftless"),
    ALUMINIUM_COGWHEEL_SHAFTLESS = block("aluminium_cogwheel_shaftless"), LARGE_ALUMINIUM_COGWHEEL_SHAFTLESS = block("large_aluminium_cogwheel_shaftless"),
    STAINLESS_STEEL_COGWHEEL_SHAFTLESS = block("stainless_steel_cogwheel_shaftless"), LARGE_STAINLESS_STEEL_COGWHEEL_SHAFTLESS = block("large_stainless_steel_cogwheel_shaftless"),
    TITANIUM_COGWHEEL_SHAFTLESS = block("titanium_cogwheel_shaftless"), LARGE_TITANIUM_COGWHEEL_SHAFTLESS = block("large_titanium_cogwheel_shaftless"),
    TUNGSTENSTEEL_COGWHEEL_SHAFTLESS = block("tungstensteel_cogwheel_shaftless"), LARGE_TUNGSTENSTEEL_COGWHEEL_SHAFTLESS = block("large_tungstensteel_cogwheel_shaftless"),
    PALLADIUM_COGWHEEL_SHAFTLESS = block("palladium_cogwheel_shaftless"), LARGE_PALLADIUM_COGWHEEL_SHAFTLESS = block("large_palladium_cogwheel_shaftless"),
    NAQUADAH_COGWHEEL_SHAFTLESS = block("naquadah_cogwheel_shaftless"), LARGE_NAQUADAH_COGWHEEL_SHAFTLESS = block("large_naquadah_cogwheel_shaftless"),
    DARMSTADTIUM_COGWHEEL_SHAFTLESS = block("darmstadtium_cogwheel_shaftless"), LARGE_DARMSTADTIUM_COGWHEEL_SHAFTLESS = block("large_darmstadtium_cogwheel_shaftless"),
    NEUTRONIUM_COGWHEEL_SHAFTLESS = block("neutronium_cogwheel_shaftless"), LARGE_NEUTRONIUM_COGWHEEL_SHAFTLESS = block("large_neutronium_cogwheel_shaftless"),
    ANDESITE_COGWHEEL_SHAFT = block("andesite_cogwheel_shaft"), ANDESITE_SHAFT_HALF = block("andesite_shaft_half"),
    STEEL_COGWHEEL_SHAFT = block("steel_cogwheel_shaft"), STEEL_SHAFT_HALF = block("steel_shaft_half"),
    ALUMINIUM_COGWHEEL_SHAFT = block("aluminium_cogwheel_shaft"), ALUMINIUM_SHAFT_HALF = block("aluminium_shaft_half"),
    STAINLESS_STEEL_COGWHEEL_SHAFT = block("stainless_steel_cogwheel_shaft"), STAINLESS_STEEL_SHAFT_HALF = block("stainless_steel_shaft_half"),
    TITANIUM_COGWHEEL_SHAFT = block("titanium_cogwheel_shaft"), TITANIUM_SHAFT_HALF = block("titanium_shaft_half"),
    TUNGSTENSTEEL_COGWHEEL_SHAFT = block("tungstensteel_cogwheel_shaft"), TUNGSTENSTEEL_SHAFT_HALF = block("tungstensteel_shaft_half"),
    PALLADIUM_COGWHEEL_SHAFT = block("palladium_cogwheel_shaft"), PALLADIUM_SHAFT_HALF = block("palladium_shaft_half"),
    NAQUADAH_COGWHEEL_SHAFT = block("naquadah_cogwheel_shaft"), NAQUADAH_SHAFT_HALF = block("naquadah_shaft_half"),
    DARMSTADTIUM_COGWHEEL_SHAFT = block("darmstadtium_cogwheel_shaft"), DARMSTADTIUM_SHAFT_HALF = block("darmstadtium_shaft_half"),
    NEUTRONIUM_COGWHEEL_SHAFT = block("neutronium_cogwheel_shaft"), NEUTRONIUM_SHAFT_HALF = block("neutronium_shaft_half"),
    ANDESITE_MILLSTONE_INNER = block("andesite_millstone_inner"),
    STEEL_MILLSTONE_INNER = block("steel_millstone_inner"),
    ALUMINIUM_MILLSTONE_INNER = block("aluminium_millstone_inner"),
    STAINLESS_STEEL_MILLSTONE_INNER = block("stainless_steel_millstone_inner"),
    TITANIUM_MILLSTONE_INNER = block("titanium_millstone_inner"),
    TUNGSTENSTEEL_MILLSTONE_INNER = block("tungstensteel_millstone_inner"),
    PALLADIUM_MILLSTONE_INNER = block("palladium_millstone_inner"),
    NAQUADAH_MILLSTONE_INNER = block("naquadah_millstone_inner"),
    DARMSTADTIUM_MILLSTONE_INNER = block("darmstadtium_millstone_inner"),
    NEUTRONIUM_MILLSTONE_INNER = block("neutronium_millstone_inner"),

    RUBBER_BELT_ANDESITE_PULLEY = belt(BELT_TYPE.RUBBER,"rubber_belt_andesite_pulley"),
    RUBBER_BELT_STEEL_PULLEY = belt(BELT_TYPE.RUBBER,"rubber_belt_steel_pulley"),
    RUBBER_BELT_START = belt(BELT_TYPE.RUBBER,"rubber_belt_start"), RUBBER_BELT_MIDDLE = belt(BELT_TYPE.RUBBER,"rubber_belt_middle"),
    RUBBER_BELT_END = belt(BELT_TYPE.RUBBER,"rubber_belt_end"), RUBBER_BELT_START_BOTTOM = belt(BELT_TYPE.RUBBER,"rubber_belt_start_bottom"),
    RUBBER_BELT_MIDDLE_BOTTOM = belt(BELT_TYPE.RUBBER,"rubber_belt_middle_bottom"), RUBBER_BELT_END_BOTTOM = belt(BELT_TYPE.RUBBER,"rubber_belt_end_bottom"),
    RUBBER_BELT_DIAGONAL_START = belt(BELT_TYPE.RUBBER,"rubber_belt_diagonal_start"), RUBBER_BELT_DIAGONAL_MIDDLE = belt(BELT_TYPE.RUBBER,"rubber_belt_diagonal_middle"),
    RUBBER_BELT_DIAGONAL_END = belt(BELT_TYPE.RUBBER,"rubber_belt_diagonal_end"),

    SILICONE_RUBBER_BELT_ALUMINIUM_PULLEY = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_aluminium_pulley"),
    SILICONE_RUBBER_BELT_STAINLESS_STEEL_PULLEY = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_stainless_steel_pulley"),
    SILICONE_RUBBER_BELT_START = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_start"), SILICONE_RUBBER_BELT_MIDDLE = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_middle"),
    SILICONE_RUBBER_BELT_END = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_end"), SILICONE_RUBBER_BELT_START_BOTTOM = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_start_bottom"),
    SILICONE_RUBBER_BELT_MIDDLE_BOTTOM = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_middle_bottom"), SILICONE_RUBBER_BELT_END_BOTTOM = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_end_bottom"),
    SILICONE_RUBBER_BELT_DIAGONAL_START = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_diagonal_start"), SILICONE_RUBBER_BELT_DIAGONAL_MIDDLE = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_diagonal_middle"),
    SILICONE_RUBBER_BELT_DIAGONAL_END = belt(BELT_TYPE.SILICONE_RUBBER,"silicone_rubber_belt_diagonal_end"),

    POLYETHYLENE_BELT_TITANIUM_PULLEY = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_titanium_pulley"),
    POLYETHYLENE_BELT_TUNGSTENSTEEL_PULLEY = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_tungstensteel_pulley"),
    POLYETHYLENE_BELT_START = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_start"), POLYETHYLENE_BELT_MIDDLE = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_middle"),
    POLYETHYLENE_BELT_END = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_end"), POLYETHYLENE_BELT_START_BOTTOM = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_start_bottom"),
    POLYETHYLENE_BELT_MIDDLE_BOTTOM = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_middle_bottom"), POLYETHYLENE_BELT_END_BOTTOM = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_end_bottom"),
    POLYETHYLENE_BELT_DIAGONAL_START = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_diagonal_start"), POLYETHYLENE_BELT_DIAGONAL_MIDDLE = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_diagonal_middle"),
    POLYETHYLENE_BELT_DIAGONAL_END = belt(BELT_TYPE.POLYETHYLENE,"polyethylene_belt_diagonal_end"),

    POLYTETRAFLUOROETHYLENE_BELT_PALLADIUM_PULLEY = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_palladium_pulley"),
    POLYTETRAFLUOROETHYLENE_BELT_NAQUADAH_PULLEY = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_naquadah_pulley"),
    POLYTETRAFLUOROETHYLENE_BELT_START = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_start"), POLYTETRAFLUOROETHYLENE_BELT_MIDDLE = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_middle"),
    POLYTETRAFLUOROETHYLENE_BELT_END = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_end"), POLYTETRAFLUOROETHYLENE_BELT_START_BOTTOM = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_start_bottom"),
    POLYTETRAFLUOROETHYLENE_BELT_MIDDLE_BOTTOM = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_middle_bottom"), POLYTETRAFLUOROETHYLENE_BELT_END_BOTTOM = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_end_bottom"),
    POLYTETRAFLUOROETHYLENE_BELT_DIAGONAL_START = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_diagonal_start"), POLYTETRAFLUOROETHYLENE_BELT_DIAGONAL_MIDDLE = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_diagonal_middle"),
    POLYTETRAFLUOROETHYLENE_BELT_DIAGONAL_END = belt(BELT_TYPE.POLYTETRAFLUOROETHYLENE,"polytetrafluoroethylene_belt_diagonal_end"),

    POLYBENZIMIDAZOLE_BELT_DARMSTADTIUM_PULLEY = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_darmstadtium_pulley"),
    POLYBENZIMIDAZOLE_BELT_NEUTRONIUM_PULLEY = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_neutronium_pulley"),
    POLYBENZIMIDAZOLE_BELT_START = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_start"), POLYBENZIMIDAZOLE_BELT_MIDDLE = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_middle"),
    POLYBENZIMIDAZOLE_BELT_END = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_end"), POLYBENZIMIDAZOLE_BELT_START_BOTTOM = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_start_bottom"),
    POLYBENZIMIDAZOLE_BELT_MIDDLE_BOTTOM = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_middle_bottom"), POLYBENZIMIDAZOLE_BELT_END_BOTTOM = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_end_bottom"),
    POLYBENZIMIDAZOLE_BELT_DIAGONAL_START = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_diagonal_start"), POLYBENZIMIDAZOLE_BELT_DIAGONAL_MIDDLE = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_diagonal_middle"),
    POLYBENZIMIDAZOLE_BELT_DIAGONAL_END = belt(BELT_TYPE.POLYBENZIMIDAZOLE,"polybenzimidazole_belt_diagonal_end"),
    BELT_OVERLAY_DIAGONAL_START = block("belt_overlay_diagonal_start"), BELT_OVERLAY_DIAGONAL_END = block("belt_overlay_diagonal_end"), BELT_OVERLAY_DIAGONAL_MIDDLE = block("belt_overlay_diagonal_middle"),
    BELT_OVERLAY_START_BOTTOM = block("belt_overlay_start_bottom"), BELT_OVERLAY_END_BOTTOM = block("belt_overlay_end_bottom"), BELT_OVERLAY_MIDDLE_BOTTOM = block("belt_overlay_middle_bottom"),
    BELT_OVERLAY_START = block("belt_overlay_start"), BELT_OVERLAY_END = block("belt_overlay_end"), BELT_OVERLAY_MIDDLE = block("belt_overlay_middle"),
    ANDESITE_MECHANICAL_PRESS_HEAD = block("andesite_mechanical_press_head"),
    STEEL_MECHANICAL_PRESS_HEAD = block("steel_mechanical_press_head"),
    ALUMINIUM_MECHANICAL_PRESS_HEAD = block("aluminium_mechanical_press_head"),
    STAINLESS_STEEL_MECHANICAL_PRESS_HEAD = block("stainless_steel_mechanical_press_head"),
    TITANIUM_MECHANICAL_PRESS_HEAD = block("titanium_mechanical_press_head"),
    TUNGSTENSTEEL_MECHANICAL_PRESS_HEAD = block("tungstensteel_mechanical_press_head"),
    PALLADIUM_MECHANICAL_PRESS_HEAD = block("palladium_mechanical_press_head"),
    NAQUADAH_MECHANICAL_PRESS_HEAD = block("naquadah_mechanical_press_head"),
    DARMSTADTIUM_MECHANICAL_PRESS_HEAD = block("darmstadtium_mechanical_press_head"),
    NEUTRONIUM_MECHANICAL_PRESS_HEAD = block("neutronium_mechanical_press_head"),

    ANDESITE_MECHANICAL_MIXER_HEAD = block("andesite_mechanical_mixer_head"),
    STEEL_MECHANICAL_MIXER_HEAD = block("steel_mechanical_mixer_head"),
    ALUMINIUM_MECHANICAL_MIXER_HEAD = block("aluminium_mechanical_mixer_head"),
    STAINLESS_STEEL_MECHANICAL_MIXER_HEAD = block("stainless_steel_mechanical_mixer_head"),
    TITANIUM_MECHANICAL_MIXER_HEAD = block("titanium_mechanical_mixer_head"),
    TUNGSTENSTEEL_MECHANICAL_MIXER_HEAD = block("tungstensteel_mechanical_mixer_head"),
    PALLADIUM_MECHANICAL_MIXER_HEAD = block("palladium_mechanical_mixer_head"),
    NAQUADAH_MECHANICAL_MIXER_HEAD = block("naquadah_mechanical_mixer_head"),
    DARMSTADTIUM_MECHANICAL_MIXER_HEAD = block("darmstadtium_mechanical_mixer_head"),
    NEUTRONIUM_MECHANICAL_MIXER_HEAD = block("neutronium_mechanical_mixer_head"),

    ANDESITE_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("andesite_mechanical_saw_blade_horizontal_active"), ANDESITE_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("andesite_mechanical_saw_blade_horizontal_reversed"), ANDESITE_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("andesite_mechanical_saw_blade_horizontal_inactive"),
    ANDESITE_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("andesite_mechanical_saw_blade_vertical_active"), ANDESITE_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("andesite_mechanical_saw_blade_vertical_reversed"), ANDESITE_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("andesite_mechanical_saw_blade_vertical_inactive"),
    STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("steel_mechanical_saw_blade_horizontal_active"), STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("steel_mechanical_saw_blade_horizontal_reversed"), STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("steel_mechanical_saw_blade_horizontal_inactive"),
    STEEL_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("steel_mechanical_saw_blade_vertical_active"), STEEL_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("steel_mechanical_saw_blade_vertical_reversed"), STEEL_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("steel_mechanical_saw_blade_vertical_inactive"),
    ALUMINIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("aluminium_mechanical_saw_blade_horizontal_active"), ALUMINIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("aluminium_mechanical_saw_blade_horizontal_reversed"), ALUMINIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("aluminium_mechanical_saw_blade_horizontal_inactive"),
    ALUMINIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("aluminium_mechanical_saw_blade_vertical_active"), ALUMINIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("aluminium_mechanical_saw_blade_vertical_reversed"), ALUMINIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("aluminium_mechanical_saw_blade_vertical_inactive"),
    STAINLESS_STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("stainless_steel_mechanical_saw_blade_horizontal_active"), STAINLESS_STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("stainless_steel_mechanical_saw_blade_horizontal_reversed"), STAINLESS_STEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("stainless_steel_mechanical_saw_blade_horizontal_inactive"),
    STAINLESS_STEEL_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("stainless_steel_mechanical_saw_blade_vertical_active"), STAINLESS_STEEL_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("stainless_steel_mechanical_saw_blade_vertical_reversed"), STAINLESS_STEEL_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("stainless_steel_mechanical_saw_blade_vertical_inactive"),
    TITANIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("titanium_mechanical_saw_blade_horizontal_active"), TITANIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("titanium_mechanical_saw_blade_horizontal_reversed"), TITANIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("titanium_mechanical_saw_blade_horizontal_inactive"),
    TITANIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("titanium_mechanical_saw_blade_vertical_active"), TITANIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("titanium_mechanical_saw_blade_vertical_reversed"), TITANIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("titanium_mechanical_saw_blade_vertical_inactive"),
    TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("tungstensteel_mechanical_saw_blade_horizontal_active"), TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("tungstensteel_mechanical_saw_blade_horizontal_reversed"), TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("tungstensteel_mechanical_saw_blade_horizontal_inactive"),
    TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("tungstensteel_mechanical_saw_blade_vertical_active"), TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("tungstensteel_mechanical_saw_blade_vertical_reversed"), TUNGSTENSTEEL_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("tungstensteel_mechanical_saw_blade_vertical_inactive"),
    PALLADIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("palladium_mechanical_saw_blade_horizontal_active"), PALLADIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("palladium_mechanical_saw_blade_horizontal_reversed"), PALLADIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("palladium_mechanical_saw_blade_horizontal_inactive"),
    PALLADIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("palladium_mechanical_saw_blade_vertical_active"), PALLADIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("palladium_mechanical_saw_blade_vertical_reversed"), PALLADIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("palladium_mechanical_saw_blade_vertical_inactive"),
    NAQUADAH_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("naquadah_mechanical_saw_blade_horizontal_active"), NAQUADAH_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("naquadah_mechanical_saw_blade_horizontal_reversed"), NAQUADAH_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("naquadah_mechanical_saw_blade_horizontal_inactive"),
    NAQUADAH_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("naquadah_mechanical_saw_blade_vertical_active"), NAQUADAH_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("naquadah_mechanical_saw_blade_vertical_reversed"), NAQUADAH_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("naquadah_mechanical_saw_blade_vertical_inactive"),
    DARMSTADTIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("darmstadtium_mechanical_saw_blade_horizontal_active"), DARMSTADTIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("darmstadtium_mechanical_saw_blade_horizontal_reversed"), DARMSTADTIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("darmstadtium_mechanical_saw_blade_horizontal_inactive"),
    DARMSTADTIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("darmstadtium_mechanical_saw_blade_vertical_active"), DARMSTADTIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("darmstadtium_mechanical_saw_blade_vertical_reversed"), DARMSTADTIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("darmstadtium_mechanical_saw_blade_vertical_inactive"),
    NEUTRONIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE = block("neutronium_mechanical_saw_blade_horizontal_active"), NEUTRONIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED = block("neutronium_mechanical_saw_blade_horizontal_reversed"), NEUTRONIUM_MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE = block("neutronium_mechanical_saw_blade_horizontal_inactive"),
    NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("neutronium_mechanical_saw_blade_vertical_active"), NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("neutronium_mechanical_saw_blade_vertical_reversed"), NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("neutronium_mechanical_saw_blade_vertical_inactive"),

    ANDESITE_PUMP_COG = block("andesite_mechanical_pump_cog"),
    STEEL_PUMP_COG = block("steel_mechanical_pump_cog"),
    ALUMINIUM_PUMP_COG = block("aluminium_mechanical_pump_cog"),
    STAINLESS_STEEL_PUMP_COG = block("stainless_steel_mechanical_pump_cog"),
    TITANIUM_PUMP_COG = block("titanium_mechanical_pump_cog"),
    TUNGSTENSTEEL_PUMP_COG = block("tungstensteel_mechanical_pump_cog"),
    PALLADIUM_PUMP_COG = block("palladium_mechanical_pump_cog"),
    NAQUADAH_PUMP_COG = block("naquadah_mechanical_pump_cog"),
    DARMSTADTIUM_PUMP_COG = block("darmstadtium_mechanical_pump_cog"),
    NEUTRONIUM_PUMP_COG = block("neutronium_mechanical_pump_cog");

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(Greate.MOD_ID, "block/" + path));
    }

    private static PartialModel belt(BELT_TYPE belt, String path) {
        PartialModel partialModel = new PartialModel(new ResourceLocation(Greate.MOD_ID, "block/" + path));
        List<PartialModel> partialModels = NEW_BELT_MODELS.getOrDefault(belt, new ArrayList<>());
        partialModels.add(partialModel);
        NEW_BELT_MODELS.put(belt, partialModels);
        return partialModel;
    }

    public static void register() {}
}
