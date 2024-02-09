package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;

import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TMS;

public class MechanicalPresses {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMechanicalPressBlock> MECHANICAL_PRESSES = new ArrayList<>();

    public static final BlockEntry<TieredMechanicalPressBlock> ANDESITE_MECHANICAL_PRESS = mechanicalPress("andesite_mechanical_press", ULV, TMS[0], GreatePartialModels.ANDESITE_MECHANICAL_PRESS_HEAD, Shafts.ANDESITE_SHAFT, 1.0);
    public static final BlockEntry<TieredMechanicalPressBlock> STEEL_MECHANICAL_PRESS = mechanicalPress("steel_mechanical_press", LV, TMS[1], GreatePartialModels.STEEL_MECHANICAL_PRESS_HEAD, Shafts.STEEL_SHAFT, 2.0);
    public static final BlockEntry<TieredMechanicalPressBlock> ALUMINIUM_MECHANICAL_PRESS = mechanicalPress("aluminium_mechanical_press", MV, TMS[2], GreatePartialModels.ALUMINIUM_MECHANICAL_PRESS_HEAD, Shafts.ALUMINIUM_SHAFT, 3.0);
    public static final BlockEntry<TieredMechanicalPressBlock> STAINLESS_STEEL_MECHANICAL_PRESS = mechanicalPress("stainless_steel_mechanical_press", HV, TMS[3], GreatePartialModels.STAINLESS_STEEL_MECHANICAL_PRESS_HEAD, Shafts.STAINLESS_STEEL_SHAFT, 4.0);
    public static final BlockEntry<TieredMechanicalPressBlock> TITANIUM_MECHANICAL_PRESS = mechanicalPress("titanium_mechanical_press", EV, TMS[4], GreatePartialModels.TITANIUM_MECHANICAL_PRESS_HEAD, Shafts.TITANIUM_SHAFT, 5.0);
    public static final BlockEntry<TieredMechanicalPressBlock> TUNGSTENSTEEL_MECHANICAL_PRESS = mechanicalPress("tungstensteel_mechanical_press", IV, TMS[5], GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_PRESS_HEAD, Shafts.TUNGSTENSTEEL_SHAFT, 6.0);
    public static final BlockEntry<TieredMechanicalPressBlock> PALLADIUM_MECHANICAL_PRESS = mechanicalPress("palladium_mechanical_press", LuV, TMS[6], GreatePartialModels.PALLADIUM_MECHANICAL_PRESS_HEAD, Shafts.PALLADIUM_SHAFT, 7.0);
    public static final BlockEntry<TieredMechanicalPressBlock> NAQUADAH_MECHANICAL_PRESS = mechanicalPress("naquadah_mechanical_press", ZPM, TMS[7], GreatePartialModels.NAQUADAH_MECHANICAL_PRESS_HEAD, Shafts.NAQUADAH_SHAFT, 8.0);
    public static final BlockEntry<TieredMechanicalPressBlock> DARMSTADTIUM_MECHANICAL_PRESS = mechanicalPress("darmstadtium_mechanical_press", UV, TMS[8], GreatePartialModels.DARMSTADTIUM_MECHANICAL_PRESS_HEAD, Shafts.DARMSTADTIUM_SHAFT, 9.0);
    public static final BlockEntry<TieredMechanicalPressBlock> NEUTRONIUM_MECHANICAL_PRESS = mechanicalPress("neutronium_mechanical_press", UHV, TMS[9], GreatePartialModels.NEUTRONIUM_MECHANICAL_PRESS_HEAD, Shafts.NEUTRONIUM_SHAFT, 10.0);

    public static BlockEntry<TieredMechanicalPressBlock> mechanicalPress(String name, int tier, String materialType, PartialModel headModel, BlockEntry<TieredShaftBlock> tieredShaft, double stressImpact) {
        return REGISTRATE.block(name, p -> new TieredMechanicalPressBlock(p, headModel, tieredShaft.get()))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(GreateBuilderTransformers.tieredMechanicalPress())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
