package electrolyte.greate.compat.gtceu.api.machine.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.ICapabilityTrait;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import electrolyte.greate.compat.gtceu.api.capability.recipe.StressRecipeCapability;
import electrolyte.greate.compat.gtceu.common.machine.kinetic.IKineticMachine;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class NotifiableStressTrait extends NotifiableRecipeHandlerTrait<Float> implements ICapabilityTrait {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(NotifiableStressTrait.class, NotifiableRecipeHandlerTrait.MANAGED_FIELD_HOLDER);

    @Getter
    @Setter
    private long timeStamp;

    @Getter
    public final IO handlerIO;

    @Getter
    public final IO capabilityIO;
    private float available, lastSpeed;

    public NotifiableStressTrait(MetaMachine machine, IO handlerIO, IO capabilityIO) {
        super(machine);
        this.handlerIO = handlerIO;
        this.capabilityIO = capabilityIO;
        this.lastSpeed = 0;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        if(machine instanceof IKineticMachine km) {
            machine.subscribeServerTick(() -> {
                var speed = km.getKineticHolder().getSpeed();
                if(speed != lastSpeed) {
                    lastSpeed = speed;
                    notifyListeners();
                }
            });
        }
    }

    @Override
    public IO getHandlerIO() {
        return handlerIO;
    }

    @Override
    public List<Float> handleRecipeInner(IO io, GTRecipe gtRecipe, List<Float> list, @Nullable String slotName, boolean simulate) {
        if(!(machine instanceof IKineticMachine km)) return list;
        float requiredSU = list.stream().reduce(0f, Float::sum);
        var kineticDef = km.getKineticDefinition();
        if(io == IO.IN && !kineticDef.isSource()) {
            float generatedSU = km.getKineticHolder().getNetworkCapacity();
            if(generatedSU > 0) {
                if(!simulate) km.getKineticHolder().setStressApplied(requiredSU);
                requiredSU -= generatedSU;
            }
        } else if(io == IO.OUT && kineticDef.isSource()) {
            if(simulate) {
                available = km.getKineticHolder().scheduleWorkingStress(requiredSU, true);
            }
            requiredSU -= available;
        }
        return requiredSU <= 0 ? null : Collections.singletonList(requiredSU);
    }

    @Override
    public List<Object> getContents() {
        return List.of(available);
    }

    @Override
    public double getTotalContentAmount() {
        return available;
    }

    @Override
    public RecipeCapability<Float> getCapability() {
        return StressRecipeCapability.STRESS_CAPABILITY;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public IO getCapabilityIO() {
        return capabilityIO;
    }

    @Override
    public void preWorking(IRecipeCapabilityHolder holder, IO io, GTRecipe recipe) {
        if(machine instanceof IKineticMachine km) {
            var kineticDef = km.getKineticDefinition();
            if(available > 0 && kineticDef.isSource() && io == IO.OUT) {
                km.getKineticHolder().scheduleWorkingStress(available, false);
            }
        }
    }

    @Override
    public void postWorking(IRecipeCapabilityHolder holder, IO io, GTRecipe recipe) {
        if(machine instanceof IKineticMachine km) {
            var kineticDef = km.getKineticDefinition();
            if(kineticDef.isSource && io == IO.OUT) {
                km.getKineticHolder().stopWorking();
            }
        }
    }

    @Override
    public boolean isDistinct() {
        return false;
    }
}
