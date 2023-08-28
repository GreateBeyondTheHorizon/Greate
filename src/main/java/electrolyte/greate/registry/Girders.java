package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.ConnectedGirderModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.decoration.encasing.GirderEncasingRegistry;
import electrolyte.greate.content.decoration.girder.GreateGirderBlockStateGenerator;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static electrolyte.greate.Greate.REGISTRATE;

public class Girders {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_ANDESITE_SHAFT = REGISTRATE
            .block("metal_girder_encased_andesite_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.ANDESITE_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.ANDESITE_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.ANDESITE_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.ANDESITE_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_STEEL_SHAFT = REGISTRATE
            .block("metal_girder_encased_steel_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.STEEL_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.STEEL_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.STEEL_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.LOW))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.STEEL_SHAFT))
            .register();
    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_ALUMINIUM_SHAFT = REGISTRATE
            .block("metal_girder_encased_aluminium_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.ALUMINIUM_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.ALUMINIUM_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.ALUMINIUM_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.ALUMINIUM_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("metal_girder_encased_stainless_steel_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.STAINLESS_STEEL_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.STAINLESS_STEEL_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.STAINLESS_STEEL_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.STAINLESS_STEEL_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_TITANIUM_SHAFT = REGISTRATE
            .block("metal_girder_encased_titanium_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.TITANIUM_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.TITANIUM_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.TITANIUM_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.TITANIUM_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_TUNGSTEN_STEEL_SHAFT = REGISTRATE
            .block("metal_girder_encased_tungsten_steel_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.TUNGSTEN_STEEL_SHAFT::get))
            .lang("Metal Girder Encased Tungstensteel Shaft")
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.TUNGSTEN_STEEL_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.TUNGSTEN_STEEL_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.TUNGSTEN_STEEL_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_PALLADIUM_SHAFT = REGISTRATE
            .block("metal_girder_encased_palladium_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.PALLADIUM_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.PALLADIUM_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.PALLADIUM_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.PALLADIUM_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_NAQUADAH_SHAFT = REGISTRATE
            .block("metal_girder_encased_naquadah_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.NAQUADAH_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.NAQUADAH_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.NAQUADAH_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.NAQUADAH_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_DARMSTADTIUM_SHAFT = REGISTRATE
            .block("metal_girder_encased_darmstadtium_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.DARMSTADTIUM_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.DARMSTADTIUM_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.DARMSTADTIUM_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.DARMSTADTIUM_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> METAL_GIRDER_ENCASED_NEUTRONIUM_SHAFT = REGISTRATE
            .block("metal_girder_encased_neutronium_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.NEUTRONIUM_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.NEUTRONIUM_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.NEUTRONIUM_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.NEUTRONIUM_SHAFT))
            .register();

    public static void register() {}
}
