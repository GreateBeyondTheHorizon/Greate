package electrolyte.greate;

import com.gregtechceu.gtceu.api.recipe.content.Content;
import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.List;

public class GreateEnums {

    public enum TIER implements Comparable<TIER> {
        ULTRA_LOW("ULS", Greate.CONFIG.ULS.CAPACITY, ChatFormatting.WHITE),
        LOW("LS", Greate.CONFIG.LS.CAPACITY, ChatFormatting.GRAY),
        MEDIUM("MS", Greate.CONFIG.MS.CAPACITY, ChatFormatting.AQUA),
        HIGH("HS", Greate.CONFIG.HS.CAPACITY, ChatFormatting.GOLD),
        EXTREME("ES", Greate.CONFIG.ES.CAPACITY, ChatFormatting.DARK_PURPLE),
        INSANE("IS", Greate.CONFIG.IS.CAPACITY, ChatFormatting.DARK_BLUE),
        LUDICRIOUS("LuS", Greate.CONFIG.LUS.CAPACITY, ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", Greate.CONFIG.ZPM.CAPACITY, ChatFormatting.RED),
        ULTIMATE("US", Greate.CONFIG.US.CAPACITY, ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", Greate.CONFIG.UHS.CAPACITY, ChatFormatting.DARK_RED);

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
            return ULTRA_LOW;
        }

        public static int getTierMultiplier(TIER tier, double machineMultiplier) {
            return tier == ULTRA_LOW ? 1 : (int) (machineMultiplier * indexOfTier(tier));
        }

        public static TIER convertGTEUToTier(List<Content> content) {
            if(content.isEmpty()) return TIER.ULTRA_LOW;
            Long eut = (Long) content.get(0).getContent();
            if(eut <= 8) return TIER.ULTRA_LOW;
            else if(eut <= 32) return TIER.LOW;
            else if(eut <= 128) return TIER.MEDIUM;
            else if(eut <= 512) return TIER.HIGH;
            else if(eut <= 2048) return TIER.EXTREME;
            else if(eut <= 8192) return TIER.INSANE;
            else if(eut <= 32768) return TIER.LUDICRIOUS;
            else if(eut <= 131072) return TIER.ZPM;
            else if(eut <= 524288) return TIER.ULTIMATE;
            else return TIER.ULTIMATE_HIGH;
        }

        public static int indexOfTier(TIER tier) {
            return Arrays.stream(values()).toList().indexOf(tier);
        }
    }
}
