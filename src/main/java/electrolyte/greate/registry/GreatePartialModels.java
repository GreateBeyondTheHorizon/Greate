package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.Greate;

import static electrolyte.greate.GreateValues.TM;

public class GreatePartialModels {

    // Cogwheel, Shaft variants
    public static final PartialModel[] COGWHEEL_MODELS = new PartialModel[10];
    public static final PartialModel[] COGWHEEL_SHAFTLESS_MODELS = new PartialModel[10];
    public static final PartialModel[] LARGE_COGWHEEL_SHAFTLESS_MODELS = new PartialModel[10];
    public static final PartialModel[] COGWHEEL_SHAFT_MODELS = new PartialModel[10];
    public static final PartialModel[] POWERED_SHAFT_MODELS = new PartialModel[10];
    public static final PartialModel[] SHAFT_HALF_MODELS = new PartialModel[10];
    public static final PartialModel[] SHAFT_MODELS = new PartialModel[10];

    static {
        for (int tier = 0; tier < TM.length; tier++) {
            Material tierMaterial = TM[tier];
            COGWHEEL_MODELS[tier] = materialBlock(tierMaterial, "_cogwheel");
            COGWHEEL_SHAFTLESS_MODELS[tier] = materialBlock(tierMaterial, "_cogwheel_shaftless");
            LARGE_COGWHEEL_SHAFTLESS_MODELS[tier] = materialBlock("large_", tierMaterial, "_cogwheel_shaftless");
            COGWHEEL_SHAFT_MODELS[tier] = materialBlock(tierMaterial, "_cogwheel_shaft");
            POWERED_SHAFT_MODELS[tier] = materialBlock("powered_", tierMaterial, "_shaft");
            SHAFT_HALF_MODELS[tier] = materialBlock(tierMaterial, "_shaft_half");
            SHAFT_MODELS[tier] = materialBlock(tierMaterial, "_shaft");
        }
    }

    // Crushing Wheel
    public static final PartialModel[] CRUSHING_WHEEL_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            CRUSHING_WHEEL_MODELS[tier] = materialBlock(TM[tier], "_crushing_wheel");
        }
    }

    // Millstone
    public static final PartialModel[] MILLSTONE_INNER_MODELS = new PartialModel[10];
    static {
        for (int tier = 0; tier < TM.length; tier++) {
            MILLSTONE_INNER_MODELS[tier] = materialBlock(TM[tier], "_millstone_inner");
        }
    }

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

    public static final PartialModel[] FAN_INNER_MODELS = new PartialModel[10];
    static {
        for(int tier = 0; tier < TM.length; tier++) {
            FAN_INNER_MODELS[tier] = materialBlock(TM[tier], "_encased_fan_propeller");
        }
    }

    private static PartialModel materialBlock(String prefix, Material material, String postfix) {
        return block(prefix + material.getName() + postfix);
    }

    private static PartialModel materialBlock(Material material, String postfix) {
        return block(material.getName() + postfix);
    }

    private static PartialModel block(String path) {
        return PartialModel.of(Greate.id("block/" + path));
    }

    public static void register() {}
}
