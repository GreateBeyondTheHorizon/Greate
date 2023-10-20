package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
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

    public static final BlockEntry<TieredMechanicalPressBlock> ANDESITE_MECHANICAL_PRESS = mechanicalPress("andesite_mechanical_press", TIER.ULTRA_LOW, GreatePartialModels.ANDESITE_MECHANICAL_PRESS_HEAD, Shafts.ANDESITE_SHAFT, Greate.CONFIG.ULS.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> STEEL_MECHANICAL_PRESS = mechanicalPress("steel_mechanical_press", TIER.LOW, GreatePartialModels.STEEL_MECHANICAL_PRESS_HEAD, Shafts.STEEL_SHAFT, Greate.CONFIG.LS.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> ALUMINIUM_MECHANICAL_PRESS = mechanicalPress("aluminium_mechanical_press", TIER.MEDIUM, GreatePartialModels.ALUMINIUM_MECHANICAL_PRESS_HEAD, Shafts.ALUMINIUM_SHAFT, Greate.CONFIG.MS.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> STAINLESS_STEEL_MECHANICAL_PRESS = mechanicalPress("stainless_steel_mechanical_press", TIER.HIGH, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_PRESS_HEAD, Shafts.STAINLESS_STEEL_SHAFT, Greate.CONFIG.HS.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> TITANIUM_MECHANICAL_PRESS = mechanicalPress("titanium_mechanical_press", TIER.EXTREME, GreatePartialModels.TITANIUM_MECHANICAL_PRESS_HEAD, Shafts.TITANIUM_SHAFT, Greate.CONFIG.ES.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> TUNGSTENSTEEL_MECHANICAL_PRESS = mechanicalPress("tungstensteel_mechanical_press", TIER.INSANE, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_PRESS_HEAD, Shafts.TUNGSTENSTEEL_SHAFT, Greate.CONFIG.IS.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> PALLADIUM_MECHANICAL_PRESS = mechanicalPress("palladium_mechanical_press", TIER.LUDICRIOUS, GreatePartialModels.PALLADIUM_MECHANICAL_PRESS_HEAD, Shafts.PALLADIUM_SHAFT, Greate.CONFIG.LUS.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> NAQUADAH_MECHANICAL_PRESS = mechanicalPress("naquadah_mechanical_press", TIER.ZPM, GreatePartialModels.NAQUADAH_MECHANICAL_PRESS_HEAD, Shafts.NAQUADAH_SHAFT, Greate.CONFIG.ZPM.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> DARMSTADTIUM_MECHANICAL_PRESS = mechanicalPress("darmstadtium_mechanical_press", TIER.ULTIMATE, GreatePartialModels.DARMSTADTIUM_MECHANICAL_PRESS_HEAD, Shafts.DARMSTADTIUM_SHAFT, Greate.CONFIG.US.MECHANICAL_PRESS_IMPACT);
    public static final BlockEntry<TieredMechanicalPressBlock> NEUTRONIUM_MECHANICAL_PRESS = mechanicalPress("neutronium_mechanical_press", TIER.ULTIMATE_HIGH, GreatePartialModels.NEUTRONIUM_MECHANICAL_PRESS_HEAD, Shafts.NEUTRONIUM_SHAFT, Greate.CONFIG.UHS.MECHANICAL_PRESS_IMPACT);

    public static BlockEntry<TieredMechanicalPressBlock> mechanicalPress(String name, TIER tier, PartialModel headModel, BlockEntry<TieredShaftBlock> tieredShaft, double stressImpact) {
        return REGISTRATE.block(name, p -> new TieredMechanicalPressBlock(p, headModel, tieredShaft.get()))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(GreateBuilderTransformers.tieredMechanicalPress())
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
