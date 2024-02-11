package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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
import static electrolyte.greate.GreateValues.TM;

public class Millstones {

    public static final BlockEntry<TieredMillstoneBlock>[] MILLSTONES = new BlockEntry[10];
    public static BlockEntry<TieredMillstoneBlock>
            ANDESITE_MILLSTONE,
            STEEL_MILLSTONE,
            ALUMINIUM_MILLSTONE,
            STAINLESS_STEEL_MILLSTONE,
            TITANIUM_MILLSTONE,
            TUNGSTENSTEEL_MILLSTONE,
            PALLADIUM_MILLSTONE,
            NAQUADAH_MILLSTONE,
            DARMSTADTIUM_MILLSTONE,
            NEUTRONIUM_MILLSTONE;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        MILLSTONES[ULV] = ANDESITE_MILLSTONE = millstone(ULV, GreatePartialModels.ANDESITE_MILLSTONE_INNER, 0.5);
        MILLSTONES[LV] = STEEL_MILLSTONE = millstone(LV, GreatePartialModels.STEEL_MILLSTONE_INNER, 1.0);
        MILLSTONES[MV] = ALUMINIUM_MILLSTONE = millstone(MV, GreatePartialModels.ALUMINIUM_MILLSTONE_INNER, 1.5);
        MILLSTONES[HV] = STAINLESS_STEEL_MILLSTONE = millstone(HV, GreatePartialModels.STAINLESS_STEEL_MILLSTONE_INNER, 2.0);
        MILLSTONES[EV] = TITANIUM_MILLSTONE = millstone(EV, GreatePartialModels.TITANIUM_MILLSTONE_INNER, 2.5);
        MILLSTONES[IV] = TUNGSTENSTEEL_MILLSTONE = millstone(IV, GreatePartialModels.TUNGSTENSTEEL_MILLSTONE_INNER, 3.0);
        MILLSTONES[LuV] = PALLADIUM_MILLSTONE = millstone(LuV, GreatePartialModels.PALLADIUM_MILLSTONE_INNER, 3.5);
        MILLSTONES[ZPM] = NAQUADAH_MILLSTONE = millstone(ZPM, GreatePartialModels.NAQUADAH_MILLSTONE_INNER, 4.0);
        MILLSTONES[UV] = DARMSTADTIUM_MILLSTONE = millstone(UV, GreatePartialModels.DARMSTADTIUM_MILLSTONE_INNER, 4.5);
        MILLSTONES[UHV] = NEUTRONIUM_MILLSTONE = millstone(UHV, GreatePartialModels.NEUTRONIUM_MILLSTONE_INNER, 5.0);
    }

    private static BlockEntry<TieredMillstoneBlock> millstone(int tier, PartialModel millstoneInnerModel, double millstoneImpact) {
        return millstone(tier, TM[tier], millstoneInnerModel, millstoneImpact);
    }

    public static BlockEntry<TieredMillstoneBlock> millstone(int tier, Material material, PartialModel millstoneInnerModel, double millstoneImpact) {
        return REGISTRATE
                .block(material.getName() + "_millstone", p -> new TieredMillstoneBlock(p, millstoneInnerModel))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(TagGen.pickaxeOnly())
                .transform(GreateBuilderTransformers.tieredMillstone())
                .transform(BlockStressDefaults.setImpact(millstoneImpact))
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
