package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsInputHandler;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ValueSettingsInputHandler.class)
public class MixinValueSettingsInputHandler {

    @WrapOperation(method = "onBlockActivated", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private static BlockEntity greate$onBlockActivated(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof TieredKineticMachine tkm) {
            return tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }
}
