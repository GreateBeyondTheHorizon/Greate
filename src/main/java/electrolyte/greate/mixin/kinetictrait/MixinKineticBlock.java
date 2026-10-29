package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KineticBlock.class)
public class MixinKineticBlock {

    @WrapOperation(method = "onPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity greate$onPlace(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }

    @WrapOperation(method = "updateIndirectNeighbourShapes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity greate$updateIndirectNeighbourShapes(LevelAccessor level, BlockPos pos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pos);
    }
    @WrapOperation(method = "setPlacedBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private BlockEntity greate$setPlacedBy(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }

}
