package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
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
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.infrastructure.config.GStress;
import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedCogwheel;
import static electrolyte.greate.foundation.data.GreateBuilderTransformers.tieredEncasedLargeCogwheel;
import static electrolyte.greate.registry.GreateTagPrefixes.*;

public class Cogwheels {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredCogwheelBlock>> COGWHEELS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> ANDESITE_ENCASED_COGWHEELS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> BRASS_ENCASED_COGWHEELS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredCogwheelBlock>> LARGE_COGWHEELS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> ANDESITE_ENCASED_LARGE_COGWHEELS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> BRASS_ENCASED_LARGE_COGWHEELS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredCogwheelBlock>> COGWHEELS;
    public static Table<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> ANDESITE_ENCASED_COGWHEELS;
    public static Table<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> BRASS_ENCASED_COGWHEELS;
    public static Table<TagPrefix, Material, BlockEntry<TieredCogwheelBlock>> LARGE_COGWHEELS;
    public static Table<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> ANDESITE_ENCASED_LARGE_COGWHEELS;
    public static Table<TagPrefix, Material, BlockEntry<TieredEncasedCogwheelBlock>> BRASS_ENCASED_LARGE_COGWHEELS;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
        generateCogwheels();
        generateLargeCogwheels();
        generateAndesiteEncasedCogwheels();
        generateBrassEncasedCogwheels();
    }

    public static void generateCogwheels() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            if(!mat.hasProperty(GreatePropertyKeys.COGWHEEL)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var cogwheelEntry = REGISTRATE
                    .block(mat.getName() + "_cogwheel", TieredCogwheelBlock::small)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.sound(SoundType.WOOD))
                    .properties(p -> p.mapColor(MapColor.DIRT))
                    .transform(GStress.setNoImpact())
                    .transform(TagGen.axeOrPickaxe())
                    .blockstate(GreateBlockStateGen.tieredCogwheelProvider(false))
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .item(CogwheelBlockItem::new).build()
                    .register();
            COGWHEELS_BUILDER.put(cogwheel, mat, cogwheelEntry);
        }
        COGWHEELS = COGWHEELS_BUILDER.build();
    }
    
    public static void generateLargeCogwheels() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            if(!mat.hasProperty(GreatePropertyKeys.COGWHEEL)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var cogwheelEntry = REGISTRATE
                    .block("large_" + mat.getName() + "_cogwheel", TieredCogwheelBlock::large)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.sound(SoundType.WOOD))
                    .properties(p -> p.mapColor(MapColor.DIRT))
                    .transform(GStress.setNoImpact())
                    .transform(TagGen.axeOrPickaxe())
                    .blockstate(GreateBlockStateGen.tieredCogwheelProvider(true))
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .item(CogwheelBlockItem::new).build()
                    .register();
            LARGE_COGWHEELS_BUILDER.put(largeCogwheel, mat, cogwheelEntry);
        }
        LARGE_COGWHEELS = LARGE_COGWHEELS_BUILDER.build();
    }
    
    public static void generateAndesiteEncasedCogwheels() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            if(!mat.hasProperty(GreatePropertyKeys.COGWHEEL)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            Supplier<TieredCogwheelBlock> cogwheelSupplier = () -> (TieredCogwheelBlock) ChemicalHelper.getBlock(cogwheel, mat);
            var cogwheelEntry = REGISTRATE
                    .block("andesite_encased_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.ANDESITE_CASING::get, mat))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(tieredEncasedCogwheel(cogwheelSupplier, () -> AllSpriteShifts.ANDESITE_CASING))
                    .transform(EncasingRegistry.addVariantTo(cogwheelSupplier))
                    .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                            Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
                    .onRegister(c -> c.setTier(tier))
                    .transform(TagGen.axeOrPickaxe())
                    .register();
            ANDESITE_ENCASED_COGWHEELS_BUILDER.put(andesiteEncasedCogwheel, mat, cogwheelEntry);

            Supplier<TieredCogwheelBlock> largeCogwheelSupplier = () -> (TieredCogwheelBlock) ChemicalHelper.getBlock(largeCogwheel, mat);
             var largeCogwheelEntry = REGISTRATE
                     .block("andesite_encased_large_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.ANDESITE_CASING::get, mat))
                     .properties(p -> p.mapColor(MapColor.PODZOL))
                     .transform(tieredEncasedLargeCogwheel(largeCogwheelSupplier, () -> AllSpriteShifts.ANDESITE_CASING))
                     .transform(EncasingRegistry.addVariantTo(largeCogwheelSupplier))
                     .transform(TagGen.axeOrPickaxe())
                     .onRegister(c -> c.setTier(tier))
                     .register();
             ANDESITE_ENCASED_LARGE_COGWHEELS_BUILDER.put(andesiteEncasedLargeCogwheel, mat, largeCogwheelEntry);
        }
        ANDESITE_ENCASED_COGWHEELS = ANDESITE_ENCASED_COGWHEELS_BUILDER.build();
        ANDESITE_ENCASED_LARGE_COGWHEELS = ANDESITE_ENCASED_LARGE_COGWHEELS_BUILDER.build();
    }

    public static void generateBrassEncasedCogwheels() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            if(!mat.hasProperty(GreatePropertyKeys.COGWHEEL)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            Supplier<TieredCogwheelBlock> cogwheelSupplier = () -> (TieredCogwheelBlock) ChemicalHelper.getBlock(cogwheel, mat);
            var cogwheelEntry = REGISTRATE
                    .block("brass_encased_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.BRASS_CASING::get, mat))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(tieredEncasedCogwheel(cogwheelSupplier, () -> AllSpriteShifts.BRASS_CASING))
                    .transform(EncasingRegistry.addVariantTo(cogwheelSupplier))
                    .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                            Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
                    .onRegister(c -> c.setTier(tier))
                    .transform(TagGen.axeOrPickaxe())
                    .register();
            BRASS_ENCASED_COGWHEELS_BUILDER.put(brassEncasedCogwheel, mat, cogwheelEntry);

            Supplier<TieredCogwheelBlock> largeCogwheelSupplier = () -> (TieredCogwheelBlock) ChemicalHelper.getBlock(largeCogwheel, mat);
             var largeCogwheelEntry = REGISTRATE
                     .block("brass_encased_large_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.BRASS_CASING::get, mat))
                     .properties(p -> p.mapColor(MapColor.PODZOL))
                     .transform(tieredEncasedLargeCogwheel(largeCogwheelSupplier, () -> AllSpriteShifts.BRASS_CASING))
                     .transform(EncasingRegistry.addVariantTo(largeCogwheelSupplier))
                     .transform(TagGen.axeOrPickaxe())
                     .onRegister(c -> c.setTier(tier))
                     .register();
             BRASS_ENCASED_LARGE_COGWHEELS_BUILDER.put(brassEncasedLargeCogwheel, mat, largeCogwheelEntry);
        }
        BRASS_ENCASED_COGWHEELS = BRASS_ENCASED_COGWHEELS_BUILDER.build();
        BRASS_ENCASED_LARGE_COGWHEELS = BRASS_ENCASED_LARGE_COGWHEELS_BUILDER.build();
    }
}
