package electrolyte.greate;

import net.minecraft.ChatFormatting;

public class GreateEnums {

    public enum TIER {

        ULTRA_LOW("ULS", GreateConfig.ULS_CAPACITY.get(), ChatFormatting.WHITE),
        LOW("LS", GreateConfig.LS_CAPACITY.get(), ChatFormatting.GRAY),
        MEDIUM("MS", GreateConfig.MS_CAPACITY.get(), ChatFormatting.AQUA),
        HIGH("HS", GreateConfig.HS_CAPACITY.get(), ChatFormatting.GOLD),
        EXTREME("ES", GreateConfig.ES_CAPACITY.get(), ChatFormatting.DARK_PURPLE),
        INSANE("IS", GreateConfig.IS_CAPACITY.get(), ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", GreateConfig.LUS_CAPACITY.get(), ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", GreateConfig.ZPMS_CAPACITY.get(), ChatFormatting.RED),
        ULTIMATE("US", GreateConfig.US_CAPACITY.get(), ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", GreateConfig.UHS_CAPACITY.get(), ChatFormatting.DARK_RED);

        private final String name;
        private final double stress;
        private final ChatFormatting tierColor;

        TIER(String name, double stress, ChatFormatting tierColor) {
            this.name = name;
            this.stress = stress;
            this.tierColor = tierColor;
        }

        public String getName() {
            return name;
        }

        public double getStress() {
            return stress;
        }

        public ChatFormatting getTierColor() {
            return tierColor;
        }
    }
}
