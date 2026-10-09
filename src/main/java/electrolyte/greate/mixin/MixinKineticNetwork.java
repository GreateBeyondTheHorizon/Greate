package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import electrolyte.greate.content.kinetics.base.KineticBlockEntityAccessor;
import electrolyte.greate.content.kinetics.base.KineticNetworkAccessor;
import net.createmod.catnip.data.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KineticNetwork.class)
public abstract class MixinKineticNetwork implements KineticNetworkAccessor {

    @Shadow public Map<KineticBlockEntity, Float> members;
    @Shadow public Long id;

    @Shadow
    public abstract void sync();

    @Unique private final Pair<Integer, Integer> greate_networkInformation = Pair.of(0,0); //first = regular WW, second = large WW

    @Inject(method = "updateFromNetwork", at = @At("HEAD"), remap = false)
    private void greate_updateFromNetwork(CallbackInfo ci, @Local(name = "be") KineticBlockEntity be) {
        ((KineticBlockEntityAccessor) be).greate_setWaterWheelCount(greate_networkInformation.getFirst());
        ((KineticBlockEntityAccessor) be).greate_setLargeWaterWheelCount(greate_networkInformation.getSecond());
    }

    @Inject(method = "add", at = @At("HEAD"), remap = false)
    private void greate_add(KineticBlockEntity be, CallbackInfo ci) {
        if(members.containsKey(be)) return;
        if(be instanceof LargeWaterWheelBlockEntity) {
            greate_networkInformation.setSecond(greate_networkInformation.getSecond() + 1);
            sync();
        } else if(be instanceof WaterWheelBlockEntity) {
            greate_networkInformation.setFirst(greate_networkInformation.getFirst() + 1);
            sync();
        }
    }

    @Inject(method = "addSilently", at = @At("HEAD"), remap = false)
    private void greate_addSilently(KineticBlockEntity be, float lastCapacity, float lastStress, CallbackInfo ci) {
        if(members.containsKey(be)) return;
        if(be instanceof LargeWaterWheelBlockEntity) {
            greate_networkInformation.setSecond(greate_networkInformation.getSecond() + 1);
            sync();
        } else if(be instanceof WaterWheelBlockEntity) {
            greate_networkInformation.setFirst(greate_networkInformation.getFirst() + 1);
            sync();
        }
    }

    @Inject(method = "remove", at = @At("HEAD"), remap = false)
    private void greate_remove(KineticBlockEntity be, CallbackInfo ci) {
        if(!members.containsKey(be)) return;
        if(be instanceof LargeWaterWheelBlockEntity) {
            greate_networkInformation.setSecond(greate_networkInformation.getSecond() - 1);
            sync();
        } else if(be instanceof WaterWheelBlockEntity) {
            greate_networkInformation.setFirst(greate_networkInformation.getFirst() - 1);
            sync();
        }
    }

    @Override
    public Pair<Integer, Integer> greate_getNetworkInfo() {
        return greate_networkInformation;
    }
}
