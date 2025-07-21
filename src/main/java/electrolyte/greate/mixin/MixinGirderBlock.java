package electrolyte.greate.mixin;

import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.content.decoration.encasing.GirderEncasingRegistry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.simibubi.create.content.decoration.girder.GirderBlock.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

@Mixin(GirderBlock.class)
public class MixinGirderBlock {

    @Inject(method = "useItemOn", at = @At("RETURN"), remap = false, cancellable = true)
    private void greate$useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if(Block.byItem(pPlayer.getItemInHand(pHand).getItem()) instanceof TieredShaftBlock tsb) {
            List<Block> variant = GirderEncasingRegistry.get(tsb);
            for(Block block : variant) {
                KineticBlockEntity.switchToBlockState(pLevel, pPos, block.defaultBlockState()
                        .setValue(WATERLOGGED, pState.getValue(WATERLOGGED))
                        .setValue(TOP, pState.getValue(TOP))
                        .setValue(BOTTOM, pState.getValue(BOTTOM))
                        .setValue(GirderEncasedShaftBlock.HORIZONTAL_AXIS, pState.getValue(X) || pHit.getDirection().getAxis() == Axis.Z ? Axis.Z : Axis.X));
                pLevel.playSound(null, pPos, SoundEvents.NETHERITE_BLOCK_HIT, SoundSource.BLOCKS, 0.5f, 1.25f);
                if (!pLevel.isClientSide && !pPlayer.isCreative()) {
                    pPlayer.getItemInHand(pHand).shrink(1);
                    if (pPlayer.getItemInHand(pHand).isEmpty()) {
                        pPlayer.setItemInHand(pHand, ItemStack.EMPTY);
                    }
                }
                cir.setReturnValue(ItemInteractionResult.SUCCESS);
            }
            cir.setReturnValue(ItemInteractionResult.SUCCESS);
        }
    }
}
