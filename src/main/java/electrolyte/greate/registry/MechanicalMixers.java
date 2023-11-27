package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class MechanicalMixers {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMechanicalMixerBlock> MECHANICAL_MIXERS = new ArrayList<>();

    public static final BlockEntry<TieredMechanicalMixerBlock> ANDESITE_MECHANICAL_MIXER = mechanicalMixer("andesite_mechanical_mixer", TIER.ULTRA_LOW, GreatePartialModels.ANDESITE_MECHANICAL_MIXER_HEAD, GreatePartialModels.ANDESITE_COGWHEEL_SHAFTLESS, 0.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> STEEL_MECHANICAL_MIXER = mechanicalMixer("steel_mechanical_mixer", TIER.LOW, GreatePartialModels.STEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.STEEL_COGWHEEL_SHAFTLESS, 1.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> ALUMINIUM_MECHANICAL_MIXER = mechanicalMixer("aluminium_mechanical_mixer", TIER.MEDIUM, GreatePartialModels.ALUMINIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFTLESS, 1.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> STAINLESS_STEEL_MECHANICAL_MIXER = mechanicalMixer("stainless_steel_mechanical_mixer", TIER.HIGH, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFTLESS, 2.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> TITANIUM_MECHANICAL_MIXER = mechanicalMixer("titanium_mechanical_mixer", TIER.EXTREME, GreatePartialModels.TITANIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.TITANIUM_COGWHEEL_SHAFTLESS, 2.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> TUNGSTENSTEEL_MECHANICAL_MIXER = mechanicalMixer("tungstensteel_mechanical_mixer", TIER.INSANE, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFTLESS, 3.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> PALLADIUM_MECHANICAL_MIXER = mechanicalMixer("palladium_mechanical_mixer", TIER.LUDICRIOUS, GreatePartialModels.PALLADIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFTLESS, 3.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> NAQUADAH_MECHANICAL_MIXER = mechanicalMixer("naquadah_mechanical_mixer", TIER.ZPM, GreatePartialModels.NAQUADAH_MECHANICAL_MIXER_HEAD, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFTLESS, 4.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> DARMSTADTIUM_MECHANICAL_MIXER = mechanicalMixer("darmstadtium_mechanical_mixer", TIER.ULTIMATE, GreatePartialModels.DARMSTADTIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFTLESS, 4.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> NEUTRONIUM_MECHANICAL_MIXER = mechanicalMixer("neutronium_mechanical_mixer", TIER.ULTIMATE_HIGH, GreatePartialModels.NEUTRONIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFTLESS, 5.0);

    public static BlockEntry<TieredMechanicalMixerBlock> mechanicalMixer(String name, TIER tier, PartialModel mixerHeadModel, PartialModel cogwheelModel, double stressImpact) {
        return REGISTRATE.block(name, p -> new TieredMechanicalMixerBlock(p, mixerHeadModel, cogwheelModel))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.STONE))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(stressImpact))
                .transform(GreateBuilderTransformers.tieredMechanicalMixer())
                .addLayer(() -> RenderType::cutoutMipped)
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
