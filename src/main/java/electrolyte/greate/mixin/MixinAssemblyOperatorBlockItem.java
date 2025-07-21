package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AssemblyOperatorBlockItem.class)
public class MixinAssemblyOperatorBlockItem {

    @Inject(method = "operatesOn", at = @At("TAIL"), remap = false, cancellable = true)
    private void greate$operatesOn(LevelReader world, BlockPos pos, BlockState placedOnState, CallbackInfoReturnable<Boolean> cir) {
        if(placedOnState.getBlock() instanceof BeltBlock) {
            cir.setReturnValue(placedOnState.getValue(BeltBlock.SLOPE) == BeltSlope.HORIZONTAL);
        }
    }
}
