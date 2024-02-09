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

import java.util.ArrayList;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class Millstones {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMillstoneBlock> MILLSTONES = new ArrayList<>();

    public static final BlockEntry<TieredMillstoneBlock> ANDESITE_MILLSTONE = millstone("andesite_millstone", ULV, TM[0], GreatePartialModels.ANDESITE_MILLSTONE_INNER, 0.5);
    public static final BlockEntry<TieredMillstoneBlock> STEEL_MILLSTONE = millstone("steel_millstone", LV, TM[1], GreatePartialModels.STEEL_MILLSTONE_INNER, 1.0);
    public static final BlockEntry<TieredMillstoneBlock> ALUMINIUM_MILLSTONE = millstone("aluminium_millstone", MV, TM[2], GreatePartialModels.ALUMINIUM_MILLSTONE_INNER, 1.5);
    public static final BlockEntry<TieredMillstoneBlock> STAINLESS_STEEL_MILLSTONE = millstone("stainless_steel_millstone", HV, TM[3], GreatePartialModels.STAINLESS_STEEL_MILLSTONE_INNER, 2.0);
    public static final BlockEntry<TieredMillstoneBlock> TITANIUM_MILLSTONE = millstone("titanium_millstone", EV, TM[4], GreatePartialModels.TITANIUM_MILLSTONE_INNER, 2.5);
    public static final BlockEntry<TieredMillstoneBlock> TUNGSTENSTEEL_MILLSTONE = millstone("tungstensteel_millstone", IV, TM[5], GreatePartialModels.TUNGSTENSTEEL_MILLSTONE_INNER, 3.0);
    public static final BlockEntry<TieredMillstoneBlock> PALLADIUM_MILLSTONE = millstone("palladium_millstone", LuV, TM[6], GreatePartialModels.PALLADIUM_MILLSTONE_INNER, 3.5);
    public static final BlockEntry<TieredMillstoneBlock> NAQUADAH_MILLSTONE = millstone("naquadah_millstone", ZPM, TM[7], GreatePartialModels.NAQUADAH_MILLSTONE_INNER, 4.0);
    public static final BlockEntry<TieredMillstoneBlock> DARMSTADTIUM_MILLSTONE = millstone("darmstadtium_millstone", UV, TM[8], GreatePartialModels.DARMSTADTIUM_MILLSTONE_INNER, 4.5);
    public static final BlockEntry<TieredMillstoneBlock> NEUTRONIUM_MILLSTONE = millstone("neutronium_millstone", UHV, TM[9], GreatePartialModels.NEUTRONIUM_MILLSTONE_INNER, 5.0);

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
