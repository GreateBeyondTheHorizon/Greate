package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class Millstones {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMillstoneBlock> MILLSTONES = new ArrayList<>();

    public static final BlockEntry<TieredMillstoneBlock> ANDESITE_MILLSTONE = REGISTRATE
            .block("andesite_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.ANDESITE_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(GreateBuilderTransformers.tieredMillstone())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.ULS.MILLSTONE_IMPACT))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> STEEL_MILLSTONE = REGISTRATE
            .block("steel_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.STEEL_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.LS.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> ALUMINIUM_MILLSTONE = REGISTRATE
            .block("aluminium_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.ALUMINIUM_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.MS.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> STAINLESS_STEEL_MILLSTONE = REGISTRATE
            .block("stainless_steel_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.STAINLESS_STEEL_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.HS.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> TITANIUM_MILLSTONE = REGISTRATE
            .block("titanium_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.TITANIUM_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.ES.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> TUNGSTEN_STEEL_MILLSTONE = REGISTRATE
            .block("tungsten_steel_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.TUNGSTEN_STEEL_MILLSTONE_INNER))
            .lang("Tungstensteel Millstone")
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.IS.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> PALLADIUM_MILLSTONE = REGISTRATE
            .block("palladium_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.PALLADIUM_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.LUS.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> NAQUADAH_MILLSTONE = REGISTRATE
            .block("naquadah_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.NAQUADAH_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.ZPM.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> DARMSTADTIUM_MILLSTONE = REGISTRATE
            .block("darmstadtium_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.DARMSTADTIUM_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.US.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final BlockEntry<TieredMillstoneBlock> NEUTRONIUM_MILLSTONE = REGISTRATE
            .block("neutronium_millstone", p -> new TieredMillstoneBlock(p, GreatePartialModels.NEUTRONIUM_MILLSTONE_INNER))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(TagGen.pickaxeOnly())
            .transform(BlockStressDefaults.setImpact(Greate.CONFIG.UHS.MILLSTONE_IMPACT))
            .transform(GreateBuilderTransformers.tieredMillstone())
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();

    public static void register() {}
}
