package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.TieredEncasedShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
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
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> STEEL_SHAFT = REGISTRATE
            .block("steel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> ALUMINIUM_SHAFT = REGISTRATE
            .block("aluminium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("stainless_steel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> TITANIUM_SHAFT = REGISTRATE
            .block("titanium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("tungstensteel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> PALLADIUM_SHAFT = REGISTRATE
            .block("palladium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> NAQUADAH_SHAFT = REGISTRATE
            .block("naquadah_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> DARMSTADTIUM_SHAFT = REGISTRATE
            .block("darmstadtium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> NEUTRONIUM_SHAFT = REGISTRATE
            .block("neutronium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_ANDESITE_SHAFT = REGISTRATE
            .block("andesite_encased_andesite_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.ANDESITE_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(ANDESITE_SHAFT, "andesite", "andesite_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ANDESITE_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_ANDESITE_SHAFT = REGISTRATE
            .block("brass_encased_andesite_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.ANDESITE_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(ANDESITE_SHAFT, "brass", "andesite_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ANDESITE_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_STEEL_SHAFT = REGISTRATE
            .block("andesite_encased_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(STEEL_SHAFT, "andesite", "steel_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_STEEL_SHAFT = REGISTRATE
            .block("brass_encased_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(STEEL_SHAFT, "brass", "steel_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_ALUMINIUM_SHAFT = REGISTRATE
            .block("andesite_encased_aluminium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.ALUMINIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(ALUMINIUM_SHAFT, "andesite", "aluminium_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ALUMINIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_ALUMINIUM_SHAFT = REGISTRATE
            .block("brass_encased_aluminium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.ALUMINIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(ALUMINIUM_SHAFT, "brass", "aluminium_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.ALUMINIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("andesite_encased_stainless_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.STAINLESS_STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(STAINLESS_STEEL_SHAFT, "andesite", "stainless_steel_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STAINLESS_STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("brass_encased_stainless_steel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.STAINLESS_STEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(STAINLESS_STEEL_SHAFT, "brass", "stainless_steel_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.STAINLESS_STEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_TITANIUM_SHAFT = REGISTRATE
            .block("andesite_encased_titanium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.TITANIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(TITANIUM_SHAFT, "andesite", "titanium_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TITANIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_TITANIUM_SHAFT = REGISTRATE
            .block("brass_encased_titanium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.TITANIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(TITANIUM_SHAFT, "brass", "titanium_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TITANIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("andesite_encased_tungstensteel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.TUNGSTENSTEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(TUNGSTENSTEEL_SHAFT, "andesite", "tungstensteel_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TUNGSTENSTEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("brass_encased_tungstensteel_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.TUNGSTENSTEEL_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(TUNGSTENSTEEL_SHAFT, "brass", "tungstensteel_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.TUNGSTENSTEEL_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_PALLADIUM_SHAFT = REGISTRATE
            .block("andesite_encased_palladium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.PALLADIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(PALLADIUM_SHAFT, "andesite", "palladium_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.PALLADIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_PALLADIUM_SHAFT = REGISTRATE
            .block("brass_encased_palladium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.PALLADIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(PALLADIUM_SHAFT, "brass", "palladium_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.PALLADIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_NAQUADAH_SHAFT = REGISTRATE
            .block("andesite_encased_naquadah_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.NAQUADAH_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(NAQUADAH_SHAFT, "andesite", "naquadah_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NAQUADAH_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_NAQUADAH_SHAFT = REGISTRATE
            .block("brass_encased_naquadah_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.NAQUADAH_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(NAQUADAH_SHAFT, "brass", "naquadah_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NAQUADAH_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_DARMSTADTIUM_SHAFT = REGISTRATE
            .block("andesite_encased_darmstadtium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.DARMSTADTIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(DARMSTADTIUM_SHAFT, "andesite", "darmstadtium_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.DARMSTADTIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_DARMSTADTIUM_SHAFT = REGISTRATE
            .block("brass_encased_darmstadtium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.DARMSTADTIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(DARMSTADTIUM_SHAFT, "brass", "darmstadtium_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.DARMSTADTIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_NEUTRONIUM_SHAFT = REGISTRATE
            .block("andesite_encased_neutronium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, Shafts.NEUTRONIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(NEUTRONIUM_SHAFT, "andesite", "neutronium_shaft", () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NEUTRONIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_NEUTRONIUM_SHAFT = REGISTRATE
            .block("brass_encased_neutronium_shaft", p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, Shafts.NEUTRONIUM_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(GreateBuilderTransformers.tieredEncasedShaft(NEUTRONIUM_SHAFT, "brass", "neutronium_shaft", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(Shafts.NEUTRONIUM_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static void register() {}
}
