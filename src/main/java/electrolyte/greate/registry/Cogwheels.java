package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.TieredCogwheelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;

import static electrolyte.greate.Greate.REGISTRATE;

public class Cogwheels {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredCogwheelBlock> ANDESITE_COGWHEEL = REGISTRATE
            .block("andesite_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_ANDESITE_COGWHEEL = REGISTRATE
            .block("large_andesite_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> STEEL_COGWHEEL = REGISTRATE
            .block("steel_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_STEEL_COGWHEEL = REGISTRATE
            .block("large_steel_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> ALUMINIUM_COGWHEEL = REGISTRATE
            .block("aluminium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_ALUMINIUM_COGWHEEL = REGISTRATE
            .block("large_aluminium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("stainless_steel_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_STAINLESS_STEEL_COGWHEEL = REGISTRATE
            .block("large_stainless_steel_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> TITANIUM_COGWHEEL = REGISTRATE
            .block("titanium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_TITANIUM_COGWHEEL = REGISTRATE
            .block("large_titanium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();


    public static final BlockEntry<TieredCogwheelBlock> TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("tungstensteel_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_TUNGSTENSTEEL_COGWHEEL = REGISTRATE
            .block("large_tungstensteel_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();


    public static final BlockEntry<TieredCogwheelBlock> PALLADIUM_COGWHEEL = REGISTRATE
            .block("palladium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_PALLADIUM_COGWHEEL = REGISTRATE
            .block("large_palladium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();


    public static final BlockEntry<TieredCogwheelBlock> NAQUADAH_COGWHEEL = REGISTRATE
            .block("naquadah_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_NAQUADAH_COGWHEEL = REGISTRATE
            .block("large_naquadah_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();


    public static final BlockEntry<TieredCogwheelBlock> DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("darmstadtium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_DARMSTADTIUM_COGWHEEL = REGISTRATE
            .block("large_darmstadtium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();


    public static final BlockEntry<TieredCogwheelBlock> NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("neutronium_cogwheel", TieredCogwheelBlock::small)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static final BlockEntry<TieredCogwheelBlock> LARGE_NEUTRONIUM_COGWHEEL = REGISTRATE
            .block("large_neutronium_cogwheel", TieredCogwheelBlock::large)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.sound(SoundType.WOOD))
            .properties(p -> p.color(MaterialColor.DIRT))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .item(CogwheelBlockItem::new).build()
            .register();

    public static void register() {}
}
