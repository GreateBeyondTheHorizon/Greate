package electrolyte.greate;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.Configurable.Comment;
import dev.toma.configuration.config.Configurable.Range;
import dev.toma.configuration.config.Configurable.Synchronized;

@Config(id = Greate.MOD_ID)
public class GreateConfig {

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for ULS tier machines")
    public double ULS_CAPACITY = 8;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for LS tier machines")
    public double LS_CAPACITY = 32;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for MS tier machines")
    public double MS_CAPACITY = 128;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for HS tier machines")
    public double HS_CAPACITY = 512;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for ES tier machines")
    public double ES_CAPACITY = 2048;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for IS tier machines")
    public double IS_CAPACITY = 8192;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for LuS tier machines")
    public double LUS_CAPACITY = 32768;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for ZPMS tier machines")
    public double ZPMS_CAPACITY = 131072;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for US tier machines")
    public double US_CAPACITY = 524288;

    @Configurable
    @Synchronized
    @Range(min = 1)
    @Comment("Maximum stress capacity for UHS tier machines")
    public double UHS_CAPACITY = 2097152;
}
