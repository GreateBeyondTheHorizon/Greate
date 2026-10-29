package electrolyte.greate.compat.gtceu.machine.kinetic.millstone;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.notifiable.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticWorkableMachine;

public class TieredMillstoneMachine extends TieredKineticWorkableMachine {

    @SaveField
    public final NotifiableItemStackHandler importItems, exportItems;

    public TieredMillstoneMachine(BlockEntityCreationInfo info) {
        super(info, new TieredMillstoneMachineBlockEntity(info.type(), info.pos(), info.state()));
        this.importItems = attachTrait(new NotifiableItemStackHandler(getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.IN));
        this.exportItems = attachTrait(new NotifiableItemStackHandler(switch(getTier()) {
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 3;
            default -> 4;
        }, IO.OUT));
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        this.kineticMachineTrait.getKineticBlockEntity().award(AllAdvancements.MILLSTONE);
    }
}
