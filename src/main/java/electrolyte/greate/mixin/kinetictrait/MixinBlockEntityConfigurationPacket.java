package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockEntityConfigurationPacket.class)
public class MixinBlockEntityConfigurationPacket {

    @Shadow(remap = false) protected BlockPos pos;

    @WrapOperation(method = "lambda$handle$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true), remap = false)
    private BlockEntity greate$handle(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pos) instanceof ITieredKineticMachine km) {
            return km.getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }
}
