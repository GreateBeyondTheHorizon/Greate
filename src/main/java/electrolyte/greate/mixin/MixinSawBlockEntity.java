package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SawBlockEntity.class)
public abstract class MixinSawBlockEntity {

    @Shadow(remap = false) private void applyRecipe() { throw new IllegalStateException("Mixin did not apply!"); }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/saw/SawBlockEntity;applyRecipe()V"), remap = false)
    private void greate_tick(SawBlockEntity be) {
        if(be instanceof TieredSawBlockEntity tsbe) {
            tsbe.applyValidRecipe();
        } else applyRecipe();
    }
}
