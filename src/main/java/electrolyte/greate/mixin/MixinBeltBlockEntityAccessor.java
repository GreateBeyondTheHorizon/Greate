package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.BeltInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeltBlockEntity.class)
public interface MixinBeltBlockEntityAccessor {

    @Accessor(value = "inventory", remap = false)
    BeltInventory getInventoryField();

    @Accessor(value = "inventory", remap = false)
    void setInventoryField(BeltInventory inventory);
}
