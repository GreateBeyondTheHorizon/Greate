package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.KineticNetwork;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KineticNetwork.class)
public class MixinKineticNetwork {

    @WrapOperation(method = "calculateStress", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true), remap = false)
    private BlockEntity greate$calculateStress(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "calculateCapacity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true), remap = false)
    private BlockEntity greate$calculateCapacity(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }
}
