package electrolyte.greate.compat.gtceu.machine.kinetic.trait;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import lombok.Getter;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

public class FilterStackTrait extends MachineTrait {

    @SyncToClient
    @SaveField
    @Getter
    private ItemStack filterStack = ItemStack.EMPTY;

    private TickableSubscription filterSub;

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        if(filterStack != null && !filterStack.isEmpty()) {
            getMachine().getTrait(KineticMachineTrait.class).getKineticBlockEntity().getBehaviour(FilteringBehaviour.TYPE).setFilter(filterStack);
        }
        if(getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateFilterSubscription));
        }
    }

    @Override
    public void onMachineUnload() {
        if(filterSub != null) {
            filterSub.unsubscribe();
            filterSub = null;
        }
        super.onMachineUnload();
    }

    private void updateFilterSubscription() {
        filterSub = subscribeServerTick(filterSub, () -> {FilteringBehaviour filteringBehaviour = getMachine().getTrait(KineticMachineTrait.class).getKineticBlockEntity().getBehaviour(FilteringBehaviour.TYPE);
            if(!this.filterStack.is(filteringBehaviour.getFilter().getItem())) {
                this.filterStack = filteringBehaviour.getFilter();
                getSyncDataHolder().markClientSyncFieldDirty("filterStack");
            }
        });
    }
}
