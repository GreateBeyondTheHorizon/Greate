package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.jozufozu.flywheel.core.PartialModel;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.Greate.REGISTRATE;

public class GreatePartialModels {

    // Cogwheel
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
            NEUTRONIUM_COGWHEEL_SHAFT = block("neutronium_cogwheel_shaft"), NEUTRONIUM_SHAFT_HALF = block("neutronium_shaft_half");

    // Millstone
    public static final PartialModel
            ANDESITE_MILLSTONE_INNER = block("andesite_millstone_inner"),
            STEEL_MILLSTONE_INNER = block("steel_millstone_inner"),
            ALUMINIUM_MILLSTONE_INNER = block("aluminium_millstone_inner"),
            STAINLESS_STEEL_MILLSTONE_INNER = block("stainless_steel_millstone_inner"),
            TITANIUM_MILLSTONE_INNER = block("titanium_millstone_inner"),
            TUNGSTENSTEEL_MILLSTONE_INNER = block("tungstensteel_millstone_inner"),
            PALLADIUM_MILLSTONE_INNER = block("palladium_millstone_inner"),
            NAQUADAH_MILLSTONE_INNER = block("naquadah_millstone_inner"),
            DARMSTADTIUM_MILLSTONE_INNER = block("darmstadtium_millstone_inner"),
            NEUTRONIUM_MILLSTONE_INNER = block("neutronium_millstone_inner");

    // Belt
    public static final Map<Material, List<PartialModel>> NEW_BELT_MODELS = new HashMap<>();
    public static final PartialModel
            RUBBER_BELT_ANDESITE_PULLEY = beltPulley(Rubber, Andesite),
            RUBBER_BELT_STEEL_PULLEY = beltPulley(Rubber, Steel),
            RUBBER_BELT_START = belt(Rubber, BeltPart.START), RUBBER_BELT_MIDDLE = belt(Rubber, BeltPart.MIDDLE),
            RUBBER_BELT_END = belt(Rubber, BeltPart.END), RUBBER_BELT_START_BOTTOM = belt(Rubber, BeltPart.BOTTOM),
            RUBBER_BELT_MIDDLE_BOTTOM = belt(Rubber, BeltPart.MIDDLE_BOTTOM), RUBBER_BELT_END_BOTTOM = belt(Rubber, BeltPart.END_BOTTOM),
            RUBBER_BELT_DIAGONAL_START = belt(Rubber, BeltPart.DIAGONAL_START), RUBBER_BELT_DIAGONAL_MIDDLE = belt(Rubber, BeltPart.DIAGONAL_MIDDLE),
            RUBBER_BELT_DIAGONAL_END = belt(Rubber, BeltPart.DIAGONAL_END),

            SILICONE_RUBBER_BELT_ALUMINIUM_PULLEY = beltPulley(SiliconeRubber, Aluminium),
            SILICONE_RUBBER_BELT_STAINLESS_STEEL_PULLEY = beltPulley(SiliconeRubber, StainlessSteel),
            SILICONE_RUBBER_BELT_START = belt(SiliconeRubber, BeltPart.START), SILICONE_RUBBER_BELT_MIDDLE = belt(SiliconeRubber, BeltPart.MIDDLE),
            SILICONE_RUBBER_BELT_END = belt(SiliconeRubber, BeltPart.END), SILICONE_RUBBER_BELT_START_BOTTOM = belt(SiliconeRubber, BeltPart.BOTTOM),
            SILICONE_RUBBER_BELT_MIDDLE_BOTTOM = belt(SiliconeRubber, BeltPart.MIDDLE_BOTTOM), SILICONE_RUBBER_BELT_END_BOTTOM = belt(SiliconeRubber, BeltPart.END_BOTTOM),
            SILICONE_RUBBER_BELT_DIAGONAL_START = belt(SiliconeRubber, BeltPart.DIAGONAL_START), SILICONE_RUBBER_BELT_DIAGONAL_MIDDLE = belt(SiliconeRubber, BeltPart.DIAGONAL_MIDDLE),
            SILICONE_RUBBER_BELT_DIAGONAL_END = belt(SiliconeRubber, BeltPart.DIAGONAL_END),

