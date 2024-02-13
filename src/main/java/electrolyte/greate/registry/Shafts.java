package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import electrolyte.greate.registry.GreateTags.GreateItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static electrolyte.greate.Greate.REGISTRATE;
import static electrolyte.greate.GreateValues.TM;

public class Shafts {

    // Shaft
    public static final BlockEntry<TieredShaftBlock>[] SHAFTS = new BlockEntry[10];
    public static BlockEntry<TieredShaftBlock>
            ANDESITE_SHAFT,
            STEEL_SHAFT,
            ALUMINIUM_SHAFT,
            STAINLESS_STEEL_SHAFT,
            TITANIUM_SHAFT,
            TUNGSTENSTEEL_SHAFT,
            PALLADIUM_SHAFT,
            NAQUADAH_SHAFT,
            DARMSTADTIUM_SHAFT,
            NEUTRONIUM_SHAFT;

    // Powered shaft
    public static final BlockEntry<TieredPoweredShaftBlock>[] POWERED_SHAFTS = new BlockEntry[10];
    public static BlockEntry<TieredPoweredShaftBlock>
            POWERED_ANDESITE_SHAFT,
            POWERED_STEEL_SHAFT,
            POWERED_ALUMINIUM_SHAFT,
            POWERED_STAINLESS_STEEL_SHAFT,
            POWERED_TITANIUM_SHAFT,
            POWERED_TUNGSTENSTEEL_SHAFT,
            POWERED_PALLADIUM_SHAFT,
            POWERED_NAQUADAH_SHAFT,
            POWERED_DARMSTADTIUM_SHAFT,
            POWERED_NEUTRONIUM_SHAFT;

    // Encased shaft
    public static final BlockEntry<TieredEncasedShaftBlock>[] ANDESITE_ENCASED_SHAFTS = new BlockEntry[10];
    public static final BlockEntry<TieredEncasedShaftBlock>[] BRASS_ENCASED_SHAFTS = new BlockEntry[10];
    public static BlockEntry<TieredEncasedShaftBlock>
            ANDESITE_ENCASED_ANDESITE_SHAFT,
            ANDESITE_ENCASED_STEEL_SHAFT,
            ANDESITE_ENCASED_ALUMINIUM_SHAFT,
            ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT,
            ANDESITE_ENCASED_TITANIUM_SHAFT,
            ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT,
            ANDESITE_ENCASED_PALLADIUM_SHAFT,
            ANDESITE_ENCASED_NAQUADAH_SHAFT,
            ANDESITE_ENCASED_DARMSTADTIUM_SHAFT,
            ANDESITE_ENCASED_NEUTRONIUM_SHAFT,
            BRASS_ENCASED_ANDESITE_SHAFT,
            BRASS_ENCASED_STEEL_SHAFT,
            BRASS_ENCASED_ALUMINIUM_SHAFT,
            BRASS_ENCASED_STAINLESS_STEEL_SHAFT,
            BRASS_ENCASED_TITANIUM_SHAFT,
            BRASS_ENCASED_TUNGSTENSTEEL_SHAFT,
            BRASS_ENCASED_PALLADIUM_SHAFT,
            BRASS_ENCASED_NAQUADAH_SHAFT,
            BRASS_ENCASED_DARMSTADTIUM_SHAFT,
            BRASS_ENCASED_NEUTRONIUM_SHAFT;

    public static void register() {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);

        // Shaft
        SHAFTS[ULV] = ANDESITE_SHAFT = shaft(ULV, GreateItemTags.SHAFTS_ANDESITE.itemTag);
        SHAFTS[LV] = STEEL_SHAFT = shaft(LV, GreateItemTags.SHAFTS_STEEL.itemTag);
        SHAFTS[MV] = ALUMINIUM_SHAFT = shaft(MV, GreateItemTags.SHAFTS_ALUMINIUM.itemTag);
        SHAFTS[HV] = STAINLESS_STEEL_SHAFT = shaft(HV, GreateItemTags.SHAFTS_STAINLESS_STEEL.itemTag);
        SHAFTS[EV] = TITANIUM_SHAFT = shaft(EV, GreateItemTags.SHAFTS_TITANIUM.itemTag);
        SHAFTS[IV] = TUNGSTENSTEEL_SHAFT = shaft(IV, GreateItemTags.SHAFTS_TUNGSTENSTEEL.itemTag);
        SHAFTS[LuV] = PALLADIUM_SHAFT = shaft(LuV, GreateItemTags.SHAFTS_PALLADIUM.itemTag);
        SHAFTS[ZPM] = NAQUADAH_SHAFT = shaft(ZPM, GreateItemTags.SHAFTS_NAQUADAH.itemTag);
        SHAFTS[UV] = DARMSTADTIUM_SHAFT = shaft(UV, GreateItemTags.SHAFTS_DARMSTADTIUM.itemTag);
        SHAFTS[UHV] = NEUTRONIUM_SHAFT = shaft(UHV, GreateItemTags.SHAFTS_NEUTRONIUM.itemTag);

