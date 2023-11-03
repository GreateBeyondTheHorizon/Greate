package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GClient extends ConfigBase {

    public final ConfigBool warningTooltip = b(true, "warningTooltip", "Show/Hide conversion tooltip on certain create items");

    @Override
    public String getName() {
        return "client";
    }
}
