package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.content.redstone.displayLink.source.ItemNameDisplaySource;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static electrolyte.greate.Greate.REGISTRATE;

public class Belts {

    public static final Map<Block, List<Block>> VALID_SHAFTS = new HashMap<>();

    public static BlockEntry<TieredBeltBlock>
            RUBBER_BELT,
            SILICONE_RUBBER_BELT,
            POLYETHYLENE_BELT,
            POLYTETRAFLUOROETHYLENE_BELT,
            POLYBENZIMIDAZOLE_BELT;

    public static ItemEntry<TieredBeltConnectorItem>
            RUBBER_BELT_CONNECTOR,
            SILICONE_RUBBER_BELT_CONNECTOR,
            POLYETHYLENE_BELT_CONNECTOR,
            POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR,
            POLYBENZIMIDAZOLE_BELT_CONNECTOR;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        RUBBER_BELT = belt(Rubber, List.of(Shafts.ANDESITE_SHAFT, Shafts.STEEL_SHAFT));
        SILICONE_RUBBER_BELT = belt(SiliconeRubber, List.of(Shafts.ALUMINIUM_SHAFT, Shafts.STAINLESS_STEEL_SHAFT));
        POLYETHYLENE_BELT = belt(Polyethylene, List.of(Shafts.TITANIUM_SHAFT, Shafts.TUNGSTENSTEEL_SHAFT));
        POLYTETRAFLUOROETHYLENE_BELT = belt(Polytetrafluoroethylene, List.of(Shafts.PALLADIUM_SHAFT, Shafts.NAQUADAH_SHAFT));
        POLYBENZIMIDAZOLE_BELT = belt(Polybenzimidazole, List.of(Shafts.DARMSTADTIUM_SHAFT, Shafts.NEUTRONIUM_SHAFT));

        RUBBER_BELT_CONNECTOR = beltConnector("rubber_belt_connector", RUBBER_BELT);
        SILICONE_RUBBER_BELT_CONNECTOR = beltConnector("silicone_rubber_belt_connector", SILICONE_RUBBER_BELT);
        POLYETHYLENE_BELT_CONNECTOR = beltConnector("polyethylene_belt_connector", POLYETHYLENE_BELT);
        POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR = beltConnector("polytetrafluoroethylene_belt_connector", POLYTETRAFLUOROETHYLENE_BELT);
        POLYBENZIMIDAZOLE_BELT_CONNECTOR = beltConnector("polybenzimidazole_belt_connector", POLYBENZIMIDAZOLE_BELT);
    }

    public static BlockEntry<TieredBeltBlock> belt(Material material, List<BlockEntry<TieredShaftBlock>> validShafts) {
        return REGISTRATE
                .block(material.getName() + "_belt", TieredBeltBlock::new)
                .addLayer(() -> RenderType::cutoutMipped)
                .properties(p -> p.sound(SoundType.WOOL))
                .properties(p -> p.strength(0.8F))
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(0))
                .transform(TieredBlockMaterials.setMaterialForBeltBlock(material))
                .blockstate(new TieredBeltGenerator()::generateModel)
                .onRegisterAfter(ForgeRegistries.ITEMS.getRegistryKey(), c -> c.validShafts(validShafts))
                .onRegister(TieredBeltBlock::setupBeltModel)
                .onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
                .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                .onRegister(c -> c.setBeltMaterial(material))
                .register();
    }

    public static ItemEntry<TieredBeltConnectorItem> beltConnector(String name, BlockEntry<TieredBeltBlock> belt) {
        return REGISTRATE
                .item(name, p -> new TieredBeltConnectorItem(belt.get(), p))
                .transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build())))
                .onRegister(c -> c.setBeltMaterial(belt.get().getBeltMaterial()))
                .register();
    }
}
