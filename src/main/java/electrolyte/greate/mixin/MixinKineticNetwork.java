package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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

    @WrapOperation(method = "calculateStress", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private BlockEntity greate$calculateStress(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }
}
