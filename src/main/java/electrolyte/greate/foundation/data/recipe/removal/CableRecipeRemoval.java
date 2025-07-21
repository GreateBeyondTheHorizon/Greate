package electrolyte.greate.foundation.data.recipe.removal;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.material.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.material.material.properties.WireProperties;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.gregtechceu.gtceu.utils.GTUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.tag.TagPrefix.*;

public class CableRecipeRemoval {

    public static void disableCableRecipes(Consumer<ResourceLocation> provider) {
        for(Material material : GTCEuAPI.materialManager) {
            //TODO: fix
            //if(material.hasFlag(MaterialFlags.NO_UNIFICATION)) continue;
            WireProperties property = material.getProperty(PropertyKey.WIRE);
            if(property != null) {
                removeRecipe(provider, property, wireGtSingle, material);
                removeRecipe(provider, property, wireGtDouble, material);
                removeRecipe(provider, property, wireGtQuadruple, material);
                removeRecipe(provider, property, wireGtOctal, material);
                removeRecipe(provider, property, wireGtHex, material);
            }
        }
    }

    public static void removeRecipe(Consumer<ResourceLocation> recipe, WireProperties property, TagPrefix wirePrefix, Material material) {
        if(property.isSuperconductor()) return;
        int voltageTier = GTUtil.getTierByVoltage(property.getVoltage());
        int factor = (int) (wirePrefix.getMaterialAmount(material) * 2 / M);
        if(voltageTier <= LV) {
            recipe.accept(GTCEu.id("shapeless/" + material.getName() + "_cable_" + factor));
            recipe.accept(GTCEu.id("packer/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name().substring(6).toLowerCase()));
        }
        if(voltageTier <= EV) {
            recipe.accept(GTCEu.id("assembler/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name.substring(6).toLowerCase() + "_rubber"));
        }
        recipe.accept(GTCEu.id("assembler/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name.substring(6).toLowerCase() + "_silicone"));
        recipe.accept(GTCEu.id("assembler/cover_" + material.getName() + "_wire_gt_" + wirePrefix.name.substring(6).toLowerCase() + "_styrene_butadiene"));
    }
}
