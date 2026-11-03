package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredGeneratingKineticMachineBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KineticBlockEntity.class)
public class MixinKineticBlockEntity {

    @WrapOperation(method = "validateKinetics", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private BlockEntity greate$validateKinetics(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "setSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity greate$setSource(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "switchToBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity greate$switchToBlockState(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "switchToBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;", remap = true), remap = false)
    private static Block greate$switchToBlockState(BlockState blockState, Operation<Block> original) {
        if(blockState.getBlock() instanceof TieredKineticMachineBlock tkmb) {
            return tkmb.getKineticBlock();
        }
        return original.call(blockState);
    }

    @Inject(method = "isSource", at = @At("RETURN"), remap = false, cancellable = true)
    private void greate$isSource(CallbackInfoReturnable<Boolean> cir) {
        if(((KineticBlockEntity) (Object) this) instanceof TieredGeneratingKineticMachineBlockEntity) {
            cir.setReturnValue(true);
        }
    }

    @WrapOperation(method = "calculateAddedStressCapacity", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/api/stress/BlockStressValues;getCapacity(Lnet/minecraft/world/level/block/Block;)D", remap = true), remap = false)
    private double greate$calculateAddedStressCapacity(Block block, Operation<Double> original) {
        if(((KineticBlockEntity) (Object) this) instanceof TieredGeneratingKineticMachineBlockEntity kbe) {
            if(((MixinKineticBlockEntityAccessor) kbe).getCapacity() == 0 || kbe.getSpeed() == 0) return 0;
            return ((MixinKineticBlockEntityAccessor) kbe).getCapacity() / kbe.getSpeed();
        }
        return original.call(block);
    }
}
