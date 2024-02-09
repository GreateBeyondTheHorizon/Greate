package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;

import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TMS;

public class Millstones {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredMillstoneBlock>[] MILLSTONES = new BlockEntry[10];

    public static BlockEntry<TieredMillstoneBlock> ANDESITE_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> STEEL_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> ALUMINIUM_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> STAINLESS_STEEL_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> TITANIUM_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> TUNGSTENSTEEL_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> PALLADIUM_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> NAQUADAH_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> DARMSTADTIUM_MILLSTONE;
    public static BlockEntry<TieredMillstoneBlock> NEUTRONIUM_MILLSTONE;

    static {
        MILLSTONES[0] = ANDESITE_MILLSTONE = millstone("andesite_millstone", ULV, TMS[0], GreatePartialModels.ANDESITE_MILLSTONE_INNER, 0.5);
        MILLSTONES[1] = STEEL_MILLSTONE = millstone("steel_millstone", LV, TMS[1], GreatePartialModels.STEEL_MILLSTONE_INNER, 1.0);
        MILLSTONES[2] = ALUMINIUM_MILLSTONE = millstone("aluminium_millstone", MV, TMS[2], GreatePartialModels.ALUMINIUM_MILLSTONE_INNER, 1.5);
        MILLSTONES[3] = STAINLESS_STEEL_MILLSTONE = millstone("stainless_steel_millstone", HV, TMS[3], GreatePartialModels.STAINLESS_STEEL_MILLSTONE_INNER, 2.0);
        MILLSTONES[4] = TITANIUM_MILLSTONE = millstone("titanium_millstone", EV, TMS[4], GreatePartialModels.TITANIUM_MILLSTONE_INNER, 2.5);
        MILLSTONES[5] = TUNGSTENSTEEL_MILLSTONE = millstone("tungstensteel_millstone", IV, TMS[5], GreatePartialModels.TUNGSTENSTEEL_MILLSTONE_INNER, 3.0);
        MILLSTONES[6] = PALLADIUM_MILLSTONE = millstone("palladium_millstone", LuV, TMS[6], GreatePartialModels.PALLADIUM_MILLSTONE_INNER, 3.5);
        MILLSTONES[7] = NAQUADAH_MILLSTONE = millstone("naquadah_millstone", ZPM, TMS[7], GreatePartialModels.NAQUADAH_MILLSTONE_INNER, 4.0);
        MILLSTONES[8] = DARMSTADTIUM_MILLSTONE = millstone("darmstadtium_millstone", UV, TMS[8], GreatePartialModels.DARMSTADTIUM_MILLSTONE_INNER, 4.5);
        MILLSTONES[9] = NEUTRONIUM_MILLSTONE = millstone("neutronium_millstone", UHV, TMS[9], GreatePartialModels.NEUTRONIUM_MILLSTONE_INNER, 5.0);
    }

    public static BlockEntry<TieredMillstoneBlock> millstone(String name, int tier, String materialType, PartialModel millstoneInnerModel, double millstoneImpact) {
        return REGISTRATE
                .block(name, p -> new TieredMillstoneBlock(p, millstoneInnerModel))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(TagGen.pickaxeOnly())
                .transform(GreateBuilderTransformers.tieredMillstone())
                .transform(BlockStressDefaults.setImpact(millstoneImpact))
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
