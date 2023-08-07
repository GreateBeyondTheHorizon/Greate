package electrolyte.greate;

import net.minecraft.ChatFormatting;

public class GreateEnums {

    public enum TIER {
        LOW("LS", getStressAtTier(1), ChatFormatting.GRAY),
        MEDIUM("MS", getStressAtTier(2), ChatFormatting.AQUA),
        HIGH("HS", getStressAtTier(3), ChatFormatting.GOLD),
        EXTREME("ES", getStressAtTier(4), ChatFormatting.DARK_PURPLE),
        INSANE("IS", getStressAtTier(5), ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", getStressAtTier(6), ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", getStressAtTier(7), ChatFormatting.RED),
        ULTIMATE("US", getStressAtTier(8), ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", getStressAtTier(9), ChatFormatting.DARK_RED);

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
