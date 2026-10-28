package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.KineticEffectHandler;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KineticEffectHandler.class)
public class MixinKineticEffectHandler {

    @WrapOperation(method = "spawnRotationIndicators", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;", remap = true), remap = false)
    private Block greate$spawnRotationIndicators(BlockState blockState, Operation<Block> original) {
        if(blockState.getBlock() instanceof TieredKineticMachineBlock tkmb) {
            return tkmb.getKineticBlock();
        }
        return original.call(blockState);
    }
}
