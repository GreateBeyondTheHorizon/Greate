package electrolyte.greate.infrastructure.config;

import com.simibubi.create.api.stress.BlockStressValues;
import electrolyte.greate.Greate;
import net.createmod.catnip.config.ConfigBase;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
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
        Pair<T, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(b -> {
           T config = factory.get();
           config.registerAll(b);
           return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(type, config);
        return config;
    }

    public static void register(ModLoadingContext context, ModContainer container) {
        if(context.getActiveNamespace().equals(Greate.MOD_ID)) {
            //CLIENT = register(GClient::new, Type.CLIENT);
            SERVER = register(GServer::new, Type.SERVER);
            for(Entry<Type, ConfigBase> pair : CONFIGS.entrySet()) {
                container.registerConfig(pair.getKey(), pair.getValue().specification);
            }
            GStress stress = server().kinetics.stressValues;
            BlockStressValues.IMPACTS.registerProvider(stress::getImpact);
            BlockStressValues.CAPACITIES.registerProvider(stress::getCapacity);
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
