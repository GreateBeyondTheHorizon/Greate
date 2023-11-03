package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GTier extends ConfigBase {

    public final ConfigGroup andesite = group(1, "andesite", formatGroupComment("andesite"));
    public final ConfigInt andesiteMaxCapacity = i(8, "andesiteMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup steel = group(1, "steel", formatGroupComment("steel"));
    public final ConfigInt steelMaxCapacity = i(32, "steelMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup aluminium = group(1, "aluminium", formatGroupComment("aluminium"));
    public final ConfigInt aluminiumMaxCapacity = i(128, "aluminiumMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup stainlessSteel = group(1, "stainlessSteel", formatGroupComment("stainless steel"));
    public final ConfigInt stainlessSteelMaxCapacity = i(512, "stainlessSteelMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup titanium = group(1, "titanium", formatGroupComment("titanium"));
    public final ConfigInt titaniumMaxCapacity = i(2048, "titaniumMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup tungstensteel = group(1, "tungstensteel", formatGroupComment("tungstensteel"));
    public final ConfigInt tungstensteelMaxCapacity = i(8192, "tungstensteelMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup palladium = group(1, "palladium", formatGroupComment("palladium"));
    public final ConfigInt palladiumMaxCapacity = i(32768, "palladiumMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup naquadah = group(1, "naquadah", formatGroupComment("naquadah"));
    public final ConfigInt naquadahMaxCapacity = i(131072, "naquadahMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup darmstadtium = group(1, "darmstadtium", formatGroupComment("darmstadtium"));
    public final ConfigInt darmstadtiumMaxCapacity = i(524288, "darmstadtiumMaxCapacity", Comments.maxCapacity);
    public final ConfigGroup neutronium = group(1, "neutronium", formatGroupComment("neutronium"));
    public final ConfigInt neutroniumMaxCapacity = i(2097152, "neutroniumMaxCapacity", Comments.maxCapacity);


    @Override
    public String getName() {
        return "maxCapacity";
    }

    private String formatGroupComment(String tier) {
        return String.format("Settings related to %s tier machines", tier);
    }

    private static class Comments {
        static String maxCapacity = "Configure the max stress a kinetic block of this tier can support.";
    }
}
