package electrolyte.greate.registry;

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

public class Girders {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_ANDESITE_SHAFT = metalGirderEncasedShaft("metal_girder_encased_andesite_shaft", ULV, TM[0], Shafts.ANDESITE_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_STEEL_SHAFT = metalGirderEncasedShaft("metal_girder_encased_steel_shaft", LV, TM[1], Shafts.STEEL_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_ALUMINIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_aluminium_shaft", MV, TM[2], Shafts.ALUMINIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_STAINLESS_STEEL_SHAFT = metalGirderEncasedShaft("metal_girder_encased_stainless_steel_shaft", HV, TM[3], Shafts.STAINLESS_STEEL_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_TITANIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_titanium_shaft", EV, TM[4], Shafts.TITANIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_TUNGSTENSTEEL_SHAFT = metalGirderEncasedShaft("metal_girder_encased_tungstensteel_shaft", IV, TM[5], Shafts.TUNGSTENSTEEL_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_PALLADIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_palladium_shaft", LuV, TM[6], Shafts.PALLADIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_NAQUADAH_SHAFT = metalGirderEncasedShaft("metal_girder_encased_naquadah_shaft", ZPM, TM[7], Shafts.NAQUADAH_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_DARMSTADTIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_darmstadtium_shaft", UV, TM[8], Shafts.DARMSTADTIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_NEUTRONIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_neutronium_shaft", UHV, TM[9], Shafts.NEUTRONIUM_SHAFT);

    public static BlockEntry<TieredGirderEncasedShaftBlock> metalGirderEncasedShaft(String name, int tier, String materialType, BlockEntry<TieredShaftBlock> shaft) {
        return REGISTRATE
                .block(name, p -> new TieredGirderEncasedShaftBlock(p, shaft::get))
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
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .register();
    }

    public static void register() {}
}
