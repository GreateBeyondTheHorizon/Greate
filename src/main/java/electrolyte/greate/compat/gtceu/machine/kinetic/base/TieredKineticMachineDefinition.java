package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class TieredKineticMachineDefinition extends MachineDefinition {

    public TieredKineticMachineDefinition(ResourceLocation id, Supplier<? extends Block> block) {
        super(id);
        this.setBlockSupplier(block);
    }
}
