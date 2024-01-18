package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import electrolyte.greate.content.fluids.pump.TieredPumpBlockEntity;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "com.simibubi.create.content.fluids.pump.PumpBlockEntity$PumpFluidTransferBehaviour")
public class MixinPumpFluidTransferBehaviour extends MixinBlockEntityBehaviour {
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;abs(F)F", remap = false), remap = false)
	private float greate_tick(float a, Operation<Float> original) {
		if (blockEntity instanceof TieredPumpBlockEntity tieredPumpBlockEntity) {
			double basePressure = GConfigUtility.getPumpPressureFromTier(tieredPumpBlockEntity.getTier());
			return (float) (basePressure * Math.abs(tieredPumpBlockEntity.getSpeed()));
		}
		return original.call(a);
	}
}
