package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.MATERIAL_TYPE;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class Millstones {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static ArrayList<TieredMillstoneBlock> MILLSTONES = new ArrayList<>();

    public static final BlockEntry<TieredMillstoneBlock> ANDESITE_MILLSTONE = millstone("andesite_millstone", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.ANDESITE_MILLSTONE_INNER, 0.5);
    public static final BlockEntry<TieredMillstoneBlock> STEEL_MILLSTONE = millstone("steel_millstone", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.STEEL_MILLSTONE_INNER, 1.0);
    public static final BlockEntry<TieredMillstoneBlock> ALUMINIUM_MILLSTONE = millstone("aluminium_millstone", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.ALUMINIUM_MILLSTONE_INNER, 1.5);
    public static final BlockEntry<TieredMillstoneBlock> STAINLESS_STEEL_MILLSTONE = millstone("stainless_steel_millstone", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.STAINLESS_STEEL_MILLSTONE_INNER, 2.0);
    public static final BlockEntry<TieredMillstoneBlock> TITANIUM_MILLSTONE = millstone("titanium_millstone", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.TITANIUM_MILLSTONE_INNER, 2.5);
    public static final BlockEntry<TieredMillstoneBlock> TUNGSTENSTEEL_MILLSTONE = millstone("tungstensteel_millstone", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.TUNGSTENSTEEL_MILLSTONE_INNER, 3.0);
    public static final BlockEntry<TieredMillstoneBlock> PALLADIUM_MILLSTONE = millstone("palladium_millstone", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.PALLADIUM_MILLSTONE_INNER, 3.5);
    public static final BlockEntry<TieredMillstoneBlock> NAQUADAH_MILLSTONE = millstone("naquadah_millstone", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.NAQUADAH_MILLSTONE_INNER, 4.0);
    public static final BlockEntry<TieredMillstoneBlock> DARMSTADTIUM_MILLSTONE = millstone("darmstadtium_millstone", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.DARMSTADTIUM_MILLSTONE_INNER, 4.5);
    public static final BlockEntry<TieredMillstoneBlock> NEUTRONIUM_MILLSTONE = millstone("neutronium_millstone", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.NEUTRONIUM_MILLSTONE_INNER, 5.0);

    public static BlockEntry<TieredMillstoneBlock> millstone(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel millstoneInnerModel, double millstoneImpact) {
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
