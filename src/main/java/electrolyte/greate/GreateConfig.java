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
    @Comment("Settings related to ULS tier Machines")
    public ULS ULS = new ULS();

    @Configurable
    @Synchronized
    @Comment("Settings related to LS tier Machines")
    public LS LS = new LS();

    @Configurable
    @Synchronized
    @Comment("Settings related to MS tier Machines")
    public MS MS = new MS();

    @Configurable
    @Synchronized
    @Comment("Settings related to HS tier Machines")
    public HS HS = new HS();

    @Configurable
    @Synchronized
    @Comment("Settings related to ES tier Machines")
    public ES ES = new ES();

    @Configurable
    @Synchronized
    @Comment("Settings related to IS tier Machines")
    public IS IS = new IS();

    @Configurable
    @Synchronized
    @Comment("Settings related to LUS tier Machines")
    public LUS LUS = new LUS();

    @Configurable
    @Synchronized
    @Comment("Settings related to ZPM tier Machines")
    public ZPM ZPM = new ZPM();

    @Configurable
    @Synchronized
    @Comment("Settings related to US tier Machines")
    public US US = new US();

    @Configurable
    @Synchronized
    @Comment("Settings related to UHS tier Machines")
    public UHS UHS = new UHS();

    public static class ULS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for ULS tier machines")
        public double CAPACITY = 8;
        
        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a ULS tier millstone")
        public double MILLSTONE_IMPACT = 0.5;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a ULS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 0.5;
    }

    public static class LS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for LS tier machines")
        public double CAPACITY = 32;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a LS tier millstone")
        public double MILLSTONE_IMPACT = 1.0;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a LS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 1.0;
    }

    public static class MS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for MS tier machines")
        public double CAPACITY = 128;

        @Configurable
        @Synchronized
        @Comment("Stress impact for a MS tier millstone")
        @Range(min = 0)
        public double MILLSTONE_IMPACT = 1.5;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a MS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 1.5;
    }

    public static class HS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for HS tier machines")
        public double CAPACITY = 512;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a HS tier millstone")
        public double MILLSTONE_IMPACT = 2.0;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a HS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 2.0;
    }

    public static class ES {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for ES tier machines")
        public double CAPACITY = 2048;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a ES tier millstone")
        public double MILLSTONE_IMPACT = 2.5;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a ES tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 2.5;
    }

    public static class IS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for IS tier machines")
        public double CAPACITY = 8192;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a IS tier millstone")
        public double MILLSTONE_IMPACT = 3.0;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a IS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 3.0;

    }

    public static class LUS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for LuS tier machines")
        public double CAPACITY = 32768;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a LUS tier millstone")
        public double MILLSTONE_IMPACT = 3.5;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a LUS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 3.5;
    }

    public static class ZPM {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for ZPM tier machines")
        public double CAPACITY = 131072;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a ZPM tier millstone")
        public double MILLSTONE_IMPACT = 4.0;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a ZPM tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 4.0;
    }

    public static class US {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for US tier machines")
        public double CAPACITY = 524288;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a US tier millstone")
        public double MILLSTONE_IMPACT = 4.5;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a US tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 4.5;
    }

    public static class UHS {
        @Configurable
        @Synchronized
        @Range(min = 1)
        @Comment("Maximum stress capacity for UHS tier machines")
        public double CAPACITY = 2097152;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a UHS tier millstone")
        public double MILLSTONE_IMPACT = 5.0;

        @Configurable
        @Synchronized
        @Range(min = 0)
        @Comment("Stress impact for a UHS tier crushing wheel")
        public double CRUSHING_WHEEL_IMPACT = 5.0;
    }
}
