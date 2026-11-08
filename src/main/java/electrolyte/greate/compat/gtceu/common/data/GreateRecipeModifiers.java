package electrolyte.greate.compat.gtceu.common.data;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.mixin.kinetictrait.MixinKineticBlockEntityAccessor;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Function;

public class GreateRecipeModifiers {

    public static final Function<OverclockingLogic, RecipeModifier> KINETIC_OVERCLOCK = Util
            .memoize(logic -> (machine, recipe) -> {
        if(!(machine instanceof TieredKineticMachine)) return RecipeModifier.nullWrongType(TieredKineticMachine.class, machine);
        if(!(machine instanceof IOverclockMachine overclockMachine)) return ModifierFunction.IDENTITY;
        if(RecipeHelper.getRecipeEUtTier(recipe) > overclockMachine.getMaxOverclockTier()) {
            return ModifierFunction.cancel(Component.translatable("greate.recipe_modifier.insufficient_machine_tier"));
        }
        float stressRequired = (float) recipe.getTickInputContents(StressRecipeCapability.STRESS_CAPABILITY).get(0).content() * (4 * (overclockMachine.getOverclockTier() - Math.max(1, RecipeHelper.getRecipeEUtTier(recipe))));
        float currentStress = ((MixinKineticBlockEntityAccessor) ((ITieredKineticMachine) machine).getKineticBlockEntity()).getStress();
        if(currentStress < stressRequired) {
            return ModifierFunction.cancel(Component.translatable("greate.recipe_modifier.insufficient_stress_overclock", stressRequired));
        }
        return logic.getModifier(machine, recipe, overclockMachine.getOverclockVoltage());
    });

    public static ModifierFunction minRPM(MetaMachine machine, GTRecipe recipe) {
        if(!(machine instanceof TieredKineticMachine tkm)) return RecipeModifier.nullWrongType(TieredKineticMachine.class, machine);
        int minRPM = recipe.data.getInt("minRPM");
        if(minRPM == 0) return ModifierFunction.IDENTITY;
        if(minRPM > Mth.abs(tkm.getSpeed())) return ModifierFunction.cancel(Component.translatable("greate.recipe_modifier.insufficient_min_rpm", minRPM));
        return ModifierFunction.IDENTITY;
    }

    public static ModifierFunction maxRPM(MetaMachine machine, GTRecipe recipe) {
        if(!(machine instanceof TieredKineticMachine tkm)) return RecipeModifier.nullWrongType(TieredKineticMachine.class, machine);
        int maxRPM = recipe.data.getInt("maxRPM");
        if(maxRPM == 0) return ModifierFunction.IDENTITY;
        if(maxRPM < Mth.abs(tkm.getSpeed())) return ModifierFunction.cancel(Component.translatable("greate.recipe_modifier.insufficient_max_rpm", maxRPM));
        return ModifierFunction.IDENTITY;
    }

    public static final RecipeModifier OC_NON_PERFECT = KINETIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK);
}
