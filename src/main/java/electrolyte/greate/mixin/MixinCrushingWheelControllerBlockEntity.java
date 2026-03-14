package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CrushingWheelControllerBlockEntity.class)
public abstract class MixinCrushingWheelControllerBlockEntity {

    @Shadow(remap = false) private void applyRecipe() { throw new IllegalStateException("Mixin did not apply!"); }

    @Shadow(remap = false) protected abstract void intakeItem(ItemEntity itemEntity);

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/crusher/CrushingWheelControllerBlockEntity;applyRecipe()V"), remap = false)
    private void greate_tick(CrushingWheelControllerBlockEntity cwbe) {
        if(cwbe instanceof TieredCrushingWheelControllerBlockEntity tcwbe) tcwbe.applyValidRecipe();
        else applyRecipe();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/crusher/CrushingWheelControllerBlockEntity;intakeItem(Lnet/minecraft/world/entity/item/ItemEntity;)V"), remap = false)
    private void greate_intakeItem(CrushingWheelControllerBlockEntity cwbe, ItemEntity itemEntity) {
        if(cwbe instanceof TieredCrushingWheelControllerBlockEntity tcwbe) tcwbe.intakeItems(itemEntity);
        else intakeItem(itemEntity);
    }
}
