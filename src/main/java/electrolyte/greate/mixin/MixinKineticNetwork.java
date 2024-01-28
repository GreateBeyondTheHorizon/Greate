package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KineticNetwork.class)
public abstract class MixinKineticNetwork {

    @Shadow public abstract void sync();
    @Shadow public Map<KineticBlockEntity, Float> members;
    @Shadow private float currentCapacity;
    @Shadow private float currentStress;
    @Shadow public abstract int getSize();
    @Shadow public abstract float calculateStress();
    @Shadow public abstract float calculateCapacity();
    @Unique private double greate_currentMaxCapacity;

    @Inject(method = "initFromTE", at = @At("RETURN"), remap = false)
    private void greate_initFromTE(float maxStress, float currentStress, int members, CallbackInfo ci) {
        greate_updateMaxCapacity();
    }

    @Inject(method = "updateNetwork", at = @At(value = "HEAD"), remap = false)
    private void greate_updateNetwork(CallbackInfo ci) {
        float newStress = calculateStress();
        float newMaxStress = calculateCapacity();
        double newMaxCapacity = greate_calculateMaxCapacity();
        if(currentStress != newStress || currentCapacity != newMaxStress || greate_currentMaxCapacity != newMaxCapacity) {
            currentStress = newStress;
            currentCapacity = newMaxStress;
            greate_currentMaxCapacity = newMaxCapacity;
            sync();
        }
    }

    @Inject(method = "updateFromNetwork", at = @At(value = "RETURN"), remap = false)
    private void greate_updateFromNetwork(KineticBlockEntity be, CallbackInfo ci) {
        if(be instanceof ITieredKineticBlockEntity itkbe) {
            itkbe.updateFromNetwork(currentCapacity, currentStress, getSize(), greate_currentMaxCapacity);
        }
    }

    @Unique
    private void greate_updateMaxCapacity() {
        double newMaxCapacity = greate_calculateMaxCapacity();
        if(greate_currentMaxCapacity != newMaxCapacity) {
            greate_currentMaxCapacity = newMaxCapacity;
            sync();
        }
    }

    @Inject(method = "updateCapacityFor", at = @At("RETURN"), remap = false)
    private void greate_updateCapacityFor(KineticBlockEntity be, float capacity, CallbackInfo ci) {
        greate_updateMaxCapacity();
    }

    @Inject(method = "updateStressFor", at = @At("RETURN"), remap = false)
    private void greate_updateStressFor(KineticBlockEntity be, float stress, CallbackInfo ci) {
        greate_updateMaxCapacity();
    }

    @Unique
    private double greate_calculateMaxCapacity() {
        double presentMaxCapacity = Integer.MAX_VALUE;
        for (KineticBlockEntity be : members.keySet()) {
            if (be instanceof ITieredKineticBlockEntity itkbe) {
                if (presentMaxCapacity > itkbe.getMaxCapacityFromBlock(be.getBlockState().getBlock())) {
                    presentMaxCapacity = itkbe.getMaxCapacityFromBlock(be.getBlockState().getBlock());
                }
            }
        }
        return presentMaxCapacity;
    }
}
