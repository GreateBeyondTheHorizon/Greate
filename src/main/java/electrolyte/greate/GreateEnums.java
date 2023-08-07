package electrolyte.greate;

import net.minecraft.ChatFormatting;

public class GreateEnums {

    public enum TIER {
        ULTRA_LOW("ULS", getStressAtTier(1), ChatFormatting.DARK_GRAY),
        LOW("LS", getStressAtTier(2), ChatFormatting.GRAY),
        MEDIUM("MS", getStressAtTier(3), ChatFormatting.AQUA),
        HIGH("HS", getStressAtTier(4), ChatFormatting.GOLD),
        EXTREME("ES", getStressAtTier(5), ChatFormatting.DARK_PURPLE),
        INSANE("IS", getStressAtTier(6), ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", getStressAtTier(7), ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", getStressAtTier(8), ChatFormatting.RED),
        ULTIMATE("US", getStressAtTier(9), ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", getStressAtTier(10), ChatFormatting.DARK_RED);

        private final String name;
        private final int stress;
        private final ChatFormatting tierColor;

        TIER(String name, int stress, ChatFormatting tierColor) {
            this.name = name;
            this.stress = stress;
            this.tierColor = tierColor;
        }

        public String getName() {
            return name;
        }

        public int getStress() {
            return stress;
        }

        public ChatFormatting getTierColor() {
            return tierColor;
        }

        private static int getStressAtTier(int tier) {
            return (int) Math.pow(2, 2 * tier + 7);
        }
    }
}
