package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.mixin.MixinKineticBlockEntityAccessor;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class GreateRecipeModifiers {

    public static final Function<OverclockingLogic, RecipeModifier> KINETIC_OVERCLOCK = Util
            .memoize(logic -> (machine, recipe) -> {
        if(!(machine instanceof TieredKineticMachine tkm)) return RecipeModifier.nullWrongType(TieredKineticMachine.class, machine);
        if(!(machine instanceof IOverclockMachine overclockMachine)) return ModifierFunction.IDENTITY;
        if(RecipeHelper.getRecipeEUtTier(recipe) > overclockMachine.getMaxOverclockTier()) {
            return ModifierFunction.cancel(Component.literal("Machine tier too low."));
        }
        float stressRequired = (float) recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).get(0).content() * (4 * (overclockMachine.getOverclockTier() - 1));
        float currentStress = ((MixinKineticBlockEntityAccessor) tkm.getTrait(KineticMachineTrait.class).getKineticBlockEntity()).getStress();
        if(currentStress < stressRequired) {
            return ModifierFunction.cancel(Component.literal("Stress too low. Required: " + stressRequired));
        }
        return logic.getModifier(machine, recipe, overclockMachine.getOverclockVoltage());
    });

    public static final RecipeModifier OC_NON_PERFECT = KINETIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK);
}
