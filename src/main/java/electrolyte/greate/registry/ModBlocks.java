package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.decoration.girder.ConnectedGirderModel;
import com.simibubi.create.content.decoration.girder.GirderBlockStateGenerator;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.blocks.andesite.AndesiteShaft;
import electrolyte.greate.blocks.andesite.BrassShaft;
import electrolyte.greate.blocks.andesite.GirderEncasedAndesiteShaft;
import electrolyte.greate.blocks.andesite.PoweredAndesiteShaft;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<AndesiteShaft> ANDESITE_SHAFT = REGISTRATE
            .block("andesite_shaft", AndesiteShaft::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();
    public static final BlockEntry<BrassShaft> BRASS_SHAFT = REGISTRATE
            .block("brass_shaft", BrassShaft::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<EncasedShaftBlock> ENCASED_BRASS_SHAFT = REGISTRATE
            .block("brass_encased_brass_shaft", p -> new EncasedShaftBlock(p, ModBlocks.BRASS_SHAFT::get))
            .properties(p -> p.color(MaterialColor.TERRACOTTA_BROWN))
            .transform(BuilderTransformers.encasedShaft("brass", () -> AllSpriteShifts.BRASS_CASING))
            .transform(EncasingRegistry.addVariantTo(ModBlocks.BRASS_SHAFT))
            .transform(TagGen.axeOrPickaxe())
            .register();

    public static final BlockEntry<GirderEncasedShaftBlock> GIRDER_ENCASED_BRASS_SHAFT = REGISTRATE
            .block("girder_brass_encased_brass_shaft", GirderEncasedShaftBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .blockstate(GirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(pickaxeOnly())
            .loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(BRASS_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(BRASS_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .register();

    public static final BlockEntry<GirderEncasedAndesiteShaft> GIRDER_ENCASED_ANDESITE_SHAFT = REGISTRATE
            .block("girder_encased_andesite_shaft", GirderEncasedAndesiteShaft::new)
            .initialProperties(SharedProperties::softMetal)
            //todo: custom datgen for shaft types
            //.blockstate(GirderBlockStateGenerator::blockStateWithShaft)
            .properties(p -> p.color(MaterialColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(b -> b.tag(BlockTags.MINEABLE_WITH_PICKAXE))
            .loot((loot, block) -> loot.add(block, RegistrateBlockLootTables.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                    .withPool(RegistrateBlockLootTables.applyExplosionCondition(ANDESITE_SHAFT.get(), LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ANDESITE_SHAFT.get()))))))
            .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
            .register();

    public static final BlockEntry<PoweredAndesiteShaft> POWERED_ANDESITE_SHAFT = REGISTRATE
            .block("powered_andesite_shaft", PoweredAndesiteShaft::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(b -> b.tag(BlockTags.MINEABLE_WITH_PICKAXE))
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .loot((loot, block) -> loot.dropOther(block, ModBlocks.ANDESITE_SHAFT.get()))
            .register();

    public static void register() {}
}
