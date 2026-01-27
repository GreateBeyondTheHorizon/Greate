package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.fan.AirCurrent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AirCurrent.class)
public abstract class MixinAirCurrent {

    @Shadow
    protected abstract int getLimit();

    @Shadow
    public boolean pushing;

    @ModifyVariable(method = "rebuild", at = @At(value = "STORE"), remap = false, name = "searchStart")
    private int rebuild(int x) {
        return pushing ? 0 : getLimit();
    }
}
