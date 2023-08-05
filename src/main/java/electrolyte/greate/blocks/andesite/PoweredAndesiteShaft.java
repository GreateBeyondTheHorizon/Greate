package electrolyte.greate.blocks.andesite;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import electrolyte.greate.registry.ModBlockEntityTypes;
import electrolyte.greate.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class PoweredAndesiteShaft extends PoweredShaftBlock {
    public PoweredAndesiteShaft(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.ANDESITE_SHAFT.get();
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(!stillValid(pState, pLevel, pPos)) {
            pLevel.setBlock(pPos, ModBlocks.ANDESITE_SHAFT.getDefaultState()
                    .setValue(AndesiteShaft.AXIS, pState.getValue(AXIS))
                    .setValue(WATERLOGGED, pState.getValue(WATERLOGGED)), 3);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return ModBlocks.ANDESITE_SHAFT.asStack();
    }

    public static BlockState getEquivalent(BlockState stateForPlacement) {
        return ModBlocks.POWERED_ANDESITE_SHAFT.getDefaultState()
                .setValue(PoweredAndesiteShaft.AXIS, stateForPlacement.getValue(AndesiteShaft.AXIS))
                .setValue(WATERLOGGED, stateForPlacement.getValue(WATERLOGGED));
    }
}
