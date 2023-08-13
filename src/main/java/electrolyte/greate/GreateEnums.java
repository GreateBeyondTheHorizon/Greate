package electrolyte.greate;

import net.minecraft.ChatFormatting;

public class GreateEnums {

    public enum TIER implements Comparable<TIER> {
        ULTRA_LOW("ULS",GreateConfig.ULS_CAPACITY.get(), ChatFormatting.WHITE),
        LOW("LS", GreateConfig.LS_CAPACITY.get(), ChatFormatting.GRAY),
        MEDIUM("MS",GreateConfig.MS_CAPACITY.get(), ChatFormatting.AQUA),
        HIGH("HS", GreateConfig.HS_CAPACITY.get(), ChatFormatting.GOLD),
        EXTREME("ES", GreateConfig.ES_CAPACITY.get(), ChatFormatting.DARK_PURPLE),
        INSANE("IS", GreateConfig.IS_CAPACITY.get(), ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", GreateConfig.LUS_CAPACITY.get(), ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", GreateConfig.ZPMS_CAPACITY.get(), ChatFormatting.RED),
        ULTIMATE("US", GreateConfig.US_CAPACITY.get(), ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", GreateConfig.UHS_CAPACITY.get(), ChatFormatting.DARK_RED),
        NONE("NONE", Double.MAX_VALUE, ChatFormatting.WHITE);

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
    }
}
