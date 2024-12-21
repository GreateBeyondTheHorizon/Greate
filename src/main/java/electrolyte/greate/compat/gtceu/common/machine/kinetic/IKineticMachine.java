package electrolyte.greate.compat.gtceu.common.machine.kinetic;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;
import electrolyte.greate.compat.gtceu.common.blockentity.TieredKineticMachineBlockEntity;
import electrolyte.greate.compat.gtceu.common.machine.TieredKineticMachineDefinition;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;

public interface IKineticMachine extends IMachineFeature {

    default TieredKineticMachineBlockEntity getKineticHolder() {
        return (TieredKineticMachineBlockEntity) self().getHolder();
    }

    default TieredKineticMachineDefinition getKineticDefinition() {
        return (TieredKineticMachineDefinition) self().getDefinition();
    }

    default float getRotationSpeedModifier(Direction dir) {
        return 1;
    }

    default Direction getRotationFacing() {
        Direction frontFacing = self().getFrontFacing();
        return getKineticDefinition().isFrontRotation() ? frontFacing :
                frontFacing.getAxis() == Axis.Y ? Direction.NORTH : frontFacing.getClockWise();
    }

    default boolean hasShaftTowards(Direction dir) {
        return dir.getAxis() == getRotationFacing().getAxis();
    }
}
