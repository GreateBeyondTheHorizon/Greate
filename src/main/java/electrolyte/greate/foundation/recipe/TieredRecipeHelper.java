package electrolyte.greate.foundation.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.HV;

public class TieredRecipeHelper {

    public static final TieredRecipeHelper INSTANCE = new TieredRecipeHelper();

    public List<ItemStack> getItemResults(Recipe<?> recipe, int machineTier) {
        List<ProcessingOutput> newResults = new ArrayList<>();
        int recipeTier = 0;
        if(recipe instanceof TieredProcessingRecipe<?> tpr) {
            recipeTier = tpr.getRecipeTier();
        }
        if(recipe instanceof ProcessingRecipe<?> pr) {
            List<ProcessingOutput> oldResults = pr.getRollableResults();
            for(int i = 0; i < oldResults.size(); i++) {
                ProcessingOutput oldResult = oldResults.get(i);
                if(pr instanceof TieredAbstractCrushingRecipe) {
                    if(machineTier < HV) {
                        if(i == 0 || oldResult.getChance() == 1) {
                            if(oldResult instanceof TieredProcessingOutput tpo) {
                                newResults.add(new TieredProcessingOutput(tpo.getStack(), tpo.getChance(), getExtraPercent(tpo.getExtraTierChance(), recipeTier, machineTier)));
                            } else {
                                newResults.add(oldResult);
                            }
                        }
                        continue;
                    }
                }
                if(oldResult instanceof TieredProcessingOutput tpo) {
                    newResults.add(new TieredProcessingOutput(tpo.getStack(), tpo.getChance(), getExtraPercent(tpo.getExtraTierChance(), recipeTier, machineTier)));
                } else {
                    newResults.add(oldResult);
                }
            }
            return new ArrayList<>(pr.rollResults(newResults));
        }
        return List.of(ItemStack.EMPTY);
    }

    private float getExtraPercent(float baseExtraPercent, int recipeTier, int machineTier) {
        return baseExtraPercent * (machineTier - recipeTier);
    }
}
