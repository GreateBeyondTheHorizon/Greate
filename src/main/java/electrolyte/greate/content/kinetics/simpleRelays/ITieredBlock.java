package electrolyte.greate.content.kinetics.simpleRelays;

import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public interface ITieredBlock {

    TIER getTier();

    void setTier(TIER tier);

    default void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.capacity").append(Component.literal(String.valueOf(GConfigUtility.getMaxCapacityFromTier(getTier()))).withStyle(getTier().getTierColor()))
                .append(" (")
                .append(Component.literal(getTier().getName()).withStyle(getTier().getTierColor()))
                .append(")").withStyle(ChatFormatting.DARK_GRAY));
    }
}
