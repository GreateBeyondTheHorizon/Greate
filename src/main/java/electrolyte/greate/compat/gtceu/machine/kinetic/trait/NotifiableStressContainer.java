package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import electrolyte.greate.compat.gtceu.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.mixin.kinetictrait.MixinKineticBlockEntityAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class NotifiableStressContainer extends NotifiableRecipeHandlerTrait<Float> /*implements ITieredKineticMachine*/ {

    protected IO handlerIO;
    protected long lastTimeStamp;
    private float currentOutputStress, lastOutputStress;

    public NotifiableStressContainer(IO handlerIO) {
        super();
        this.handlerIO = handlerIO;
        this.lastTimeStamp = Long.MIN_VALUE;
    }

    @Override
    public IO getHandlerIO() {
        return this.handlerIO;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        if(getMachine() instanceof TieredKineticMachine tkm) {
            tkm.subscribeServerTick(() -> {
                float impact = ((MixinKineticBlockEntityAccessor) tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity()).getStress();
                if(impact != lastOutputStress) lastOutputStress = impact;
            });
        }
    }

    @Override
    public @NotNull List<Float> handleRecipeInner(IO io, GTRecipe gtRecipe, List<Float> left, boolean simulate) {
        float remaining = left.stream().reduce(0F, Float::sum);
        if(io == IO.IN) {
            float extracted = Math.min(remaining, lastOutputStress);
            if(getMachine() instanceof TieredKineticMachine tkm) {
                if(tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity().getSpeed() == 0) {
                    extracted = 0;
                }
            }
            //if(!simulate) updateStress(extracted);
            remaining -= extracted;
        } else if(io == IO.OUT) {
            float inserted = Math.min(remaining, lastOutputStress);
            if(getMachine() instanceof TieredKineticMachine tkm) {
                if(tkm.getTraitHolder().getTrait(KineticMachineTrait.class).getKineticBlockEntity().getSpeed() == 0) {
                    inserted = 0;
                }
            }
            //if(!simulate) updateCapacity(inserted);
            remaining -= inserted;
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

    /*@Override
    public void updateStress(float newStress) {
        //KineticNetwork network = this.getKineticHolder().getKineticBlockEntity().getOrCreateNetwork();
        //network.updateStressFor(this.getKineticHolder().getKineticBlockEntity(), newStress);
        notifyListeners();
    }

    @Override
    public void updateCapacity(float newCapacity) {
        //KineticNetwork network = this.getKineticHolder().getKineticBlockEntity().getOrCreateNetwork();
        //network.updateCapacityFor(this.getKineticHolder().getKineticBlockEntity(), newCapacity);
        notifyListeners();
    }
     */
}
