package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.material.material.Material;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.data.material.GTMaterials.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;

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

        MECHANICAL_PRESSES[ULV] = ANDESITE_MECHANICAL_PRESS = mechanicalPress(ULV, 1.0, AndesiteAlloy);
        MECHANICAL_PRESSES[LV] = STEEL_MECHANICAL_PRESS = mechanicalPress(LV, 2.0, Steel);
        MECHANICAL_PRESSES[MV] = ALUMINIUM_MECHANICAL_PRESS = mechanicalPress(MV, 3.0, Aluminium);
        MECHANICAL_PRESSES[HV] = STAINLESS_STEEL_MECHANICAL_PRESS = mechanicalPress(HV, 4.0, StainlessSteel);
        MECHANICAL_PRESSES[EV] = TITANIUM_MECHANICAL_PRESS = mechanicalPress(EV, 5.0, Titanium);
        MECHANICAL_PRESSES[IV] = TUNGSTENSTEEL_MECHANICAL_PRESS = mechanicalPress(IV, 6.0, TungstenSteel);
        MECHANICAL_PRESSES[LuV] = PALLADIUM_MECHANICAL_PRESS = mechanicalPress(LuV, 7.0, RhodiumPlatedPalladium);
        MECHANICAL_PRESSES[ZPM] = NAQUADAH_MECHANICAL_PRESS = mechanicalPress(ZPM, 8.0, NaquadahAlloy);
        MECHANICAL_PRESSES[UV] = DARMSTADTIUM_MECHANICAL_PRESS = mechanicalPress(UV, 9.0, Darmstadtium);
        MECHANICAL_PRESSES[UHV] = NEUTRONIUM_MECHANICAL_PRESS = mechanicalPress(UHV, 10.0, Neutronium);
    }

    public static BlockEntry<TieredMechanicalPressBlock> mechanicalPress(int tier, double stressImpact, Material mat) {
        return REGISTRATE.block(mat.getName() + "_mechanical_press", p -> new TieredMechanicalPressBlock(p, mat))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .transform(TagGen.axeOrPickaxe())
                .transform(GStress.setImpact(stressImpact))
                .transform(GreateBuilderTransformers.tieredMechanicalPress())
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
