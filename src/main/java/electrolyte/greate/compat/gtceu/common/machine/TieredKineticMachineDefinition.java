package electrolyte.greate.compat.gtceu.common.machine;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;

@Accessors(chain = true)
public class TieredKineticMachineDefinition extends MachineDefinition {

    @Getter
    public final boolean isSource;

    @Getter
    @Setter
    public float torque;

    @Getter
    @Setter
    public boolean frontRotation;

    public TieredKineticMachineDefinition(ResourceLocation id, boolean isSource, float torque) {
        super(id);
        this.isSource = isSource;
        this.torque = torque;
    }
}
