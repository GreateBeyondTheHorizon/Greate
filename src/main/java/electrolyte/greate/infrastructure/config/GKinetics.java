package electrolyte.greate.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class GKinetics extends ConfigBase {

    public final GStress stressValues = nested(0, GStress::new, "Fine tune the kinetic stats of individual components");
    public final GTier tierValues = nested(1, GTier::new, "Fine tune settings related to tiers");
    public final GPumps pumpValues = nested(-1, GPumps::new, "Fine tune settings related to pumps");

    public final ConfigFloat fanSpeedMultiplier = f(0.75f, 0.0f, "fanSpeedMultiplier", "Multiplier used for calculating how many ticks should initially be removed in fan processing recipes, based on how fast the fan is spinning.");

    @Override
    public String getName() {
        return "kinetics";
    }
}
