package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RotationPropagator.class)
public class MixinRotationPropagator {

    @WrapOperation(method = "findConnectedNeighbour", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private static BlockEntity greate$findConnectedNeighbour(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "handleRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private static BlockEntity greate$handleRemoved(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }

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

    @WrapOperation(method = "propagateMissingSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private static BlockEntity greate$propagateMissingSource(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).get().getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }
}
