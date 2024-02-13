package electrolyte.greate.infrastructure.config;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;


public class GConfigUtility {

    public static int getMaxCapacityFromTier(int tier) {
        return switch (tier) {
            case ULV -> GreateConfigs.server().kinetics.tierValues.andesiteMaxCapacity.get();
            case LV -> GreateConfigs.server().kinetics.tierValues.steelMaxCapacity.get();
            case MV -> GreateConfigs.server().kinetics.tierValues.aluminiumMaxCapacity.get();
            case HV -> GreateConfigs.server().kinetics.tierValues.stainlessSteelMaxCapacity.get();
            case EV -> GreateConfigs.server().kinetics.tierValues.titaniumMaxCapacity.get();
            case IV -> GreateConfigs.server().kinetics.tierValues.tungstensteelMaxCapacity.get();
            case LuV -> GreateConfigs.server().kinetics.tierValues.palladiumMaxCapacity.get();
            case ZPM -> GreateConfigs.server().kinetics.tierValues.naquadahMaxCapacity.get();
            case UV -> GreateConfigs.server().kinetics.tierValues.darmstadtiumMaxCapacity.get();
            case UHV -> GreateConfigs.server().kinetics.tierValues.neutroniumMaxCapacity.get();
            default -> throw new IllegalStateException("Unexpected value: " + tier);
        };
    }

    public static int getBeltLengthFromMaterial(Material beltMaterial) {
        if (beltMaterial == Rubber) {
            return GreateConfigs.server().kinetics.beltValues.rubberMaxBeltLength.get();
        } else if (beltMaterial == SiliconeRubber) {
            return GreateConfigs.server().kinetics.beltValues.siliconeMaxBeltLength.get();
        } else if (beltMaterial == Polyethylene) {
            return GreateConfigs.server().kinetics.beltValues.polyethyleneMaxBeltLength.get();
        } else if (beltMaterial == Polytetrafluoroethylene) {
            return GreateConfigs.server().kinetics.beltValues.polytetrafluoroethyleneMaxBeltLength.get();
        } else if (beltMaterial == Polybenzimidazole) {
            return GreateConfigs.server().kinetics.beltValues.polybenzimidazoleMaxBeltLength.get();
        }
        throw new IllegalStateException("Unexpected value: " + beltMaterial);
    }

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
