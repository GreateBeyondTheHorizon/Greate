package electrolyte.greate.foundation.advancement;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.advancement.AllTriggers;
import com.simibubi.create.foundation.advancement.SimpleCreateTrigger;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.Greate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class GreateAdvancement {

    static final ResourceLocation BACKGROUND = Create.asResource("textures/gui/advancements.png");
    static final String LANG = "advancement." + Greate.MOD_ID + ".";
    static final String SECRET_SUFFIX = "§7\n(Hidden Advancement)";
    private Advancement.Builder builder;
    private SimpleCreateTrigger builtInTrigger;
    private GreateAdvancement parent;
    Advancement datagenResult;
    private String id;
    private String title;
    private String description;

    public GreateAdvancement(String id, UnaryOperator<Builder> b) {
        this.builder = Advancement.Builder.advancement();
        this.id = id;

        Builder t = new Builder();
        b.apply(t);

        if(!t.externalTrigger) {
            builtInTrigger = AllTriggers.addSimple(id + "_builtin");
            builder.addCriterion("0", builtInTrigger.instance());
        }

        builder.display(t.icon, Components.translatable(titleKey()),
                Components.translatable(descriptionKey()).withStyle(s -> s.withColor(0xDBA213)),
                id.equals("root") ? BACKGROUND : null, t.type.frame, t.type.toast, t.type.announce, t.type.hide);

        if(t.type == TaskType.SECRET) {
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

    void save(Consumer<Advancement> advancementConsumer) {
        if(parent != null) builder.parent(parent.datagenResult);
        datagenResult = builder.save(advancementConsumer, new ResourceLocation(Greate.MOD_ID, id).toString());
    }

    void provideLang(BiConsumer<String, String> consumer) {
        consumer.accept(titleKey(), title);
        consumer.accept(descriptionKey(), description);
    }

    static enum TaskType {

        SILENT(FrameType.TASK, false, false, false),
        NORMAL(FrameType.TASK, true, false, false),
        NOISY(FrameType.TASK, true, true, false),
        EXPERT(FrameType.GOAL, true, true, false),
        SECRET(FrameType.GOAL, true, true, true),
        SECRET_NOISY(FrameType.CHALLENGE, true, true, true),

        ;

        private FrameType frame;
        private boolean toast;
        private boolean announce;
        private boolean hide;

        private TaskType(FrameType frame, boolean toast, boolean announce, boolean hide) {
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

        Builder special(TaskType type) {
            this.type = type;
            return this;
        }

        Builder after(GreateAdvancement other) {
            GreateAdvancement.this.parent = other;
            return this;
        }

        Builder icon(ItemProviderEntry<?> item) {
            return icon(item.asStack());
        }

        Builder icon(ItemStack stack) {
            icon = stack;
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

        Builder externalTrigger(CriterionTriggerInstance trigger) {
            builder.addCriterion(String.valueOf(keyIndex), trigger);
            externalTrigger = true;
            keyIndex++;
            return this;
        }
    }
}
