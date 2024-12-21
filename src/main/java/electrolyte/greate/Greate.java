package electrolyte.greate;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes;
import electrolyte.greate.foundation.advancement.GreateAdvancements;
import electrolyte.greate.foundation.data.GreateTagGen.GreateBlockTagGen;
import electrolyte.greate.foundation.data.GreateTagGen.GreateItemTagGen;
import electrolyte.greate.foundation.item.GreateKineticStats;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import electrolyte.greate.infrastructure.ponder.GreatePonderIndex;
import electrolyte.greate.infrastructure.ponder.GreatePonderTags;
import electrolyte.greate.registry.*;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Mod(Greate.MOD_ID)
public class Greate {

    public static final String MOD_ID = "greate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Greate.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Greate.MOD_ID);

    static {
        REGISTRATE.setTooltipModifierFactory(i -> new ItemDescription.Modifier(i, Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(GreateKineticStats.create(i))));
    }

    public Greate() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        GreateRegistries.REGISTRATE.registerRegistrate();
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(GTRecipeType.class, GreateRegistries::registerRecipeTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(MachineDefinition.class, GreateRegistries::registerMachines);
        CREATIVE_TABS.register(eventBus);
        REGISTRATE.registerEventListeners(eventBus);
        GreateLang.register();
        REGISTRATE.addRegisterCallback(ForgeRegistries.BLOCKS.getRegistryKey(), () -> GreateConfigs.register(ModLoadingContext.get()));
        ModRecipeTypes.register(eventBus);
        GreateFanProcessingTypes.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, FormattingUtil.toLowerCaseUnder(path));
    }

    public static final RegistryObject<CreativeModeTab> GREATE_TAB = CREATIVE_TABS.register("greate",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.greate"))
                    .icon(() -> new ItemStack(Items.GOLDEN_APPLE))
                    .displayItems(new GreateRegistrateDisplayItemsGenerator())
                    .build());

    private void clientSetup(FMLClientSetupEvent event) {
        GreatePartialModels.register();
        GreatePonderTags.register();
        GreatePonderIndex.register();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        GreateMaterials.modifyMaterials();
    }

    private void gatherData(GatherDataEvent event) {
        REGISTRATE.addDataGenerator(ProviderType.LANG, p -> GreateAdvancements.provideLang(p::add));
        REGISTRATE.addDataGenerator(ProviderType.LANG, p -> GreatePonderTags.register());
        REGISTRATE.addDataGenerator(ProviderType.LANG, p -> GreatePonderIndex.register());
        if(event.includeServer()) {
            event.getGenerator().addProvider(true, new GreateAdvancements(event.getGenerator().getPackOutput()));
            GreateBlockTagGen blockTags = new GreateBlockTagGen(event.getGenerator().getPackOutput(), event.getLookupProvider(), Greate.MOD_ID, event.getExistingFileHelper());
            event.getGenerator().addProvider(true, blockTags);
            event.getGenerator().addProvider(true, new GreateItemTagGen(event.getGenerator().getPackOutput(), event.getLookupProvider(), blockTags.contentsGetter(), Greate.MOD_ID, event.getExistingFileHelper()));
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
