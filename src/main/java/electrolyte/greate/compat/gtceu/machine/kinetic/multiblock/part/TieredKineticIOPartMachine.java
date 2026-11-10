package electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic.Status;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.IRotate.StressImpact;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredGeneratingKineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticInputBoxBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.NotifiableStressContainer;
import electrolyte.greate.mixin.kinetictrait.MixinKineticBlockEntityAccessor;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;

public class TieredKineticIOPartMachine extends TieredIOPartMachine implements ITieredMachine, ITieredKineticMachine, IHaveGoggleInformation {

    @Getter
    @SaveField
    protected final NotifiableStressContainer stressContainer;

    @Getter
    @SaveField
    @SyncToClient
    protected final KineticMachineTrait kineticMachineTrait;

    public TieredKineticIOPartMachine(BlockEntityCreationInfo info, int tier, IO io, NotifiableStressContainer stressContainer, KineticMachineTrait kineticTrait) {
        super(info, tier, io);
        this.stressContainer = attachTrait(stressContainer);
        this.kineticMachineTrait = attachTrait(kineticTrait);
    }

    public TieredKineticIOPartMachine(BlockEntityCreationInfo info, int tier, IO io, KineticBlockEntity kbe) {
        this(info, tier, io, new NotifiableStressContainer(io), new KineticMachineTrait(kbe));
    }

    @Override
    public void clientTick() {
        super.clientTick();
        kineticMachineTrait.getKineticBlockEntity().tick();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if(this.io == IO.IN) {
            boolean added = false;
            if (!StressImpact.isEnabled()) return added;
            float stressAtBase = ((MixinKineticBlockEntityAccessor) kineticMachineTrait.getKineticBlockEntity()).getStressApplied();
            if (!Mth.equal(stressAtBase, 0)) {
                CreateLang.translate("gui.goggles.kinetic_stats").forGoggles(tooltip);
                ((MixinKineticBlockEntityAccessor) kineticMachineTrait.getKineticBlockEntity()).addStressStats(tooltip, stressAtBase);
            }
        }
        return kineticMachineTrait.getKineticBlockEntity().addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    @Override
    public void recipeLogicStatusChanged(Status oldStatus, Status newStatus) {
        if(newStatus == Status.SUSPEND || newStatus == Status.WAITING) {
            if(this.kineticMachineTrait.getKineticBlockEntity() instanceof TieredGeneratingKineticBlockEntity gkbe) {
                gkbe.setRecipeRPM(0);
                gkbe.setRecipeStress(0);

                gkbe.updateGeneratedRotation();
                this.stressContainer.notifyListeners();
            } else if(this.kineticMachineTrait.getKineticBlockEntity() instanceof TieredKineticInputBoxBlockEntity kbe) {
                kbe.setRecipeStress(0);
                ((MixinKineticBlockEntityAccessor) kbe).setStressApplied(0);
                if(kbe.getOrCreateNetwork() != null) {
                    kbe.getOrCreateNetwork().updateStressFor(kbe, 0);
                }
            }
        }
    }
}
