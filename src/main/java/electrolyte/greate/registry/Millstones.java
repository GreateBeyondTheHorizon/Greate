package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class Millstones {

    static {
        REGISTRATE.useCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMillstoneBlock> MILLSTONES = new ArrayList<>();

    public static final BlockEntry<TieredMillstoneBlock> ANDESITE_MILLSTONE = millstone("andesite_millstone", TIER.ULTRA_LOW, GreatePartialModels.ANDESITE_MILLSTONE_INNER, Greate.CONFIG.ULS.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> STEEL_MILLSTONE = millstone("steel_millstone", TIER.LOW, GreatePartialModels.STEEL_MILLSTONE_INNER, Greate.CONFIG.LS.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> ALUMINIUM_MILLSTONE = millstone("aluminium_millstone", TIER.MEDIUM, GreatePartialModels.ALUMINIUM_MILLSTONE_INNER, Greate.CONFIG.MS.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> STAINLESS_STEEL_MILLSTONE = millstone("stainless_steel_millstone", TIER.HIGH, GreatePartialModels.STAINLESS_STEEL_MILLSTONE_INNER, Greate.CONFIG.HS.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> TITANIUM_MILLSTONE = millstone("titanium_millstone", TIER.EXTREME, GreatePartialModels.TITANIUM_MILLSTONE_INNER, Greate.CONFIG.ES.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> TUNGSTENSTEEL_MILLSTONE = millstone("tungstensteel_millstone", TIER.INSANE, GreatePartialModels.TUNGSTENSTEEL_MILLSTONE_INNER, Greate.CONFIG.IS.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> PALLADIUM_MILLSTONE = millstone("palladium_millstone", TIER.LUDICRIOUS, GreatePartialModels.PALLADIUM_MILLSTONE_INNER, Greate.CONFIG.LUS.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> NAQUADAH_MILLSTONE = millstone("naquadah_millstone", TIER.ZPM, GreatePartialModels.NAQUADAH_MILLSTONE_INNER, Greate.CONFIG.ZPM.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> DARMSTADTIUM_MILLSTONE = millstone("darmstadtium_millstone", TIER.ULTIMATE, GreatePartialModels.DARMSTADTIUM_MILLSTONE_INNER, Greate.CONFIG.US.MILLSTONE_IMPACT);
    public static final BlockEntry<TieredMillstoneBlock> NEUTRONIUM_MILLSTONE = millstone("neutronium_millstone", TIER.ULTIMATE_HIGH, GreatePartialModels.NEUTRONIUM_MILLSTONE_INNER, Greate.CONFIG.UHS.MILLSTONE_IMPACT);

    public static BlockEntry<TieredMillstoneBlock> millstone(String name, TIER tier, PartialModel millstoneInnerModel, double millstoneImpact) {
        return REGISTRATE
                .block(name, p -> new TieredMillstoneBlock(p, millstoneInnerModel))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(TagGen.pickaxeOnly())
                .transform(GreateBuilderTransformers.tieredMillstone())
                .transform(BlockStressDefaults.setImpact(millstoneImpact))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
