package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.crafter.CrafterCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.crafter.TieredMechanicalCrafterBlock;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.GTValues.UHV;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class MechanicalCrafters {

    public static BlockEntry<TieredMechanicalCrafterBlock>[] MECHANICAL_CRAFTERS = new BlockEntry[10];

    public static BlockEntry<TieredMechanicalCrafterBlock>

            ANDESITE_MECHANICAL_CRAFTER,
            STEEL_MECHANICAL_CRAFTER,
            ALUMINIUM_MECHANICAL_CRAFTER,
            STAINLESS_STEEL_MECHANICAL_CRAFTER,
            TITANIUM_MECHANICAL_CRAFTER,
            TUNGSTENSTEEL_MECHANICAL_CRAFTER,
            PALLADIUM_MECHANICAL_CRAFTER,
            NAQUADAH_MECHANICAL_CRAFTER,
            DARMSTADTIUM_MECHANICAL_CRAFTER,
            NEUTRONIUM_MECHANICAL_CRAFTER;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        MECHANICAL_CRAFTERS[ULV] = ANDESITE_MECHANICAL_CRAFTER = mechanicalCrafter(ULV, 0.5);
        MECHANICAL_CRAFTERS[LV] = STEEL_MECHANICAL_CRAFTER = mechanicalCrafter(LV, 1.0);
        MECHANICAL_CRAFTERS[MV] = ALUMINIUM_MECHANICAL_CRAFTER = mechanicalCrafter(MV, 1.5);
        MECHANICAL_CRAFTERS[HV] = STAINLESS_STEEL_MECHANICAL_CRAFTER = mechanicalCrafter(HV, 2.0);
        MECHANICAL_CRAFTERS[EV] = TITANIUM_MECHANICAL_CRAFTER = mechanicalCrafter(EV, 2.5);
        MECHANICAL_CRAFTERS[IV] = TUNGSTENSTEEL_MECHANICAL_CRAFTER = mechanicalCrafter(IV, 3.0);
        MECHANICAL_CRAFTERS[LuV] = PALLADIUM_MECHANICAL_CRAFTER = mechanicalCrafter(LuV, 3.5);
        MECHANICAL_CRAFTERS[ZPM] = NAQUADAH_MECHANICAL_CRAFTER = mechanicalCrafter(ZPM, 4.0);
        MECHANICAL_CRAFTERS[UV] = DARMSTADTIUM_MECHANICAL_CRAFTER = mechanicalCrafter(UV, 4.5);
        MECHANICAL_CRAFTERS[UHV] = NEUTRONIUM_MECHANICAL_CRAFTER = mechanicalCrafter(UHV, 5.0);
    }

    public static BlockEntry<TieredMechanicalCrafterBlock> mechanicalCrafter(int tier, double stressImpact) {
        return REGISTRATE.block(TM[tier].getName() + "_mechanical_crafter", TieredMechanicalCrafterBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_YELLOW))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .onRegister(CreateRegistrate.connectedTextures(CrafterCTBehaviour::new))
                .addLayer(() -> RenderType::cutoutMipped)
                //.blockstate(GreateBlockStateGen.tieredMechanicalCrafterProvider)
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
