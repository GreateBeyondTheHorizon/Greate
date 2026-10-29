package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

@MethodsReturnNonnullByDefault
public class KineticMachineTrait extends MachineTrait {

    @SyncToClient
    @Getter
    private KineticBlockEntity kineticBlockEntity;
    private TickableSubscription kineticsSub;

    //TODO: move to custom trait (need both for mech press/mixer too, in addition to saw)
    @SyncToClient
    @SaveField
    @Getter
    private ItemStack filterStack = ItemStack.EMPTY;

    @SyncToClient
    @SaveField
    @Getter
    private int circuitValue = 0;

    public KineticMachineTrait(KineticBlockEntity kineticBlockEntity) {
        this.kineticBlockEntity = kineticBlockEntity;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        kineticBlockEntity.setLevel(getLevel());
        kineticBlockEntity.initialize();
        for(BlockEntityBehaviour behaviour : kineticBlockEntity.getAllBehaviours()) {
            if(behaviour instanceof FilteringBehaviour filteringBehaviour) {
                if(filterStack != null && !filterStack.isEmpty()) filteringBehaviour.setFilter(filterStack);
            } if(behaviour instanceof ScrollValueBehaviour scrollValueBehaviour) {
                scrollValueBehaviour.setValue(circuitValue);
            }
        }

        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateKineticsSubscription));
        }
    }

    @Override
    public void onMachineUnload() {
        if(kineticsSub != null) {
            kineticsSub.unsubscribe();
            kineticsSub = null;
        }
        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, () -> RotationPropagator.handleRemoved(getLevel(), getBlockPos(), kineticBlockEntity)));
        }
        super.onMachineUnload();
    }

    @Override
    public void onMachineDestroyed() {
        this.getKineticBlockEntity().remove();
        super.onMachineDestroyed();
    }

    public void updateKineticsSubscription() {
        kineticsSub = subscribeServerTick(kineticsSub, () -> {
            kineticBlockEntity.tick();
            getSyncDataHolder().markClientSyncFieldDirty("kineticBlockEntity");

            for(BlockEntityBehaviour behaviour : this.getKineticBlockEntity().getAllBehaviours()) {
                if(behaviour instanceof FilteringBehaviour filteringBehaviour) {
                    if(!this.filterStack.is(filteringBehaviour.getFilter().getItem())) {
                        this.filterStack = filteringBehaviour.getFilter();
                        getSyncDataHolder().markClientSyncFieldDirty("filterStack");
                    }
                } if(behaviour instanceof ScrollValueBehaviour scrollValueBehaviour) {
                    if(this.circuitValue != scrollValueBehaviour.getValue()) {
                        this.circuitValue = scrollValueBehaviour.getValue();
                        getSyncDataHolder().markClientSyncFieldDirty("circuitValue");
                    }
                }
            }
        });
    }
}
