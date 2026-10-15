package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueRenderer;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScrollValueRenderer.class)
public class MixinScrollValueRenderer {

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private static BlockEntity greate$tick(ClientLevel clientLevel, BlockPos pos, Operation<BlockEntity> original) {
        if(clientLevel.getBlockEntity(pos) instanceof TieredKineticMachine tkm) {
            return tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity();
        }
        return original.call(clientLevel, pos);
    }
}
