package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class MechanicalPresses {

    public static BlockEntry<TieredMechanicalPressBlock>[] MECHANICAL_PRESSES = new BlockEntry[10];
    public static BlockEntry<TieredMechanicalPressBlock>
            ANDESITE_MECHANICAL_PRESS,
            STEEL_MECHANICAL_PRESS,
            ALUMINIUM_MECHANICAL_PRESS,
            STAINLESS_STEEL_MECHANICAL_PRESS,
            TITANIUM_MECHANICAL_PRESS,
            TUNGSTENSTEEL_MECHANICAL_PRESS,
            PALLADIUM_MECHANICAL_PRESS,
            NAQUADAH_MECHANICAL_PRESS,
            DARMSTADTIUM_MECHANICAL_PRESS,
            NEUTRONIUM_MECHANICAL_PRESS;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        MECHANICAL_PRESSES[ULV] = ANDESITE_MECHANICAL_PRESS = mechanicalPress(ULV, GreatePartialModels.ANDESITE_MECHANICAL_PRESS_HEAD, 1.0);
        MECHANICAL_PRESSES[LV] = STEEL_MECHANICAL_PRESS = mechanicalPress(LV, GreatePartialModels.STEEL_MECHANICAL_PRESS_HEAD, 2.0);
        MECHANICAL_PRESSES[MV] = ALUMINIUM_MECHANICAL_PRESS = mechanicalPress(MV, GreatePartialModels.ALUMINIUM_MECHANICAL_PRESS_HEAD, 3.0);
        MECHANICAL_PRESSES[HV] = STAINLESS_STEEL_MECHANICAL_PRESS = mechanicalPress(HV, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_PRESS_HEAD, 4.0);
        MECHANICAL_PRESSES[EV] = TITANIUM_MECHANICAL_PRESS = mechanicalPress(EV, GreatePartialModels.TITANIUM_MECHANICAL_PRESS_HEAD, 5.0);
        MECHANICAL_PRESSES[IV] = TUNGSTENSTEEL_MECHANICAL_PRESS = mechanicalPress(IV, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_PRESS_HEAD, 6.0);
        MECHANICAL_PRESSES[LuV] = PALLADIUM_MECHANICAL_PRESS = mechanicalPress(LuV, GreatePartialModels.PALLADIUM_MECHANICAL_PRESS_HEAD, 7.0);
        MECHANICAL_PRESSES[ZPM] = NAQUADAH_MECHANICAL_PRESS = mechanicalPress(ZPM, GreatePartialModels.NAQUADAH_MECHANICAL_PRESS_HEAD, 8.0);
        MECHANICAL_PRESSES[UV] = DARMSTADTIUM_MECHANICAL_PRESS = mechanicalPress(UV, GreatePartialModels.DARMSTADTIUM_MECHANICAL_PRESS_HEAD, 9.0);
        MECHANICAL_PRESSES[UHV] = NEUTRONIUM_MECHANICAL_PRESS = mechanicalPress(UHV, GreatePartialModels.NEUTRONIUM_MECHANICAL_PRESS_HEAD, 10.0);
    }

    private static BlockEntry<TieredMechanicalPressBlock> mechanicalPress(int tier, PartialModel headModel, double stressImpact) {
        return mechanicalPress(tier, TM[tier], headModel, SHAFTS[tier], stressImpact);
    }

    public static BlockEntry<TieredMechanicalPressBlock> mechanicalPress(int tier, Material material, PartialModel headModel, BlockEntry<TieredShaftBlock> tieredShaft, double stressImpact) {
        return REGISTRATE.block(material.getName() + "_mechanical_press", p -> new TieredMechanicalPressBlock(p, headModel, tieredShaft.get()))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(GreateBuilderTransformers.tieredMechanicalPress())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
