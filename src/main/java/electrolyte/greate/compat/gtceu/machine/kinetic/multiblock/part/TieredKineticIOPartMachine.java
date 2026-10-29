package electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.NotifiableStressContainer;
import lombok.Getter;
import net.minecraft.network.chat.Component;

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
        return kineticMachineTrait.getKineticBlockEntity().addToGoggleTooltip(tooltip, isPlayerSneaking);
    }
}
