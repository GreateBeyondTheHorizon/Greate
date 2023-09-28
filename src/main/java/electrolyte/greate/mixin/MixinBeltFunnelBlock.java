package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.logistics.funnel.BeltFunnelBlock;
import com.simibubi.create.content.logistics.funnel.BeltFunnelBlock.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeltFunnelBlock.class)
public class MixinBeltFunnelBlock {

    @Inject(method = "getShapeForPosition", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void greate_getShapeForPosition(BlockGetter world, BlockPos pos, Direction facing, boolean extracting, CallbackInfoReturnable<Shape> cir) {
        cir.setReturnValue(greate_getShapeForPosition(world, pos, facing, extracting));
    }

    @Unique
    private static Shape greate_getShapeForPosition(BlockGetter world, BlockPos pos, Direction facing, boolean extracting) {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = world.getBlockState(posBelow);
        Shape perpendicularState = extracting ? Shape.PUSHING : Shape.PULLING;
        if (!(stateBelow.getBlock() instanceof BeltBlock)) return perpendicularState;
        Direction movementFacing = stateBelow.getValue(BeltBlock.HORIZONTAL_FACING);
        return movementFacing.getAxis() != facing.getAxis() ? perpendicularState : Shape.RETRACTED;
    }
}
