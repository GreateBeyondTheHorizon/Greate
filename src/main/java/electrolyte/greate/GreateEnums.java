package electrolyte.greate;

import net.minecraft.ChatFormatting;

public class GreateEnums {

    public enum CAPACITY_TIER {
        LOW("LC", GreateConfig.LC_MAX.get(), ChatFormatting.GRAY),
        MEDIUM("MC", GreateConfig.MC_MAX.get(), ChatFormatting.AQUA),
        HIGH("HC", GreateConfig.HC_MAX.get(), ChatFormatting.GOLD),
        EXTREME("EC", GreateConfig.EC_MAX.get(), ChatFormatting.DARK_PURPLE),
        INSANE("IC", GreateConfig.IC_MAX.get(), ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuC", GreateConfig.LUC_MAX.get(), ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMC", GreateConfig.ZPMC_MAX.get(), ChatFormatting.RED),
        ULTIMATE("UC", GreateConfig.UC_MAX.get(), ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHC", GreateConfig.UHC_MAX.get(), ChatFormatting.DARK_RED);

        private final String name;
        private final double maxCapacity;
        private final ChatFormatting tierColor;

        CAPACITY_TIER(String name, double maxCapacity, ChatFormatting tierColor) {
            this.name = name;
            this.maxCapacity = maxCapacity;
            this.tierColor = tierColor;
        }

        public String getName() {
            return name;
        }

        public double getMaxCapacity() {
            return maxCapacity;
        }

        public ChatFormatting getTierColor() {
            return tierColor;
        }
    }
}
