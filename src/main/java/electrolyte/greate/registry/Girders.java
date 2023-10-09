package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.ConnectedGirderModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.decoration.encasing.GirderEncasingRegistry;
import electrolyte.greate.content.decoration.girder.GreateGirderBlockStateGenerator;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasedShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static electrolyte.greate.Greate.REGISTRATE;

public class Girders {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static BlockEntry<TieredGirderEncasedShaftBlock> metalGirderEncasedShaft(String name, TIER tier, BlockEntry<TieredShaftBlock> shaft) {
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
                .register();
    }

    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_ANDESITE_SHAFT = metalGirderEncasedShaft("metal_girder_encased_andesite_shaft", TIER.ULTRA_LOW, Shafts.ANDESITE_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_STEEL_SHAFT = metalGirderEncasedShaft("metal_girder_encased_steel_shaft", TIER.LOW, Shafts.STEEL_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_ALUMINIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_aluminium_shaft", TIER.MEDIUM, Shafts.ALUMINIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_STAINLESS_STEEL_SHAFT = metalGirderEncasedShaft("metal_girder_encased_stainless_steel_shaft", TIER.HIGH, Shafts.STAINLESS_STEEL_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_TITANIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_titanium_shaft", TIER.EXTREME, Shafts.TITANIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_TUNGSTENSTEEL_SHAFT = metalGirderEncasedShaft("metal_girder_encased_tungstensteel_shaft", TIER.INSANE, Shafts.TUNGSTENSTEEL_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_PALLADIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_palladium_shaft", TIER.LUDICRIOUS, Shafts.PALLADIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_NAQUADAH_SHAFT = metalGirderEncasedShaft("metal_girder_encased_naquadah_shaft", TIER.ZPM, Shafts.NAQUADAH_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_DARMSTADTIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_darmstadtium_shaft", TIER.ULTIMATE, Shafts.DARMSTADTIUM_SHAFT);
    public static final BlockEntry<TieredGirderEncasedShaftBlock> METAL_GIRDER_ENCASED_NEUTRONIUM_SHAFT = metalGirderEncasedShaft("metal_girder_encased_neutronium_shaft", TIER.ULTIMATE_HIGH, Shafts.NEUTRONIUM_SHAFT);

    public static void register() {}
}
