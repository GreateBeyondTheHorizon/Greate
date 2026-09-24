package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RotationPropagator.class)
public class MixinRotationPropagator {

    @Inject(method = "propagateNewSource", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/base/KineticBlockEntity;getFlickerScore()I", shift = Shift.AFTER), remap = false, cancellable = true)
    private static void greate$propagateNewSource(KineticBlockEntity currentTE, CallbackInfo ci,
                                                  @Local(name = "speedOfCurrent") float speedOfCurrent,
                                                  @Local(name = "neighbourTE") KineticBlockEntity neighbourTE,
                                                  @Local(name = "world") Level world) {
        if(currentTE instanceof ITieredKineticBlockEntity itkbe) {
            if(itkbe.getMaxSpeedFromBlock(currentTE.getBlockState().getBlock()) < Mth.abs(speedOfCurrent)) {
                world.destroyBlock(currentTE.getBlockPos(), true);
                ci.cancel();
                return;
            }
        }
        if(neighbourTE instanceof ITieredKineticBlockEntity itkbe) {
            if(itkbe.getMaxSpeedFromBlock(neighbourTE.getBlockState().getBlock()) < Mth.abs(speedOfCurrent)) {
                world.destroyBlock(neighbourTE.getBlockPos(), true);
                ci.cancel();
                return;
            }
        }

    }
}
