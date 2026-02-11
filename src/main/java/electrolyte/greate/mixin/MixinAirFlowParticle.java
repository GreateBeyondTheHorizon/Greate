package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.fan.AirFlowParticle;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType.AirFlowParticleAccess;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlockEntity;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredSplashingType;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AirFlowParticle.class)
public class MixinAirFlowParticle {

    @Shadow(remap = false) @Final private IAirCurrentSource source;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/fan/processing/FanProcessingType;morphAirFlow(Lcom/simibubi/create/content/kinetics/fan/processing/FanProcessingType$AirFlowParticleAccess;Lnet/minecraft/util/RandomSource;)V", remap = false))
    private void tick(FanProcessingType type, AirFlowParticleAccess airFlowParticleAccess, RandomSource randomSource) {
        if(type instanceof TieredSplashingType tst) {
            tst.morphAirFlow(airFlowParticleAccess, randomSource, (TieredEncasedFanBlockEntity) source.getAirCurrentWorld().getBlockEntity(source.getAirCurrentPos()));
        } else type.morphAirFlow(airFlowParticleAccess, randomSource);
    }
}
