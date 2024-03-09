package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlicer;
import com.simibubi.create.content.kinetics.belt.BeltSlicer.Feedback;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeltSlicer.class)
public class MixinBeltSlicer {

    @Inject(method = "useConnector", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", ordinal = 0), remap = false)
    private static void greate_useConnector(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, Feedback feedBack, CallbackInfoReturnable<InteractionResult> cir) {
        BlockPos beltVector = BlockPos.containing(BeltHelper.getBeltVector(state));
        BeltPart part = state.getValue(BeltBlock.PART);
        BlockPos next = part == BeltPart.START ? pos.subtract(beltVector) : pos.offset(beltVector);
        TieredBeltBlockEntity newBE = (TieredBeltBlockEntity) BeltHelper.getSegmentBE(world, next);
        TieredBeltBlockEntity prevBE = (TieredBeltBlockEntity) BeltHelper.getSegmentBE(world, pos);
        if(newBE != null) {
            newBE.setShaftType(prevBE.getShaftType());
        }
    }
}
