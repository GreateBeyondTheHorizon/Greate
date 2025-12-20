package electrolyte.greate.infrastructure.config;

import static com.gregtechceu.gtceu.api.GTValues.*;


public class GConfigUtility {

    public static double getPumpPressureFromTier(int tier) {
        return switch (tier) {
            case ULV -> GreateConfigs.server().kinetics.pumpValues.andesitePressure.get();
            case LV -> GreateConfigs.server().kinetics.pumpValues.steelPressure.get();
            case MV -> GreateConfigs.server().kinetics.pumpValues.aluminiumPressure.get();
            case HV -> GreateConfigs.server().kinetics.pumpValues.stainlessSteelPressure.get();
            case EV -> GreateConfigs.server().kinetics.pumpValues.titaniumPressure.get();
            case IV -> GreateConfigs.server().kinetics.pumpValues.tungstensteelPressure.get();
            case LuV -> GreateConfigs.server().kinetics.pumpValues.palladiumPressure.get();
            case ZPM -> GreateConfigs.server().kinetics.pumpValues.naquadahPressure.get();
            case UV -> GreateConfigs.server().kinetics.pumpValues.darmstadtiumPressure.get();
            case UHV -> GreateConfigs.server().kinetics.pumpValues.neutroniumPressure.get();
            default -> throw new IllegalStateException("Unexpected value: " + tier);
        };
    }
}
