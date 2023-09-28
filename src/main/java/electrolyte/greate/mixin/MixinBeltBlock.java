package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.kinetics.belt.BeltBlock.SLOPE;

@Mixin(BeltBlock.class)
public class MixinBeltBlock {

    @Inject(method = "canTransportObjects", at = @At("HEAD"), cancellable = true, remap = false)
    private static void greate_canTransportObjects(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(!(state.getBlock() instanceof BeltBlock)) cir.setReturnValue(false);
        BeltSlope slope = state.getValue(SLOPE);
        cir.setReturnValue(slope != BeltSlope.VERTICAL && slope != BeltSlope.SIDEWAYS);
    }

}
