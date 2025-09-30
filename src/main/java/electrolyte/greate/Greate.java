package electrolyte.greate;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.ConfigHolder;
import dev.toma.configuration.config.format.ConfigFormats;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes;
import electrolyte.greate.foundation.advancement.GreateAdvancements;
import electrolyte.greate.foundation.data.GreateTagGen.GreateBlockTagGen;
import electrolyte.greate.foundation.data.GreateTagGen.GreateItemTagGen;
import electrolyte.greate.foundation.data.recipe.datagen.GreateItemApplicationRecipeGen;
import electrolyte.greate.foundation.item.GreateKineticStats;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import electrolyte.greate.infrastructure.config.GreateRecipeConfig;
import electrolyte.greate.infrastructure.ponder.GreatePonderPlugin;
import electrolyte.greate.registry.GreateLang;
import electrolyte.greate.registry.GreatePartialModels;
import electrolyte.greate.registry.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static electrolyte.greate.registry.Millstones.MILLSTONES;

@Mod(Greate.MOD_ID)
public class Greate {

    public static final String MOD_ID = "greate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Greate.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Greate.MOD_ID);
    public static GreateRecipeConfig CONFIG;

    static {
        REGISTRATE.setTooltipModifierFactory(i -> new ItemDescription.Modifier(i, Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(GreateKineticStats.create(i))));
    }

    public Greate() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRegister);

        if(!DatagenModLoader.isRunningDataGen()) { //needed due to using both create & gt registrate
            GreateRegistries.REGISTRATE.registerRegistrate();
            FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(MachineDefinition.class, GreateRegistries::registerMachines);
        }

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(GTRecipeType.class, GreateRegistries::registerRecipeTypes);

        CREATIVE_TABS.register(eventBus);
        REGISTRATE.registerEventListeners(eventBus);
        GreateLang.register();
        ModRecipeTypes.register(eventBus);

        REGISTRATE.addRegisterCallback(ForgeRegistries.BLOCKS.getRegistryKey(), () -> GreateConfigs.register(ModLoadingContext.get()));
        ConfigHolder<GreateRecipeConfig> configHolder = Configuration.registerConfig(GreateRecipeConfig.class, ConfigFormats.yaml());
        CONFIG = configHolder.getConfigInstance();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, FormattingUtil.toLowerCaseUnderscore(path));
    }

    public static final RegistryObject<CreativeModeTab> GREATE_TAB = CREATIVE_TABS.register("greate",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.greate"))
                    .icon(() -> new ItemStack(MILLSTONES[GTValues.UHV]))
                    .displayItems(new GreateRegistrateDisplayItemsGenerator())
                    .build());

    private void clientSetup(FMLClientSetupEvent event) {
        GreatePartialModels.register();
        PonderIndex.addPlugin(new GreatePonderPlugin());
    }

    private void onRegister(RegisterEvent event) {
        GreateFanProcessingTypes.register();
    }

    private void gatherData(GatherDataEvent event) {
        REGISTRATE.addDataGenerator(ProviderType.LANG, p -> {
            PonderIndex.addPlugin(new GreatePonderPlugin());
            PonderIndex.getLangAccess().provideLang(Greate.MOD_ID, p::add);
            GreateAdvancements.provideLang(p::add);
        });
        if(event.includeServer()) {
            event.getGenerator().addProvider(true, new GreateAdvancements(event.getGenerator().getPackOutput()));
            GreateBlockTagGen blockTags = new GreateBlockTagGen(event.getGenerator().getPackOutput(), event.getLookupProvider(), Greate.MOD_ID, event.getExistingFileHelper());
            event.getGenerator().addProvider(true, blockTags);
            event.getGenerator().addProvider(true, new GreateItemTagGen(event.getGenerator().getPackOutput(), event.getLookupProvider(), blockTags.contentsGetter(), Greate.MOD_ID, event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new GreateItemApplicationRecipeGen(event.getGenerator().getPackOutput(), Greate.MOD_ID));
        }
    }

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
            List<ItemProviderEntry<?>> simpleExclusions = List.of();
            for(ItemProviderEntry<?> entry : simpleExclusions) {
                exclusions.add(entry.asItem());
            }
            return exclusions::contains;
        }

        private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();
            for(RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
                if(!REGISTRATE.isInCreativeTab(entry, GREATE_TAB)) continue;
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
                if(!REGISTRATE.isInCreativeTab(entry, GREATE_TAB)) continue;
                if(entry.get() instanceof BlockItem) continue;
                if(!exclusionPredicate.test(entry.get())) items.add(entry.get());
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }
    }
}
