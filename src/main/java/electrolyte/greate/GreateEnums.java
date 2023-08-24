package electrolyte.greate;

import net.minecraft.ChatFormatting;

import java.util.Arrays;

public class GreateEnums {

    public enum TIER implements Comparable<TIER> {
        NONE("NONE", Double.MAX_VALUE, ChatFormatting.WHITE),
        ULTRA_LOW("ULS", Greate.CONFIG.ULS_CAPACITY, ChatFormatting.WHITE),
        LOW("LS", Greate.CONFIG.LS_CAPACITY, ChatFormatting.GRAY),
        MEDIUM("MS", Greate.CONFIG.MS_CAPACITY, ChatFormatting.AQUA),
        HIGH("HS", Greate.CONFIG.HS_CAPACITY, ChatFormatting.GOLD),
        EXTREME("ES", Greate.CONFIG.ES_CAPACITY, ChatFormatting.DARK_PURPLE),
        INSANE("IS", Greate.CONFIG.IS_CAPACITY, ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", Greate.CONFIG.LUS_CAPACITY, ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", Greate.CONFIG.ZPMS_CAPACITY, ChatFormatting.RED),
        ULTIMATE("US", Greate.CONFIG.US_CAPACITY, ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", Greate.CONFIG.UHS_CAPACITY, ChatFormatting.DARK_RED);

        private final String name;
        private final double stressCapacity;
        private final ChatFormatting tierColor;

        TIER(String name, double stressCapacity, ChatFormatting tierColor) {
            this.name = name;
            this.stressCapacity = stressCapacity;
            this.tierColor = tierColor;
        }

        public String getName() {
            return name;
        }

        public double getStressCapacity() {
            return stressCapacity;
        }

        public ChatFormatting getTierColor() {
            return tierColor;
        }

        public static TIER deserialize(String name) {
            for (TIER tier : values()) {
                if (tier.name().equalsIgnoreCase(name))
                    return tier;
            }
            Greate.LOGGER.warn("Tried to deserialize invalid recipe tier condition: \"" + name + "\"");
            return NONE;
        }

        public static int getTierMultiplier(TIER tier, double machineMultiplier) {
            return tier == ULTRA_LOW ? 1 : (int) (machineMultiplier * (Arrays.stream(values()).toList().indexOf(tier) - 1));
        }
    }
}
