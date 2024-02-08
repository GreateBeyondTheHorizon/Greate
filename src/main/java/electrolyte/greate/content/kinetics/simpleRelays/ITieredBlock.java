package electrolyte.greate.content.kinetics.simpleRelays;

import com.gregtechceu.gtceu.api.GTValues;
import electrolyte.greate.GreateValues;

import electrolyte.greate.infrastructure.config.GConfigUtility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public interface ITieredBlock {

    int getTier();

    void setTier(int tier);

    default void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("greate.tooltip.capacity").append(Component.literal(String.valueOf(GConfigUtility.getMaxCapacityFromTier(getTier()))).withStyle(Style.EMPTY.withColor(GTValues.VC[getTier()])))
                .append(" (")
                .append(Component.literal(GreateValues.SNF[getTier()]))
                .append(")").withStyle(ChatFormatting.DARK_GRAY));
    }
}
