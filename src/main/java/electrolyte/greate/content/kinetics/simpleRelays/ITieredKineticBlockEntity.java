package electrolyte.greate.content.kinetics.simpleRelays;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.foundation.utility.CreateLang;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import net.createmod.catnip.lang.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface ITieredKineticBlockEntity {

    default float getMaxSpeedFromBlock(Block block) {
        boolean tieredMachine = block instanceof ITieredBlock;
        if(tieredMachine) {
            ITieredBlock tieredBlock = (ITieredBlock) block;
            int tier = tieredBlock.getTier();
            if(tier != -1) {
                return GreateValues.getMaxSpeedFromMaterial(tieredBlock.getMaterial());
            }
        }
        return Integer.MAX_VALUE;
    }

    default boolean addToGoggleTooltip(List<Component> tooltip, Material material, float currentSpeed) {
        if(material.hasProperty(GreatePropertyKeys.KINETIC)) {
            if(!tooltip.isEmpty()) {
                CreateLang.builder().space();
            } else {
                CreateLang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
            }
            Lang.builder(Greate.MOD_ID)
                    .add(CreateLang.number(Mth.abs(currentSpeed)).style(ChatFormatting.AQUA))
                    .space()
                    .add(CreateLang.text("/").style(ChatFormatting.DARK_GRAY))
                    .space()
                    .add(CreateLang.number(GreateValues.getMaxSpeedFromMaterial(material)).style(ChatFormatting.AQUA))
                    .space()
                    .add(CreateLang.text("RPM"))
                    .space()
                    .add(CreateLang.text("at current shaft tier").style(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip, 1);
            return true;
        }
        return false;
    }

    default boolean renderNormally() {
        return true;
    }
}
