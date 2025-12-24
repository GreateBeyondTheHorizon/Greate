package electrolyte.greate.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class GKinetics extends ConfigBase {

    public final GStress stressValues = nested(0, GStress::new, "Fine tune the kinetic stats of individual components");
    public final GPumps pumpValues = nested(-1, GPumps::new, "Fine tune settings related to pumps");

    @Override
    public String getName() {
        return "kinetics";
    }
}
