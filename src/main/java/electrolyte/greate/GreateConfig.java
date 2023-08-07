package electrolyte.greate;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GreateConfig {

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.DoubleValue LC_MAX;
    public static ForgeConfigSpec.DoubleValue MC_MAX;
    public static ForgeConfigSpec.DoubleValue HC_MAX;
    public static ForgeConfigSpec.DoubleValue EC_MAX;
    public static ForgeConfigSpec.DoubleValue IC_MAX;
    public static ForgeConfigSpec.DoubleValue LUC_MAX;
    public static ForgeConfigSpec.DoubleValue ZPMC_MAX;
    public static ForgeConfigSpec.DoubleValue UC_MAX;
    public static ForgeConfigSpec.DoubleValue UHC_MAX;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment("Server Settings").push("server_settings");
        LC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for LC shafts.")
                .defineInRange("lc_max", 32, 1, Double.MAX_VALUE);
        MC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for MC shafts.")
                .defineInRange("mc_max", 128, 1, Double.MAX_VALUE);
        HC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for HC shafts.")
                .defineInRange("hc_max", 512, 1, Double.MAX_VALUE);
        EC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for EC shafts.")
                .defineInRange("ec_max", 2048, 1, Double.MAX_VALUE);
        IC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for IC shafts.")
                .defineInRange("ic_max", 8192, 1, Double.MAX_VALUE);
        LUC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for LUC shafts.")
                .defineInRange("luc_max", 32768, 1, Double.MAX_VALUE);
        ZPMC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for ZPMC shafts.")
                .defineInRange("zpmc_max", 131072, 1, Double.MAX_VALUE);
        UC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for UC shafts.")
                .defineInRange("uc_max", 524288, 1, Double.MAX_VALUE);
        UHC_MAX = SERVER_BUILDER
                .comment("Maximum capacity for UHC shafts.")
                .defineInRange("uhc_max", Double.MAX_VALUE, 1, Double.MAX_VALUE);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
