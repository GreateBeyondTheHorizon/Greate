package electrolyte.greate;

import com.gregtechceu.gtceu.api.recipe.content.Content;
import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.List;

public class GreateValues {

    public enum TIER implements Comparable<TIER> {
        ULTRA_LOW("ULS", ChatFormatting.WHITE),
        LOW("LS", ChatFormatting.GRAY),
        MEDIUM("MS", ChatFormatting.AQUA),
        HIGH("HS", ChatFormatting.GOLD),
        EXTREME("ES", ChatFormatting.DARK_PURPLE),
        INSANE("IS", ChatFormatting.BLUE),
        LUDICRIOUS("LuS", ChatFormatting.LIGHT_PURPLE),
        ZPM("ZPMS", ChatFormatting.RED),
        ULTIMATE("US", ChatFormatting.DARK_AQUA),
        ULTIMATE_HIGH("UHS", ChatFormatting.DARK_RED);

        private final String name;
        private final ChatFormatting tierColor;

        TIER(String name, ChatFormatting tierColor) {
            this.name = name;
            this.tierColor = tierColor;
        }

        public String getName() {
            return name;
        }

        public ChatFormatting getTierColor() {
            return tierColor;
        }

        public static TIER deserialize(String name) {
            for(TIER tier : values()) {
                if (tier.name().equalsIgnoreCase(name) || tier.getName().equalsIgnoreCase(name)) {
                    return tier;
                }
            }
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

    public enum BELT_TYPE {
        RUBBER,
        SILICONE_RUBBER,
        POLYETHYLENE,
        POLYTETRAFLUOROETHYLENE,
        POLYBENZIMIDAZOLE
    }

    public enum MATERIAL_TYPE {
        ANDESITE,
        STEEL,
        ALUMINIUM,
        STAINLESS_STEEL,
        TITANIUM,
        TUNGSTENSTEEL,
        PALLADIUM,
        NAQUADAH,
        DARMSTADTIUM,
        NEUTRONIUM
    }
}
