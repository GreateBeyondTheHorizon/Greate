package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues.MATERIAL_TYPE;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.registry.GreateTags.GreateItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedCogwheel;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedLargeCogwheel;

public class Cogwheels {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredCogwheelBlock> ANDESITE_COGWHEEL = registerCogwheel("andesite_cogwheel", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.LARGE_ANDESITE_COGWHEEL_SHAFTLESS, GreatePartialModels.ANDESITE_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_ANDESITE.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_ANDESITE_COGWHEEL = registerLargeCogwheel("large_andesite_cogwheel", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.LARGE_ANDESITE_COGWHEEL_SHAFTLESS, GreatePartialModels.ANDESITE_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_ANDESITE.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_ANDESITE_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_andesite_cogwheel", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, ANDESITE_COGWHEEL, GreatePartialModels.ANDESITE_SHAFT_HALF, GreatePartialModels.ANDESITE_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_ANDESITE_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_andesite_cogwheel", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, ANDESITE_COGWHEEL, LARGE_ANDESITE_COGWHEEL, GreatePartialModels.ANDESITE_SHAFT_HALF, GreatePartialModels.LARGE_ANDESITE_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_ANDESITE_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_andesite_cogwheel", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, ANDESITE_COGWHEEL, GreatePartialModels.ANDESITE_SHAFT_HALF, GreatePartialModels.ANDESITE_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_ANDESITE_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_andesite_cogwheel", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, ANDESITE_COGWHEEL, LARGE_ANDESITE_COGWHEEL, GreatePartialModels.ANDESITE_SHAFT_HALF, GreatePartialModels.LARGE_ANDESITE_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> STEEL_COGWHEEL = registerCogwheel("steel_cogwheel", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.LARGE_STEEL_COGWHEEL_SHAFTLESS, GreatePartialModels.STEEL_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_STEEL.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_STEEL_COGWHEEL = registerLargeCogwheel("large_steel_cogwheel", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.LARGE_STEEL_COGWHEEL_SHAFTLESS, GreatePartialModels.STEEL_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_STEEL.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_STEEL_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_steel_cogwheel", TIER.LOW, MATERIAL_TYPE.STEEL, STEEL_COGWHEEL, GreatePartialModels.STEEL_SHAFT_HALF, GreatePartialModels.STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_STEEL_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_steel_cogwheel", TIER.LOW, MATERIAL_TYPE.STEEL, STEEL_COGWHEEL, LARGE_STEEL_COGWHEEL, GreatePartialModels.STEEL_SHAFT_HALF, GreatePartialModels.LARGE_STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_STEEL_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_steel_cogwheel", TIER.LOW, MATERIAL_TYPE.STEEL, STEEL_COGWHEEL, GreatePartialModels.STEEL_SHAFT_HALF, GreatePartialModels.STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_STEEL_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_steel_cogwheel", TIER.LOW, MATERIAL_TYPE.STEEL, STEEL_COGWHEEL, LARGE_STEEL_COGWHEEL, GreatePartialModels.STEEL_SHAFT_HALF, GreatePartialModels.LARGE_STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> ALUMINIUM_COGWHEEL = registerCogwheel("aluminium_cogwheel", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.LARGE_ALUMINIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_ALUMINIUM.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_ALUMINIUM_COGWHEEL = registerLargeCogwheel("large_aluminium_cogwheel", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.LARGE_ALUMINIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_ALUMINIUM.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_ALUMINIUM_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_aluminium_cogwheel", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, ALUMINIUM_COGWHEEL, GreatePartialModels.ALUMINIUM_SHAFT_HALF, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_ALUMINIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_aluminium_cogwheel", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, ALUMINIUM_COGWHEEL, LARGE_ALUMINIUM_COGWHEEL, GreatePartialModels.ALUMINIUM_SHAFT_HALF, GreatePartialModels.LARGE_ALUMINIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_ALUMINIUM_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_aluminium_cogwheel", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, ALUMINIUM_COGWHEEL, GreatePartialModels.ALUMINIUM_SHAFT_HALF, GreatePartialModels.ALUMINIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_ALUMINIUM_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_aluminium_cogwheel", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, ALUMINIUM_COGWHEEL, LARGE_ALUMINIUM_COGWHEEL, GreatePartialModels.ALUMINIUM_SHAFT_HALF, GreatePartialModels.LARGE_ALUMINIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> STAINLESS_STEEL_COGWHEEL = registerCogwheel("stainless_steel_cogwheel", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.LARGE_STAINLESS_STEEL_COGWHEEL_SHAFTLESS, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_STAINLESS_STEEL.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_STAINLESS_STEEL_COGWHEEL = registerLargeCogwheel("large_stainless_steel_cogwheel", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.LARGE_STAINLESS_STEEL_COGWHEEL_SHAFTLESS, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_STAINLESS_STEEL.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_stainless_steel_cogwheel", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, STAINLESS_STEEL_COGWHEEL, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_stainless_steel_cogwheel", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, STAINLESS_STEEL_COGWHEEL, LARGE_STAINLESS_STEEL_COGWHEEL, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF, GreatePartialModels.LARGE_STAINLESS_STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_stainless_steel_cogwheel", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, STAINLESS_STEEL_COGWHEEL, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF, GreatePartialModels.STAINLESS_STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_stainless_steel_cogwheel", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, STAINLESS_STEEL_COGWHEEL, LARGE_STAINLESS_STEEL_COGWHEEL, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF, GreatePartialModels.LARGE_STAINLESS_STEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> TITANIUM_COGWHEEL = registerCogwheel("titanium_cogwheel", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.LARGE_TITANIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.TITANIUM_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_TITANIUM.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_TITANIUM_COGWHEEL = registerLargeCogwheel("large_titanium_cogwheel", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.LARGE_TITANIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.TITANIUM_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_TITANIUM.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_TITANIUM_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_titanium_cogwheel", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, TITANIUM_COGWHEEL, GreatePartialModels.TITANIUM_SHAFT_HALF, GreatePartialModels.TITANIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_TITANIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_titanium_cogwheel", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, TITANIUM_COGWHEEL, LARGE_TITANIUM_COGWHEEL, GreatePartialModels.TITANIUM_SHAFT_HALF, GreatePartialModels.LARGE_TITANIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_TITANIUM_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_titanium_cogwheel", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, TITANIUM_COGWHEEL, GreatePartialModels.TITANIUM_SHAFT_HALF, GreatePartialModels.TITANIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_TITANIUM_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_titanium_cogwheel", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, TITANIUM_COGWHEEL, LARGE_TITANIUM_COGWHEEL, GreatePartialModels.TITANIUM_SHAFT_HALF, GreatePartialModels.LARGE_TITANIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> TUNGSTENSTEEL_COGWHEEL = registerCogwheel("tungstensteel_cogwheel", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.LARGE_TUNGSTENSTEEL_COGWHEEL_SHAFTLESS, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_TUNGSTENSTEEL.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_TUNGSTENSTEEL_COGWHEEL = registerLargeCogwheel("large_tungstensteel_cogwheel", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.LARGE_TUNGSTENSTEEL_COGWHEEL_SHAFTLESS, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_TUNGSTENSTEEL.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_tungstensteel_cogwheel", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, TUNGSTENSTEEL_COGWHEEL, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_tungstensteel_cogwheel", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, TUNGSTENSTEEL_COGWHEEL, LARGE_TUNGSTENSTEEL_COGWHEEL, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF, GreatePartialModels.LARGE_TUNGSTENSTEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_tungstensteel_cogwheel", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, TUNGSTENSTEEL_COGWHEEL, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF, GreatePartialModels.TUNGSTENSTEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_tungstensteel_cogwheel", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, TUNGSTENSTEEL_COGWHEEL, LARGE_TUNGSTENSTEEL_COGWHEEL, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF, GreatePartialModels.LARGE_TUNGSTENSTEEL_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> PALLADIUM_COGWHEEL = registerCogwheel("palladium_cogwheel", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.LARGE_PALLADIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_PALLADIUM.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_PALLADIUM_COGWHEEL = registerLargeCogwheel("large_palladium_cogwheel", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.LARGE_PALLADIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_PALLADIUM.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_PALLADIUM_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_palladium_cogwheel", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, PALLADIUM_COGWHEEL, GreatePartialModels.PALLADIUM_SHAFT_HALF, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_PALLADIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_palladium_cogwheel", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, PALLADIUM_COGWHEEL, LARGE_PALLADIUM_COGWHEEL, GreatePartialModels.PALLADIUM_SHAFT_HALF, GreatePartialModels.LARGE_PALLADIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_PALLADIUM_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_palladium_cogwheel", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, PALLADIUM_COGWHEEL, GreatePartialModels.PALLADIUM_SHAFT_HALF, GreatePartialModels.PALLADIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_PALLADIUM_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_palladium_cogwheel", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, PALLADIUM_COGWHEEL, LARGE_PALLADIUM_COGWHEEL, GreatePartialModels.PALLADIUM_SHAFT_HALF, GreatePartialModels.LARGE_PALLADIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> NAQUADAH_COGWHEEL = registerCogwheel("naquadah_cogwheel", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.LARGE_NAQUADAH_COGWHEEL_SHAFTLESS, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_NAQUADAH.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_NAQUADAH_COGWHEEL = registerLargeCogwheel("large_naquadah_cogwheel", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.LARGE_NAQUADAH_COGWHEEL_SHAFTLESS, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_NAQUADAH.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_NAQUADAH_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_naquadah_cogwheel", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, NAQUADAH_COGWHEEL, GreatePartialModels.NAQUADAH_SHAFT_HALF, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_NAQUADAH_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_naquadah_cogwheel", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, NAQUADAH_COGWHEEL, LARGE_NAQUADAH_COGWHEEL, GreatePartialModels.NAQUADAH_SHAFT_HALF, GreatePartialModels.LARGE_NAQUADAH_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_NAQUADAH_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_naquadah_cogwheel", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, NAQUADAH_COGWHEEL, GreatePartialModels.NAQUADAH_SHAFT_HALF, GreatePartialModels.NAQUADAH_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_NAQUADAH_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_naquadah_cogwheel", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, NAQUADAH_COGWHEEL, LARGE_NAQUADAH_COGWHEEL, GreatePartialModels.NAQUADAH_SHAFT_HALF, GreatePartialModels.LARGE_NAQUADAH_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> DARMSTADTIUM_COGWHEEL = registerCogwheel("darmstadtium_cogwheel", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.LARGE_DARMSTADTIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_DARMSTADTIUM.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_DARMSTADTIUM_COGWHEEL = registerLargeCogwheel("large_darmstadtium_cogwheel", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.LARGE_DARMSTADTIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_DARMSTADTIUM.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_darmstadtium_cogwheel", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, DARMSTADTIUM_COGWHEEL, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_darmstadtium_cogwheel", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, DARMSTADTIUM_COGWHEEL, LARGE_DARMSTADTIUM_COGWHEEL, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF, GreatePartialModels.LARGE_DARMSTADTIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_DARMSTADTIUM_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_darmstadtium_cogwheel", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, DARMSTADTIUM_COGWHEEL, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF, GreatePartialModels.DARMSTADTIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_darmstadtium_cogwheel", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, DARMSTADTIUM_COGWHEEL, LARGE_DARMSTADTIUM_COGWHEEL, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF, GreatePartialModels.LARGE_DARMSTADTIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredCogwheelBlock> NEUTRONIUM_COGWHEEL = registerCogwheel("neutronium_cogwheel", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.LARGE_NEUTRONIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFT, GreateItemTags.COGWHEELS_NEUTRONIUM.itemTag);
    public static final BlockEntry<TieredCogwheelBlock> LARGE_NEUTRONIUM_COGWHEEL = registerLargeCogwheel("large_neutronium_cogwheel", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.LARGE_NEUTRONIUM_COGWHEEL_SHAFTLESS, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFT, GreateItemTags.LARGE_COGWHEELS_NEUTRONIUM.itemTag);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL = registerAndesiteEncasedCogwheel("andesite_encased_neutronium_cogwheel", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, NEUTRONIUM_COGWHEEL, GreatePartialModels.NEUTRONIUM_SHAFT_HALF, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_LARGE_NEUTRONIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel("andesite_encased_large_neutronium_cogwheel", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, NEUTRONIUM_COGWHEEL, LARGE_NEUTRONIUM_COGWHEEL, GreatePartialModels.NEUTRONIUM_SHAFT_HALF, GreatePartialModels.LARGE_NEUTRONIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_NEUTRONIUM_COGWHEEL = registerBrassEncasedCogwheel("brass_encased_neutronium_cogwheel", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, NEUTRONIUM_COGWHEEL, GreatePartialModels.NEUTRONIUM_SHAFT_HALF, GreatePartialModels.NEUTRONIUM_COGWHEEL_SHAFTLESS);
    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_LARGE_NEUTRONIUM_COGWHEEL = registerBrassEncasedLargeCogwheel("brass_encased_large_neutronium_cogwheel", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, NEUTRONIUM_COGWHEEL, LARGE_NEUTRONIUM_COGWHEEL, GreatePartialModels.NEUTRONIUM_SHAFT_HALF, GreatePartialModels.LARGE_NEUTRONIUM_COGWHEEL_SHAFTLESS);

    public static BlockEntry<TieredCogwheelBlock> registerCogwheel(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel largeCogwheelShaftless, PartialModel cogwheelShaft, TagKey<Item> smallCogwheelTag) {
        return REGISTRATE
                .block(name, p -> TieredCogwheelBlock.small(p, largeCogwheelShaftless, cogwheelShaft))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD))
                .properties(p -> p.mapColor(MapColor.DIRT))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .item(CogwheelBlockItem::new)
                .tag(GreateItemTags.COGWHEELS.itemTag)
                .tag(smallCogwheelTag).build()
                .register();
    }

    public static BlockEntry<TieredCogwheelBlock> registerLargeCogwheel(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel largeCogwheelShaftless, PartialModel cogwheelShaft, TagKey<Item> largeCogwheelTag) {
        return REGISTRATE
                .block(name, p -> TieredCogwheelBlock.large(p, largeCogwheelShaftless, cogwheelShaft))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD))
                .properties(p -> p.mapColor(MapColor.DIRT))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .item(CogwheelBlockItem::new)
                .tag(GreateItemTags.COGWHEELS.itemTag)
                .tag(largeCogwheelTag).build()
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedCogwheel(String name, TIER tier, MATERIAL_TYPE materialType, BlockEntry<TieredCogwheelBlock> cogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block(name, p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.ANDESITE_CASING::get, cogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedCogwheel(cogwheel, () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(cogwheel))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                        Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
                .onRegister(c -> c.setTier(tier))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedLargeCogwheel(String name, TIER tier, MATERIAL_TYPE materialType, BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredCogwheelBlock> largeCogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block(name, p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.ANDESITE_CASING::get, largeCogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedLargeCogwheel(cogwheel, () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(largeCogwheel))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedCogwheel(String name, TIER tier, MATERIAL_TYPE materialType, BlockEntry<TieredCogwheelBlock> cogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block(name, p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.BRASS_CASING::get, cogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedCogwheel(cogwheel, () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(cogwheel))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                        Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
                .onRegister(c -> c.setTier(tier))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedLargeCogwheel(String name, TIER tier, MATERIAL_TYPE materialType, BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredCogwheelBlock> largeCogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block(name, p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.BRASS_CASING::get, largeCogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedLargeCogwheel(cogwheel, () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(largeCogwheel))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
