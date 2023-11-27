package electrolyte.greate.content.kinetics.mixer;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlock;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredEncasedCogwheel;
import electrolyte.greate.registry.MechanicalMixers;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class TieredMechanicalMixerBlock extends MechanicalMixerBlock implements ITieredBlock, ITieredEncasedCogwheel {

    private PartialModel mixerHeadModel, cogwheelModel;
    private TIER tier;

    public TieredMechanicalMixerBlock(Properties properties, PartialModel mixerHeadModel, PartialModel cogwheelModel) {
        super(properties);
        this.mixerHeadModel = mixerHeadModel;
        this.cogwheelModel = cogwheelModel;
        MechanicalMixers.MECHANICAL_MIXERS.add(this);
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
    public BlockEntityType<? extends MechanicalMixerBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MECHANICAL_MIXER.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ITieredBlock.super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public PartialModel getCogwheelModel() {
        return cogwheelModel;
    }

    @Override
    public PartialModel getPartialModel() {
        return mixerHeadModel;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.SLOW;
    }
}
