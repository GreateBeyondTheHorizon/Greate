package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.equipment.clipboard.ClipboardValueSettingsHandler;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClipboardValueSettingsHandler.class)

public class MixinClipboardValueSettingsHandler {

    @WrapOperation(method = "drawCustomBlockSelection", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true), remap = false)
    private static BlockEntity greate$drawCustomBlockSelection(ClientLevel clientLevel, BlockPos pos, Operation<BlockEntity> original) {
        if(clientLevel.getBlockEntity(pos) instanceof TieredKineticMachine tkm) {
            return tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
        }
        return original.call(clientLevel, pos);
    }

    @WrapOperation(method = "clientTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true), remap = false)
    private static BlockEntity greate$clientTick(ClientLevel clientLevel, BlockPos pos, Operation<BlockEntity> original) {
        if(clientLevel.getBlockEntity(pos) instanceof TieredKineticMachine tkm) {
            return tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
        }
        return original.call(clientLevel, pos);
    }

    @WrapOperation(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true), remap = false)
    private static BlockEntity greate$interact(Level level, BlockPos pPos, Operation<BlockEntity> original) {
        if(level.getBlockEntity(pPos) instanceof TieredKineticMachine tkm) {
            return tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
        }
        return original.call(level, pPos);
    }
}