            POLYETHYLENE_BELT_TITANIUM_PULLEY = beltPulley(Polyethylene, Titanium),
            POLYETHYLENE_BELT_TUNGSTENSTEEL_PULLEY = beltPulley(Polyethylene, TungstenSteel),
            POLYETHYLENE_BELT_START = belt(Polyethylene, BeltPart.START), POLYETHYLENE_BELT_MIDDLE = belt(Polyethylene, BeltPart.MIDDLE),
            POLYETHYLENE_BELT_END = belt(Polyethylene, BeltPart.END), POLYETHYLENE_BELT_START_BOTTOM = belt(Polyethylene, BeltPart.BOTTOM),
            POLYETHYLENE_BELT_MIDDLE_BOTTOM = belt(Polyethylene, BeltPart.MIDDLE_BOTTOM), POLYETHYLENE_BELT_END_BOTTOM = belt(Polyethylene, BeltPart.END_BOTTOM),
            POLYETHYLENE_BELT_DIAGONAL_START = belt(Polyethylene, BeltPart.DIAGONAL_START), POLYETHYLENE_BELT_DIAGONAL_MIDDLE = belt(Polyethylene, BeltPart.DIAGONAL_MIDDLE),
            POLYETHYLENE_BELT_DIAGONAL_END = belt(Polyethylene, BeltPart.DIAGONAL_END),

            POLYTETRAFLUOROETHYLENE_BELT_PALLADIUM_PULLEY = beltPulley(Polytetrafluoroethylene, Palladium),
            POLYTETRAFLUOROETHYLENE_BELT_NAQUADAH_PULLEY = beltPulley(Polytetrafluoroethylene, Naquadah),
            POLYTETRAFLUOROETHYLENE_BELT_START = belt(Polytetrafluoroethylene, BeltPart.START), POLYTETRAFLUOROETHYLENE_BELT_MIDDLE = belt(Polytetrafluoroethylene, BeltPart.MIDDLE),
            POLYTETRAFLUOROETHYLENE_BELT_END = belt(Polytetrafluoroethylene, BeltPart.END), POLYTETRAFLUOROETHYLENE_BELT_START_BOTTOM = belt(Polytetrafluoroethylene, BeltPart.BOTTOM),
            POLYTETRAFLUOROETHYLENE_BELT_MIDDLE_BOTTOM = belt(Polytetrafluoroethylene, BeltPart.MIDDLE_BOTTOM), POLYTETRAFLUOROETHYLENE_BELT_END_BOTTOM = belt(Polytetrafluoroethylene, BeltPart.END_BOTTOM),
            POLYTETRAFLUOROETHYLENE_BELT_DIAGONAL_START = belt(Polytetrafluoroethylene, BeltPart.DIAGONAL_START), POLYTETRAFLUOROETHYLENE_BELT_DIAGONAL_MIDDLE = belt(Polytetrafluoroethylene, BeltPart.DIAGONAL_MIDDLE),
            POLYTETRAFLUOROETHYLENE_BELT_DIAGONAL_END = belt(Polytetrafluoroethylene, BeltPart.DIAGONAL_END),

            POLYBENZIMIDAZOLE_BELT_DARMSTADTIUM_PULLEY = beltPulley(Polybenzimidazole, Darmstadtium),
            POLYBENZIMIDAZOLE_BELT_NEUTRONIUM_PULLEY = beltPulley(Polybenzimidazole, Neutronium),
            POLYBENZIMIDAZOLE_BELT_START = belt(Polybenzimidazole, BeltPart.START), POLYBENZIMIDAZOLE_BELT_MIDDLE = belt(Polybenzimidazole, BeltPart.MIDDLE),
            POLYBENZIMIDAZOLE_BELT_END = belt(Polybenzimidazole, BeltPart.END), POLYBENZIMIDAZOLE_BELT_START_BOTTOM = belt(Polybenzimidazole, BeltPart.BOTTOM),
            POLYBENZIMIDAZOLE_BELT_MIDDLE_BOTTOM = belt(Polybenzimidazole, BeltPart.MIDDLE_BOTTOM), POLYBENZIMIDAZOLE_BELT_END_BOTTOM = belt(Polybenzimidazole, BeltPart.END_BOTTOM),
            POLYBENZIMIDAZOLE_BELT_DIAGONAL_START = belt(Polybenzimidazole, BeltPart.DIAGONAL_START), POLYBENZIMIDAZOLE_BELT_DIAGONAL_MIDDLE = belt(Polybenzimidazole, BeltPart.DIAGONAL_MIDDLE),
            POLYBENZIMIDAZOLE_BELT_DIAGONAL_END = belt(Polybenzimidazole, BeltPart.DIAGONAL_END);
    public static final PartialModel
            BELT_OVERLAY_DIAGONAL_START = block("belt_overlay_diagonal_start"), BELT_OVERLAY_DIAGONAL_END = block("belt_overlay_diagonal_end"), BELT_OVERLAY_DIAGONAL_MIDDLE = block("belt_overlay_diagonal_middle"),
            BELT_OVERLAY_START_BOTTOM = block("belt_overlay_start_bottom"), BELT_OVERLAY_END_BOTTOM = block("belt_overlay_end_bottom"), BELT_OVERLAY_MIDDLE_BOTTOM = block("belt_overlay_middle_bottom"),
            BELT_OVERLAY_START = block("belt_overlay_start"), BELT_OVERLAY_END = block("belt_overlay_end"), BELT_OVERLAY_MIDDLE = block("belt_overlay_middle");

