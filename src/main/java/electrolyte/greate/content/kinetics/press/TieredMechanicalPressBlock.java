package electrolyte.greate.content.kinetics.press;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.registry.MechanicalPresses;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class TieredMechanicalPressBlock extends MechanicalPressBlock implements ITieredBlock, ITieredShaftBlock, ITieredPartialModel {

    private TIER tier;
    private PartialModel headModel;
    private Block shaft;

    public TieredMechanicalPressBlock(Properties properties, PartialModel headModel, Block shaft) {
        super(properties);
        this.headModel = headModel;
        this.shaft = shaft;
        MechanicalPresses.MECHANICAL_PRESSES.add(this);
    }

    @Override
    public BlockEntityType<? extends MechanicalPressBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MECHANICAL_PRESS.get();
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
        return this.headModel;
    }

    @Override
    public Block getShaft() {
        return this.shaft;
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
