package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogwheelBlock;
import electrolyte.greate.foundation.client.models.CogwheelModel;
import electrolyte.greate.infrastructure.config.GStress;
import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.Objects;

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
        GreateRegistries.REGISTRATE.creativeModeTab(Greate.GREATE_TAB);
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
            var cogwheelEntry = GreateRegistries.REGISTRATE
                    .block(mat.getName() + "_cogwheel", p -> TieredCogwheelBlock.small(p, mat))
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.DIRT).noLootTable())
                    .transform(GStress.setNoImpact())
                    .transform(GTBlocks.unificationBlock(cogwheel, mat))
                    .blockstate(NonNullBiConsumer.noop())
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(CogwheelModel::create)
                    .item(CogwheelBlockItem::new)
                    .model(NonNullBiConsumer.noop()).build()
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
            var cogwheelEntry = GreateRegistries.REGISTRATE
                    .block("large_" + mat.getName() + "_cogwheel", p -> TieredCogwheelBlock.large(p, mat))
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.DIRT).noLootTable())
                    .transform(GStress.setNoImpact())
                    .transform(GTBlocks.unificationBlock(largeCogwheel, mat))
                    .blockstate(NonNullBiConsumer.noop())
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(CogwheelModel::create)
                    .item(CogwheelBlockItem::new)
                    .model(NonNullBiConsumer.noop()).build()
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
            var cogwheelEntry = GreateRegistries.REGISTRATE
                    .block("andesite_encased_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.ANDESITE_CASING::get, mat))
                    .properties(p -> p.mapColor(MapColor.PODZOL).noLootTable())
                    .transform(tieredEncasedCogwheel(() -> AllSpriteShifts.ANDESITE_CASING))
                    .transform(EncasingRegistry.addVariantTo(Objects.requireNonNull(COGWHEELS.get(cogwheel, mat))))
                    .transform(GTBlocks.unificationBlock(andesiteEncasedCogwheel, mat))
                    .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.ANDESITE_CASING,
                            Couple.create(AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.ANDESITE_ENCASED_COGWHEEL_OTHERSIDE))))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(CogwheelModel::create)
                    .register();
            ANDESITE_ENCASED_COGWHEELS_BUILDER.put(andesiteEncasedCogwheel, mat, cogwheelEntry);

            var largeCogwheelEntry = GreateRegistries.REGISTRATE
                     .block("andesite_encased_large_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.ANDESITE_CASING::get, mat))
                     .properties(p -> p.mapColor(MapColor.PODZOL).noLootTable())
                     .transform(tieredEncasedLargeCogwheel(() -> AllSpriteShifts.ANDESITE_CASING))
                     .transform(EncasingRegistry.addVariantTo(Objects.requireNonNull(LARGE_COGWHEELS.get(largeCogwheel, mat))))
                     .transform(GTBlocks.unificationBlock(andesiteEncasedLargeCogwheel, mat))
                     .onRegister(c -> c.setTier(tier))
                     .onRegister(CogwheelModel::create)
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
            var cogwheelEntry = GreateRegistries.REGISTRATE
                    .block("brass_encased_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.small(p, AllBlocks.BRASS_CASING::get, mat))
                    .properties(p -> p.mapColor(MapColor.PODZOL).noLootTable())
                    .transform(tieredEncasedCogwheel(() -> AllSpriteShifts.BRASS_CASING))
                    .transform(EncasingRegistry.addVariantTo(Objects.requireNonNull(COGWHEELS.get(cogwheel, mat))))
                    .transform(GTBlocks.unificationBlock(brassEncasedCogwheel, mat))
                    .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(AllSpriteShifts.BRASS_CASING,
                            Couple.create(AllSpriteShifts.BRASS_ENCASED_COGWHEEL_SIDE, AllSpriteShifts.BRASS_ENCASED_COGWHEEL_OTHERSIDE))))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(CogwheelModel::create)
                    .register();
            BRASS_ENCASED_COGWHEELS_BUILDER.put(brassEncasedCogwheel, mat, cogwheelEntry);

            var largeCogwheelEntry = GreateRegistries.REGISTRATE
                     .block("brass_encased_large_" + mat.getName() + "_cogwheel", p -> TieredEncasedCogwheelBlock.large(p, AllBlocks.BRASS_CASING::get, mat))
                     .properties(p -> p.mapColor(MapColor.PODZOL).noLootTable())
                     .transform(tieredEncasedLargeCogwheel(() -> AllSpriteShifts.BRASS_CASING))
                     .transform(EncasingRegistry.addVariantTo(Objects.requireNonNull(LARGE_COGWHEELS.get(largeCogwheel, mat))))
                     .transform(GTBlocks.unificationBlock(brassEncasedLargeCogwheel, mat))
                     .onRegister(c -> c.setTier(tier))
                     .onRegister(CogwheelModel::create)
                     .register();
             BRASS_ENCASED_LARGE_COGWHEELS_BUILDER.put(brassEncasedLargeCogwheel, mat, largeCogwheelEntry);
        }
        BRASS_ENCASED_COGWHEELS = BRASS_ENCASED_COGWHEELS_BUILDER.build();
        BRASS_ENCASED_LARGE_COGWHEELS = BRASS_ENCASED_LARGE_COGWHEELS_BUILDER.build();
    }
}
