package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.material.material.Material;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.infrastructure.config.GStress;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.data.material.GTMaterials.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static electrolyte.greate.registry.GreatePartialModels.FAN_INNER_MODELS;
import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class EncasedFans {

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        FANS[ULV] = ANDESITE_ENCASED_FAN = fan(AndesiteAlloy, ULV, 0.5);
        FANS[LV] = STEEL_ENCASED_FAN = fan(Steel, LV, 1.0);
        FANS[MV] = ALUMINIUM_ENCASED_FAN = fan(Aluminium, MV, 1.5);
        FANS[HV] = STAINLESS_STEEL_ENCASED_FAN = fan(StainlessSteel, HV, 2.0);
        FANS[EV] = TITANIUM_ENCASED_FAN = fan(Titanium, EV, 2.5);
        FANS[IV] = TUNGSTENSTEEL_ENCASED_FAN = fan(TungstenSteel, IV, 3.0);
        FANS[LuV] = PALLADIUM_ENCASED_FAN = fan(Rhodium, LuV, 3.5);
        FANS[ZPM] = NAQUADAH_ENCASED_FAN = fan(Naquadah, ZPM, 4.0);
        FANS[UV] = DARMSTADTIUM_ENCASED_FAN = fan(Darmstadtium, UV, 4.5);
        FANS[UHV] = NEUTRONIUM_ENCASED_FAN = fan(Neutronium, UHV, 5.6);
    }

    public static final BlockEntry<TieredEncasedFanBlock>[] FANS = new BlockEntry[10];

    public static BlockEntry<TieredEncasedFanBlock>
            ANDESITE_ENCASED_FAN,
            STEEL_ENCASED_FAN,
            ALUMINIUM_ENCASED_FAN,
            STAINLESS_STEEL_ENCASED_FAN,
            TITANIUM_ENCASED_FAN,
            TUNGSTENSTEEL_ENCASED_FAN,
            PALLADIUM_ENCASED_FAN,
            NAQUADAH_ENCASED_FAN,
            DARMSTADTIUM_ENCASED_FAN,
            NEUTRONIUM_ENCASED_FAN;

    public static BlockEntry<TieredEncasedFanBlock> fan(Material mat, int tier, double stressImpact) {
        return REGISTRATE
                .block(mat.getName() + "_encased_fan", p -> new TieredEncasedFanBlock(p, FAN_INNER_MODELS[tier], SHAFT_HALF_MODELS[tier]))
                .initialProperties(SharedProperties::stone)
                .transform(GreateBuilderTransformers.tieredEncasedFan())
                .transform(TagGen.axeOrPickaxe())
                .transform(GStress.setImpact(stressImpact))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
