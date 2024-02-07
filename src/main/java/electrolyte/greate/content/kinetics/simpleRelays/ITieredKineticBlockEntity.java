package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.foundation.utility.Lang;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.infrastructure.config.GConfigUtility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface ITieredKineticBlockEntity {

    default double getMaxCapacityFromBlock(Block block) {
        boolean tieredMachine = block instanceof ITieredBlock;
        TIER tier = null;
        if(tieredMachine) {
            tier = ((ITieredBlock) block).getTier();
        }
        if(tier != null) {
            return GConfigUtility.getMaxCapacityFromTier(tier);
        }
        return Integer.MAX_VALUE;
    }

    default void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity) {};

    default boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, TIER tier, double capacity) {
        if(tier != null) {
            if(!tooltip.isEmpty()) {
                Lang.builder().space();
            } else {
                Lang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
            }
            Lang.builder(Greate.MOD_ID).translate("tooltip.capacity").style(ChatFormatting.GRAY).forGoggles(tooltip);
            Lang.number(capacity).style(ChatFormatting.AQUA).add(Lang.text("su")).space().add(Lang.text("/").space().add(Lang.number(GConfigUtility.getMaxCapacityFromTier(tier))).add(Lang.text("su").space().add(Lang.text("at current shaft tier").style(ChatFormatting.DARK_GRAY)))).forGoggles(tooltip, 1);
            return true;
        }
        return false;
    }
}
