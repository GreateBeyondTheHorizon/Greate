package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GServer extends ConfigBase {

    public final GKinetics kinetics = nested(0, GKinetics::new, "Parameters and abilities of Greate's kinetic mechanisms");

    @Override
    public String getName() {
        return "server";
    }
}
