package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.base.KineticBlockEntityAccessor;
import electrolyte.greate.content.kinetics.waterWheel.WaterWheelBreaker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GeneratingKineticBlockEntity.class)
public class MixinGeneratingKineticBlockEntity {

    @Inject(method = "addToGoggleTooltip", at = @At("RETURN"), remap = false)
    private void greate_addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        if(((KineticBlockEntity) (Object) this) instanceof LargeWaterWheelBlockEntity be && be.hasNetwork() && WaterWheelBreaker.getMaxLargeWaterWheelLimit() != Integer.MAX_VALUE) {
            CreateLang.builder(Greate.MOD_ID).translate("tooltip.large_water_wheel_count", ((KineticBlockEntityAccessor) be).greate_getLargeWaterWheelCount(), WaterWheelBreaker.getMaxLargeWaterWheelLimit()).style(ChatFormatting.GRAY).forGoggles(tooltip);
        } else if(((KineticBlockEntity) (Object) this) instanceof WaterWheelBlockEntity be && be.hasNetwork() && WaterWheelBreaker.getMaxWaterWheelLimit() != Integer.MAX_VALUE) {
            CreateLang.builder(Greate.MOD_ID).translate("tooltip.water_wheel_count", ((KineticBlockEntityAccessor) be).greate_getWaterWheelCount(), WaterWheelBreaker.getMaxWaterWheelLimit()).style(ChatFormatting.GRAY).forGoggles(tooltip);
        }
    }
}
