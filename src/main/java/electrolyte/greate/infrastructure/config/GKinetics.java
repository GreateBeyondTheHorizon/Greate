package electrolyte.greate.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class GKinetics extends ConfigBase {

    public final GStress stressValues = nested(0, GStress::new, "Fine tune the kinetic stats of individual components");
    public final GPumps pumpValues = nested(-1, GPumps::new, "Fine tune settings related to pumps");

    public final ConfigInt maxWaterWheelCount = i(8, -1, "maxWaterWheelCount", "Maximum amount of Water Wheels allowed on a Kinetic Network; Set to -1 to allow infinite.");
    public final ConfigInt maxLargeWaterWheelCount = i(8, -1, "maxLargeWaterWheelCount", "Maximum amount of Large Water Wheels allowed on a Kinetic Network; Set to -1 to allow infinite.");

    @Override
    public String getName() {
        return "kinetics";
    }
}
