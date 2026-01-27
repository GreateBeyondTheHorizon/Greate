package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.fan.AirCurrent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AirCurrent.class)
public interface MixinAirCurrentInvoker {

    @Invoker(remap = false) int callGetLimit();
}
