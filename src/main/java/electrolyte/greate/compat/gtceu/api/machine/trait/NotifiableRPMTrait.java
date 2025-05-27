package electrolyte.greate.compat.gtceu.api.machine.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.ICapabilityTrait;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import electrolyte.greate.compat.gtceu.api.capability.recipe.RPMRecipeCapability;
import electrolyte.greate.compat.gtceu.common.machine.kinetic.IKineticMachine;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class NotifiableRPMTrait extends NotifiableRecipeHandlerTrait<Float> implements ICapabilityTrait {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(NotifiableRPMTrait.class, NotifiableRecipeHandlerTrait.MANAGED_FIELD_HOLDER);

    @Getter
    @Setter
    private long timeStamp;

    @Getter
    public final IO handlerIO;

    @Getter
    public final IO capabilityIO;
    private float available, lastSpeed;

    public NotifiableRPMTrait(MetaMachine machine, IO handlerIO, IO capabilityIO) {
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
        float requiredRPM = list.stream().reduce(0f, Float::sum);
        var kineticDef = km.getKineticDefinition();
        if(io == IO.IN && !kineticDef.isSource()) {
            float currentRPM = Mth.abs(km.getKineticHolder().getSpeed());
            if(currentRPM > 0) requiredRPM = requiredRPM - currentRPM;
        } else if(io == IO.OUT && kineticDef.isSource()) {
            if(simulate) {
                //kineticDef.setTorque((float) gtRecipe.getTickOutputContents(RPMRecipeCapability.RPM_CAPABILITY).get(0).getContent());
                available = km.getKineticHolder().scheduleWorkingRPM((float) gtRecipe.getTickOutputContents(RPMRecipeCapability.RPM_CAPABILITY).get(0).getContent(), true);
            }
            requiredRPM -= available;
        }
        return requiredRPM <= 0 ? null : Collections.singletonList(requiredRPM);
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
        return RPMRecipeCapability.RPM_CAPABILITY;
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
}
