package electrolyte.greate.compat.gtceu.api.machine;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.recipelogic.WorkableStressElectricMultiblockMachineRecipeLogic;

public class WorkableStressElectricMultiblockMachine extends WorkableElectricMultiblockMachine {
    public WorkableStressElectricMultiblockMachine(BlockEntityCreationInfo info) {
        super(info, new WorkableStressElectricMultiblockMachineRecipeLogic());
    }
}
