package electrolyte.greate.content.kinetics.simpleRelays;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;

import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class TieredCogwheelBlock extends CogWheelBlock implements ITieredBlock {

    private int tier;
    private PartialModel shaftlessLargeCogwheel, cogwheelShaft;

    protected TieredCogwheelBlock(boolean large, Properties properties, PartialModel shaftlessLargeCogwheel, PartialModel cogwheelShaft) {
        super(large, properties);
        this.shaftlessLargeCogwheel = shaftlessLargeCogwheel;
        this.cogwheelShaft = cogwheelShaft;
    }

    public static TieredCogwheelBlock small(Properties properties, PartialModel shaftlessLargeCogwheel, PartialModel cogwheelShaft) {
        return new TieredCogwheelBlock(false, properties, shaftlessLargeCogwheel, cogwheelShaft);
    }

    public static TieredCogwheelBlock large(Properties properties, PartialModel shaftlessLargeCogwheel, PartialModel cogwheelShaft) {
        return new TieredCogwheelBlock(true, properties, shaftlessLargeCogwheel, cogwheelShaft);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_BRACKETED_KINETIC.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    public PartialModel[] getPartialModels() {
        return new PartialModel[]{shaftlessLargeCogwheel, cogwheelShaft};
    }
}
