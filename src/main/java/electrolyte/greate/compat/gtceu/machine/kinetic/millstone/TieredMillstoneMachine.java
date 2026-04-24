package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;
import electrolyte.greate.compat.gtceu.machine.kinetic.StressRecipeHandler;
import electrolyte.greate.compat.gtceu.machine.kinetic.TieredKineticWorkableMachine;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredMillstoneMachine extends TieredKineticWorkableMachine {

    public TieredMillstoneMachine(BlockEntityCreationInfo info, int tier) {
        super(info, tier);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.addHandlerList(RecipeHandlerList.of(IO.IN, new StressRecipeHandler(stressContainer, 1.0F))); //todo: support custom conversion rates
    }
}
