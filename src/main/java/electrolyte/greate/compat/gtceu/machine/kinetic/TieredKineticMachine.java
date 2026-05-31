package electrolyte.greate.compat.gtceu.machine.kinetic;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.KineticMachineTrait;
import electrolyte.greate.compat.gtceu.machine.kinetic.trait.NotifiableStressContainer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredKineticMachine extends MetaMachine implements ITieredMachine, IHaveGoggleInformation {

    private final int tier;

    @SaveField
    public final NotifiableStressContainer stressContainer;

    @SaveField
    @SyncToClient
    public final KineticMachineTrait kineticMachineTrait;


    public TieredKineticMachine(BlockEntityCreationInfo info, int tier, IO io) {
        super(info);
        this.tier = tier;
        this.stressContainer = attachTrait(new NotifiableStressContainer(io));
        this.kineticMachineTrait = attachTrait(new KineticMachineTrait());
    }

    @Override
    public void clientTick() {
        super.clientTick();
        kineticMachineTrait.getKineticBlockEntity().tick();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return kineticMachineTrait.getKineticBlockEntity().addToGoggleTooltip(tooltip, isPlayerSneaking);
    }
}
