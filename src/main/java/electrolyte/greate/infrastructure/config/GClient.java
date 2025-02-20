package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GClient extends ConfigBase {

    public final ConfigBool enableWireFactoryWarning = b(true, "enableWireFactoryWarning");

    @Override
    public String getName() {
        return "client";
    }
}
