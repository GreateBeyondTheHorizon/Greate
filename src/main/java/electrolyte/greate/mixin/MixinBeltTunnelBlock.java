package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.logistics.tunnel.BeltTunnelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeltTunnelBlock.class)
public class MixinBeltTunnelBlock {

    @Inject(method = "isValidPositionForPlacement", at = @At("HEAD"), cancellable = true, remap = false)
    private void greate_isValidPositionForPlacement(BlockState state, LevelReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = worldIn.getBlockState(pos.below());
        if(!(blockState.getBlock() instanceof BeltBlock)) cir.setReturnValue(false);
        if(blockState.getValue(BeltBlock.SLOPE) != BeltSlope.HORIZONTAL) cir.setReturnValue(false);
        cir.setReturnValue(true);
    }
}
