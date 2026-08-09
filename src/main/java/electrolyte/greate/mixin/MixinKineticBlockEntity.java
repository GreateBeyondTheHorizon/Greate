package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachineBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KineticBlockEntity.class)
public class MixinKineticBlockEntity {

    @WrapOperation(method = "validateKinetics", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private BlockEntity greate$validateKinetics(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "setSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity greate$setSource(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "switchToBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity greate$switchToBlockState(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof MetaMachine mm) {
            if(mm.getTraitHolder().getTraitOptional(KineticMachineTrait.class).isPresent()) {
                return mm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
            }
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "switchToBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"), remap = false)
    private static Block greate$switchToBlockState(BlockState blockState, Operation<Block> original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos) {
        if(blockState.getBlock() instanceof TieredKineticMachineBlock tkmb) {
            return tkmb.getKineticBlock();
        }
        return original.call(blockState);
    }

}
