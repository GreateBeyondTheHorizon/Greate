package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.infrastructure.config.GStress;
import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedCogwheel;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedLargeCogwheel;

public class Cogwheels {
    
    public static final BlockEntry<TieredCogwheelBlock>[] COGWHEELS = new BlockEntry[10];
    public static final BlockEntry<TieredCogwheelBlock>[] LARGE_COGWHEELS = new BlockEntry[10];
    public static BlockEntry<TieredCogwheelBlock>
            ANDESITE_COGWHEEL,
            STEEL_COGWHEEL,
            ALUMINIUM_COGWHEEL,
            STAINLESS_STEEL_COGWHEEL,
            TITANIUM_COGWHEEL,
            TUNGSTENSTEEL_COGWHEEL,
            PALLADIUM_COGWHEEL,
            NAQUADAH_COGWHEEL,
            DARMSTADTIUM_COGWHEEL,
            NEUTRONIUM_COGWHEEL,
            LARGE_ANDESITE_COGWHEEL,
            LARGE_STEEL_COGWHEEL,
            LARGE_ALUMINIUM_COGWHEEL,
            LARGE_STAINLESS_STEEL_COGWHEEL,
            LARGE_TITANIUM_COGWHEEL,
            LARGE_TUNGSTENSTEEL_COGWHEEL,
            LARGE_PALLADIUM_COGWHEEL,
            LARGE_NAQUADAH_COGWHEEL,
            LARGE_DARMSTADTIUM_COGWHEEL,
            LARGE_NEUTRONIUM_COGWHEEL;

    public static final BlockEntry<TieredEncasedCogwheelBlock>[] ANDESITE_ENCASED_COGWHEELS = new BlockEntry[10];
    public static final BlockEntry<TieredEncasedCogwheelBlock>[] ANDESITE_ENCASED_LARGE_COGWHEELS = new BlockEntry[10];
    public static final BlockEntry<TieredEncasedCogwheelBlock>[] BRASS_ENCASED_COGWHEELS = new BlockEntry[10];
    public static final BlockEntry<TieredEncasedCogwheelBlock>[] BRASS_ENCASED_LARGE_COGWHEELS = new BlockEntry[10];
    public static BlockEntry<TieredEncasedCogwheelBlock>
            ANDESITE_ENCASED_ANDESITE_COGWHEEL,
            ANDESITE_ENCASED_LARGE_ANDESITE_COGWHEEL,
            BRASS_ENCASED_ANDESITE_COGWHEEL,
            BRASS_ENCASED_LARGE_ANDESITE_COGWHEEL,
            ANDESITE_ENCASED_STEEL_COGWHEEL,
            ANDESITE_ENCASED_LARGE_STEEL_COGWHEEL,
            BRASS_ENCASED_STEEL_COGWHEEL,
            BRASS_ENCASED_LARGE_STEEL_COGWHEEL,
            ANDESITE_ENCASED_ALUMINIUM_COGWHEEL,
            ANDESITE_ENCASED_LARGE_ALUMINIUM_COGWHEEL,
            BRASS_ENCASED_ALUMINIUM_COGWHEEL,
            BRASS_ENCASED_LARGE_ALUMINIUM_COGWHEEL,
            ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL,
            ANDESITE_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL,
            BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL,
            BRASS_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL,
            ANDESITE_ENCASED_TITANIUM_COGWHEEL,
            ANDESITE_ENCASED_LARGE_TITANIUM_COGWHEEL,
            BRASS_ENCASED_TITANIUM_COGWHEEL,
            BRASS_ENCASED_LARGE_TITANIUM_COGWHEEL,
            ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL,
            ANDESITE_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL,
            BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL,
            BRASS_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL,
            ANDESITE_ENCASED_PALLADIUM_COGWHEEL,
            ANDESITE_ENCASED_LARGE_PALLADIUM_COGWHEEL,
            BRASS_ENCASED_PALLADIUM_COGWHEEL,
            BRASS_ENCASED_LARGE_PALLADIUM_COGWHEEL,
            ANDESITE_ENCASED_NAQUADAH_COGWHEEL,
            ANDESITE_ENCASED_LARGE_NAQUADAH_COGWHEEL,
            BRASS_ENCASED_NAQUADAH_COGWHEEL,
            BRASS_ENCASED_LARGE_NAQUADAH_COGWHEEL,
            ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL,
            ANDESITE_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL,
            BRASS_ENCASED_DARMSTADTIUM_COGWHEEL,
            BRASS_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL,
            ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL,
            ANDESITE_ENCASED_LARGE_NEUTRONIUM_COGWHEEL,
            BRASS_ENCASED_NEUTRONIUM_COGWHEEL,
            BRASS_ENCASED_LARGE_NEUTRONIUM_COGWHEEL;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        // Cogwheels
        COGWHEELS[ULV] = ANDESITE_COGWHEEL = registerCogwheel(ULV);
        COGWHEELS[LV] = STEEL_COGWHEEL = registerCogwheel(LV);
        COGWHEELS[MV] = ALUMINIUM_COGWHEEL = registerCogwheel(MV);
        COGWHEELS[HV] = STAINLESS_STEEL_COGWHEEL = registerCogwheel(HV);
        COGWHEELS[EV] = TITANIUM_COGWHEEL = registerCogwheel(EV);
        COGWHEELS[IV] = TUNGSTENSTEEL_COGWHEEL = registerCogwheel(IV);
        COGWHEELS[LuV] = PALLADIUM_COGWHEEL = registerCogwheel(LuV);
        COGWHEELS[ZPM] = NAQUADAH_COGWHEEL = registerCogwheel(ZPM);
        COGWHEELS[UV] = DARMSTADTIUM_COGWHEEL = registerCogwheel(UV);
        COGWHEELS[UHV] = NEUTRONIUM_COGWHEEL = registerCogwheel(UHV);

