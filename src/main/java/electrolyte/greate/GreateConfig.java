package electrolyte.greate;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GreateConfig {

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.DoubleValue ULS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue LS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue MS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue HS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ES_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue IS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue LUS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ZPMS_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue US_CAPACITY_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue UHS_CAPACITY_MULTIPLIER;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment("Server Settings").push("server_settings");
        ULS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for ULS shafts and cogwheels.")
                .defineInRange("uls_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        LS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for LS shafts and cogwheels.")
                .defineInRange("ls_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        MS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for MS shafts and cogwheels.")
                .defineInRange("ms_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        HS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for HS shafts and cogwheels.")
                .defineInRange("hs_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        ES_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for ES shafts and cogwheels.")
                .defineInRange("es_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        IS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for IS shafts and cogwheels.")
                .defineInRange("is_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        LUS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for LUS shafts and cogwheels.")
                .defineInRange("lus_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        ZPMS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for ZPMS shafts and cogwheels.")
                .defineInRange("zpms_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        US_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for US shafts and cogwheels.")
                .defineInRange("us_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        UHS_CAPACITY_MULTIPLIER = SERVER_BUILDER
                .comment("Stress capacity multiplier for UHS shafts and cogwheels.")
                .defineInRange("uhs_capacity_multiplier", 1, 1, Double.MAX_VALUE);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
