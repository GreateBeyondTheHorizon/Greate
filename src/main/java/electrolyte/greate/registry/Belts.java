package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.belt.BeltGenerator;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.content.redstone.displayLink.source.ItemNameDisplaySource;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.BELT_TYPE;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltConnectorItem;
import electrolyte.greate.content.kinetics.belt.TieredBeltGenerator;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static electrolyte.greate.Greate.REGISTRATE;

public class Belts {

    static {
        REGISTRATE.useCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredBeltBlock> RUBBER_BELT = belt("rubber_belt", BELT_TYPE.RUBBER, List.of(Shafts.ANDESITE_SHAFT, Shafts.STEEL_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> RUBBER_BELT_CONNECTOR = beltConnector("rubber_belt_connector", BELT_TYPE.RUBBER, RUBBER_BELT);
    public static final BlockEntry<TieredBeltBlock> SILICON_BELT = belt("silicon_belt", BELT_TYPE.SILICON, List.of(Shafts.ALUMINIUM_SHAFT, Shafts.STAINLESS_STEEL_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> SILICON_BELT_CONNECTOR = beltConnector("silicon_belt_connector", BELT_TYPE.SILICON, SILICON_BELT);
    public static final BlockEntry<TieredBeltBlock> POLYETHYLENE_BELT = belt("polyethylene_belt", BELT_TYPE.POLYETHYLENE, List.of(Shafts.TITANIUM_SHAFT, Shafts.TUNGSTENSTEEL_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> POLYETHYLENE_BELT_CONNECTOR = beltConnector("polyethylene_belt_connector", BELT_TYPE.POLYETHYLENE, POLYETHYLENE_BELT);
    public static final BlockEntry<TieredBeltBlock> POLYTETRAFLUOROETHYLENE_BELT = belt("polytetrafluoroethylene_belt", BELT_TYPE.PTFE, List.of(Shafts.PALLADIUM_SHAFT, Shafts.NAQUADAH_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR = beltConnector("polytetrafluoroethylene_belt_connector", BELT_TYPE.PTFE, POLYTETRAFLUOROETHYLENE_BELT);
    public static final BlockEntry<TieredBeltBlock> POLYBENZIMIDAZOLE_BELT = belt("polybenzimidazole_belt", BELT_TYPE.PBI, List.of(Shafts.DARMSTADTIUM_SHAFT, Shafts.NEUTRONIUM_SHAFT));
    public static final ItemEntry<TieredBeltConnectorItem> POLYBENZIMIDAZOLE_BELT_CONNECTOR = beltConnector("polybenzimidazole_belt_connector", BELT_TYPE.PBI, POLYBENZIMIDAZOLE_BELT);

    public static BlockEntry<TieredBeltBlock> belt(String name, BELT_TYPE beltType, List<BlockEntry<TieredShaftBlock>> validShafts) {
        return REGISTRATE
                .block(name, TieredBeltBlock::new)
                .addLayer(() -> RenderType::cutoutMipped)
                .properties(p -> p.sound(SoundType.WOOL))
                .properties(p -> p.strength(0.8F))
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(TagGen.axeOrPickaxe())
                .transform(BlockStressDefaults.setNoImpact())
                .blockstate(new TieredBeltGenerator()::generate)
                .onRegisterAfter(ForgeRegistries.ITEMS.getRegistryKey(), c -> c.validShafts(validShafts))
                .onRegister(TieredBeltBlock::setupBeltModel)
                .onRegister(c -> c.setType(beltType))
                .onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
                .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
                .register();
    }

    public static ItemEntry<TieredBeltConnectorItem> beltConnector(String name, BELT_TYPE beltType, BlockEntry<TieredBeltBlock> belt) {
        return REGISTRATE
                .item(name, p -> new TieredBeltConnectorItem(belt.get(), p))
                .transform(p -> p.properties(b -> b.food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0, true, true), 1.0F).build())))
                .lang(name.substring(0, 1).toUpperCase() + name.substring(1, name.length() - 15) + " Mechanical Belt")
                .onRegister(c -> c.setType(beltType))
                .register();
    }

    public static void register() {}
}
