package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredGeneratingKineticMachineBlockEntity;
import electrolyte.greate.mixin.kinetictrait.MixinKineticBlockEntityAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class NotifiableStressContainer extends NotifiableRecipeHandlerTrait<Float> {

    protected IO handlerIO;
    private float currentOutputStress, lastOutputStress;
    private TickableSubscription stressSub;


    public NotifiableStressContainer(IO handlerIO) {
        super();
        this.handlerIO = handlerIO;
    }

    @Override
    public IO getHandlerIO() {
        return this.handlerIO;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        getMachine().subscribeServerTick(stressSub, () -> {
            KineticBlockEntity kbe = ((ITieredKineticMachine) getMachine()).getKineticBlockEntity();
            if(!(kbe instanceof TieredGeneratingKineticMachineBlockEntity)) {
                float impact = ((MixinKineticBlockEntityAccessor) kbe).getStress();
                if(impact != lastOutputStress) {
                    lastOutputStress = impact;
                    notifyListeners();
                }
            }
        });
    }

    @Override
    public void onMachineUnload() {
        if(stressSub != null) {
            stressSub.unsubscribe();
            stressSub = null;
        }
        super.onMachineUnload();
    }

    @Override
    public @NotNull List<Float> handleRecipeInner(IO io, GTRecipe gtRecipe, List<Float> left, boolean simulate) {
        float remaining = left.stream().reduce(0F, Float::sum);
        ITieredKineticMachine kineticMachine = (ITieredKineticMachine) getMachine();
        if(io == IO.IN) {
            float extracted = Math.min(remaining, lastOutputStress);
            if(kineticMachine.getKineticBlockEntity().getSpeed() == 0) {
                extracted = 0;
            }
            remaining -= extracted;
        } else if(io == IO.OUT) {
            if(!simulate) {
                TieredGeneratingKineticMachineBlockEntity generator = (TieredGeneratingKineticMachineBlockEntity) kineticMachine.getKineticBlockEntity();
                generator.setRecipeRPM(16f); //todo:based on recipe
                generator.setRecipeStress(remaining * generator.getRecipeRPM());
                generator.updateGeneratedRotation();
                generator.notifyUpdate();
                if(generator.getOrCreateNetwork() != null) {
                    generator.getOrCreateNetwork().updateCapacityFor(generator, remaining);
                    notifyListeners();
                }
            }
            remaining = 0;
        }
        return remaining <= 0 ? Collections.emptyList() : Collections.singletonList(remaining);
    }

    @Override
    public @NotNull List<Object> getContents() {
        return List.of(this.lastOutputStress);
    }

    @Override
    public double getTotalContentAmount() {
        return this.lastOutputStress;
    }

    @Override
    public RecipeCapability<Float> getCapability() {
        return StressRecipeCapability.STRESS_CAPABILITY;
    }
}
