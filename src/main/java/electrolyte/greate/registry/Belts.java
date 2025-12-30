package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.foundation.client.models.BeltConnectorModel;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static electrolyte.greate.registry.GreateTagPrefixes.belt;
import static electrolyte.greate.registry.GreateTagPrefixes.beltConnector;

public class Belts {
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredBeltBlock>> BELT_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredBeltBlock>> BELTS;
    static ImmutableTable.Builder<TagPrefix, Material, ItemEntry<TieredBeltConnectorItem>> BELT_CONNECTORS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, ItemEntry<TieredBeltConnectorItem>> BELT_CONNECTORS;

    public static void register() {
        GreateRegistries.REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);

        generateBelts();
    }

    public static void generateBelts() {
        for(Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(material.hasProperty(GreatePropertyKeys.BELT)) {
                var gtBeltEntry = GreateRegistries.REGISTRATE
                        .block(material.getName() + "_belt", TieredBeltBlock::new)
                        .blockstate(NonNullBiConsumer.noop())
                        .properties(p -> p.sound(SoundType.WOOL).strength(0.8F).mapColor(MapColor.COLOR_GRAY).noLootTable())
                        .transform(GStress.setNoImpact())
                        .transform(displaySource(AllDisplaySources.ITEM_NAMES))
                        .onRegister(c -> c.setBeltMaterial(material))
                        .onRegister(c -> c.setupBeltModel(material))
                        .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                        .onRegister(electrolyte.greate.foundation.client.models.BeltModel::create)
                        .register();
                BELT_BUILDER.put(belt, material, gtBeltEntry);

                var beltConnectorEntry = GreateRegistries.REGISTRATE
                        .item(material.getName() + "_belt_connector", p -> new TieredBeltConnectorItem(ChemicalHelper.getBlock(belt, material), p, material))
                        .model(NonNullBiConsumer.noop())
                        .transform(GTItems.unificationItem(beltConnector, material))
                        .onRegister(BeltConnectorModel::create)
                        //.transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build()))) TODO: disabled b/c quarktech armor auto eats
                        .register();
                BELT_CONNECTORS_BUILDER.put(beltConnector, material, beltConnectorEntry);
            }
        }
        BELTS = BELT_BUILDER.build();
        BELT_CONNECTORS = BELT_CONNECTORS_BUILDER.build();
    }
}
