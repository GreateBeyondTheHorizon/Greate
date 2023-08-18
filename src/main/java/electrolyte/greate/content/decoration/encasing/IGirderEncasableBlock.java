package electrolyte.greate.content.decoration.encasing;

import com.simibubi.create.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public interface IGirderEncasableBlock {

    default InteractionResult tryGirderEncase(BlockState state, Level level, BlockPos pos, ItemStack heldItem, Player player, InteractionHand hand, BlockHitResult hit) {
        List<Block> variant = GirderEncasingRegistry.get(state.getBlock());
        for(Block block : variant) {
            if(block instanceof IGirderEncasedBlock girderEncasedBlock && heldItem.is(AllBlocks.METAL_GIRDER.get().asItem()) && state.getValue(BlockStateProperties.AXIS) != Axis.Y) {
                if(level.isClientSide) return InteractionResult.SUCCESS;
                girderEncasedBlock.handleEncasing(state, level, pos, heldItem, player, hand);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                    if (player.getItemInHand(hand).isEmpty()) {
                        player.setItemInHand(hand, ItemStack.EMPTY);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
