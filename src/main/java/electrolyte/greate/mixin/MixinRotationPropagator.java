package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.RotationPropagator;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
