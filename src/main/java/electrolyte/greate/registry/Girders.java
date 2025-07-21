package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.ConnectedGirderModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.content.decoration.encasing.GirderEncasingRegistry;
import electrolyte.greate.content.decoration.girder.GreateGirderBlockStateGenerator;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasedShaftBlock;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Objects;

import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.registry.GreateTagPrefixes.girderEncasedShaft;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class Girders {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredGirderEncasedShaftBlock>> GIRDERS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredGirderEncasedShaftBlock>> GIRDERS;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
        generateGirders();
    }

    public static void generateGirders() {
        for(Material mat : GTCEuAPI.materialManager) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var girderEntry = REGISTRATE
                    .block("metal_girder_encased_" + mat.getName() + "_shaft", p -> new TieredGirderEncasedShaftBlock(p, mat))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(GreateGirderBlockStateGenerator::blockStateWithShaft)
                    .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                    .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
                    .transform(TagGen.pickaxeOnly())
                    .loot((p, b) -> p.add(b, p.createSingleItemTable(AllBlocks.METAL_GIRDER.get())
                            .withPool(p.applyExplosionCondition(Objects.requireNonNull(Shafts.NEW_SHAFTS.get(shaft, mat)), LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(Objects.requireNonNull(Shafts.NEW_SHAFTS.get(shaft, mat))))))))
                    .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .transform(GirderEncasingRegistry.addVariantTo(Shafts.NEW_SHAFTS.get(shaft, mat)))
                    .register();
            GIRDERS_BUILDER.put(girderEncasedShaft, mat, girderEntry);
        }
        GIRDERS = GIRDERS_BUILDER.build();
    }
}