        // Powered shaft
        POWERED_SHAFTS[ULV] = POWERED_ANDESITE_SHAFT = poweredShaft(ULV);
        POWERED_SHAFTS[LV] = POWERED_STEEL_SHAFT = poweredShaft(LV);
        POWERED_SHAFTS[MV] = POWERED_ALUMINIUM_SHAFT = poweredShaft(MV);
        POWERED_SHAFTS[HV] = POWERED_STAINLESS_STEEL_SHAFT = poweredShaft(HV);
        POWERED_SHAFTS[EV] = POWERED_TITANIUM_SHAFT = poweredShaft(EV);
        POWERED_SHAFTS[IV] = POWERED_TUNGSTENSTEEL_SHAFT = poweredShaft(IV);
        POWERED_SHAFTS[LuV] = POWERED_PALLADIUM_SHAFT = poweredShaft(LuV);
        POWERED_SHAFTS[ZPM] = POWERED_NAQUADAH_SHAFT = poweredShaft(ZPM);
        POWERED_SHAFTS[UV] = POWERED_DARMSTADTIUM_SHAFT = poweredShaft(UV);
        POWERED_SHAFTS[UHV] = POWERED_NEUTRONIUM_SHAFT = poweredShaft(UHV);

        // Andesite encased shaft
        ANDESITE_ENCASED_SHAFTS[ULV] = ANDESITE_ENCASED_ANDESITE_SHAFT = andesiteEncasedShaft(ULV);
        ANDESITE_ENCASED_SHAFTS[LV] = ANDESITE_ENCASED_STEEL_SHAFT = andesiteEncasedShaft(LV);
        ANDESITE_ENCASED_SHAFTS[MV] = ANDESITE_ENCASED_ALUMINIUM_SHAFT = andesiteEncasedShaft(MV);
        ANDESITE_ENCASED_SHAFTS[HV] = ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT = andesiteEncasedShaft(HV);
        ANDESITE_ENCASED_SHAFTS[EV] = ANDESITE_ENCASED_TITANIUM_SHAFT = andesiteEncasedShaft(EV);
        ANDESITE_ENCASED_SHAFTS[IV] = ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT = andesiteEncasedShaft(IV);
        ANDESITE_ENCASED_SHAFTS[LuV] = ANDESITE_ENCASED_PALLADIUM_SHAFT = andesiteEncasedShaft(LuV);
        ANDESITE_ENCASED_SHAFTS[ZPM] = ANDESITE_ENCASED_NAQUADAH_SHAFT = andesiteEncasedShaft(ZPM);
        ANDESITE_ENCASED_SHAFTS[UV] = ANDESITE_ENCASED_DARMSTADTIUM_SHAFT = andesiteEncasedShaft(UV);
        ANDESITE_ENCASED_SHAFTS[UHV] = ANDESITE_ENCASED_NEUTRONIUM_SHAFT = andesiteEncasedShaft(UHV);

