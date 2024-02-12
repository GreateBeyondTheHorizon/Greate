package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.jozufozu.flywheel.core.PartialModel;
import electrolyte.greate.Greate;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.GreateValues.TM;

public class GreatePartialModels {

    // Cogwheel
    public static final PartialModel[] COGWHEEL_SHAFTLESS_MODELS = new PartialModel[10];
    public static final PartialModel[] LARGE_COGWHEEL_SHAFTLESS_MODELS = new PartialModel[10];
    public static final PartialModel[] COGWHEEL_SHAFT_MODELS = new PartialModel[10];
    public static final PartialModel[] SHAFT_HALF_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            Material tierMaterial = TM[tier];
            COGWHEEL_SHAFTLESS_MODELS[tier] = materialBlock(tierMaterial, "_cogwheel_shaftless");
            LARGE_COGWHEEL_SHAFTLESS_MODELS[tier] = materialBlock("large_", tierMaterial, "_cogwheel_shaftless");
            COGWHEEL_SHAFT_MODELS[tier] = materialBlock(tierMaterial, "_cogwheel_shaft");
            SHAFT_HALF_MODELS[tier] = materialBlock(tierMaterial, "_shaft_half");
        }
    }

    // Millstone
    public static final PartialModel[] MILLSTONE_INNER_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            MILLSTONE_INNER_MODELS[tier] = materialBlock(TM[tier], "_millstone_inner");
        }
    }

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
    public static final PartialModel[] MECHANICAL_PRESS_HEAD_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            MECHANICAL_PRESS_HEAD_MODELS[tier] = materialBlock(TM[tier], "_mechanical_press_head");
        }
    }

    // Mechanical Mixer
    public static final PartialModel[] MECHANICAL_MIXER_HEAD_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            MECHANICAL_MIXER_HEAD_MODELS[tier] = materialBlock(TM[tier], "_mechanical_mixer_head");
        }
    }

    // Mechanical Saw
    public static final PartialModel[] MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE_MODELS = new PartialModel[10];
    public static final PartialModel[] MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED_MODELS = new PartialModel[10];
    public static final PartialModel[] MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE_MODELS = new PartialModel[10];
    public static final PartialModel[] MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE_MODELS = new PartialModel[10];
    public static final PartialModel[] MECHANICAL_SAW_BLADE_VERTICAL_REVERSED_MODELS = new PartialModel[10];
    public static final PartialModel[] MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            // Horizontal
            MECHANICAL_SAW_BLADE_HORIZONTAL_ACTIVE_MODELS[tier] = materialBlock(TM[tier], "_mechanical_saw_blade_horizontal_active");
            MECHANICAL_SAW_BLADE_HORIZONTAL_REVERSED_MODELS[tier] = materialBlock(TM[tier], "_mechanical_saw_blade_horizontal_reversed");
            MECHANICAL_SAW_BLADE_HORIZONTAL_INACTIVE_MODELS[tier] = materialBlock(TM[tier], "_mechanical_saw_blade_horizontal_inactive");
            // Vertical
            MECHANICAL_SAW_BLADE_VERTICAL_ACTIVE_MODELS[tier] = materialBlock(TM[tier], "_mechanical_saw_blade_vertical_active");
            MECHANICAL_SAW_BLADE_VERTICAL_REVERSED_MODELS[tier] = materialBlock(TM[tier], "_mechanical_saw_blade_vertical_reversed");
            MECHANICAL_SAW_BLADE_VERTICAL_INACTIVE_MODELS[tier] = materialBlock(TM[tier], "_mechanical_saw_blade_vertical_inactive");
        }
    }

    // Mechanical Pump
    public static final PartialModel[] MECHANICAL_PUMP_COG_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            MECHANICAL_PUMP_COG_MODELS[tier] = materialBlock(TM[tier], "_mechanical_pump_cog");
        }
    }

    private static PartialModel belt(Material beltMaterial, BeltPart beltPart) {
        String beltpartName = beltPart.name().toLowerCase();
        PartialModel partialModel = materialBlock(beltMaterial, "_belt_" + beltpartName);
        List<PartialModel> partialModels = NEW_BELT_MODELS.getOrDefault(beltMaterial, new ArrayList<>());
        partialModels.add(partialModel);
        NEW_BELT_MODELS.put(beltMaterial, partialModels);
        return partialModel;
    }

    private static PartialModel beltPulley(Material beltMaterial, Material pulleyMaterial) {
        PartialModel partialModel = materialBlock(beltMaterial, "_belt_" + pulleyMaterial.getName() + "_pulley");
        List<PartialModel> partialModels = NEW_BELT_MODELS.getOrDefault(beltMaterial, new ArrayList<>());
        partialModels.add(partialModel);
        NEW_BELT_MODELS.put(beltMaterial, partialModels);
        return partialModel;
    }

    private static PartialModel materialBlock(String prefix, Material material, String postfix) {
        return block(prefix + material.getName() + postfix);
    }

    private static PartialModel materialBlock(Material material, String postfix) {
        return block(material.getName() + postfix);
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
