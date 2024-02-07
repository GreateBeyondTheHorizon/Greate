package electrolyte.greate.infrastructure.config;

import electrolyte.greate.GreateValues.BELT_TYPE;
import electrolyte.greate.GreateValues.TIER;

public class GConfigUtility {

    public static int getMaxCapacityFromTier(TIER tier) {
        return switch (tier) {
            case ULTRA_LOW -> GreateConfigs.server().kinetics.tierValues.andesiteMaxCapacity.get();
            case LOW -> GreateConfigs.server().kinetics.tierValues.steelMaxCapacity.get();
            case MEDIUM -> GreateConfigs.server().kinetics.tierValues.aluminiumMaxCapacity.get();
            case HIGH -> GreateConfigs.server().kinetics.tierValues.stainlessSteelMaxCapacity.get();
            case EXTREME -> GreateConfigs.server().kinetics.tierValues.titaniumMaxCapacity.get();
            case INSANE -> GreateConfigs.server().kinetics.tierValues.tungstensteelMaxCapacity.get();
            case LUDICRIOUS -> GreateConfigs.server().kinetics.tierValues.palladiumMaxCapacity.get();
            case ZPM -> GreateConfigs.server().kinetics.tierValues.naquadahMaxCapacity.get();
            case ULTIMATE -> GreateConfigs.server().kinetics.tierValues.darmstadtiumMaxCapacity.get();
            case ULTIMATE_HIGH -> GreateConfigs.server().kinetics.tierValues.neutroniumMaxCapacity.get();
        };
    }

    public static int getBeltLengthFromType(BELT_TYPE type) {
        return switch (type) {
            case RUBBER -> GreateConfigs.server().kinetics.beltValues.rubberMaxBeltLength.get();
            case SILICONE_RUBBER -> GreateConfigs.server().kinetics.beltValues.siliconeMaxBeltLength.get();
            case POLYETHYLENE -> GreateConfigs.server().kinetics.beltValues.polyethyleneMaxBeltLength.get();
            case POLYTETRAFLUOROETHYLENE -> GreateConfigs.server().kinetics.beltValues.polytetrafluoroethyleneMaxBeltLength.get();
            case POLYBENZIMIDAZOLE -> GreateConfigs.server().kinetics.beltValues.polybenzimidazoleMaxBeltLength.get();
        };
    }

    public static double getPumpPressureFromTier(TIER tier) {
		return switch (tier) {
            case ULTRA_LOW -> GreateConfigs.server().kinetics.pumpValues.andesitePressure.get();
            case LOW -> GreateConfigs.server().kinetics.pumpValues.steelPressure.get();
            case MEDIUM -> GreateConfigs.server().kinetics.pumpValues.aluminiumPressure.get();
            case HIGH -> GreateConfigs.server().kinetics.pumpValues.stainlessSteelPressure.get();
            case EXTREME -> GreateConfigs.server().kinetics.pumpValues.titaniumPressure.get();
            case INSANE -> GreateConfigs.server().kinetics.pumpValues.tungstensteelPressure.get();
            case LUDICRIOUS -> GreateConfigs.server().kinetics.pumpValues.palladiumPressure.get();
            case ZPM -> GreateConfigs.server().kinetics.pumpValues.naquadahPressure.get();
            case ULTIMATE -> GreateConfigs.server().kinetics.pumpValues.darmstadtiumPressure.get();
            case ULTIMATE_HIGH -> GreateConfigs.server().kinetics.pumpValues.neutroniumPressure.get();
        };
    }
}
