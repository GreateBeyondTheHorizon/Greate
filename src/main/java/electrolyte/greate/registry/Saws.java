package electrolyte.greate.registry;

import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.saw.TieredSawBlock;
import electrolyte.greate.content.kinetics.saw.TieredSawGenerator;
import electrolyte.greate.content.kinetics.saw.TieredSawMovementBehaviour;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Shafts.SHAFTS;

public class Saws {

    public static final BlockEntry<TieredSawBlock>[] SAWS = new BlockEntry[10];
    public static BlockEntry<TieredSawBlock>
            ANDESITE_SAW,
            STEEL_SAW,
            ALUMINIUM_SAW,
            STAINLESS_STEEL_SAW,
            TITANIUM_SAW,
            TUNGSTENSTEEL_SAW,
            PALLADIUM_SAW,
            NAQUADAH_SAW,
            DARMSTADTIUM_SAW,
            NEUTRONIUM_SAW;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        SAWS[ULV] = ANDESITE_SAW = saw(ULV, 1.0);
        SAWS[LV] = STEEL_SAW = saw(LV, 2.0);
        SAWS[MV] = ALUMINIUM_SAW = saw(MV, 3.0);
        SAWS[HV] = STAINLESS_STEEL_SAW = saw(HV, 4.0);
        SAWS[EV] = TITANIUM_SAW = saw(EV, 5.0);
        SAWS[IV] = TUNGSTENSTEEL_SAW = saw(IV, 6.0);
        SAWS[LuV] = PALLADIUM_SAW = saw(LuV, 7.0);
        SAWS[ZPM] = NAQUADAH_SAW = saw(ZPM, 8.0);
        SAWS[UV] = DARMSTADTIUM_SAW = saw(UV, 9.0);
        SAWS[UHV] = NEUTRONIUM_SAW = saw(UHV, 10.0);
    }

    public static BlockEntry<TieredSawBlock> saw(int tier, double stressImpact) {
        return REGISTRATE
                .block(TM[tier].getName() + "_mechanical_saw", p -> new TieredSawBlock(p, SHAFTS[tier].get()))
                .initialProperties(SharedProperties::stone)
                .addLayer(() -> RenderType::cutoutMipped)
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(TagGen.axeOrPickaxe())
                .blockstate(new TieredSawGenerator()::generateModel)
                .transform(GStress.setImpact(stressImpact))
                .transform(TieredBlockMaterials.setMaterialForBlock(TM[tier]))
                .onRegister(movementBehaviour(new TieredSawMovementBehaviour()))
                .onRegister(c -> c.setTier(tier))
                .addLayer(() -> RenderType::cutoutMipped)
                .item()
                .model(GreateBuilderTransformers::tieredSaw)
                .tag(AllItemTags.CONTRAPTION_CONTROLLED.tag).build()
                .register();
    }
}
