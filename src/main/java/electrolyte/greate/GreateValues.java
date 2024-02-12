package electrolyte.greate;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.recipe.content.Content;

import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;
import static net.minecraft.ChatFormatting.*;

public class GreateValues {

    /**
     * The short names for the stress tiers
     */
    public static final String[] SN = new String[]{"ULS", "LS", "MS", "HS", "ES", "IS", "LuS", "ZPMS", "US", "UHS"};

    /**
     * The short names for the stress tiers, formatted for text
     */
    public static final String[] SNF = new String[]{
            DARK_GRAY + "ULS",
            GRAY + "LS",
            AQUA + "MS",
            GOLD + "HS",
            DARK_PURPLE + "ES",
            BLUE + "IS",
            LIGHT_PURPLE + "LuS",
            RED + "ZPMS",
            DARK_AQUA + "US",
            DARK_RED + "UHS",
    };

    /**
     * The long names for the stress tiers
     */
    public static final String[] STRESS_NAMES = new String[]{
            "Ultra Low Stress",
            "Low Stress",
            "Medium Stress",
            "High Stress",
            "Extreme Stress",
            "Insane Stress",
            "Ludicrous Stress",
            "ZPM Stress",
            "Ultimate Stress",
            "Ultra High Stress",
            "Ultra Excessive Stress",
            "Ultra Immense Stress",
            "Ultra Extreme Stress",
            "Overpowered Stress",
            "Maximum Stress"
    };

    public static int convertGTEUToTier(List<Content> content) {
        if (content.isEmpty()) return GTValues.ULV;
        long eut = (Long) content.get(0).getContent();
        for (int i = 0; i < GTValues.V.length; i++) {
            long voltage = GTValues.V[i];
            if (eut <= voltage) {
                return GTValues.ALL_TIERS[i];
            }
        }
        return GTValues.MAX;
    }

    public static int getTierFromTierMaterial(Material tierMaterial) {
        for (int i = 0; i < TM.length; i++) {
            if (TM[i] == tierMaterial) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Tier materials
     * Based on GTMaterials.VOLTAGE_COMMON_MATERIALS, but uses AndesiteAlloy instead of WroughtIron in the first tier.
     */
    public static Material[] TM;

    /**
     * Belt materials
     */
    public static Material[] BM;

    public static void init() {
        TM = new Material[]{
                AndesiteAlloy,
                Steel,
                Aluminium,
                StainlessSteel,
                Titanium,
                TungstenSteel,
                RhodiumPlatedPalladium,
                NaquadahAlloy,
                Darmstadtium,
                Neutronium,
        };

        BM = new Material[]{
                Rubber,
                SiliconeRubber,
                Polyethylene,
                Polytetrafluoroethylene,
                Polybenzimidazole,
        };
    }
}
