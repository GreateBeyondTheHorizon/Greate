package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.gregtechceu.gtceu.data.item.GTItems;
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

import static com.gregtechceu.gtceu.data.material.GTMaterials.*;
import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.tterrag.registrate.providers.RegistrateLangProvider.toEnglishName;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.registry.GreateTagPrefixes.beltConnector;

public class Belts {

    public static final BlockEntry<TieredBeltBlock>[] BELTS = new BlockEntry[10];
    public static BlockEntry<TieredBeltBlock>
            RUBBER_BELT_ANDESITE,
            RUBBER_BELT_STEEL,
            SILICONE_RUBBER_BELT_ALUMINIUM,
            SILICONE_RUBBER_BELT_STAINLESS_STEEL,
            POLYETHYLENE_BELT_TITANIUM,
            POLYETHYLENE_BELT_TUNGSTEN_STEEL,
            POLYTETRAFLUOROETHYLENE_BELT_PALLADIUM,
            POLYTETRAFLUOROETHYLENE_BELT_NAQUADAH,
            POLYBENZIMIDAZOLE_BELT_DARMSTADTIUM,
            POLYBENZIMIDAZOLE_BELT_NEUTRONIUM;

    static ImmutableTable.Builder<TagPrefix, Material, ItemEntry<TieredBeltConnectorItem>> BELT_CONNECTORS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, ItemEntry<TieredBeltConnectorItem>> NEW_BELT_CONNECTORS;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        BELTS[0] = RUBBER_BELT_ANDESITE = belt(Rubber, 0);
        BELTS[1] = RUBBER_BELT_STEEL = belt(Rubber, 1);
        BELTS[2] = SILICONE_RUBBER_BELT_ALUMINIUM = belt(SiliconeRubber, 2);
        BELTS[3] = SILICONE_RUBBER_BELT_STAINLESS_STEEL = belt(SiliconeRubber, 3);
        BELTS[4] = POLYETHYLENE_BELT_TITANIUM = belt(Polyethylene, 4);
        BELTS[5] = POLYETHYLENE_BELT_TUNGSTEN_STEEL = belt(Polyethylene, 5);
        BELTS[6] = POLYTETRAFLUOROETHYLENE_BELT_PALLADIUM = belt(Polytetrafluoroethylene, 6);
        BELTS[7] = POLYTETRAFLUOROETHYLENE_BELT_NAQUADAH = belt(Polytetrafluoroethylene, 7);
        BELTS[8] = POLYBENZIMIDAZOLE_BELT_DARMSTADTIUM = belt(Polybenzimidazole, 8);
        BELTS[9] = POLYBENZIMIDAZOLE_BELT_NEUTRONIUM = belt(Polybenzimidazole, 9);

        generateBeltConnectors();
    }

    public static BlockEntry<TieredBeltBlock> belt(Material material, int tier) {
        return REGISTRATE
                .block(material.getName() + "_belt_" + material.getName(), TieredBeltBlock::new)
                .lang(toEnglishName(material.getName() + "_belt"))
                .properties(p -> p.sound(SoundType.WOOL))
                .properties(p -> p.strength(0.8F))
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(TagGen.axeOrPickaxe())
                .transform(GStress.setNoImpact())
                .transform(displaySource(AllDisplaySources.ITEM_NAMES))
                .blockstate(new TieredBeltGenerator()::generateModel)
                .onRegister(TieredBeltBlock::setupBeltModel)
                .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                .onRegister(c -> c.setBeltMaterial(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    private static void generateBeltConnectors() {
        for(Material material : GTCEuAPI.materialManager) {
            if(material.hasProperty(GreatePropertyKeys.BELT)) {
                var beltEntry = REGISTRATE
                .item(material.getName() + "_belt_connector", p -> new TieredBeltConnectorItem(p, material))
                .transform(GTItems.unificationItem(beltConnector, material))
                //.transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build()))) TODO: disabled b/c quarktech armor auto eats
                .register();
                BELT_CONNECTORS_BUILDER.put(beltConnector, material, beltEntry);
            }
        }
        NEW_BELT_CONNECTORS = BELT_CONNECTORS_BUILDER.build();
    }
}
