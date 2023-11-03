package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GBelts extends ConfigBase {

    public final ConfigGroup rubber = group(0, "rubber", "Rubber Belt Settings");
    public final ConfigInt rubberMaxBeltLength = i(20, 5, "rubberMaxBeltLength", "Maximum length in blocks of rubber mechanical belts");

    public final ConfigGroup silicone = group(0, "silicone", "Silicone Rubber Belt Settings");
    public final ConfigInt siliconeMaxBeltLength = i(20, 5, "siliconeMaxBeltLength", "Maximum length in blocks of silicone mechanical belts");

    public final ConfigGroup polyethylene = group(0, "polyethylene", "Polyethylene Belt Settings");
    public final ConfigInt polyethyleneMaxBeltLength = i(20, 5, "polyethyleneMaxBeltLength", "Maximum length in blocks of polyethylene mechanical belts");

    public final ConfigGroup polytetrafluoroethylene = group(0, "polytetrafluoroethylene", "Polytetrafluoroethylene Belt Settings");
    public final ConfigInt polytetrafluoroethyleneMaxBeltLength = i(20, 5, "polytetrafluoroethyleneMaxBeltLength", "Maximum length in blocks of polytetrafluoroethylene mechanical belts");

    public final ConfigGroup polybenzimidazole = group(0, "polybenzimidazole", "Polybenzimidazole Belt Settings");
    public final ConfigInt polybenzimidazoleMaxBeltLength = i(20, 5, "polybenzimidazoleMaxBeltLength", "Maximum length in blocks of polybenzimidazole mechanical belts");

    @Override
    public String getName() {
        return "belts";
    }
}
