package electrolyte.greate;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GreateConfig {

    public static final ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.DoubleValue ULS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue LS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue MS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue HS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue ES_CAPACITY;
    public static ForgeConfigSpec.DoubleValue IS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue LUS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue ZPMS_CAPACITY;
    public static ForgeConfigSpec.DoubleValue US_CAPACITY;
    public static ForgeConfigSpec.DoubleValue UHS_CAPACITY;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment("Server Settings").push("server_settings");
        ULS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for ULS shafts and cogwheels.")
                .defineInRange("uls_capacity", 8, 1, Double.MAX_VALUE);
        LS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for LS shafts and cogwheels.")
                .defineInRange("ls_capacity", 32, 1, Double.MAX_VALUE);
        MS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for MS shafts and cogwheels.")
                .defineInRange("ms_capacity", 128, 1, Double.MAX_VALUE);
        HS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for HS shafts and cogwheels.")
                .defineInRange("hs_capacity", 512, 1, Double.MAX_VALUE);
        ES_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for ES shafts and cogwheels.")
                .defineInRange("es_capacity", 2048, 1, Double.MAX_VALUE);
        IS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for IS shafts and cogwheels.")
                .defineInRange("is_capacity", 8192, 1, Double.MAX_VALUE);
        LUS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for LUS shafts and cogwheels.")
                .defineInRange("lus_capacity", 32768, 1, Double.MAX_VALUE);
        ZPMS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for ZPMS shafts and cogwheels.")
                .defineInRange("zpms_capacity", 131072, 1, Double.MAX_VALUE);
        US_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for US shafts and cogwheels.")
                .defineInRange("us_capacity", 524288, 1, Double.MAX_VALUE);
        UHS_CAPACITY = SERVER_BUILDER
                .comment("Stress capacity multiplier for UHS shafts and cogwheels.")
                .defineInRange("uhs_capacity", 2097152, 1, Double.MAX_VALUE);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
