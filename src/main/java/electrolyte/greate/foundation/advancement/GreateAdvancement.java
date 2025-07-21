package electrolyte.greate.foundation.advancement;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.advancement.AllTriggers;
import com.simibubi.create.foundation.advancement.SimpleCreateTrigger;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.Greate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class GreateAdvancement {

    static final ResourceLocation BACKGROUND = Create.asResource("textures/gui/advancements.png");
    static final String LANG = "advancement." + Greate.MOD_ID + ".";
    static final String SECRET_SUFFIX = "§7\n(Hidden Advancement)";
    private final Advancement.Builder mcBuilder = Advancement.Builder.advancement();
    private final Builder builder = new Builder();
    private SimpleCreateTrigger builtInTrigger;
    private GreateAdvancement parent;
    AdvancementHolder datagenResult;
    private String id;
    private String title;
    private String description;

    public GreateAdvancement(String id, UnaryOperator<Builder> b) {
        this.id = id;
        b.apply(builder);

        if(!builder.externalTrigger) {
            builtInTrigger = AllTriggers.addSimple(id + "_builtin");
            mcBuilder.addCriterion("0", builtInTrigger.createCriterion(builtInTrigger.instance()));
        }

        if(builder.type == TaskType.SECRET) {
            description += SECRET_SUFFIX;
        }

        GreateAdvancements.ENTRIES.add(this);
    }

    private String titleKey() {
        return LANG + id;
    }

    private String descriptionKey() {
        return titleKey() + ".desc";
    }

    void save(Consumer<AdvancementHolder> advancementConsumer, HolderLookup.Provider registries) {
        if(parent != null) mcBuilder.parent(parent.datagenResult);
        if(builder.func != null) builder.icon(builder.func.apply(registries));

        mcBuilder.display(builder.icon, Component.translatable(titleKey()),
                Component.translatable(descriptionKey()).withStyle(s -> s.withColor(0xDBA213)),
                id.equals("root") ? BACKGROUND : null, builder.type.frame, builder.type.toast, builder.type.announce, builder.type.hide);

        datagenResult = mcBuilder.save(advancementConsumer, Greate.id(id).toString());
    }

    void provideLang(BiConsumer<String, String> consumer) {
        consumer.accept(titleKey(), title);
        consumer.accept(descriptionKey(), description);
    }

    static enum TaskType {

        SILENT(AdvancementType.TASK, false, false, false),
        NORMAL(AdvancementType.TASK, true, false, false),
        NOISY(AdvancementType.TASK, true, true, false),
        EXPERT(AdvancementType.GOAL, true, true, false),
        SECRET(AdvancementType.GOAL, true, true, true),
        SECRET_NOISY(AdvancementType.CHALLENGE, true, true, true),

        ;

        private AdvancementType frame;
        private boolean toast;
        private boolean announce;
        private boolean hide;

        TaskType(AdvancementType frame, boolean toast, boolean announce, boolean hide) {
            this.frame = frame;
            this.toast = toast;
            this.announce = announce;
            this.hide = hide;
        }
    }

    class Builder {
        private TaskType type = TaskType.NORMAL;
        private boolean externalTrigger;
        private int keyIndex;
        private ItemStack icon;
        private Function<Provider, ItemStack> func;

        Builder special(TaskType type) {
            this.type = type;
            return this;
        }

        Builder after(GreateAdvancement other) {
            GreateAdvancement.this.parent = other;
            return this;
        }

        Builder icon(ItemProviderEntry<?, ?> item) {
            return icon(item.asStack());
        }

        Builder icon(ItemStack stack) {
            icon = stack;
            return this;
        }

        Builder icon(Function<Provider, ItemStack> func) {
            this.func = func;
            return this;
        }

        Builder title(String title) {
            GreateAdvancement.this.title = title;
            return this;
        }

        Builder description(String description) {
            GreateAdvancement.this.description = description;
            return this;
        }

        Builder awardedForFree() {
            return externalTrigger(InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] {}));
        }

        Builder whenItemsConsumed(ItemLike... items) {
            Builder builder = externalTrigger(ConsumeItemTrigger.TriggerInstance.usedItem(items[0]));
            for (int i = 1; i < items.length; i++) {
                builder = externalTrigger(ConsumeItemTrigger.TriggerInstance.usedItem(items[i]));
            }
            return builder;
        }

        Builder whenItemConsumed(ItemLike item) {
            return externalTrigger(ConsumeItemTrigger.TriggerInstance.usedItem(item));
        }

        Builder externalTrigger(Criterion<?> trigger) {
            mcBuilder.addCriterion(String.valueOf(keyIndex), trigger);
            externalTrigger = true;
            keyIndex++;
            return this;
        }
    }
}
