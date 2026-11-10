package electrolyte.greate.compat.gtceu.machine.kinetic.recipelogic;

import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import electrolyte.greate.compat.gtceu.api.machine.WorkableStressElectricMultiblockMachine;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.util.Mth;

public class WorkableStressElectricMultiblockMachineRecipeLogic extends RecipeLogic {

    @Override
    protected ActionResult matchRecipe(GTRecipe recipe) {
        var match = super.matchRecipe(recipe);
        if(!match.isSuccess() && match.capability() instanceof StressRecipeCapability) {
            float impact = recipe.data.getFloat("stressImpact");
            if(impact == 0) impact = 1;
            if(this.getRLMachine() instanceof WorkableStressElectricMultiblockMachine machine) {
                KineticMachineTrait trait = machine.getTraitFromParts(KineticMachineTrait.class);
                if(trait != null) {
                    float requiredImpact = Mth.abs(trait.getSpeed()) * impact;
                    if(requiredImpact > trait.getCapacity()) return match;
                    else return ActionResult.SUCCESS;
                }
            }
        }
        return match;
    }
}
