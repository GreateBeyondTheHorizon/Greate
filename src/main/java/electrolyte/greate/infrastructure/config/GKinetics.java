package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GKinetics extends ConfigBase {

    public final GBelts beltValues = nested(0, GBelts::new,"Fine tune settings related to belts");
    public final GStress stressValues = nested(0, GStress::new, "Fine tune the kinetic stats of individual components");
    public final GTier tierValues = nested(1, GTier::new, "Fine tune settings related to tiers");
    public final GPumps pumpValues = nested(-1, GPumps::new, "Fine tune settings related to pumps");

    @Override
    public String getName() {
        return "kinetics";
    }
}
