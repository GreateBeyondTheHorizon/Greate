package electrolyte.greate.content.kinetics.millstone;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.registry.Millstones;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class TieredMillstoneBlock extends MillstoneBlock implements ITieredBlock, ITieredPartialModel {

    private TIER tier;
    private PartialModel model;

    public TieredMillstoneBlock(Properties properties, PartialModel model) {
        super(properties);
        this.model = model;
        Millstones.MILLSTONES.add(this);
    }

    @Override
    public BlockEntityType<? extends MillstoneBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MILLSTONE.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public TIER getTier() {
        return tier;
    }

    @Override
    public void setTier(TIER tier) {
        this.tier = tier;
    }

    @Override
    public PartialModel getPartialModel() {
        return this.model;
    }
}
