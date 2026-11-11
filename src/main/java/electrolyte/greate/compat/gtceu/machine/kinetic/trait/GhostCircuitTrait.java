package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import lombok.Getter;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;

public class GhostCircuitTrait extends MachineTrait {

    @SyncToClient
    @SaveField
    @Getter
    private int circuitValue = 0;

    private TickableSubscription circuitSub;

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        getMachine().getTrait(KineticMachineTrait.class).getKineticBlockEntity().getBehaviour(ScrollValueBehaviour.TYPE).setValue(circuitValue);
        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateCircuitSubscription));
        }
    }

    @Override
    public void onMachineUnload() {
        if(circuitSub != null) {
            circuitSub.unsubscribe();
            circuitSub = null;
        }
        super.onMachineUnload();
    }

    private void updateCircuitSubscription() {
        circuitSub = subscribeServerTick(circuitSub, () -> {
            ScrollValueBehaviour scrollValueBehaviour = getMachine().getTrait(KineticMachineTrait.class).getKineticBlockEntity().getBehaviour(ScrollValueBehaviour.TYPE);
            if(this.circuitValue != scrollValueBehaviour.getValue()) {
                this.circuitValue = scrollValueBehaviour.getValue();
                getSyncDataHolder().markClientSyncFieldDirty("circuitValue");
            }
        });
    }
}
