package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.mixin.MixinKineticBlockEntityAccessor;
import net.minecraft.network.chat.Component;

public class GreateRecipeModifiers {

    // todo: 1 modifier for rpm 10%$ increase in speed every 16 rpm? (and 10% extra stress)
    // another for tier: 2x stress required every tier up

    public static ModifierFunction stressRequired(MetaMachine machine, GTRecipe recipe) {
        if(!(machine instanceof TieredKineticMachine tkm)) {
            return RecipeModifier.nullWrongType(TieredKineticMachine.class, machine);
        }
        float stressRequired = (float) recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).get(0).content();
        float currentStress = ((MixinKineticBlockEntityAccessor) tkm.getTrait(KineticMachineTrait.TYPE).getKineticBlockEntity()).getStress();
        if(currentStress < stressRequired) {
            return ModifierFunction.cancel(Component.literal("Stress too low. Required: " + stressRequired));
        }
        return ModifierFunction.IDENTITY;
    }
}
