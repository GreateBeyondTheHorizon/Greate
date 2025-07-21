package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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

    @Shadow(remap = false) public abstract void sync();
    @Shadow(remap = false) public Map<KineticBlockEntity, Float> members;
    @Shadow(remap = false) private float currentCapacity;
    @Shadow(remap = false) private float currentStress;
    @Shadow(remap = false) public abstract int getSize();
    @Shadow(remap = false) public abstract float calculateStress();
    @Shadow(remap = false) public abstract float calculateCapacity();
    @Unique private float greate$currentMaxCapacity;

    @Inject(method = "initFromTE", at = @At("RETURN"), remap = false)
    private void greate$initFromTE(float maxStress, float currentStress, int members, CallbackInfo ci) {
        greate$updateMaxCapacity();
    }

    @Inject(method = "updateNetwork", at = @At(value = "HEAD"), remap = false)
    private void greate$updateNetwork(CallbackInfo ci) {
        float newStress = calculateStress();
        float newMaxStress = calculateCapacity();
        float newMaxCapacity = greate$calculateMaxCapacity();
        if(currentStress != newStress || currentCapacity != newMaxStress || greate$currentMaxCapacity != newMaxCapacity) {
            currentStress = newStress;
            currentCapacity = newMaxStress;
            greate$currentMaxCapacity = newMaxCapacity;
            sync();
        }
    }

    @Unique
    private void greate$updateMaxCapacity() {
        float newMaxCapacity = greate$calculateMaxCapacity();
        if(greate$currentMaxCapacity != newMaxCapacity) {
            greate$currentMaxCapacity = newMaxCapacity;
            sync();
        }
    }

    @Inject(method = "updateCapacityFor", at = @At("RETURN"), remap = false)
    private void greate$updateCapacityFor(KineticBlockEntity be, float capacity, CallbackInfo ci) {
        greate$updateMaxCapacity();
    }

    @Inject(method = "updateStressFor", at = @At("RETURN"), remap = false)
    private void greate$updateStressFor(KineticBlockEntity be, float stress, CallbackInfo ci) {
        greate$updateMaxCapacity();
    }

    @Unique
    private float greate$calculateMaxCapacity() {
        float presentMaxCapacity = Integer.MAX_VALUE;
        for (KineticBlockEntity be : members.keySet()) {
            if (be instanceof ITieredKineticBlockEntity itkbe) {
                if (presentMaxCapacity > itkbe.getMaxCapacityFromBlock(be.getBlockState().getBlock())) {
                    presentMaxCapacity = itkbe.getMaxCapacityFromBlock(be.getBlockState().getBlock());
                }
            }
        }
        return presentMaxCapacity;
    }

    @ModifyReturnValue(method = "calculateCapacity", at = @At("RETURN"), remap = false)
    private float greate$calculateCapacity(float original) {
        greate$updateMaxCapacity();
        return Math.min(original, greate$currentMaxCapacity);
    }
}
