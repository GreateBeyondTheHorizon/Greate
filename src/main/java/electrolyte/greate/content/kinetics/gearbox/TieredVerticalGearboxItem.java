package electrolyte.greate.content.kinetics.gearbox;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.Map;

public class TieredVerticalGearboxItem extends BlockItem {

    public static final Map<Block, TieredVerticalGearboxItem> MAP = new HashMap<>();
    private String material;

    public TieredVerticalGearboxItem(Properties pProperties, Block pBlock) {
        super(pBlock, pProperties);
        material = pBlock.getName().getString();
        material = material.substring(13, material.length() - 8);
        MAP.put(pBlock, this);
    }

    @Override
    public String getDescriptionId() {
        return "item.greate." +  material + "_vertical_gearbox";
    }

    @Override
    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {}

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, Player pPlayer, ItemStack pStack, BlockState pState) {
        Axis axis = null;
        for(Direction dir : Iterate.horizontalDirections) {
            BlockState state = pLevel.getBlockState(pPos.relative(dir));
            if(state.getBlock() instanceof IRotate) {
                if(((IRotate) state.getBlock()).hasShaftTowards(pLevel, pPos.relative(dir), state, dir.getOpposite())) {
                    if(axis != null && axis != dir.getAxis()) {
                        axis = null;
                        break;
                    } else {
                        axis = dir.getAxis();
                    }
                }
            }
        }
        Axis axis1 = axis == null ? pPlayer.getDirection().getClockWise().getAxis() : axis == Axis.X ? Axis.Z : Axis.X;
        pLevel.setBlockAndUpdate(pPos, pState.setValue(BlockStateProperties.AXIS, axis1));
        return super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);
    }
}
