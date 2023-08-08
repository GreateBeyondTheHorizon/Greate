package electrolyte.greate.content.kinetics.simpleRelays;

import electrolyte.greate.GreateEnums.TIER;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public interface ITieredBlock {

    TIER tier = TIER.NONE;

    TIER getTier();

    void setTier(TIER tier);
}
