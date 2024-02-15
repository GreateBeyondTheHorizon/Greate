package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.registry.GreateTags.GreateItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedCogwheel;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedLargeCogwheel;
import static electrolyte.greate.registry.GreatePartialModels.*;

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
        COGWHEELS[ULV] = ANDESITE_COGWHEEL = registerCogwheel(ULV, GreateItemTags.COGWHEELS_ANDESITE.itemTag);
        COGWHEELS[LV] = STEEL_COGWHEEL = registerCogwheel(LV, GreateItemTags.COGWHEELS_STEEL.itemTag);
        COGWHEELS[MV] = ALUMINIUM_COGWHEEL = registerCogwheel(MV, GreateItemTags.COGWHEELS_ALUMINIUM.itemTag);
        COGWHEELS[HV] = STAINLESS_STEEL_COGWHEEL = registerCogwheel(HV, GreateItemTags.COGWHEELS_STAINLESS_STEEL.itemTag);
        COGWHEELS[EV] = TITANIUM_COGWHEEL = registerCogwheel(EV, GreateItemTags.COGWHEELS_TITANIUM.itemTag);
        COGWHEELS[IV] = TUNGSTENSTEEL_COGWHEEL = registerCogwheel(IV, GreateItemTags.COGWHEELS_TUNGSTENSTEEL.itemTag);
        COGWHEELS[LuV] = PALLADIUM_COGWHEEL = registerCogwheel(LuV, GreateItemTags.COGWHEELS_PALLADIUM.itemTag);
        COGWHEELS[ZPM] = NAQUADAH_COGWHEEL = registerCogwheel(ZPM, GreateItemTags.COGWHEELS_NAQUADAH.itemTag);
        COGWHEELS[UV] = DARMSTADTIUM_COGWHEEL = registerCogwheel(UV, GreateItemTags.COGWHEELS_DARMSTADTIUM.itemTag);
        COGWHEELS[UHV] = NEUTRONIUM_COGWHEEL = registerCogwheel(UHV, GreateItemTags.COGWHEELS_NEUTRONIUM.itemTag);

        // Large cogwheels
        LARGE_COGWHEELS[ULV] = LARGE_ANDESITE_COGWHEEL = registerLargeCogwheel(ULV, GreateItemTags.LARGE_COGWHEELS_ANDESITE.itemTag);
        LARGE_COGWHEELS[LV] = LARGE_STEEL_COGWHEEL = registerLargeCogwheel(LV, GreateItemTags.LARGE_COGWHEELS_STEEL.itemTag);
        LARGE_COGWHEELS[MV] = LARGE_ALUMINIUM_COGWHEEL = registerLargeCogwheel(MV, GreateItemTags.LARGE_COGWHEELS_ALUMINIUM.itemTag);
        LARGE_COGWHEELS[HV] = LARGE_STAINLESS_STEEL_COGWHEEL = registerLargeCogwheel(HV, GreateItemTags.LARGE_COGWHEELS_STAINLESS_STEEL.itemTag);
        LARGE_COGWHEELS[EV] = LARGE_TITANIUM_COGWHEEL = registerLargeCogwheel(EV, GreateItemTags.LARGE_COGWHEELS_TITANIUM.itemTag);
        LARGE_COGWHEELS[IV] = LARGE_TUNGSTENSTEEL_COGWHEEL = registerLargeCogwheel(IV, GreateItemTags.LARGE_COGWHEELS_TUNGSTENSTEEL.itemTag);
        LARGE_COGWHEELS[LuV] = LARGE_PALLADIUM_COGWHEEL = registerLargeCogwheel(LuV, GreateItemTags.LARGE_COGWHEELS_PALLADIUM.itemTag);
        LARGE_COGWHEELS[ZPM] = LARGE_NAQUADAH_COGWHEEL = registerLargeCogwheel(ZPM, GreateItemTags.LARGE_COGWHEELS_NAQUADAH.itemTag);
        LARGE_COGWHEELS[UV] = LARGE_DARMSTADTIUM_COGWHEEL = registerLargeCogwheel(UV, GreateItemTags.LARGE_COGWHEELS_DARMSTADTIUM.itemTag);
        LARGE_COGWHEELS[UHV] = LARGE_NEUTRONIUM_COGWHEEL = registerLargeCogwheel(UHV, GreateItemTags.LARGE_COGWHEELS_NEUTRONIUM.itemTag);

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

    public static BlockEntry<TieredCogwheelBlock> registerCogwheel(int tier, TagKey<Item> smallCogwheelTag) {
        return registerCogwheel(tier, TM[tier], COGWHEEL_SHAFTLESS_MODELS[tier], COGWHEEL_SHAFT_MODELS[tier], smallCogwheelTag);
    }
    
    public static BlockEntry<TieredCogwheelBlock> registerCogwheel(int tier, Material material, PartialModel largeCogwheelShaftless, PartialModel cogwheelShaft, TagKey<Item> smallCogwheelTag) {
        return REGISTRATE
                .block(material.getName() + "_cogwheel", p -> TieredCogwheelBlock.small(p, largeCogwheelShaftless, cogwheelShaft))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD))
                .properties(p -> p.mapColor(MapColor.DIRT))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .item(CogwheelBlockItem::new)
                .tag(GreateItemTags.COGWHEELS.itemTag)
                .tag(smallCogwheelTag).build()
                .register();
    }

    public static BlockEntry<TieredCogwheelBlock> registerLargeCogwheel(int tier, TagKey<Item> largeCogwheelTag) {
        return registerLargeCogwheel(tier, TM[tier], LARGE_COGWHEEL_SHAFTLESS_MODELS[tier], COGWHEEL_SHAFT_MODELS[tier], largeCogwheelTag);
    }
    
    public static BlockEntry<TieredCogwheelBlock> registerLargeCogwheel(int tier, Material material, PartialModel largeCogwheelShaftless, PartialModel cogwheelShaft, TagKey<Item> largeCogwheelTag) {
        return REGISTRATE
                .block("large_" + material.getName() + "_cogwheel", p -> TieredCogwheelBlock.large(p, largeCogwheelShaftless, cogwheelShaft))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD))
                .properties(p -> p.mapColor(MapColor.DIRT))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .item(CogwheelBlockItem::new)
                .tag(GreateItemTags.COGWHEELS.itemTag)
                .tag(largeCogwheelTag).build()
                .register();
    }

    private static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedCogwheel(int tier) {
        return registerAndesiteEncasedCogwheel(tier, TM[tier], COGWHEELS[tier], SHAFT_HALF_MODELS[tier], COGWHEEL_SHAFTLESS_MODELS[tier]);
    }
    
    public static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedCogwheel(int tier, Material material, BlockEntry<TieredCogwheelBlock> cogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block("andesite_encased_" + material.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.ANDESITE_CASING::get, cogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedCogwheel(cogwheel, () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(cogwheel))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                        Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
                .onRegister(c -> c.setTier(tier))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .register();
    }

    private static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedLargeCogwheel(int tier) {
        return registerAndesiteEncasedLargeCogwheel(tier, TM[tier], COGWHEELS[tier], LARGE_COGWHEELS[tier], SHAFT_HALF_MODELS[tier], LARGE_COGWHEEL_SHAFTLESS_MODELS[tier]);
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerAndesiteEncasedLargeCogwheel(int tier, Material material, BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredCogwheelBlock> largeCogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block("andesite_encased_large_" + material.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.ANDESITE_CASING::get, largeCogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedLargeCogwheel(cogwheel, () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(largeCogwheel))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    private static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedCogwheel(int tier) {
        return registerBrassEncasedCogwheel(tier, TM[tier], COGWHEELS[tier], SHAFT_HALF_MODELS[tier], COGWHEEL_SHAFTLESS_MODELS[tier]);
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedCogwheel(int tier, Material material, BlockEntry<TieredCogwheelBlock> cogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block("brass_encased_" + material.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.BRASS_CASING::get, cogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedCogwheel(cogwheel, () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(cogwheel))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                        Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
                .onRegister(c -> c.setTier(tier))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .register();
    }

    private static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedLargeCogwheel(int tier) {
        return registerBrassEncasedLargeCogwheel(tier, TM[tier], COGWHEELS[tier], LARGE_COGWHEELS[tier], SHAFT_HALF_MODELS[tier], LARGE_COGWHEEL_SHAFTLESS_MODELS[tier]);
    }

    public static BlockEntry<TieredEncasedCogwheelBlock> registerBrassEncasedLargeCogwheel(int tier, Material material, BlockEntry<TieredCogwheelBlock> cogwheel, BlockEntry<TieredCogwheelBlock> largeCogwheel, PartialModel halfShaftModel, PartialModel cogwheelShaftlessModel) {
        return REGISTRATE
                .block("brass_encased_large_" + material.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.BRASS_CASING::get, largeCogwheel::get, halfShaftModel, cogwheelShaftlessModel))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(tieredEncasedLargeCogwheel(cogwheel, () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(largeCogwheel))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
