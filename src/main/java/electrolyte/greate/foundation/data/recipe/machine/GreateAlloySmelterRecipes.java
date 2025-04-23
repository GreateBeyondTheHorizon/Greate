package electrolyte.greate.foundation.data.recipe.machine;

import electrolyte.greate.GreateValues;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ingot;
import static com.gregtechceu.gtceu.common.data.GTMaterials.WroughtIron;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ALLOY_SMELTER_RECIPES;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.ModItems.ALLOYS;

public class GreateAlloySmelterRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(int tier = 0; tier < TM.length; tier++) {
            if(tier != 0) {
                ALLOY_SMELTER_RECIPES
                        .recipeBuilder(ALLOYS[tier].getId().withSuffix("_from_dust"))
                        .inputItems(dust, GreateValues.getMaterialFromTier(tier))
                        .inputItems(Blocks.ANDESITE.asItem())
                        .outputItems(ALLOYS[tier])
                        .duration(100)
                        .EUt(VA[tier])
                        .save(provider);

                ALLOY_SMELTER_RECIPES
                        .recipeBuilder(ALLOYS[tier].getId().withSuffix("_from_ingot"))
                        .inputItems(ingot, GreateValues.getMaterialFromTier(tier))
                        .inputItems(Blocks.ANDESITE.asItem())
                        .outputItems(ALLOYS[tier])
                        .duration(100)
                        .EUt(VA[tier])
                        .save(provider);
            } else {
                ALLOY_SMELTER_RECIPES
                        .recipeBuilder(ALLOYS[tier].getId().withSuffix("_from_dust"))
                        .inputItems(dust, WroughtIron)
                        .inputItems(Blocks.ANDESITE.asItem())
                        .outputItems(ALLOYS[tier])
                        .duration(100)
                        .EUt(VA[tier])
                        .save(provider);

                ALLOY_SMELTER_RECIPES
                        .recipeBuilder(ALLOYS[tier].getId().withSuffix("_from_ingot"))
                        .inputItems(ingot, WroughtIron)
                        .inputItems(Blocks.ANDESITE.asItem())
                        .outputItems(ALLOYS[tier])
                        .duration(100)
                        .EUt(VA[tier])
                        .save(provider);
            }
        }
    }
}
