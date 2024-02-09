package electrolyte.greate.registry;

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

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.BMS;

public class Belts {

    public static final Map<Block, List<Block>> VALID_SHAFTS = new HashMap<>();

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredBeltBlock> RUBBER_BELT = belt("rubber_belt", BMS[0], List.of(Shafts.ANDESITE_SHAFT, Shafts.STEEL_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> RUBBER_BELT_CONNECTOR = beltConnector("rubber_belt_connector", RUBBER_BELT);
    public static final BlockEntry<TieredBeltBlock> SILICONE_RUBBER_BELT = belt("silicone_rubber_belt", BMS[1], List.of(Shafts.ALUMINIUM_SHAFT, Shafts.STAINLESS_STEEL_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> SILICONE_RUBBER_BELT_CONNECTOR = beltConnector("silicone_rubber_belt_connector", SILICONE_RUBBER_BELT);
    public static final BlockEntry<TieredBeltBlock> POLYETHYLENE_BELT = belt("polyethylene_belt", BMS[2], List.of(Shafts.TITANIUM_SHAFT, Shafts.TUNGSTENSTEEL_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> POLYETHYLENE_BELT_CONNECTOR = beltConnector("polyethylene_belt_connector", POLYETHYLENE_BELT);
    public static final BlockEntry<TieredBeltBlock> POLYTETRAFLUOROETHYLENE_BELT = belt("polytetrafluoroethylene_belt", BMS[3], List.of(Shafts.PALLADIUM_SHAFT, Shafts.NAQUADAH_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR = beltConnector("polytetrafluoroethylene_belt_connector", POLYTETRAFLUOROETHYLENE_BELT);
    public static final BlockEntry<TieredBeltBlock> POLYBENZIMIDAZOLE_BELT = belt("polybenzimidazole_belt", BMS[4], List.of(Shafts.DARMSTADTIUM_SHAFT, Shafts.NEUTRONIUM_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> POLYBENZIMIDAZOLE_BELT_CONNECTOR = beltConnector("polybenzimidazole_belt_connector", POLYBENZIMIDAZOLE_BELT);

    public static BlockEntry<TieredBeltBlock> belt(String name, String beltType, List<BlockEntry<TieredShaftBlock>> validShafts) {
        return REGISTRATE
                .block(name, TieredBeltBlock::new)
                .addLayer(() -> RenderType::cutoutMipped)
                .properties(p -> p.sound(SoundType.WOOL))
                .properties(p -> p.strength(0.8F))
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setImpact(0))
                .transform(TieredBlockMaterials.setBeltTypeForBlock(beltType))
                .blockstate(new TieredBeltGenerator()::generateModel)
                .onRegisterAfter(ForgeRegistries.ITEMS.getRegistryKey(), c -> c.validShafts(validShafts))
                .onRegister(TieredBeltBlock::setupBeltModel)
                .onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
                .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                .onRegister(c -> c.setBeltType(beltType))
                .register();
    }

    public static ItemEntry<TieredBeltConnectorItem> beltConnector(String name, BlockEntry<TieredBeltBlock> belt) {
        return REGISTRATE
                .item(name, p -> new TieredBeltConnectorItem(belt.get(), p))
                .transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build())))
                .lang(name.contains("silicone") ? "Silicone Rubber Mechanical Belt" : name.substring(0, 1).toUpperCase() + name.substring(1, name.length() - 15) + " Mechanical Belt")
                .onRegister(c -> c.setBeltType(belt.get().getBeltType()))
                .register();
    }

    public static void register() {}
}
