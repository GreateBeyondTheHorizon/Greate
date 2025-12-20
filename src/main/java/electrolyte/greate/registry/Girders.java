package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.simibubi.create.content.decoration.girder.ConnectedGirderModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.decoration.encasing.GirderEncasingRegistry;
import electrolyte.greate.content.decoration.girder.TieredGirderEncasedShaftBlock;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import electrolyte.greate.foundation.client.models.ShaftModel;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static electrolyte.greate.registry.GreateTagPrefixes.girderEncasedShaft;
import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class Girders {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredGirderEncasedShaftBlock>> GIRDERS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredGirderEncasedShaftBlock>> GIRDERS;

    public static void register() {
        GreateRegistries.REGISTRATE.creativeModeTab(Greate.GREATE_TAB);
        generateGirders();
    }

    public static void generateGirders() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var girderEntry = GreateRegistries.REGISTRATE
                    .block("metal_girder_encased_" + mat.getName() + "_shaft", p -> new TieredGirderEncasedShaftBlock(p, mat))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(NonNullBiConsumer.noop())
                    .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK).noLootTable())
                    .onRegister(CreateRegistrate.blockModel(() -> ConnectedGirderModel::new))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(ShaftModel::create)
                    .transform(GirderEncasingRegistry.addVariantTo(Shafts.SHAFTS.get(shaft, mat)))
                    .transform(GTBlocks.unificationBlock(girderEncasedShaft, mat))
                    .register();
            GIRDERS_BUILDER.put(girderEncasedShaft, mat, girderEntry);
        }
        GIRDERS = GIRDERS_BUILDER.build();
    }
}