    // Mechanical Press
    public static final PartialModel
            ANDESITE_MECHANICAL_PRESS_HEAD = block("andesite_mechanical_press_head"),
            STEEL_MECHANICAL_PRESS_HEAD = block("steel_mechanical_press_head"),
            ALUMINIUM_MECHANICAL_PRESS_HEAD = block("aluminium_mechanical_press_head"),
            STAINLESS_STEEL_MECHANICAL_PRESS_HEAD = block("stainless_steel_mechanical_press_head"),
            TITANIUM_MECHANICAL_PRESS_HEAD = block("titanium_mechanical_press_head"),
            TUNGSTENSTEEL_MECHANICAL_PRESS_HEAD = block("tungstensteel_mechanical_press_head"),
            PALLADIUM_MECHANICAL_PRESS_HEAD = block("palladium_mechanical_press_head"),
            NAQUADAH_MECHANICAL_PRESS_HEAD = block("naquadah_mechanical_press_head"),
            DARMSTADTIUM_MECHANICAL_PRESS_HEAD = block("darmstadtium_mechanical_press_head"),
            NEUTRONIUM_MECHANICAL_PRESS_HEAD = block("neutronium_mechanical_press_head");

    // Mechanical Mixer
    public static final PartialModel
            ANDESITE_MECHANICAL_MIXER_HEAD = block("andesite_mechanical_mixer_head"),
            STEEL_MECHANICAL_MIXER_HEAD = block("steel_mechanical_mixer_head"),
            ALUMINIUM_MECHANICAL_MIXER_HEAD = block("aluminium_mechanical_mixer_head"),
            STAINLESS_STEEL_MECHANICAL_MIXER_HEAD = block("stainless_steel_mechanical_mixer_head"),
            TITANIUM_MECHANICAL_MIXER_HEAD = block("titanium_mechanical_mixer_head"),
            TUNGSTENSTEEL_MECHANICAL_MIXER_HEAD = block("tungstensteel_mechanical_mixer_head"),
            PALLADIUM_MECHANICAL_MIXER_HEAD = block("palladium_mechanical_mixer_head"),
            NAQUADAH_MECHANICAL_MIXER_HEAD = block("naquadah_mechanical_mixer_head"),
            DARMSTADTIUM_MECHANICAL_MIXER_HEAD = block("darmstadtium_mechanical_mixer_head"),
            NEUTRONIUM_MECHANICAL_MIXER_HEAD = block("neutronium_mechanical_mixer_head");

    // Mechanical Saw
    public static final PartialModel
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
            NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE = block("neutronium_mechanical_saw_blade_vertical_active"), NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_REVERSED = block("neutronium_mechanical_saw_blade_vertical_reversed"), NEUTRONIUM_MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE = block("neutronium_mechanical_saw_blade_vertical_inactive");

    // Mechanical Pump
    public static final PartialModel
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

    private static PartialModel belt(Material beltMaterial, BeltPart beltPart) {
        String beltpartName = beltPart.name().toLowerCase();
        PartialModel partialModel = new PartialModel(new ResourceLocation(Greate.MOD_ID, "block/" + beltMaterial.getName() + "_belt_" + beltpartName));
        List<PartialModel> partialModels = NEW_BELT_MODELS.getOrDefault(beltMaterial, new ArrayList<>());
        partialModels.add(partialModel);
        NEW_BELT_MODELS.put(beltMaterial, partialModels);
        return partialModel;
    }

    private static PartialModel beltPulley(Material beltMaterial, Material pulleyMaterial) {
        PartialModel partialModel = new PartialModel(new ResourceLocation(Greate.MOD_ID, "block/" + beltMaterial.getName() + "_belt_" + pulleyMaterial.getName() + "_pulley"));
        List<PartialModel> partialModels = NEW_BELT_MODELS.getOrDefault(beltMaterial, new ArrayList<>());
        partialModels.add(partialModel);
        NEW_BELT_MODELS.put(beltMaterial, partialModels);
        return partialModel;
    }

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(Greate.MOD_ID, "block/" + path));
    }

    public static void register() {}

    public enum BeltPart {
        PULLEY,
        START,
        END,
        MIDDLE,
        BOTTOM,
        MIDDLE_BOTTOM,
        END_BOTTOM,
        DIAGONAL_START,
        DIAGONAL_MIDDLE,
        DIAGONAL_END,
    }
}
