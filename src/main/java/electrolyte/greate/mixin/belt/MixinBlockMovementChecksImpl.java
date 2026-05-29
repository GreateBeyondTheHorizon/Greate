package electrolyte.greate.mixin.belt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.impl.contraption.BlockMovementChecksImpl;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockMovementChecksImpl.class)
public class MixinBlockMovementChecksImpl {

    @WrapOperation(method = "isMovementAllowedFallback", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private static boolean greate_isMovementAllowedFallback(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }
}
