package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.MATERIAL_TYPE;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class MechanicalPresses {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMechanicalPressBlock> MECHANICAL_PRESSES = new ArrayList<>();

    public static final BlockEntry<TieredMechanicalPressBlock> ANDESITE_MECHANICAL_PRESS = mechanicalPress("andesite_mechanical_press", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.ANDESITE_MECHANICAL_PRESS_HEAD, Shafts.ANDESITE_SHAFT, 1.0);
    public static final BlockEntry<TieredMechanicalPressBlock> STEEL_MECHANICAL_PRESS = mechanicalPress("steel_mechanical_press", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.STEEL_MECHANICAL_PRESS_HEAD, Shafts.STEEL_SHAFT, 2.0);
    public static final BlockEntry<TieredMechanicalPressBlock> ALUMINIUM_MECHANICAL_PRESS = mechanicalPress("aluminium_mechanical_press", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.ALUMINIUM_MECHANICAL_PRESS_HEAD, Shafts.ALUMINIUM_SHAFT, 3.0);
    public static final BlockEntry<TieredMechanicalPressBlock> STAINLESS_STEEL_MECHANICAL_PRESS = mechanicalPress("stainless_steel_mechanical_press", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_PRESS_HEAD, Shafts.STAINLESS_STEEL_SHAFT, 4.0);
    public static final BlockEntry<TieredMechanicalPressBlock> TITANIUM_MECHANICAL_PRESS = mechanicalPress("titanium_mechanical_press", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.TITANIUM_MECHANICAL_PRESS_HEAD, Shafts.TITANIUM_SHAFT, 5.0);
    public static final BlockEntry<TieredMechanicalPressBlock> TUNGSTENSTEEL_MECHANICAL_PRESS = mechanicalPress("tungstensteel_mechanical_press", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_PRESS_HEAD, Shafts.TUNGSTENSTEEL_SHAFT, 6.0);
    public static final BlockEntry<TieredMechanicalPressBlock> PALLADIUM_MECHANICAL_PRESS = mechanicalPress("palladium_mechanical_press", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.PALLADIUM_MECHANICAL_PRESS_HEAD, Shafts.PALLADIUM_SHAFT, 7.0);
    public static final BlockEntry<TieredMechanicalPressBlock> NAQUADAH_MECHANICAL_PRESS = mechanicalPress("naquadah_mechanical_press", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.NAQUADAH_MECHANICAL_PRESS_HEAD, Shafts.NAQUADAH_SHAFT, 8.0);
    public static final BlockEntry<TieredMechanicalPressBlock> DARMSTADTIUM_MECHANICAL_PRESS = mechanicalPress("darmstadtium_mechanical_press", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.DARMSTADTIUM_MECHANICAL_PRESS_HEAD, Shafts.DARMSTADTIUM_SHAFT, 9.0);
    public static final BlockEntry<TieredMechanicalPressBlock> NEUTRONIUM_MECHANICAL_PRESS = mechanicalPress("neutronium_mechanical_press", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.NEUTRONIUM_MECHANICAL_PRESS_HEAD, Shafts.NEUTRONIUM_SHAFT, 10.0);

    public static BlockEntry<TieredMechanicalPressBlock> mechanicalPress(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel headModel, BlockEntry<TieredShaftBlock> tieredShaft, double stressImpact) {
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
}
