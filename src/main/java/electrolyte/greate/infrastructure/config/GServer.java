package electrolyte.greate.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class GServer extends ConfigBase {

    public final GKinetics kinetics = nested(0, GKinetics::new, "Parameters and abilities of Greate's kinetic mechanisms");

    @Override
    public String getName() {
        return "server";
    }
}
