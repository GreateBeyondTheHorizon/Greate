package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MaterialColor;

import static electrolyte.greate.Greate.REGISTRATE;

public class Shafts {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredShaftBlock> ANDESITE_SHAFT = REGISTRATE
            .block("andesite_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .simpleItem()
            .item().tab(() -> Greate.GREATE_TAB).build()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_ANDESITE_SHAFT = REGISTRATE
            .block("powered_andesite_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.ANDESITE_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, ANDESITE_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_ANDESITE_SHAFT = REGISTRATE
            .block("andesite_encased_andesite_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.ANDESITE_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(ANDESITE_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ANDESITE_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_ANDESITE_SHAFT = REGISTRATE
            .block("brass_encased_andesite_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.ANDESITE_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(ANDESITE_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ANDESITE_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredShaftBlock> STEEL_SHAFT = REGISTRATE
            .block("steel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.LOW))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_STEEL_SHAFT = REGISTRATE
            .block("powered_steel_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.STEEL_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, STEEL_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_STEEL_SHAFT = REGISTRATE
            .block("andesite_encased_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(STEEL_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_STEEL_SHAFT = REGISTRATE
            .block("brass_encased_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(STEEL_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final BlockEntry<TieredShaftBlock> ALUMINIUM_SHAFT = REGISTRATE
            .block("aluminium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_ALUMINIUM_SHAFT = REGISTRATE
            .block("powered_aluminium_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.ALUMINIUM_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, ALUMINIUM_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_ALUMINIUM_SHAFT = REGISTRATE
            .block("andesite_encased_aluminium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.ALUMINIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(ALUMINIUM_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ALUMINIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_ALUMINIUM_SHAFT = REGISTRATE
            .block("brass_encased_aluminium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.ALUMINIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(ALUMINIUM_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ALUMINIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .register();

    public static final BlockEntry<TieredShaftBlock> STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("stainless_steel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("powered_stainless_steel_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.STAINLESS_STEEL_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, STAINLESS_STEEL_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("andesite_encased_stainless_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.STAINLESS_STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(STAINLESS_STEEL_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STAINLESS_STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("brass_encased_stainless_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.STAINLESS_STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(STAINLESS_STEEL_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STAINLESS_STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final BlockEntry<TieredShaftBlock> TITANIUM_SHAFT = REGISTRATE
            .block("titanium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_TITANIUM_SHAFT = REGISTRATE
            .block("powered_titanium_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.TITANIUM_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, TITANIUM_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_TITANIUM_SHAFT = REGISTRATE
            .block("andesite_encased_titanium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.TITANIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(TITANIUM_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TITANIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_TITANIUM_SHAFT = REGISTRATE
            .block("brass_encased_titanium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.TITANIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(TITANIUM_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TITANIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final BlockEntry<TieredShaftBlock> TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("tungstensteel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("powered_tungstensteel_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.TUNGSTENSTEEL_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, TUNGSTENSTEEL_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("andesite_encased_tungstensteel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.TUNGSTENSTEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(TUNGSTENSTEEL_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TUNGSTENSTEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("brass_encased_tungstensteel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.TUNGSTENSTEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(TUNGSTENSTEEL_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TUNGSTENSTEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final BlockEntry<TieredShaftBlock> PALLADIUM_SHAFT = REGISTRATE
            .block("palladium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_PALLADIUM_SHAFT = REGISTRATE
            .block("powered_palladium_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.PALLADIUM_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, PALLADIUM_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_PALLADIUM_SHAFT = REGISTRATE
            .block("andesite_encased_palladium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.PALLADIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(PALLADIUM_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.PALLADIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_PALLADIUM_SHAFT = REGISTRATE
            .block("brass_encased_palladium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.PALLADIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(PALLADIUM_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.PALLADIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final BlockEntry<TieredShaftBlock> NAQUADAH_SHAFT = REGISTRATE
            .block("naquadah_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_NAQUADAH_SHAFT = REGISTRATE
            .block("powered_naquadah_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.NAQUADAH_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, NAQUADAH_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_NAQUADAH_SHAFT = REGISTRATE
            .block("andesite_encased_naquadah_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.NAQUADAH_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(NAQUADAH_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NAQUADAH_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_NAQUADAH_SHAFT = REGISTRATE
            .block("brass_encased_naquadah_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.NAQUADAH_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(NAQUADAH_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NAQUADAH_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final BlockEntry<TieredShaftBlock> DARMSTADTIUM_SHAFT = REGISTRATE
            .block("darmstadtium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_DARMSTADTIUM_SHAFT = REGISTRATE
            .block("powered_darmstadtium_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.DARMSTADTIUM_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, DARMSTADTIUM_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_DARMSTADTIUM_SHAFT = REGISTRATE
            .block("andesite_encased_darmstadtium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.DARMSTADTIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(DARMSTADTIUM_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.DARMSTADTIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_DARMSTADTIUM_SHAFT = REGISTRATE
            .block("brass_encased_darmstadtium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.DARMSTADTIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(DARMSTADTIUM_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.DARMSTADTIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final BlockEntry<TieredShaftBlock> NEUTRONIUM_SHAFT = REGISTRATE
            .block("neutronium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredShaftProvider())
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_NEUTRONIUM_SHAFT = REGISTRATE
            .block("powered_neutronium_shaft", p -> new TieredPoweredShaftBlock(p, Shafts.NEUTRONIUM_SHAFT::get))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
            .loot((l, b) -> l.dropOther(b, NEUTRONIUM_SHAFT.get()))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_NEUTRONIUM_SHAFT = REGISTRATE
            .block("andesite_encased_neutronium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.NEUTRONIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(NEUTRONIUM_SHAFT, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NEUTRONIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_NEUTRONIUM_SHAFT = REGISTRATE
            .block("brass_encased_neutronium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.NEUTRONIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(NEUTRONIUM_SHAFT, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NEUTRONIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();
    public static void register() {}
}
