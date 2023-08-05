package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.be.ITieredKineticBlockEntity;
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
    @Unique private float greate_currentMaxCapacity;


    @Inject(method = "updateNetwork", at = @At(value = "HEAD"), remap = false)
    private void greate_updateNetwork(CallbackInfo ci) {
        float newMaxCapacity = greate_calculateMaxCapacity();
        if(greate_currentMaxCapacity != newMaxCapacity) {
            greate_currentMaxCapacity = newMaxCapacity;
            sync();
        }
    }

    @Inject(method = "updateFromNetwork", at = @At(value = "RETURN"), remap = false)
    private void greateUpdateFromNetwork(KineticBlockEntity be, CallbackInfo ci) {
        if(be instanceof ITieredKineticBlockEntity itkbe) {
            itkbe.updateFromNetwork(currentCapacity, currentStress, getSize(), greate_currentMaxCapacity);
        }
    }

    @Unique
    private void greate_updateMaxCapacity() {
        float newMaxCapacity = greate_calculateMaxCapacity();
        if(greate_currentMaxCapacity != newMaxCapacity) {
            greate_currentMaxCapacity = newMaxCapacity;
            sync();
        }
    }

    @Inject(method = "updateCapacityFor", at = @At("RETURN"), remap = false)
    private void greate_updateCapacityFor(KineticBlockEntity be, float capacity, CallbackInfo ci) {
        greate_updateMaxCapacity();
    }

    @Unique
    private float greate_calculateMaxCapacity() {
        float presentMaxCapacity = Integer.MAX_VALUE;
        for (KineticBlockEntity be : members.keySet()) {
            if (be instanceof ITieredKineticBlockEntity itkbe) {
                if (presentMaxCapacity > itkbe.getShaftMaxCapacity()) {
                    presentMaxCapacity = itkbe.getShaftMaxCapacity();
                }
            }
        }
        return presentMaxCapacity;
    }
}
