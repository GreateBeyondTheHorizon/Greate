package electrolyte.greate.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.logistics.tunnel.BeltTunnelBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllArmInteractionPointTypes.BeltType.class)
public abstract class MixinAllArmInteractionPointTypes$BeltType {

    @Inject(method = "canCreatePoint", at = @At("RETURN"), remap = false, cancellable = true)
    private void greate_beltType(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((AllBlocks.BELT.has(state) || state.getBlock() instanceof TieredBeltBlock) &&
                !(level.getBlockState(pos.above()).getBlock() instanceof BeltTunnelBlock));
    }
}
