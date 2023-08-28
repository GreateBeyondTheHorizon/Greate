package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.content.kinetics.base.TieredDirectionalShaftHalvesBlockEntity;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlockEntity;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RotationPropagator.class)
public abstract class MixinRotationPropagator {

    @Inject(method = "getAxisModifier", at = @At("RETURN"), remap = false, cancellable = true)
    private static void greate_axisModifier(KineticBlockEntity be, Direction direction, CallbackInfoReturnable<Float> cir) {
        if((be.hasSource() || be.isSource()) && be instanceof TieredDirectionalShaftHalvesBlockEntity tdshbe) {
        Direction source = tdshbe.getSourceFacing();
        if(be instanceof TieredGearboxBlockEntity) {
            cir.setReturnValue((float) (direction.getAxis() == source.getAxis() ?
                    direction == source ? 1 : -1 :
                    direction.getAxisDirection() == source.getAxisDirection() ? -1 : 1));
            }
        }
    }
}
