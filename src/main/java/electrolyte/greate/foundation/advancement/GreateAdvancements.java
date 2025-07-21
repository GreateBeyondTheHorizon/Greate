package electrolyte.greate.foundation.advancement;

import com.google.common.collect.Sets;
import electrolyte.greate.foundation.advancement.GreateAdvancement.Builder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class GreateAdvancements implements DataProvider {

    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public GreateAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.output = output;
        this.registries = registries;
    }

    public static final List<GreateAdvancement> ENTRIES = new ArrayList<>();
    public static final GreateAdvancement START = null,

    /*ROOT = create("root", b -> b
            .icon(AllItems.WRENCH)
            .title("Welcome to Greate")
            .description("Prepare for endless torture")
            .awardedForFree()
            .special(SILENT)),

    EAT_ALL_BELTS = create("eat_all_belts", b -> b
            .icon(Belts.SILICONE_RUBBER_BELT_CONNECTOR)
            .title("Tasty!")
            .description("Consume every type of belt")
            .whenItemsConsumed(Belts.BELT_CONNECTORS)
            .after(ROOT)
            .special(SECRET_NOISY)),*/ //TODO: disabled b/c quarktech armor auto eats

    END = null;

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return this.registries.thenCompose(provider -> {
            PathProvider pathProvider = output.createPathProvider(Target.DATA_PACK, "advancements");
            List<CompletableFuture<?>> futures = new ArrayList<>();
            Set<ResourceLocation> set = Sets.newHashSet();
            Consumer<AdvancementHolder> consumer = (adv) -> {
                ResourceLocation id = adv.id();
                if(!set.add(id)) {
                    throw new IllegalStateException("Duplicate Advancement " + id);
                }
                Path path = pathProvider.json(id);
                futures.add(DataProvider.saveStable(pOutput, provider, Advancement.CODEC, adv.value(), path));
            };

            for(GreateAdvancement adv : ENTRIES) {
                adv.save(consumer, provider);
            }
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Greate's Advancements";
    }

    private static GreateAdvancement create(String id, UnaryOperator<Builder> b) {
        return new GreateAdvancement(id, b);
    }

    public static void provideLang(BiConsumer<String, String> consumer) {
        for(GreateAdvancement adv : ENTRIES) {
            adv.provideLang(consumer);
        }
    }

    public static void register() {}
}
