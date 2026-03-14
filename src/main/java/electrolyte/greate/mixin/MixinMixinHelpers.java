package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.core.MixinHelpers;
import com.gregtechceu.gtceu.core.mixins.BlockBehaviourAccessor;
import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.registry.*;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

@Mixin(MixinHelpers.class)
public class MixinMixinHelpers {

    @Shadow(remap = false) @Final private static VanillaBlockLoot BLOCK_LOOT;

    @Inject(method = "generateGTDynamicLoot", at = @At("RETURN"), remap = false)
    private static void greate_generateGTDynamicLoot(Map<ResourceLocation, LootTable> lootTables, CallbackInfo ci) {
        Belts.BELTS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                MixinHelpers.addMaterialBlockLootTables(lootTables, tagPrefix, materialBlockEntryMap));

        Cogwheels.COGWHEELS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                MixinHelpers.addMaterialBlockLootTables(lootTables, tagPrefix, materialBlockEntryMap));
        Cogwheels.ANDESITE_ENCASED_COGWHEELS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.cogwheel, materialBlockEntryMap));
        Cogwheels.BRASS_ENCASED_COGWHEELS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.cogwheel, materialBlockEntryMap));
        Cogwheels.LARGE_COGWHEELS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                MixinHelpers.addMaterialBlockLootTables(lootTables, tagPrefix, materialBlockEntryMap));
        Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.largeCogwheel, materialBlockEntryMap));
        Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.largeCogwheel, materialBlockEntryMap));

        Gearboxes.GEARBOXES.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                MixinHelpers.addMaterialBlockLootTables(lootTables, tagPrefix, materialBlockEntryMap));

        Shafts.SHAFTS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                MixinHelpers.addMaterialBlockLootTables(lootTables, tagPrefix, materialBlockEntryMap));
        Shafts.POWERED_SHAFTS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.shaft, materialBlockEntryMap));
        Shafts.ANDESITE_ENCASED_SHAFTS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.shaft, materialBlockEntryMap));
        Shafts.BRASS_ENCASED_SHAFTS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.shaft, materialBlockEntryMap));
        Girders.GIRDERS.rowMap().forEach((tagPrefix, materialBlockEntryMap) ->
                greate_generateCustomMaterialBlockLootTables(lootTables, GreateTagPrefixes.shaft, materialBlockEntryMap, AllBlocks.METAL_GIRDER.get()));
    }

    @Unique
    private static <B extends Block> void greate_generateCustomMaterialBlockLootTables(Map<ResourceLocation, LootTable> lootTables, TagPrefix tagPrefix, Map<Material, BlockEntry<B>> map, @Nullable Block firstEntry) {
        map.forEach((material, blockEntry) -> {
            ResourceLocation lootTableId = ResourceLocation.fromNamespaceAndPath(blockEntry.getId().getNamespace(), "blocks/" + blockEntry.getId().getPath());
            ((BlockBehaviourAccessor) blockEntry.get()).setDrops(lootTableId);
            LootTable.Builder builder;
            if(firstEntry != null) {
                builder = BLOCK_LOOT
                        .createSingleItemTable(firstEntry)
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Objects.requireNonNull(ChemicalHelper.getBlock(tagPrefix, material)))));
            } else {
                builder = BLOCK_LOOT
                        .createSingleItemTable(Objects.requireNonNull(ChemicalHelper.getBlock(tagPrefix, material)));
            }
            builder.setParamSet(LootContextParamSets.BLOCK);
            lootTables.put(lootTableId, builder.build());
        });
    }

    @Unique
    private static <B extends Block> void greate_generateCustomMaterialBlockLootTables(Map<ResourceLocation, LootTable> lootTables, TagPrefix tagPrefix, Map<Material, BlockEntry<B>> map) {
        greate_generateCustomMaterialBlockLootTables(lootTables, tagPrefix, map, null);
    }
}
