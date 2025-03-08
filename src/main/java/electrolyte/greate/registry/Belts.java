package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltGenerator;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.tterrag.registrate.providers.RegistrateLangProvider.toEnglishName;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.Shafts.SHAFTS;

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

    public static final ItemEntry<TieredBeltConnectorItem>[] BELT_CONNECTORS = new ItemEntry[5];
    public static ItemEntry<TieredBeltConnectorItem>
            RUBBER_BELT_CONNECTOR,
            SILICONE_RUBBER_BELT_CONNECTOR,
            POLYETHYLENE_BELT_CONNECTOR,
            POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR,
            POLYBENZIMIDAZOLE_BELT_CONNECTOR;

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

        BELT_CONNECTORS[0] = RUBBER_BELT_CONNECTOR = beltConnector("rubber_belt_connector", List.of(SHAFTS[0], SHAFTS[1]), Rubber);
        BELT_CONNECTORS[1] = SILICONE_RUBBER_BELT_CONNECTOR = beltConnector("silicone_rubber_belt_connector", List.of(SHAFTS[2], SHAFTS[3]), SiliconeRubber);
        BELT_CONNECTORS[2] = POLYETHYLENE_BELT_CONNECTOR = beltConnector("polyethylene_belt_connector", List.of(SHAFTS[4], SHAFTS[5]), Polyethylene);
        BELT_CONNECTORS[3] = POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR = beltConnector("polytetrafluoroethylene_belt_connector", List.of(SHAFTS[6], SHAFTS[7]), Polytetrafluoroethylene);
        BELT_CONNECTORS[4] = POLYBENZIMIDAZOLE_BELT_CONNECTOR = beltConnector("polybenzimidazole_belt_connector", List.of(SHAFTS[8], SHAFTS[9]), Polybenzimidazole);
    }

    public static BlockEntry<TieredBeltBlock> belt(Material material, int tier) {
        return REGISTRATE
                .block(material.getName() + "_belt_" + TM[tier].getName(), TieredBeltBlock::new)
                .lang(toEnglishName(material.getName() + "_belt"))
                .addLayer(() -> RenderType::cutoutMipped)
                .properties(p -> p.sound(SoundType.WOOL))
                .properties(p -> p.strength(0.8F))
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(TagGen.axeOrPickaxe())
                //.transform(CStress.setNoImpact())
                .transform(TieredBlockMaterials.setMaterialForBeltBlock(material))
                .transform(displaySource(AllDisplaySources.ITEM_NAMES))
                .blockstate(new TieredBeltGenerator()::generateModel)
                .onRegister(TieredBeltBlock::setupBeltModel)
                .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                .onRegister(c -> c.setBeltMaterial(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static ItemEntry<TieredBeltConnectorItem> beltConnector(String name, List<BlockEntry<TieredShaftBlock>> validShafts, Material beltMaterial) {
        return REGISTRATE
                .item(name, p -> new TieredBeltConnectorItem(p, validShafts))
                .transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build())))
                .onRegister(c -> c.setBeltMaterial(beltMaterial))
                .register();
    }
}
