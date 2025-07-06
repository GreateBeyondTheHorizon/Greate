package electrolyte.greate.content.kinetics.gearbox;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.base.IRotate;
import electrolyte.greate.Greate;
import net.createmod.catnip.data.Iterate;
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

import java.util.Map;

import static electrolyte.greate.registry.Gearboxes.GEARBOXES;
import static electrolyte.greate.registry.GreateTagPrefixes.gearbox;

public class TieredVerticalGearboxItem extends BlockItem {

    private Material material;

    public TieredVerticalGearboxItem(Properties pProperties, Material material) {
        super(GEARBOXES.column(material).get(gearbox).get(), pProperties);
        this.material = material;
    }

    @Override
    public String getDescriptionId() {
        return "item." + Greate.MOD_ID + "." + material.getName() + "_vertical_gearbox";
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
