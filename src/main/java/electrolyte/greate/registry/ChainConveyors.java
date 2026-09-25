package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import electrolyte.greate.content.kinetics.chainConveyor.TieredChainConveyorBlock;
import electrolyte.greate.foundation.client.models.ChainConveyorModel;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.material.MapColor;

import static electrolyte.greate.registry.GreateTagPrefixes.chainConveyor;

public class ChainConveyors {
    
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredChainConveyorBlock>> CHAIN_CONVEYORS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredChainConveyorBlock>> CHAIN_CONVEYORS;
    
    public static void register() {
        GreateRegistries.REGISTRATE.creativeModeTab(Greate.GREATE_TAB);
        generateChainConveyors();
    }

    public static void generateChainConveyors() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var chainConveyorEntry = GreateRegistries.REGISTRATE
                    .block(mat.getName() + "_chain_conveyor", TieredChainConveyorBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                    .transform(GStress.setImpact(1))
                    .transform(GStress.setImpact(1))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(ChainConveyorModel::create)
                    .blockstate(NonNullBiConsumer.noop())
                    .item()
                    .transform(GTItems.unificationItem(chainConveyor, mat))
                    .model(NonNullBiConsumer.noop()).build()
                    .register();
            CHAIN_CONVEYORS_BUILDER.put(chainConveyor, mat, chainConveyorEntry);
        }
        CHAIN_CONVEYORS = CHAIN_CONVEYORS_BUILDER.build();
    }
}
