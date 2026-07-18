package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;

public class KineticMachineRecipeLogic extends RecipeLogic {

    @Override
    public void setupRecipe(GTRecipe recipe) {
        if(!this.getRLMachine().beforeWorking(recipe)) {
            this.setStatus(Status.IDLE);
            this.consecutiveRecipes = 0;
            this.progress = 0;
            this.duration = 0;
            this.isActive = false;
            this.syncDataHolder.resyncAllFields();
            return;
        }

        if(this.lastRecipe != null && !recipe.equals(this.lastRecipe)) {
            this.chanceCaches.clear();
        }
        this.failureReasonMap.clear();
        this.recipeDirty = false;
        this.lastRecipe = recipe;
        this.setStatus(Status.WORKING);
        this.progress = 0;
        this.duration = recipe.duration;
        this.isActive = true;
        this.syncDataHolder.resyncAllFields();
    }

    @Override
    public void handleRecipeWorking() {
        assert this.lastRecipe != null;
        ActionResult inputResult = RecipeHelper.matchRecipe(this.getRLMachine(), this.lastRecipe);
        if(!inputResult.isSuccess()) {
            this.interruptRecipe();
            return;
        }
        super.handleRecipeWorking();
    }

    @Override
    public void onRecipeFinish() {
        if(this.lastRecipe != null) {
            ActionResult result = this.handleRecipeIO(this.lastRecipe, IO.IN);
            if(!result.isSuccess()) {
                this.interruptRecipe();
                return;
            }
        }
        super.onRecipeFinish();
    }
}
