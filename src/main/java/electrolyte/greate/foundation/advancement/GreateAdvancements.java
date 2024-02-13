package electrolyte.greate.foundation.advancement;

import com.google.common.collect.Sets;
import com.simibubi.create.AllItems;
import electrolyte.greate.foundation.advancement.GreateAdvancement.Builder;
import electrolyte.greate.registry.Belts;
import net.minecraft.advancements.Advancement;
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

import static electrolyte.greate.foundation.advancement.GreateAdvancement.TaskType.SECRET_NOISY;
import static electrolyte.greate.foundation.advancement.GreateAdvancement.TaskType.SILENT;

public class GreateAdvancements implements DataProvider {

    private final PackOutput output;

    public GreateAdvancements(PackOutput output) {
        this.output = output;
    }

    public static final List<GreateAdvancement> ENTRIES = new ArrayList<>();
    public static final GreateAdvancement START = null,

    ROOT = create("root", b -> b
            .icon(AllItems.WRENCH)
            .title("Welcome to Greate")
            .description("Prepare for endless torture")
            .awardedForFree()
            .special(SILENT)),

    EAT_ALL_BELTS = create("eat_all_belts", b -> b
            .icon(Belts.SILICONE_RUBBER_BELT)
            .title("Tasty!")
            .description("Consume every type of belt")
            .whenItemsConsumed(Belts.BELT_CONNECTORS)
            .after(ROOT)
            .special(SECRET_NOISY)),

    END = null;

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        PathProvider pathProvider = output.createPathProvider(Target.DATA_PACK, "advancements");
        List<CompletableFuture<?>> futures = new ArrayList<>();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (adv) -> {
            ResourceLocation id = adv.getId();
            if(!set.add(id)) {
                throw new IllegalStateException("Duplicate Advancement " + id);
            }
            Path path = pathProvider.json(id);
            futures.add(DataProvider.saveStable(pOutput, adv.deconstruct().serializeToJson(), path));
        };

        for(GreateAdvancement adv : ENTRIES) {
            adv.save(consumer);
        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
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
