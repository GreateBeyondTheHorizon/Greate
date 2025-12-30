package electrolyte.greate.registry;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateRegistries;
import electrolyte.greate.content.gtceu.material.GreatePropertyKeys;
import electrolyte.greate.content.gtceu.material.KineticProperty;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredVerticalGearboxItem;
import electrolyte.greate.foundation.client.models.GearboxModel;
import electrolyte.greate.infrastructure.config.GStress;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static electrolyte.greate.registry.GreateTagPrefixes.gearbox;
import static electrolyte.greate.registry.GreateTagPrefixes.verticalGearbox;

public class Gearboxes {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<TieredGearboxBlock>> GEARBOXES_BUILDER = ImmutableTable.builder();
    static ImmutableTable.Builder<TagPrefix, Material, ItemEntry<TieredVerticalGearboxItem>> VERTICAL_GEARBOXES_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<TieredGearboxBlock>> GEARBOXES;
    public static Table<TagPrefix, Material, ItemEntry<TieredVerticalGearboxItem>> VERTICAL_GEARBOXES;

    public static void register() {
        GreateRegistries.REGISTRATE.creativeModeTab(Greate.GREATE_TAB);
        generateGearboxes();
        generateVerticalGearboxes();
    }

    public static void generateGearboxes() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            if(!mat.hasProperty(GreatePropertyKeys.COGWHEEL)) continue;
            KineticProperty prop = mat.getProperty(GreatePropertyKeys.KINETIC);
            int tier = prop.getTier();
            var gearboxEntry = GreateRegistries.REGISTRATE
                    .block(mat.getName() + "_gearbox", p -> new TieredGearboxBlock(p, mat))
                    .blockstate(NonNullBiConsumer.noop())
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL).pushReaction(PushReaction.PUSH_ONLY).noLootTable())
                    .transform(GStress.setNoImpact())
                    .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
                    .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                            (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
                    .onRegister(c -> c.setTier(tier))
                    .onRegister(GearboxModel::create)
                    .item()
                    .transform(GTItems.unificationItem(gearbox, mat))
                    .model(NonNullBiConsumer.noop()).build()
                    .register();
            GEARBOXES_BUILDER.put(gearbox, mat, gearboxEntry);
        }
        GEARBOXES = GEARBOXES_BUILDER.build();
    }

    public static void generateVerticalGearboxes() {
        for(Material mat : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if(!mat.hasProperty(GreatePropertyKeys.KINETIC)) continue;
            if(!mat.hasProperty(GreatePropertyKeys.COGWHEEL)) continue;
            var gearboxEntry = GreateRegistries.REGISTRATE
                    .item(mat.getName() + "_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, mat))
                    .model(NonNullBiConsumer.noop())
                    .register();
            VERTICAL_GEARBOXES_BUILDER.put(verticalGearbox, mat, gearboxEntry);
        }
        VERTICAL_GEARBOXES = VERTICAL_GEARBOXES_BUILDER.build();
    }
}
