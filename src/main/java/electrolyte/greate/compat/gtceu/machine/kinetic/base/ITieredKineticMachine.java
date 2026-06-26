package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

public interface ITieredKineticMachine extends IMachineFeature {

    default TieredKineticMachine getKineticHolder() {
        return (TieredKineticMachine) self();
    }

    void updateStress(float newStress);

    void updateCapacity(float newCapacity);
}
