package electrolyte.greate.registry;

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

import java.util.ArrayList;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TMS;

public class MechanicalMixers {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMechanicalMixerBlock> MECHANICAL_MIXERS = new ArrayList<>();

    public static final BlockEntry<TieredMechanicalMixerBlock> ANDESITE_MECHANICAL_MIXER = mechanicalMixer("andesite_mechanical_mixer", ULV, TMS[0], GreatePartialModels.ANDESITE_MECHANICAL_MIXER_HEAD, GreatePartialModels.ANDESITE_COGWHEEL_SHAFTLESS, 0.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> STEEL_MECHANICAL_MIXER = mechanicalMixer("steel_mechanical_mixer", LV, TMS[1], GreatePartialModels.STEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.STEEL_COGWHEEL_SHAFTLESS, 1.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> ALUMINIUM_MECHANICAL_MIXER = mechanicalMixer("aluminium_mechanical_mixer", MV, TMS[2], GreatePartialModels.ALUMINIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFTLESS, 1.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> STAINLESS_STEEL_MECHANICAL_MIXER = mechanicalMixer("stainless_steel_mechanical_mixer", HV, TMS[3], GreatePartialModels.STAINLESS_STEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFTLESS, 2.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> TITANIUM_MECHANICAL_MIXER = mechanicalMixer("titanium_mechanical_mixer", EV, TMS[4], GreatePartialModels.TITANIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.TITANIUM_COGWHEEL_SHAFTLESS, 2.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> TUNGSTENSTEEL_MECHANICAL_MIXER = mechanicalMixer("tungstensteel_mechanical_mixer", IV, TMS[5], GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFTLESS, 3.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> PALLADIUM_MECHANICAL_MIXER = mechanicalMixer("palladium_mechanical_mixer", LuV, TMS[6], GreatePartialModels.PALLADIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFTLESS, 3.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> NAQUADAH_MECHANICAL_MIXER = mechanicalMixer("naquadah_mechanical_mixer", ZPM, TMS[7], GreatePartialModels.NAQUADAH_MECHANICAL_MIXER_HEAD, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFTLESS, 4.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> DARMSTADTIUM_MECHANICAL_MIXER = mechanicalMixer("darmstadtium_mechanical_mixer", UV, TMS[8], GreatePartialModels.DARMSTADTIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFTLESS, 4.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> NEUTRONIUM_MECHANICAL_MIXER = mechanicalMixer("neutronium_mechanical_mixer", UHV, TMS[9], GreatePartialModels.NEUTRONIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFTLESS, 5.0);

    public static BlockEntry<TieredMechanicalMixerBlock> mechanicalMixer(String name, int tier, String materialType, PartialModel mixerHeadModel, PartialModel cogwheelModel, double stressImpact) {
        return REGISTRATE.block(name, p -> new TieredMechanicalMixerBlock(p, mixerHeadModel, cogwheelModel))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.STONE))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .transform(GreateBuilderTransformers.tieredMechanicalMixer())
                .addLayer(() -> RenderType::cutoutMipped)
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
