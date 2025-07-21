package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.logistics.funnel.BeltFunnelBlock;
import com.simibubi.create.content.logistics.funnel.BeltFunnelBlock.Shape;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeltFunnelBlock.class)
public class MixinBeltFunnelBlock {

    @Inject(method = "getShapeForPosition", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void greate$getShapeForPosition(BlockGetter world, BlockPos pos, Direction facing, boolean extracting, CallbackInfoReturnable<Shape> cir) {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = world.getBlockState(posBelow);
        Shape perpendicularState = extracting ? Shape.PUSHING : Shape.PULLING;
        if (!(stateBelow.getBlock() instanceof BeltBlock)) {
            cir.setReturnValue(perpendicularState);
            return;
        }
        Direction movementFacing = stateBelow.getValue(BeltBlock.HORIZONTAL_FACING);
        cir.setReturnValue(movementFacing.getAxis() != facing.getAxis() ? perpendicularState : Shape.RETRACTED);
    }

    @Inject(method = "isOnValidBelt", at = @At("HEAD"), remap = false, cancellable = true)
    private static void greate$isOnValidBelt(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState stateBelow = world.getBlockState(pos.below());
        if(stateBelow.getBlock() instanceof TieredBeltBlock) cir.setReturnValue(TieredBeltBlock.canTransportObjects(stateBelow));
    }
}
