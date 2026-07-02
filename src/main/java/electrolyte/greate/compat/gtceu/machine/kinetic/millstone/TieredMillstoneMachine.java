package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;

public class TieredMillstoneMachine extends TieredKineticWorkableMachine {

    @SaveField
    @SyncToClient
    public final NotifiableItemStackHandler importItems, exportItems;

    public TieredMillstoneMachine(BlockEntityCreationInfo info, int tier) {
        super(info, tier, new TieredMillstoneMachineBlockEntity(info.type(), info.pos(), info.state()));
        this.importItems = attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.IN));
        this.exportItems = attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxOutputs(ItemRecipeCapability.CAP), IO.OUT));
    }

    @Override
    public void onMachineDestroyed() {
        super.onMachineDestroyed();
        importItems.dropInventoryInWorld();
        exportItems.dropInventoryInWorld();
    }

    @Override
    public void afterWorking() {
        this.kineticMachineTrait.getKineticBlockEntity().award(AllAdvancements.MILLSTONE);
    }
}