        // Brass encased shaft
        BRASS_ENCASED_SHAFTS[ULV] = BRASS_ENCASED_ANDESITE_SHAFT = brassEncasedShaft(ULV);
        BRASS_ENCASED_SHAFTS[LV] = BRASS_ENCASED_STEEL_SHAFT = brassEncasedShaft(LV);
        BRASS_ENCASED_SHAFTS[MV] = BRASS_ENCASED_ALUMINIUM_SHAFT = brassEncasedShaft(MV);
        BRASS_ENCASED_SHAFTS[HV] = BRASS_ENCASED_STAINLESS_STEEL_SHAFT = brassEncasedShaft(HV);
        BRASS_ENCASED_SHAFTS[EV] = BRASS_ENCASED_TITANIUM_SHAFT = brassEncasedShaft(EV);
        BRASS_ENCASED_SHAFTS[IV] = BRASS_ENCASED_TUNGSTENSTEEL_SHAFT = brassEncasedShaft(IV);
        BRASS_ENCASED_SHAFTS[LuV] = BRASS_ENCASED_PALLADIUM_SHAFT = brassEncasedShaft(LuV);
        BRASS_ENCASED_SHAFTS[ZPM] = BRASS_ENCASED_NAQUADAH_SHAFT = brassEncasedShaft(ZPM);
        BRASS_ENCASED_SHAFTS[UV] = BRASS_ENCASED_DARMSTADTIUM_SHAFT = brassEncasedShaft(UV);
        BRASS_ENCASED_SHAFTS[UHV] = BRASS_ENCASED_NEUTRONIUM_SHAFT = brassEncasedShaft(UHV);
    }

    private static BlockEntry<TieredShaftBlock> shaft(int tier, TagKey<Item> shaftTag) {
        return shaft(tier, TM[tier], shaftTag);
    }

    public static BlockEntry<TieredShaftBlock> shaft(int tier, Material material, TagKey<Item> shaftTag) {
        return REGISTRATE
                .block(material.getName() + "_shaft", TieredShaftBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .transform(TagGen.pickaxeOnly())
                .blockstate(GreateBlockStateGen.tieredShaftProvider())
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .onRegister(c -> c.setTier(tier))
                .simpleItem()
                .item()
                .tag(GreateItemTags.SHAFTS.itemTag)
                .tag(shaftTag).build()
                .register();
    }

    private static BlockEntry<TieredPoweredShaftBlock> poweredShaft(int tier) {
        return poweredShaft(tier, TM[tier], SHAFTS[tier]);
    }

    public static BlockEntry<TieredPoweredShaftBlock> poweredShaft(int tier, Material material, BlockEntry<TieredShaftBlock> shaft) {
        return REGISTRATE
                .block("powered_" + material.getName() + "_shaft", p -> new TieredPoweredShaftBlock(p, shaft::get))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(TagGen.pickaxeOnly())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
                .loot((l, b) -> l.dropOther(b, shaft.get()))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    private static BlockEntry<TieredEncasedShaftBlock> andesiteEncasedShaft(int tier) {
        return andesiteEncasedShaft(tier, TM[tier], SHAFTS[tier]);
    }

    public static BlockEntry<TieredEncasedShaftBlock> andesiteEncasedShaft(int tier, Material material, BlockEntry<TieredShaftBlock> shaft) {
        return encasedShaft(tier, material, shaft, AllBlocks.ANDESITE_CASING, AllSpriteShifts.ANDESITE_CASING);
    }

    private static BlockEntry<TieredEncasedShaftBlock> brassEncasedShaft(int tier) {
        return brassEncasedShaft(tier, TM[tier], SHAFTS[tier]);
    }

    public static BlockEntry<TieredEncasedShaftBlock> brassEncasedShaft(int tier, Material material, BlockEntry<TieredShaftBlock> shaft) {
        return encasedShaft(tier, material, shaft, AllBlocks.BRASS_CASING, AllSpriteShifts.BRASS_CASING);
    }

    public static BlockEntry<TieredEncasedShaftBlock> encasedShaft(int tier, Material material, BlockEntry<TieredShaftBlock> shaft, BlockEntry<CasingBlock> casing, CTSpriteShiftEntry casingSpriteShiftEntry) {
        boolean andesiteCasing = casing == AllBlocks.ANDESITE_CASING;
        String casingName = andesiteCasing ? "andesite" : "brass";
        NonNullUnaryOperator<BlockBuilder<TieredEncasedShaftBlock, CreateRegistrate>> encasingTransformer = andesiteCasing ?
                GreateBuilderTransformers.tieredAndesiteEncasedShaft(shaft, () -> casingSpriteShiftEntry) :
                GreateBuilderTransformers.tieredBrassEncasedShaft(shaft, () -> casingSpriteShiftEntry);
        return REGISTRATE
                .block( casingName + "_encased_" + material.getName() + "_shaft", p -> new TieredEncasedShaftBlock(p, casing::get, shaft::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(encasingTransformer)
                .transform(EncasingRegistry.addVariantTo(shaft))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialForBlock(material))
                .onRegister(c -> c.setTier(tier))
                .register();
    }
}
