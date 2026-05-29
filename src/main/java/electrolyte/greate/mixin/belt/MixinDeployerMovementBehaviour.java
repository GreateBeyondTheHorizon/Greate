package electrolyte.greate.mixin.belt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerMovementBehaviour;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DeployerMovementBehaviour.class)
public class MixinDeployerMovementBehaviour {

    @WrapOperation(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private boolean greate_activateAsSchematicPrinter(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }
}
