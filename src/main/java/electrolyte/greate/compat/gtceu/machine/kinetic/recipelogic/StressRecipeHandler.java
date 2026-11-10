package electrolyte.greate.compat.gtceu.machine.kinetic.recipelogic;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.utils.GTMath;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.NotifiableStressContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StressRecipeHandler implements IRecipeHandler<EnergyStack> {

    private final NotifiableStressContainer stressContainer;
    private final double conversionRate; //su per EU

    public StressRecipeHandler(NotifiableStressContainer stressContainer, double conversionRate) {
        this.stressContainer = stressContainer;
        this.conversionRate = conversionRate;
    }

    @Override
    public @NotNull List<EnergyStack> handleRecipeInner(IO io, GTRecipe gtRecipe, List<EnergyStack> left, boolean simulate) {
        for(var it = left.listIterator(); it.hasNext();) {
            EnergyStack stack = it.next();
            if(stack.isEmpty()) {
                it.remove();
                continue;
            }

            long totalEU = stack.getTotalEU();
            float totalStress = GTMath.saturatedCast((long) Math.ceil(totalEU * conversionRate));
            if(totalStress > 0) {
                var list = new ArrayList<Float>();
                list.add(totalStress);
                var leftStress = stressContainer.handleRecipeInner(io, gtRecipe, list, simulate);
                if(leftStress.isEmpty()) {
                    it.remove();
                } else {
                    totalEU = (long) (leftStress.get(0) / conversionRate);
                    it.set(new EnergyStack(totalEU));
                }
            }
        }
        return left;
    }

    @Override
    public @NotNull List<Object> getContents() {
        return List.of(Math.ceil(stressContainer.getTotalContentAmount() * conversionRate));
    }

    @Override
    public double getTotalContentAmount() {
        return Math.ceil(stressContainer.getTotalContentAmount() * conversionRate);
    }

    @Override
    public RecipeCapability<EnergyStack> getCapability() {
        return EURecipeCapability.CAP;
    }
}
