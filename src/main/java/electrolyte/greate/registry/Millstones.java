package electrolyte.greate.registry;

import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
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
        GreateRegistries.REGISTRATE.creativeModeTab(Greate.GREATE_TAB);

        MILLSTONES[ULV] = ANDESITE_MILLSTONE = millstone(ULV, 0.5);
        MILLSTONES[LV] = STEEL_MILLSTONE = millstone(LV, 1.0);
        MILLSTONES[MV] = ALUMINIUM_MILLSTONE = millstone(MV, 1.5);
        MILLSTONES[HV] = STAINLESS_STEEL_MILLSTONE = millstone(HV, 2.0);
        MILLSTONES[EV] = TITANIUM_MILLSTONE = millstone(EV, 2.5);
        MILLSTONES[IV] = TUNGSTENSTEEL_MILLSTONE = millstone(IV, 3.0);
        MILLSTONES[LuV] = PALLADIUM_MILLSTONE = millstone(LuV, 3.5);
        MILLSTONES[ZPM] = NAQUADAH_MILLSTONE = millstone(ZPM, 4.0);
        MILLSTONES[UV] = DARMSTADTIUM_MILLSTONE = millstone(UV, 4.5);
        MILLSTONES[UHV] = NEUTRONIUM_MILLSTONE = millstone(UHV, 5.0);
    }

    public static BlockEntry<TieredMillstoneBlock> millstone(int tier, double millstoneImpact) {
        return GreateRegistries.REGISTRATE
                .block(TM[tier].getName() + "_millstone", TieredMillstoneBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(TagGen.pickaxeOnly())
                .transform(GreateBuilderTransformers.tieredMillstone())
                .transform(GStress.setImpact(millstoneImpact))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
