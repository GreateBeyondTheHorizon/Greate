package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;

public interface ITieredKineticMachine extends IMachineFeature {

    KineticMachineTrait getKineticMachineTrait();

    default KineticBlockEntity getKineticBlockEntity() {
        return getKineticMachineTrait().getKineticBlockEntity();
    }

    default float getSpeed() {
        return getKineticMachineTrait().getKineticBlockEntity().getSpeed();
    }

    default <T extends BlockEntityBehaviour> T getBehaviour(BehaviourType<T> behaviourType) {
        return getKineticMachineTrait().getKineticBlockEntity().getBehaviour(behaviourType);
    }

    default boolean isVirtual() {
        return getKineticMachineTrait().getKineticBlockEntity().isVirtual();
    }

    default void notifyUpdate() {
        getKineticMachineTrait().getKineticBlockEntity().notifyUpdate();
    }
}
