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
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltGenerator;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.tterrag.registrate.providers.RegistrateLangProvider.toEnglishName;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.registry.GreateTagPrefixes.belt;
import static electrolyte.greate.registry.GreateTagPrefixes.beltConnector;

public class Belts {
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredBeltBlock>> BELT_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredBeltBlock>> BELTS;
    static ImmutableTable.Builder<TagPrefix, Material, ItemEntry<TieredBeltConnectorItem>> BELT_CONNECTORS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, ItemEntry<TieredBeltConnectorItem>> BELT_CONNECTORS;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        generateBelts();
    }

    public static void generateBelts() {
        for(Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(material.hasProperty(GreatePropertyKeys.BELT)) {
                var beltEntry = REGISTRATE
                    .block(material.getName() + "_belt", TieredBeltBlock::new)
                    .lang(toEnglishName(material.getName() + "_belt"))
                    .properties(p -> p.sound(SoundType.WOOL))
                    .properties(p -> p.strength(0.8F))
                    .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                    .transform(TagGen.axeOrPickaxe())
                    .transform(GStress.setNoImpact())
                    .transform(displaySource(AllDisplaySources.ITEM_NAMES))
                    .blockstate(new TieredBeltGenerator()::generateModel)
                    .onRegister(c -> c.setBeltMaterial(material))
                    .onRegister(c -> c.setupBeltModel(material))
                    .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                    .register();
                BELT_BUILDER.put(belt, material, beltEntry);

                var beltConnectorEntry = REGISTRATE
                .item(material.getName() + "_belt_connector", p -> new TieredBeltConnectorItem(ChemicalHelper.getBlock(belt, material), p, material))
                .transform(GTItems.unificationItem(beltConnector, material))
                //.transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build()))) TODO: disabled b/c quarktech armor auto eats
                .register();
                BELT_CONNECTORS_BUILDER.put(beltConnector, material, beltConnectorEntry);
            }
        }
        BELTS = BELT_BUILDER.build();
        BELT_CONNECTORS = BELT_CONNECTORS_BUILDER.build();
    }
}
