package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.gregtechceu.gtceu.data.block.GTBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.material.MapColor;

import java.util.Objects;

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.registry.GreateTagPrefixes.*;

public class Shafts {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredShaftBlock>> SHAFTS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredPoweredShaftBlock>> POWERED_SHAFTS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredEncasedShaftBlock>> ANDESITE_ENCASED_SHAFTS_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredEncasedShaftBlock>> BRASS_ENCASED_SHAFTS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredShaftBlock>> NEW_SHAFTS;
    public static Table<TagPrefix, Material, BlockEntry<TieredPoweredShaftBlock>> POWERED_SHAFTS;
    public static Table<TagPrefix, Material, BlockEntry<TieredEncasedShaftBlock>> ANDESITE_ENCASED_SHAFTS;
    public static Table<TagPrefix, Material, BlockEntry<TieredEncasedShaftBlock>> BRASS_ENCASED_SHAFTS;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
        generateShafts();
        generatePoweredShafts();
        generateAndesiteEncasedShafts();
        generateBrassEncasedShafts();
    }

    public static void generateShafts() {
        for(Material mat : GTCEuAPI.materialManager) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var shaftEntry = REGISTRATE
                    .block(mat.getName() + "_shaft", p -> new TieredShaftBlock(p, mat))
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.mapColor(MapColor.METAL))
                    .transform(GStress.setNoImpact())
                    .transform(TagGen.pickaxeOnly())
                    .transform(GTBlocks.unificationBlock(shaft, mat))
                    .blockstate(GreateBlockStateGen.tieredShaftProvider())
                    .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .simpleItem()
                    .item().build()
                    .register();
            SHAFTS_BUILDER.put(shaft, mat, shaftEntry);
        }
        NEW_SHAFTS = SHAFTS_BUILDER.build();
    }

    public static void generatePoweredShafts() {
        for(Material mat : GTCEuAPI.materialManager) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var shaftEntry = REGISTRATE
                    .block("powered_" + mat.getName() + "_shaft", p -> new TieredPoweredShaftBlock(p, mat))
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.mapColor(MapColor.METAL))
                    .transform(TagGen.pickaxeOnly())
                    .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
                    .loot((l, b) -> l.dropOther(b, Objects.requireNonNull(NEW_SHAFTS.get(shaft, mat))))
                    .onRegister(c -> c.setTier(tier))
                    .register();
            POWERED_SHAFTS_BUILDER.put(poweredShaft, mat, shaftEntry);
        }
        POWERED_SHAFTS = POWERED_SHAFTS_BUILDER.build();
    }

    public static void generateAndesiteEncasedShafts() {
        for(Material mat : GTCEuAPI.materialManager) {
             if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
             KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
             int tier = prop.getTier();
             BlockEntry<TieredShaftBlock> shaftEntry = Objects.requireNonNull(NEW_SHAFTS.get(shaft, mat));
             var encasedShaftEntry = REGISTRATE
                     .block("andesite_encased_" + mat.getName() + "_shaft", p -> new TieredEncasedShaftBlock(p, mat, AllBlocks.ANDESITE_CASING::get))
                     .properties(p -> p.mapColor(MapColor.PODZOL))
                     .transform(GreateBuilderTransformers.tieredEncasedShaft(shaftEntry, () -> AllSpriteShifts.ANDESITE_CASING))
                     .transform(EncasingRegistry.addVariantTo(shaftEntry))
                     .transform(TagGen.axeOrPickaxe())
                     .onRegister(c -> c.setTier(tier))
                     .register();
             ANDESITE_ENCASED_SHAFTS_BUILDER.put(andesiteEncasedShaft, mat, encasedShaftEntry);
        }
        ANDESITE_ENCASED_SHAFTS = ANDESITE_ENCASED_SHAFTS_BUILDER.build();
    }

    public static void generateBrassEncasedShafts() {
        for(Material mat : GTCEuAPI.materialManager) {
             if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
             KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
             int tier = prop.getTier();
             BlockEntry<TieredShaftBlock> shaftEntry = Objects.requireNonNull(NEW_SHAFTS.get(shaft, mat));
             var encasedShaftEntry = REGISTRATE
                     .block("brass_encased_" + mat.getName() + "_shaft", p -> new TieredEncasedShaftBlock(p, mat, AllBlocks.BRASS_CASING::get))
                     .properties(p -> p.mapColor(MapColor.PODZOL))
                     .transform(GreateBuilderTransformers.tieredEncasedShaft(shaftEntry, () -> AllSpriteShifts.BRASS_CASING))
                     .transform(EncasingRegistry.addVariantTo(shaftEntry))
                     .transform(TagGen.axeOrPickaxe())
                     .onRegister(c -> c.setTier(tier))
                     .register();
             BRASS_ENCASED_SHAFTS_BUILDER.put(brassEncasedShaft, mat, encasedShaftEntry);
        }
        BRASS_ENCASED_SHAFTS = BRASS_ENCASED_SHAFTS_BUILDER.build();
    }
}
