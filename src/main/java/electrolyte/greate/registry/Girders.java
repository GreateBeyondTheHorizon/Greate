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

    public static final BlockEntry<TieredGirderEncasableBlock> ANDESITE_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("andesite_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.ANDESITE_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> STEEL_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("steel_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.STEEL_SHAFT::get))
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
    public static final BlockEntry<TieredGirderEncasableBlock> ALUMINIUM_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("aluminium_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.ALUMINIUM_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> STAINLESS_STEEL_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("stainless_steel_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.STAINLESS_STEEL_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> TITANIUM_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("titanium_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.TITANIUM_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> TUNGSTENSTEEL_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("tungstensteel_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.TUNGSTENSTEEL_SHAFT::get))
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(Shafts.TUNGSTENSTEEL_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Shafts.TUNGSTENSTEEL_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .transform(GirderEncasingRegistry.addVariantTo(Shafts.TUNGSTENSTEEL_SHAFT))
            .register();

    public static final BlockEntry<TieredGirderEncasableBlock> PALLADIUM_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("palladium_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.PALLADIUM_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> NAQUADAH_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("naquadah_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.NAQUADAH_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> DARMSTADTIUM_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("darmstadtium_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.DARMSTADTIUM_SHAFT::get))
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

    public static final BlockEntry<TieredGirderEncasableBlock> NEUTRONIUM_METAL_GIRDER_ENCASED_SHAFT = REGISTRATE
            .block("neutronium_metal_girder_encased_shaft", (p) -> new TieredGirderEncasableBlock(p, Shafts.NEUTRONIUM_SHAFT::get))
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
