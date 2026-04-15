package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.MachineTraitType;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.simibubi.create.content.kinetics.KineticNetwork;
import electrolyte.greate.compat.gtceu.machine.kinetic.ITieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.TieredKineticWorkableMachine;
import electrolyte.greate.compat.gtceu.recipe.StressRecipeCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class NotifiableStressContainer extends NotifiableRecipeHandlerTrait<Float> implements ITieredKineticMachine {

    public static final MachineTraitType<NotifiableStressContainer> TYPE = new MachineTraitType<>(NotifiableStressContainer.class);
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
                float speed = tkm.getTraitHolder().getTrait(KineticMachineTrait.TYPE).getKineticBlockEntity().getSpeed();
                if(speed != lastOutputStress) lastOutputStress = speed;
            });
        }
    }

    @Override
    public List<Float> handleRecipeInner(IO io, GTRecipe gtRecipe, List<Float> left, boolean simulate) {
        float remaining = left.stream().reduce(0F, Float::sum);
        if(io == IO.IN) {
            float extracted = Math.min(remaining, lastOutputStress);
            if(!simulate) updateStress(extracted);
            remaining -= extracted;
        } else if(io == IO.OUT) {
            float inserted = Math.min(remaining, lastOutputStress);
            if(!simulate) updateCapacity(inserted);
            remaining -= inserted;
        }
        return remaining <= 0 ? null : Collections.singletonList(remaining);
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
        return StressRecipeCapability.CAP;
    }

    @Override
    public MachineTraitType<?> getTraitType() {
        return TYPE;
    }

    @Override
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
}