        // Large cogwheels
        LARGE_COGWHEELS[ULV] = LARGE_ANDESITE_COGWHEEL = registerLargeCogwheel(ULV);
        LARGE_COGWHEELS[LV] = LARGE_STEEL_COGWHEEL = registerLargeCogwheel(LV);
        LARGE_COGWHEELS[MV] = LARGE_ALUMINIUM_COGWHEEL = registerLargeCogwheel(MV);
        LARGE_COGWHEELS[HV] = LARGE_STAINLESS_STEEL_COGWHEEL = registerLargeCogwheel(HV);
        LARGE_COGWHEELS[EV] = LARGE_TITANIUM_COGWHEEL = registerLargeCogwheel(EV);
        LARGE_COGWHEELS[IV] = LARGE_TUNGSTENSTEEL_COGWHEEL = registerLargeCogwheel(IV);
        LARGE_COGWHEELS[LuV] = LARGE_PALLADIUM_COGWHEEL = registerLargeCogwheel(LuV);
        LARGE_COGWHEELS[ZPM] = LARGE_NAQUADAH_COGWHEEL = registerLargeCogwheel(ZPM);
        LARGE_COGWHEELS[UV] = LARGE_DARMSTADTIUM_COGWHEEL = registerLargeCogwheel(UV);
        LARGE_COGWHEELS[UHV] = LARGE_NEUTRONIUM_COGWHEEL = registerLargeCogwheel(UHV);

