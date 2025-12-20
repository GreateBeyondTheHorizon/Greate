package electrolyte.greate.registry;

import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlock;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.infrastructure.config.GStress;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.GreateValues.TM;

public class EncasedFans {

    public static void register() {
        GreateRegistries.REGISTRATE.creativeModeTab(Greate.GREATE_TAB);

        FANS[ULV] = ANDESITE_ENCASED_FAN = fan(ULV, 0.5);
        FANS[LV] = STEEL_ENCASED_FAN = fan(LV, 1.0);
        FANS[MV] = ALUMINIUM_ENCASED_FAN = fan(MV, 1.5);
        FANS[HV] = STAINLESS_STEEL_ENCASED_FAN = fan(HV, 2.0);
        FANS[EV] = TITANIUM_ENCASED_FAN = fan(EV, 2.5);
        FANS[IV] = TUNGSTENSTEEL_ENCASED_FAN = fan(IV, 3.0);
        FANS[LuV] = PALLADIUM_ENCASED_FAN = fan(LuV, 3.5);
        FANS[ZPM] = NAQUADAH_ENCASED_FAN = fan(ZPM, 4.0);
        FANS[UV] = DARMSTADTIUM_ENCASED_FAN = fan(UV, 4.5);
        FANS[UHV] = NEUTRONIUM_ENCASED_FAN = fan(UHV, 5.6);
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

    public static BlockEntry<TieredEncasedFanBlock> fan(int tier, double stressImpact) {
        return GreateRegistries.REGISTRATE
                .block(TM[tier].getName() + "_encased_fan", TieredEncasedFanBlock::new)
                .initialProperties(SharedProperties::stone)
                .transform(GreateBuilderTransformers.tieredEncasedFan())
                .transform(TagGen.axeOrPickaxe())
                .transform(GStress.setImpact(stressImpact))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
