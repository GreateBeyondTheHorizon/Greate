package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlockItem;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedCogwheel;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedLargeCogwheel;

public class Cogwheels {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredCogwheelBlock> ANDESITE_COGWHEEL = REGISTRATE
            .block("andesite_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_ANDESITE_COGWHEEL = REGISTRATE
            .block("large_andesite_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_ANDESITE_COGWHEEL = REGISTRATE
            .block("andesite_encased_andesite_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, ANDESITE_COGWHEEL::get, LARGE_ANDESITE_COGWHEEL::get, GreatePartialModels.ANDESITE_HALF_SHAFT, GreatePartialModels.ANDESITE_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ANDESITE_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(ANDESITE_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(ANDESITE_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_ANDESITE_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_andesite_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, ANDESITE_COGWHEEL::get, LARGE_ANDESITE_COGWHEEL::get, GreatePartialModels.ANDESITE_HALF_SHAFT, GreatePartialModels.ANDESITE_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ANDESITE_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(ANDESITE_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_ANDESITE_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_ANDESITE_COGWHEEL = REGISTRATE
            .block("brass_encased_andesite_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, ANDESITE_COGWHEEL::get, LARGE_ANDESITE_COGWHEEL::get, GreatePartialModels.ANDESITE_HALF_SHAFT, GreatePartialModels.ANDESITE_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ANDESITE_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(ANDESITE_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(ANDESITE_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_ANDESITE_COGWHEEL = REGISTRATE
            .block("large_brass_encased_andesite_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, ANDESITE_COGWHEEL::get, LARGE_ANDESITE_COGWHEEL::get, GreatePartialModels.ANDESITE_HALF_SHAFT, GreatePartialModels.ANDESITE_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ANDESITE_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(ANDESITE_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_ANDESITE_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> STEEL_COGWHEEL = REGISTRATE
            .block("steel_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.LOW))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_STEEL_COGWHEEL = REGISTRATE
            .block("large_steel_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.LOW))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_STEEL_COGWHEEL = REGISTRATE
            .block("andesite_encased_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, STEEL_COGWHEEL::get, LARGE_STEEL_COGWHEEL::get, GreatePartialModels.STEEL_HALF_SHAFT, GreatePartialModels.STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(STEEL_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(STEEL_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.LOW))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_STEEL_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, STEEL_COGWHEEL::get, LARGE_STEEL_COGWHEEL::get, GreatePartialModels.STEEL_HALF_SHAFT, GreatePartialModels.STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(STEEL_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_STEEL_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_STEEL_COGWHEEL = REGISTRATE
            .block("brass_encased_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, STEEL_COGWHEEL::get, LARGE_STEEL_COGWHEEL::get, GreatePartialModels.STEEL_HALF_SHAFT, GreatePartialModels.STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(STEEL_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(STEEL_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.LOW))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_STEEL_COGWHEEL = REGISTRATE
            .block("large_brass_encased_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, STEEL_COGWHEEL::get, LARGE_STEEL_COGWHEEL::get, GreatePartialModels.STEEL_HALF_SHAFT, GreatePartialModels.STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(STEEL_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_STEEL_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> ALUMINIUM_COGWHEEL = REGISTRATE
            .block("aluminium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_ALUMINIUM_COGWHEEL = REGISTRATE
            .block("large_aluminium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_ALUMINIUM_COGWHEEL = REGISTRATE
            .block("andesite_encased_aluminium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, ALUMINIUM_COGWHEEL::get, LARGE_ALUMINIUM_COGWHEEL::get, GreatePartialModels.ALUMINIUM_HALF_SHAFT, GreatePartialModels.ALUMINIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ALUMINIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(ALUMINIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(ALUMINIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_ALUMINIUM_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_aluminium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, ALUMINIUM_COGWHEEL::get, LARGE_ALUMINIUM_COGWHEEL::get, GreatePartialModels.ALUMINIUM_HALF_SHAFT, GreatePartialModels.ALUMINIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ALUMINIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(ALUMINIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_ALUMINIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_ALUMINIUM_COGWHEEL = REGISTRATE
            .block("brass_encased_aluminium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, ALUMINIUM_COGWHEEL::get, LARGE_ALUMINIUM_COGWHEEL::get, GreatePartialModels.ALUMINIUM_HALF_SHAFT, GreatePartialModels.ALUMINIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ALUMINIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(ALUMINIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(ALUMINIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_ALUMINIUM_COGWHEEL = REGISTRATE
            .block("large_brass_encased_aluminium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, ALUMINIUM_COGWHEEL::get, LARGE_ALUMINIUM_COGWHEEL::get, GreatePartialModels.ALUMINIUM_HALF_SHAFT, GreatePartialModels.ALUMINIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_ALUMINIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(ALUMINIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_ALUMINIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("stainless_steel_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("large_stainless_steel_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("andesite_encased_stainless_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, STAINLESS_STEEL_COGWHEEL::get, LARGE_STAINLESS_STEEL_COGWHEEL::get, GreatePartialModels.STAINLESS_STEEL_HALF_SHAFT, GreatePartialModels.STAINLESS_STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STAINLESS_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(STAINLESS_STEEL_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(STAINLESS_STEEL_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_stainless_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, STAINLESS_STEEL_COGWHEEL::get, LARGE_STAINLESS_STEEL_COGWHEEL::get, GreatePartialModels.STAINLESS_STEEL_HALF_SHAFT, GreatePartialModels.STAINLESS_STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STAINLESS_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(STAINLESS_STEEL_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_STAINLESS_STEEL_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("brass_encased_stainless_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, STAINLESS_STEEL_COGWHEEL::get, LARGE_STAINLESS_STEEL_COGWHEEL::get, GreatePartialModels.STAINLESS_STEEL_HALF_SHAFT, GreatePartialModels.STAINLESS_STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STAINLESS_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(STAINLESS_STEEL_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(STAINLESS_STEEL_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("large_brass_encased_stainless_steel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, STAINLESS_STEEL_COGWHEEL::get, LARGE_STAINLESS_STEEL_COGWHEEL::get, GreatePartialModels.STAINLESS_STEEL_HALF_SHAFT, GreatePartialModels.STAINLESS_STEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_STAINLESS_STEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(STAINLESS_STEEL_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_STAINLESS_STEEL_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> TITANIUM_COGWHEEL = REGISTRATE
            .block("titanium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_TITANIUM_COGWHEEL = REGISTRATE
            .block("large_titanium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_TITANIUM_COGWHEEL = REGISTRATE
            .block("andesite_encased_titanium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, TITANIUM_COGWHEEL::get, LARGE_TITANIUM_COGWHEEL::get, GreatePartialModels.TITANIUM_HALF_SHAFT, GreatePartialModels.TITANIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TITANIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(TITANIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(TITANIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_TITANIUM_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_titanium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, TITANIUM_COGWHEEL::get, LARGE_TITANIUM_COGWHEEL::get, GreatePartialModels.TITANIUM_HALF_SHAFT, GreatePartialModels.TITANIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TITANIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(TITANIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_TITANIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_TITANIUM_COGWHEEL = REGISTRATE
            .block("brass_encased_titanium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, TITANIUM_COGWHEEL::get, LARGE_TITANIUM_COGWHEEL::get, GreatePartialModels.TITANIUM_HALF_SHAFT, GreatePartialModels.TITANIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TITANIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(TITANIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(TITANIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_TITANIUM_COGWHEEL = REGISTRATE
            .block("large_brass_encased_titanium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, TITANIUM_COGWHEEL::get, LARGE_TITANIUM_COGWHEEL::get, GreatePartialModels.TITANIUM_HALF_SHAFT, GreatePartialModels.TITANIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TITANIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(TITANIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_TITANIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("tungstensteel_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("large_tungstensteel_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("andesite_encased_tungstensteel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, TUNGSTENSTEEL_COGWHEEL::get, LARGE_TUNGSTENSTEEL_COGWHEEL::get, GreatePartialModels.TUNGSTENSTEEL_HALF_SHAFT, GreatePartialModels.TUNGSTENSTEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TUNGSTENSTEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(TUNGSTENSTEEL_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(TUNGSTENSTEEL_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_tungstensteel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, TUNGSTENSTEEL_COGWHEEL::get, LARGE_TUNGSTENSTEEL_COGWHEEL::get, GreatePartialModels.TUNGSTENSTEEL_HALF_SHAFT, GreatePartialModels.TUNGSTENSTEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TUNGSTENSTEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(TUNGSTENSTEEL_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_TUNGSTENSTEEL_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("brass_encased_tungstensteel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, TUNGSTENSTEEL_COGWHEEL::get, LARGE_TUNGSTENSTEEL_COGWHEEL::get, GreatePartialModels.TUNGSTENSTEEL_HALF_SHAFT, GreatePartialModels.TUNGSTENSTEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TUNGSTENSTEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(TUNGSTENSTEEL_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(TUNGSTENSTEEL_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("large_brass_encased_tungstensteel_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, TUNGSTENSTEEL_COGWHEEL::get, LARGE_TUNGSTENSTEEL_COGWHEEL::get, GreatePartialModels.TUNGSTENSTEEL_HALF_SHAFT, GreatePartialModels.TUNGSTENSTEEL_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_TUNGSTENSTEEL_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(TUNGSTENSTEEL_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_TUNGSTENSTEEL_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> PALLADIUM_COGWHEEL = REGISTRATE
            .block("palladium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_PALLADIUM_COGWHEEL = REGISTRATE
            .block("large_palladium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_PALLADIUM_COGWHEEL = REGISTRATE
            .block("andesite_encased_palladium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, PALLADIUM_COGWHEEL::get, LARGE_PALLADIUM_COGWHEEL::get, GreatePartialModels.PALLADIUM_HALF_SHAFT, GreatePartialModels.PALLADIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_PALLADIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(PALLADIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(PALLADIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_PALLADIUM_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_palladium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, PALLADIUM_COGWHEEL::get, LARGE_PALLADIUM_COGWHEEL::get, GreatePartialModels.PALLADIUM_HALF_SHAFT, GreatePartialModels.PALLADIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_PALLADIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(PALLADIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_PALLADIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_PALLADIUM_COGWHEEL = REGISTRATE
            .block("brass_encased_palladium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, PALLADIUM_COGWHEEL::get, LARGE_PALLADIUM_COGWHEEL::get, GreatePartialModels.PALLADIUM_HALF_SHAFT, GreatePartialModels.PALLADIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_PALLADIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(PALLADIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(PALLADIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_PALLADIUM_COGWHEEL = REGISTRATE
            .block("large_brass_encased_palladium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, PALLADIUM_COGWHEEL::get, LARGE_PALLADIUM_COGWHEEL::get, GreatePartialModels.PALLADIUM_HALF_SHAFT, GreatePartialModels.PALLADIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_PALLADIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(PALLADIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_PALLADIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> NAQUADAH_COGWHEEL = REGISTRATE
            .block("naquadah_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_NAQUADAH_COGWHEEL = REGISTRATE
            .block("large_naquadah_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_NAQUADAH_COGWHEEL = REGISTRATE
            .block("andesite_encased_naquadah_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, NAQUADAH_COGWHEEL::get, LARGE_NAQUADAH_COGWHEEL::get, GreatePartialModels.NAQUADAH_HALF_SHAFT, GreatePartialModels.NAQUADAH_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NAQUADAH_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(NAQUADAH_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(NAQUADAH_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_NAQUADAH_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_naquadah_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, NAQUADAH_COGWHEEL::get, LARGE_NAQUADAH_COGWHEEL::get, GreatePartialModels.NAQUADAH_HALF_SHAFT, GreatePartialModels.NAQUADAH_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NAQUADAH_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(NAQUADAH_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_NAQUADAH_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_NAQUADAH_COGWHEEL = REGISTRATE
            .block("brass_encased_naquadah_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, NAQUADAH_COGWHEEL::get, LARGE_NAQUADAH_COGWHEEL::get, GreatePartialModels.NAQUADAH_HALF_SHAFT, GreatePartialModels.NAQUADAH_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NAQUADAH_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(NAQUADAH_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(NAQUADAH_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_NAQUADAH_COGWHEEL = REGISTRATE
            .block("large_brass_encased_naquadah_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, NAQUADAH_COGWHEEL::get, LARGE_NAQUADAH_COGWHEEL::get, GreatePartialModels.NAQUADAH_HALF_SHAFT, GreatePartialModels.NAQUADAH_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NAQUADAH_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(NAQUADAH_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_NAQUADAH_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("darmstadtium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("large_darmstadtium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("andesite_encased_darmstadtium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, DARMSTADTIUM_COGWHEEL::get, LARGE_DARMSTADTIUM_COGWHEEL::get, GreatePartialModels.DARMSTADTIUM_HALF_SHAFT, GreatePartialModels.DARMSTADTIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_DARMSTADTIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(DARMSTADTIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(DARMSTADTIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_darmstadtium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, DARMSTADTIUM_COGWHEEL::get, LARGE_DARMSTADTIUM_COGWHEEL::get, GreatePartialModels.DARMSTADTIUM_HALF_SHAFT, GreatePartialModels.DARMSTADTIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_DARMSTADTIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(DARMSTADTIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_DARMSTADTIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("brass_encased_darmstadtium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, DARMSTADTIUM_COGWHEEL::get, LARGE_DARMSTADTIUM_COGWHEEL::get, GreatePartialModels.DARMSTADTIUM_HALF_SHAFT, GreatePartialModels.DARMSTADTIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_DARMSTADTIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(DARMSTADTIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(DARMSTADTIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("large_brass_encased_darmstadtium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, DARMSTADTIUM_COGWHEEL::get, LARGE_DARMSTADTIUM_COGWHEEL::get, GreatePartialModels.DARMSTADTIUM_HALF_SHAFT, GreatePartialModels.DARMSTADTIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_DARMSTADTIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(DARMSTADTIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_DARMSTADTIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final BlockEntry<TieredCogwheelBlock> NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("neutronium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("large_neutronium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .item(TieredCogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("andesite_encased_neutronium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.ANDESITE_CASING::get, NEUTRONIUM_COGWHEEL::get, LARGE_NEUTRONIUM_COGWHEEL::get, GreatePartialModels.NEUTRONIUM_HALF_SHAFT, GreatePartialModels.NEUTRONIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NEUTRONIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(NEUTRONIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(NEUTRONIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                    Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("large_andesite_encased_neutronium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.ANDESITE_CASING::get, NEUTRONIUM_COGWHEEL::get, LARGE_NEUTRONIUM_COGWHEEL::get, GreatePartialModels.NEUTRONIUM_HALF_SHAFT, GreatePartialModels.NEUTRONIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NEUTRONIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(NEUTRONIUM_COGWHEEL, () -> AllSpriteShifts.ANDESITE_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_NEUTRONIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> BRASS_ENCASED_NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("brass_encased_neutronium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,false, AllBlocks.BRASS_CASING::get, NEUTRONIUM_COGWHEEL::get, LARGE_NEUTRONIUM_COGWHEEL::get, GreatePartialModels.NEUTRONIUM_HALF_SHAFT, GreatePartialModels.NEUTRONIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NEUTRONIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedCogwheel(NEUTRONIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(NEUTRONIUM_COGWHEEL))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                    Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<TieredEncasedCogwheelBlock> LARGE_BRASS_ENCASED_NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("large_brass_encased_neutronium_cogwheel", p -> new TieredEncasedCogwheelBlock(p,true, AllBlocks.BRASS_CASING::get, NEUTRONIUM_COGWHEEL::get, LARGE_NEUTRONIUM_COGWHEEL::get, GreatePartialModels.NEUTRONIUM_HALF_SHAFT, GreatePartialModels.NEUTRONIUM_SHAFTLESS_COGWHEEL, GreatePartialModels.LARGE_NEUTRONIUM_SHAFTLESS_COGWHEEL))
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(tieredEncasedLargeCogwheel(NEUTRONIUM_COGWHEEL, () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(LARGE_NEUTRONIUM_COGWHEEL))
            .transform(TagGen.axeOrPickaxe())
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();

    public static void register() {}
}
