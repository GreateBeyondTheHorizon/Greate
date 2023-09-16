package electrolyte.greate;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.format.ConfigFormats;
import electrolyte.greate.registry.*;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Greate implements ModInitializer {

    public static final String MOD_ID = "greate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static GreateConfig CONFIG;
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Greate.MOD_ID);

    @Override
    public void onInitialize() {
        CONFIG = Configuration.registerConfig(GreateConfig.class, ConfigFormats.yaml()).getConfigInstance();
        CommonEvents.register();
        GreateLang.register();
        GreatePartialModels.register();
        GreateTags.init();
        Cogwheels.register();
        CrushingWheels.register();
        Gearboxes.register();
        Girders.register();
        Millstones.register();
        Shafts.register();
        ModBlockEntityTypes.register();
        ModItems.register();
        ModRecipeTypes.register();
        REGISTRATE.register();
    }

    public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(Greate.MOD_ID, "tab"));
    public static final CreativeModeTab GREATE_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CREATIVE_TAB_KEY,
            FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup.greate"))
                    .icon(() -> new ItemStack(Items.GOLDEN_APPLE))
                    .displayItems(new GreateRegistrateDisplayItemsGenerator())
                    .build());

    public static class GreateRegistrateDisplayItemsGenerator implements DisplayItemsGenerator {


        @Override
        public void accept(ItemDisplayParameters itemDisplayParameters, Output output) {
            Predicate<Item> exclusionPredicate = excludedItems();
            List<Item> items = new LinkedList<>();
            items.addAll(collectBlocks(exclusionPredicate));
            items.addAll(collectItems(exclusionPredicate));
            for(Item item : items) {
                output.accept(new ItemStack(item));
            }
        }

        private static Predicate<Item> excludedItems() {
            Set<Item> exclusions = new ReferenceOpenHashSet<>();
            List<ItemProviderEntry<?>> simpleExclusions = List.of(
                    Cogwheels.ANDESITE_ENCASED_ANDESITE_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_ANDESITE_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_ANDESITE_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_ANDESITE_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_STEEL_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_STEEL_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_ALUMINIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_ALUMINIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_ALUMINIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_ALUMINIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_TITANIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_TITANIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_TITANIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_TITANIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_PALLADIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_PALLADIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_PALLADIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_PALLADIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_NAQUADAH_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_NAQUADAH_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_NAQUADAH_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_NAQUADAH_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_NEUTRONIUM_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_NEUTRONIUM_COGWHEEL,
                    Cogwheels.BRASS_ENCASED_LARGE_NEUTRONIUM_COGWHEEL,

                    Shafts.ANDESITE_ENCASED_ANDESITE_SHAFT,
                    Shafts.BRASS_ENCASED_ANDESITE_SHAFT,
                    Shafts.ANDESITE_ENCASED_STEEL_SHAFT,
                    Shafts.BRASS_ENCASED_STEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_ALUMINIUM_SHAFT,
                    Shafts.BRASS_ENCASED_ALUMINIUM_SHAFT,
                    Shafts.ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT,
                    Shafts.BRASS_ENCASED_STAINLESS_STEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_TITANIUM_SHAFT,
                    Shafts.BRASS_ENCASED_TITANIUM_SHAFT,
                    Shafts.ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT,
                    Shafts.BRASS_ENCASED_TUNGSTENSTEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_PALLADIUM_SHAFT,
                    Shafts.BRASS_ENCASED_PALLADIUM_SHAFT,
                    Shafts.ANDESITE_ENCASED_NAQUADAH_SHAFT,
                    Shafts.BRASS_ENCASED_NAQUADAH_SHAFT,
                    Shafts.ANDESITE_ENCASED_DARMSTADTIUM_SHAFT,
                    Shafts.BRASS_ENCASED_DARMSTADTIUM_SHAFT,
                    Shafts.ANDESITE_ENCASED_NEUTRONIUM_SHAFT,
                    Shafts.BRASS_ENCASED_NEUTRONIUM_SHAFT
            );
            for(ItemProviderEntry<?> entry : simpleExclusions) {
                exclusions.add(entry.asItem());
            }
            return exclusions::contains;
        }

        private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();
            for(RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
                if(!REGISTRATE.isInCreativeTab(entry, CREATIVE_TAB_KEY)) continue;
                Item item = entry.get().asItem();
                if(item == Items.AIR) continue;
                if(!exclusionPredicate.test(item)) items.add(item);
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }

        private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();
            for(RegistryEntry<Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
                if(!REGISTRATE.isInCreativeTab(entry, CREATIVE_TAB_KEY)) continue;
                if(entry.get() instanceof BlockItem) continue;
                if(!exclusionPredicate.test(entry.get())) items.add(entry.get());
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }
    }
}
