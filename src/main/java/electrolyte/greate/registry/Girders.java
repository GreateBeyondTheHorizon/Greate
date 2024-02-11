package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.ConnectedGirderModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;

import electrolyte.greate.content.decoration.encasing.GirderEncasingRegistry;
import electrolyte.greate.content.decoration.girder.GreateGirderBlockStateGenerator;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasedShaftBlock;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class Girders {

    public static BlockEntry<TieredGirderEncasedShaftBlock>
            METAL_GIRDER_ENCASED_ANDESITE_SHAFT,
            METAL_GIRDER_ENCASED_STEEL_SHAFT,
            METAL_GIRDER_ENCASED_ALUMINIUM_SHAFT,
            METAL_GIRDER_ENCASED_STAINLESS_STEEL_SHAFT,
            METAL_GIRDER_ENCASED_TITANIUM_SHAFT,
            METAL_GIRDER_ENCASED_TUNGSTENSTEEL_SHAFT,
            METAL_GIRDER_ENCASED_PALLADIUM_SHAFT,
            METAL_GIRDER_ENCASED_NAQUADAH_SHAFT,
            METAL_GIRDER_ENCASED_DARMSTADTIUM_SHAFT,
            METAL_GIRDER_ENCASED_NEUTRONIUM_SHAFT;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        METAL_GIRDER_ENCASED_ANDESITE_SHAFT = metalGirderEncasedShaft(ULV);
        METAL_GIRDER_ENCASED_STEEL_SHAFT = metalGirderEncasedShaft(LV);
        METAL_GIRDER_ENCASED_ALUMINIUM_SHAFT = metalGirderEncasedShaft(MV);
        METAL_GIRDER_ENCASED_STAINLESS_STEEL_SHAFT = metalGirderEncasedShaft(HV);
        METAL_GIRDER_ENCASED_TITANIUM_SHAFT = metalGirderEncasedShaft(EV);
        METAL_GIRDER_ENCASED_TUNGSTENSTEEL_SHAFT = metalGirderEncasedShaft(IV);
        METAL_GIRDER_ENCASED_PALLADIUM_SHAFT = metalGirderEncasedShaft(LuV);
        METAL_GIRDER_ENCASED_NAQUADAH_SHAFT = metalGirderEncasedShaft(ZPM);
        METAL_GIRDER_ENCASED_DARMSTADTIUM_SHAFT = metalGirderEncasedShaft(UV);
        METAL_GIRDER_ENCASED_NEUTRONIUM_SHAFT = metalGirderEncasedShaft(UHV);
    }

    private static BlockEntry<TieredGirderEncasedShaftBlock> metalGirderEncasedShaft(int tier) {
        return metalGirderEncasedShaft(tier, TM[tier], SHAFTS[tier]);
    }

    public static BlockEntry<TieredGirderEncasedShaftBlock> metalGirderEncasedShaft(int tier, Material material, BlockEntry<TieredShaftBlock> shaft) {
        return REGISTRATE
                .block("metal_girder_encased_" + material.getName() + "_shaft", p -> new TieredGirderEncasedShaftBlock(p, shaft::get))
                .initialProperties(SharedProperties::softMetal)
                .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                .transform(TagGen.pickaxeOnly())
                .loot((p, b) -> p.add(b, p.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                        .withPool(p.applyExplosionCondition(shaft.get(), LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(shaft.get()))))))
                .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
                .onRegister(c -> c.setTier(tier))
                .transform(GirderEncasingRegistry.addVariantTo(shaft))
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .register();
    }
}
