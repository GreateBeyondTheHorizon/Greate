package electrolyte.greate.infrastructure.config;

import com.simibubi.create.content.kinetics.BlockStressValues;
import com.simibubi.create.foundation.config.ConfigBase;
import electrolyte.greate.Greate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

@EventBusSubscriber(bus = Bus.MOD)
public class GreateConfigs {

    private static final Map<Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    public static GClient CLIENT;
    private static GServer SERVER;

    public static GClient client() {
        return CLIENT;
    }

    public static GServer server() {
        return SERVER;
    }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type type) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(b -> {
           T config = factory.get();
           config.registerAll(b);
           return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(type, config);
        return config;
    }

    public static void register(ModLoadingContext context) {
        if(context.getActiveNamespace().equals(Greate.MOD_ID)) {
            CLIENT = register(GClient::new, Type.CLIENT);
            SERVER = register(GServer::new, Type.SERVER);
            for(Entry<Type, ConfigBase> pair : CONFIGS.entrySet()) {
                context.registerConfig(pair.getKey(), pair.getValue().specification);
            }

            BlockStressValues.registerProvider(context.getActiveNamespace(), server().kinetics.stressValues);
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        for(ConfigBase config : CONFIGS.values()) {
            if(config.specification == event.getConfig().getSpec()) {
                config.onLoad();
            }
        }
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        for(ConfigBase config : CONFIGS.values()) {
            if(config.specification == event.getConfig().getSpec()) {
                config.onReload();
            }
        }
    }
}
