package electrolyte.greate.compat.gtceu.client.renderer.machine;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.simibubi.create.content.kinetics.base.IRotate;
import net.minecraft.core.Direction;

public abstract class BaseKineticDynamicRender<T extends IMachineFeature, S extends DynamicRender<T, S>> extends DynamicRender<T, S> {

    protected Direction.Axis getRotationAxisOf(MetaMachine be) {
        return ((IRotate) be.getBlockState()
			.getBlock()).getRotationAxis(be.getBlockState());
    }
}