        // Andesite encased cogwheels
        ANDESITE_ENCASED_COGWHEELS[ULV] = ANDESITE_ENCASED_ANDESITE_COGWHEEL = registerAndesiteEncasedCogwheel(ULV);
        ANDESITE_ENCASED_COGWHEELS[LV] = ANDESITE_ENCASED_STEEL_COGWHEEL = registerAndesiteEncasedCogwheel(LV);
        ANDESITE_ENCASED_COGWHEELS[MV] = ANDESITE_ENCASED_ALUMINIUM_COGWHEEL = registerAndesiteEncasedCogwheel(MV);
        ANDESITE_ENCASED_COGWHEELS[HV] = ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL = registerAndesiteEncasedCogwheel(HV);
        ANDESITE_ENCASED_COGWHEELS[EV] = ANDESITE_ENCASED_TITANIUM_COGWHEEL = registerAndesiteEncasedCogwheel(EV);
        ANDESITE_ENCASED_COGWHEELS[IV] = ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL = registerAndesiteEncasedCogwheel(IV);
        ANDESITE_ENCASED_COGWHEELS[LuV] = ANDESITE_ENCASED_PALLADIUM_COGWHEEL = registerAndesiteEncasedCogwheel(LuV);
        ANDESITE_ENCASED_COGWHEELS[ZPM] = ANDESITE_ENCASED_NAQUADAH_COGWHEEL = registerAndesiteEncasedCogwheel(ZPM);
        ANDESITE_ENCASED_COGWHEELS[UV] = ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL = registerAndesiteEncasedCogwheel(UV);
        ANDESITE_ENCASED_COGWHEELS[UHV] = ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL = registerAndesiteEncasedCogwheel(UHV);

