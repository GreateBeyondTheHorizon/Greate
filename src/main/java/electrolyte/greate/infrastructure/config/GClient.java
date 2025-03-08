package electrolyte.greate.infrastructure.config;


import net.createmod.catnip.config.ConfigBase;

public class GClient extends ConfigBase {

    public final ConfigBool enableWireFactoryWarning = b(true, "enableWireFactoryWarning");

    @Override
    public String getName() {
        return "client";
    }
}
