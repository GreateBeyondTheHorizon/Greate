package electrolyte.greate.infrastructure.config;

import electrolyte.greate.GreateEnums.BELT_TYPE;
import electrolyte.greate.GreateEnums.TIER;

public class GConfigUtility {

    public static int getMaxCapacityFromTier(TIER tier) {
        if(tier == TIER.ULTRA_LOW) return GreateConfigs.server().kinetics.tierValues.andesiteMaxCapacity.get();
        if(tier == TIER.LOW) return GreateConfigs.server().kinetics.tierValues.steelMaxCapacity.get();
        if(tier == TIER.MEDIUM) return GreateConfigs.server().kinetics.tierValues.aluminiumMaxCapacity.get();
        if(tier == TIER.HIGH) return GreateConfigs.server().kinetics.tierValues.stainlessSteelMaxCapacity.get();
        if(tier == TIER.EXTREME) return GreateConfigs.server().kinetics.tierValues.titaniumMaxCapacity.get();
        if(tier == TIER.INSANE) return GreateConfigs.server().kinetics.tierValues.tungstensteelMaxCapacity.get();
        if(tier == TIER.LUDICRIOUS) return GreateConfigs.server().kinetics.tierValues.palladiumMaxCapacity.get();
        if(tier == TIER.ZPM) return GreateConfigs.server().kinetics.tierValues.naquadahMaxCapacity.get();
        if(tier == TIER.ULTIMATE) return GreateConfigs.server().kinetics.tierValues.darmstadtiumMaxCapacity.get();
        return GreateConfigs.server().kinetics.tierValues.neutroniumMaxCapacity.get();
    }

    public static int getBeltLengthFromType(BELT_TYPE type) {
        if(type == BELT_TYPE.RUBBER) return GreateConfigs.server().kinetics.beltValues.rubberMaxBeltLength.get();
        if(type == BELT_TYPE.SILICONE_RUBBER) return GreateConfigs.server().kinetics.beltValues.siliconeMaxBeltLength.get();
        if(type == BELT_TYPE.POLYETHYLENE) return GreateConfigs.server().kinetics.beltValues.polyethyleneMaxBeltLength.get();
        if(type == BELT_TYPE.POLYTETRAFLUOROETHYLENE) return GreateConfigs.server().kinetics.beltValues.polytetrafluoroethyleneMaxBeltLength.get();
        return GreateConfigs.server().kinetics.beltValues.polybenzimidazoleMaxBeltLength.get();
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
