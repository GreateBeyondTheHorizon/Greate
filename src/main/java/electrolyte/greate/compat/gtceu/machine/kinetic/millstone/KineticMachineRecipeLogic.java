package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import lombok.Getter;
import lombok.experimental.Accessors;

public class KineticMachineRecipeLogic extends RecipeLogic {

    @Accessors(fluent = true)
    @Getter
    @SyncToClient
    private boolean postEffects = true;

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
        lastUnrolledRecipe = recipe.copy();
        syncDataHolder.markClientSyncFieldDirty("lastDisplayedRecipe");
        GTRecipe runningRecipe = RecipeHelper.doPrerolls(recipe, chanceCaches);
        clearFailureReason();
        this.recipeDirty = false;
        this.lastRecipe = runningRecipe;
        this.setStatus(Status.WORKING);
        this.progress = 0;
        this.duration = runningRecipe.duration;
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
                this.postEffects = false;
                this.syncDataHolder.markClientSyncFieldDirty("postEffects");
                this.interruptRecipe();
                return;
            }
            this.postEffects = true;
            this.syncDataHolder.markClientSyncFieldDirty("postEffects");
        }
        super.onRecipeFinish();
    }
}