        // Andesite encased large cogwheels
        ANDESITE_ENCASED_LARGE_COGWHEELS[ULV] = ANDESITE_ENCASED_LARGE_ANDESITE_COGWHEEL = registerAndesiteEncasedLargeCogwheel(ULV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[LV] = ANDESITE_ENCASED_LARGE_STEEL_COGWHEEL = registerAndesiteEncasedLargeCogwheel(LV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[MV] = ANDESITE_ENCASED_LARGE_ALUMINIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel(MV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[HV] = ANDESITE_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL = registerAndesiteEncasedLargeCogwheel(HV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[EV] = ANDESITE_ENCASED_LARGE_TITANIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel(EV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[IV] = ANDESITE_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL = registerAndesiteEncasedLargeCogwheel(IV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[LuV] = ANDESITE_ENCASED_LARGE_PALLADIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel(LuV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[ZPM] = ANDESITE_ENCASED_LARGE_NAQUADAH_COGWHEEL = registerAndesiteEncasedLargeCogwheel(ZPM);
        ANDESITE_ENCASED_LARGE_COGWHEELS[UV] = ANDESITE_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel(UV);
        ANDESITE_ENCASED_LARGE_COGWHEELS[UHV] = ANDESITE_ENCASED_LARGE_NEUTRONIUM_COGWHEEL = registerAndesiteEncasedLargeCogwheel(UHV);

        // Brass encased cogwheels
        BRASS_ENCASED_COGWHEELS[ULV] = BRASS_ENCASED_ANDESITE_COGWHEEL = registerBrassEncasedCogwheel(ULV);
        BRASS_ENCASED_COGWHEELS[LV] = BRASS_ENCASED_STEEL_COGWHEEL = registerBrassEncasedCogwheel(LV);
        BRASS_ENCASED_COGWHEELS[MV] = BRASS_ENCASED_ALUMINIUM_COGWHEEL = registerBrassEncasedCogwheel(MV);
        BRASS_ENCASED_COGWHEELS[HV] = BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL = registerBrassEncasedCogwheel(HV);
        BRASS_ENCASED_COGWHEELS[EV] = BRASS_ENCASED_TITANIUM_COGWHEEL = registerBrassEncasedCogwheel(EV);
        BRASS_ENCASED_COGWHEELS[IV] = BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL = registerBrassEncasedCogwheel(IV);
        BRASS_ENCASED_COGWHEELS[LuV] = BRASS_ENCASED_PALLADIUM_COGWHEEL = registerBrassEncasedCogwheel(LuV);
        BRASS_ENCASED_COGWHEELS[ZPM] = BRASS_ENCASED_NAQUADAH_COGWHEEL = registerBrassEncasedCogwheel(ZPM);
        BRASS_ENCASED_COGWHEELS[UV] = BRASS_ENCASED_DARMSTADTIUM_COGWHEEL = registerBrassEncasedCogwheel(UV);
        BRASS_ENCASED_COGWHEELS[UHV] = BRASS_ENCASED_NEUTRONIUM_COGWHEEL = registerBrassEncasedCogwheel(UHV);

        // Brass encased large cogwheels
        BRASS_ENCASED_LARGE_COGWHEELS[ULV] = BRASS_ENCASED_LARGE_ANDESITE_COGWHEEL = registerBrassEncasedLargeCogwheel(ULV);
        BRASS_ENCASED_LARGE_COGWHEELS[LV] = BRASS_ENCASED_LARGE_STEEL_COGWHEEL = registerBrassEncasedLargeCogwheel(LV);
        BRASS_ENCASED_LARGE_COGWHEELS[MV] = BRASS_ENCASED_LARGE_ALUMINIUM_COGWHEEL = registerBrassEncasedLargeCogwheel(MV);
        BRASS_ENCASED_LARGE_COGWHEELS[HV] = BRASS_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL = registerBrassEncasedLargeCogwheel(HV);
        BRASS_ENCASED_LARGE_COGWHEELS[EV] = BRASS_ENCASED_LARGE_TITANIUM_COGWHEEL = registerBrassEncasedLargeCogwheel(EV);
        BRASS_ENCASED_LARGE_COGWHEELS[IV] = BRASS_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL = registerBrassEncasedLargeCogwheel(IV);
        BRASS_ENCASED_LARGE_COGWHEELS[LuV] = BRASS_ENCASED_LARGE_PALLADIUM_COGWHEEL = registerBrassEncasedLargeCogwheel(LuV);
        BRASS_ENCASED_LARGE_COGWHEELS[ZPM] = BRASS_ENCASED_LARGE_NAQUADAH_COGWHEEL = registerBrassEncasedLargeCogwheel(ZPM);
        BRASS_ENCASED_LARGE_COGWHEELS[UV] = BRASS_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL = registerBrassEncasedLargeCogwheel(UV);
        BRASS_ENCASED_LARGE_COGWHEELS[UHV] = BRASS_ENCASED_LARGE_NEUTRONIUM_COGWHEEL = registerBrassEncasedLargeCogwheel(UHV);
    }
    
    public static BlockEntry<TieredCogwheelBlock> registerCogwheel(int tier) {
        return REGISTRATE
                .block(TM[tier].getName() + "_cogwheel", TieredCogwheelBlock::small)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD))
                .properties(p -> p.mapColor(MapColor.DIRT))
                .transform(GStress.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .item(CogwheelBlockItem::new).build()
                .register();
    }
    
    public static BlockEntry<TieredCogwheelBlock> registerLargeCogwheel(int tier) {
        return REGISTRATE
                .block("large_" + TM[tier].getName() + "_cogwheel", TieredCogwheelBlock::large)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD))
                .properties(p -> p.mapColor(MapColor.DIRT))
                .transform(GStress.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .item(CogwheelBlockItem::new).build()
                .register();
    }
    
    public static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedCogwheel(int tier) {
        return REGISTRATE
                .block("andesite_encased_" + TM[tier].getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.ANDESITE_CASING::get, COGWHEELS[tier]::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedCogwheel(COGWHEELS[tier], () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(COGWHEELS[tier]))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                        Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
                .onRegister(c -> c.setTier(tier))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedLargeCogwheel(int tier) {
        return REGISTRATE
                .block("andesite_encased_large_" + TM[tier].getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.ANDESITE_CASING::get, LARGE_COGWHEELS[tier]::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedLargeCogwheel(COGWHEELS[tier], () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(LARGE_COGWHEELS[tier]))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedCogwheel(int tier) {
        return REGISTRATE
                .block("brass_encased_" + TM[tier].getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.BRASS_CASING::get, COGWHEELS[tier]::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedCogwheel(COGWHEELS[tier], () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(COGWHEELS[tier]))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                        Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
                .onRegister(c -> c.setTier(tier))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .register();
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedLargeCogwheel(int tier) {
        return REGISTRATE
                .block("brass_encased_large_" + TM[tier].getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.BRASS_CASING::get, LARGE_COGWHEELS[tier]::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedLargeCogwheel(COGWHEELS[tier], () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(LARGE_COGWHEELS[tier]))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
