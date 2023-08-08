package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.world.level.material.MaterialColor;

import static electrolyte.greate.Greate.REGISTRATE;

public class Shafts {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredShaftBlock> ANDESITE_SHAFT = REGISTRATE
            .block("andesite_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> STEEL_SHAFT = REGISTRATE
            .block("steel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> ALUMINIUM_SHAFT = REGISTRATE
            .block("aluminium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> STAINLESS_STEEL_SHAFT = REGISTRATE
            .block("stainless_steel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> TITANIUM_SHAFT = REGISTRATE
            .block("titanium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> TUNGSTENSTEEL_SHAFT = REGISTRATE
            .block("tungstensteel_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> PALLADIUM_SHAFT = REGISTRATE
            .block("palladium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> NAQUADAH_SHAFT = REGISTRATE
            .block("naquadah_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> DARMSTADTIUM_SHAFT = REGISTRATE
            .block("darmstadtium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<TieredShaftBlock> NEUTRONIUM_SHAFT = REGISTRATE
            .block("neutronium_shaft", TieredShaftBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static void register() {}
}
