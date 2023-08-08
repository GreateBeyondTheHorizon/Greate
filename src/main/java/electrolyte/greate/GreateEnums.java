package electrolyte.greate;

import net.minecraft.ChatFormatting;

public class GreateEnums {

    public enum TIER {
        NONE("NONE", 0, Double.MAX_VALUE, ChatFormatting.WHITE),
        ULTRA_LOW("ULS", 8, GreateConfig.ULS_CAPACITY.get(), ChatFormatting.WHITE),
        LOW("LS", 32, GreateConfig.LS_CAPACITY.get(), ChatFormatting.GRAY),
        MEDIUM("MS", 128, GreateConfig.MS_CAPACITY.get(), ChatFormatting.AQUA),
        HIGH("HS", 512, GreateConfig.HS_CAPACITY.get(), ChatFormatting.GOLD),
        EXTREME("ES", 2048, GreateConfig.ES_CAPACITY.get(), ChatFormatting.DARK_PURPLE),
        INSANE("IS", 8192, GreateConfig.IS_CAPACITY.get(), ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", 32768, GreateConfig.LUS_CAPACITY.get(), ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", 131072, GreateConfig.ZPMS_CAPACITY.get(), ChatFormatting.RED),
        ULTIMATE("US", 524288, GreateConfig.US_CAPACITY.get(), ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", 2097152, GreateConfig.UHS_CAPACITY.get(), ChatFormatting.DARK_RED);

        private final String name;
        private final int stress;
        private final double stressCapacity;
        private final ChatFormatting tierColor;

        TIER(String name, int stress, double stressCapacity, ChatFormatting tierColor) {
            this.name = name;
            this.stress = stress;
            this.stressCapacity = stressCapacity;
            this.tierColor = tierColor;
        }

        public String getName() {
            return name;
        }

        public int getStress() {
            return stress;
        }

        public double getStressCapacity() {
            return stressCapacity;
        }

        public ChatFormatting getTierColor() {
            return tierColor;
        }

        public static TIER deserialize(String name) {
            for (TIER tier : values())
                if (tier.name().equals(name))
                    return tier;
            Greate.LOGGER.warn("Tried to deserialize invalid recipe tier condition: \"" + name + "\"");
            return NONE;
        }
    }
}
