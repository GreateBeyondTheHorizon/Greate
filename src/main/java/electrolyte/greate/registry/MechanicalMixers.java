package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues.MATERIAL_TYPE;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
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

    public static final BlockEntry<TieredMechanicalMixerBlock> ANDESITE_MECHANICAL_MIXER = mechanicalMixer("andesite_mechanical_mixer", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.ANDESITE_MECHANICAL_MIXER_HEAD, GreatePartialModels.ANDESITE_COGWHEEL_SHAFTLESS, 0.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> STEEL_MECHANICAL_MIXER = mechanicalMixer("steel_mechanical_mixer", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.STEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.STEEL_COGWHEEL_SHAFTLESS, 1.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> ALUMINIUM_MECHANICAL_MIXER = mechanicalMixer("aluminium_mechanical_mixer", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.ALUMINIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFTLESS, 1.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> STAINLESS_STEEL_MECHANICAL_MIXER = mechanicalMixer("stainless_steel_mechanical_mixer", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.STAINLESS_STEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFTLESS, 2.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> TITANIUM_MECHANICAL_MIXER = mechanicalMixer("titanium_mechanical_mixer", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.TITANIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.TITANIUM_COGWHEEL_SHAFTLESS, 2.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> TUNGSTENSTEEL_MECHANICAL_MIXER = mechanicalMixer("tungstensteel_mechanical_mixer", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.TUNGSTENSTEEL_MECHANICAL_MIXER_HEAD, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFTLESS, 3.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> PALLADIUM_MECHANICAL_MIXER = mechanicalMixer("palladium_mechanical_mixer", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.PALLADIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFTLESS, 3.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> NAQUADAH_MECHANICAL_MIXER = mechanicalMixer("naquadah_mechanical_mixer", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.NAQUADAH_MECHANICAL_MIXER_HEAD, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFTLESS, 4.0);
    public static final BlockEntry<TieredMechanicalMixerBlock> DARMSTADTIUM_MECHANICAL_MIXER = mechanicalMixer("darmstadtium_mechanical_mixer", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.DARMSTADTIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFTLESS, 4.5);
    public static final BlockEntry<TieredMechanicalMixerBlock> NEUTRONIUM_MECHANICAL_MIXER = mechanicalMixer("neutronium_mechanical_mixer", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.NEUTRONIUM_MECHANICAL_MIXER_HEAD, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFTLESS, 5.0);

    public static BlockEntry<TieredMechanicalMixerBlock> mechanicalMixer(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel mixerHeadModel, PartialModel cogwheelModel, double stressImpact) {
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
