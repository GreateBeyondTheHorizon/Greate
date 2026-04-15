package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.foundation.sound.SoundScapes;
import com.simibubi.create.foundation.sound.SoundScapes.AmbienceGroup;
import electrolyte.greate.compat.gtceu.machine.kinetic.StressRecipeHandler;
import electrolyte.greate.compat.gtceu.machine.kinetic.TieredKineticWorkableMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredMillstoneMachine extends TieredKineticWorkableMachine {

    @SaveField
    private int timer;

    private GTRecipe lastRecipe;

    //private final ConditionalSubscriptionHandler recipeSub;

    public TieredMillstoneMachine(BlockEntityCreationInfo info, int tier) {
        super(info, tier);
        //this.recipeSub = new ConditionalSubscriptionHandler(this, this::updateRecipeLogic, this::hasItems);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.addHandlerList(RecipeHandlerList.of(IO.IN, new StressRecipeHandler(stressContainer, 1.0F))); //todo: support custom conversion rates
        //this.recipeSub.initialize(getLevel());
    }

    @Override
    public GTRecipeType getRecipeType() {
        return GTRecipeTypes.MACERATOR_RECIPES;
    }

    public void updateRecipeLogic() {
        if(kineticMachineTrait.getKineticBlockEntity().getSpeed() == 0) return;
        for(int slot = 0; slot < exportItems.getSlots(); slot++) {
            if(exportItems.getStackInSlot(slot).getCount() == exportItems.getSlotLimit(slot)) return;
        }
        if(timer > 0) {
            timer -= ((MillstoneBlockEntity) kineticMachineTrait.getKineticBlockEntity()).getProcessingSpeed();
            if(level.isClientSide) {
                ((MillstoneBlockEntity) kineticMachineTrait.getKineticBlockEntity()).spawnParticles();
                return;
            }
            if(timer <= 0) process();
            return;
        }
        if(importItems.getStackInSlot(0).isEmpty()) return;
        if(lastRecipe == null || !RecipeHelper.matchRecipe(this, lastRecipe).isSuccess()) {
            GTRecipe nextRecipe = getNextRecipe();
            if(nextRecipe == null) {
                timer = 100;
                kineticMachineTrait.getKineticBlockEntity().sendData();
            } else {
                lastRecipe = nextRecipe;
                timer = nextRecipe.duration;
                kineticMachineTrait.getKineticBlockEntity().sendData();
            }
            return;
        }
        timer = lastRecipe.duration;
        kineticMachineTrait.getKineticBlockEntity().sendData();
    }

    public void process() {
        if(lastRecipe == null || !RecipeHelper.matchRecipe(this, lastRecipe).isSuccess()) {
            GTRecipe nextRecipe = getNextRecipe();
            if(nextRecipe == null) return;
            lastRecipe = nextRecipe;
        }
        //ItemStack stackInSlot = importItems.getStackInSlot(0);
        //ItemStack craftingRemainingItem = stackInSlot.getCraftingRemainingItem();
        //stackInSlot.shrink(1);
        //importItems.setStackInSlot(0, stackInSlot);

    }

    public boolean hasItems() {
        return !importItems.getStackInSlot(0).isEmpty();
    }

    public @Nullable GTRecipe getNextRecipe() {
        Iterator<GTRecipe> recipe = GTRecipeTypes.MACERATOR_RECIPES.searchRecipe(this,
                r -> RecipeHelper.matchContents(this, r).isSuccess() &&
                RecipeHelper.checkConditions(r, getRecipeLogic()).isSuccess());
        if(recipe.hasNext()) return recipe.next();
        return null;
    }
}
