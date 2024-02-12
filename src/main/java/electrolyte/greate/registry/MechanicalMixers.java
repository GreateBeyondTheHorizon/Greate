package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;

import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreatePartialModels.*;

public class MechanicalMixers {

    public static BlockEntry<TieredMechanicalMixerBlock>[] MECHANICAL_MIXERS = new BlockEntry[10];
    public static BlockEntry<TieredMechanicalMixerBlock>
            ANDESITE_MECHANICAL_MIXER,
            STEEL_MECHANICAL_MIXER,
            ALUMINIUM_MECHANICAL_MIXER,
            STAINLESS_STEEL_MECHANICAL_MIXER,
            TITANIUM_MECHANICAL_MIXER,
            TUNGSTENSTEEL_MECHANICAL_MIXER,
            PALLADIUM_MECHANICAL_MIXER,
            NAQUADAH_MECHANICAL_MIXER,
            DARMSTADTIUM_MECHANICAL_MIXER,
            NEUTRONIUM_MECHANICAL_MIXER;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        MECHANICAL_MIXERS[ULV] = ANDESITE_MECHANICAL_MIXER = mechanicalMixer(ULV, 0.5);
        MECHANICAL_MIXERS[LV] = STEEL_MECHANICAL_MIXER = mechanicalMixer(LV, 1.0);
        MECHANICAL_MIXERS[MV] = ALUMINIUM_MECHANICAL_MIXER = mechanicalMixer(MV, 1.5);
        MECHANICAL_MIXERS[HV] = STAINLESS_STEEL_MECHANICAL_MIXER = mechanicalMixer(HV, 2.0);
        MECHANICAL_MIXERS[EV] = TITANIUM_MECHANICAL_MIXER = mechanicalMixer(EV, 2.5);
        MECHANICAL_MIXERS[IV] = TUNGSTENSTEEL_MECHANICAL_MIXER = mechanicalMixer(IV, 3.0);
        MECHANICAL_MIXERS[LuV] = PALLADIUM_MECHANICAL_MIXER = mechanicalMixer(LuV, 3.5);
        MECHANICAL_MIXERS[ZPM] = NAQUADAH_MECHANICAL_MIXER = mechanicalMixer(ZPM, 4.0);
        MECHANICAL_MIXERS[UV] = DARMSTADTIUM_MECHANICAL_MIXER = mechanicalMixer(UV, 4.5);
        MECHANICAL_MIXERS[UHV] = NEUTRONIUM_MECHANICAL_MIXER = mechanicalMixer(UHV, 5.0);
    }

    private static BlockEntry<TieredMechanicalMixerBlock> mechanicalMixer(int tier, double stressImpact) {
        return mechanicalMixer(tier, TM[tier], MECHANICAL_MIXER_HEAD_MODELS[tier], COGWHEEL_SHAFTLESS_MODELS[tier], stressImpact);
    }
    
    public static BlockEntry<TieredMechanicalMixerBlock> mechanicalMixer(int tier, Material material, PartialModel mixerHeadModel, PartialModel cogwheelModel, double stressImpact) {
        return REGISTRATE.block(material.getName() + "_mechanical_mixer", p -> new TieredMechanicalMixerBlock(p, mixerHeadModel, cogwheelModel))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.STONE))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .transform(GreateBuilderTransformers.tieredMechanicalMixer())
                .addLayer(() -> RenderType::cutoutMipped)
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
