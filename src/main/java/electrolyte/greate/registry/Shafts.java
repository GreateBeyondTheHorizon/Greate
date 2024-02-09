package electrolyte.greate.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
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
import static electrolyte.greate.GreateValues.TMS;

public class Shafts {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredShaftBlock> ANDESITE_SHAFT = shaft("andesite_shaft", ULV, TMS[0], GreateItemTags.SHAFTS_ANDESITE.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_ANDESITE_SHAFT = poweredShaft("powered_andesite_shaft", ULV, TMS[0], Shafts.ANDESITE_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_ANDESITE_SHAFT = andesiteEncasedShaft("andesite_encased_andesite_shaft", ULV, TMS[0], Shafts.ANDESITE_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_ANDESITE_SHAFT = brassEncasedShaft("brass_encased_andesite_shaft", ULV, TMS[0], Shafts.ANDESITE_SHAFT);
    public static final BlockEntry<TieredShaftBlock> STEEL_SHAFT = shaft("steel_shaft", LV, TMS[1], GreateItemTags.SHAFTS_STEEL.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_STEEL_SHAFT = poweredShaft("powered_steel_shaft", LV, TMS[1], Shafts.STEEL_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_STEEL_SHAFT = andesiteEncasedShaft("andesite_encased_steel_shaft", LV, TMS[1], Shafts.STEEL_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_STEEL_SHAFT = brassEncasedShaft("brass_encased_steel_shaft", LV, TMS[1], Shafts.STEEL_SHAFT);
    public static final BlockEntry<TieredShaftBlock> ALUMINIUM_SHAFT = shaft("aluminium_shaft", MV, TMS[2], GreateItemTags.SHAFTS_ALUMINIUM.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_ALUMINIUM_SHAFT = poweredShaft("powered_aluminium_shaft", MV, TMS[2], Shafts.ALUMINIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_ALUMINIUM_SHAFT = andesiteEncasedShaft("andesite_encased_aluminium_shaft", MV, TMS[2], Shafts.ALUMINIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_ALUMINIUM_SHAFT = brassEncasedShaft("brass_encased_aluminium_shaft", MV, TMS[2], Shafts.ALUMINIUM_SHAFT);
    public static final BlockEntry<TieredShaftBlock> STAINLESS_STEEL_SHAFT = shaft("stainless_steel_shaft", HV, TMS[3], GreateItemTags.SHAFTS_STAINLESS_STEEL.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_STAINLESS_STEEL_SHAFT = poweredShaft("powered_stainless_steel_shaft", HV, TMS[3], Shafts.STAINLESS_STEEL_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT = andesiteEncasedShaft("andesite_encased_stainless_steel_shaft", HV, TMS[3], Shafts.STAINLESS_STEEL_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_STAINLESS_STEEL_SHAFT = brassEncasedShaft("brass_encased_stainless_steel_shaft", HV, TMS[3], Shafts.STAINLESS_STEEL_SHAFT);
    public static final BlockEntry<TieredShaftBlock> TITANIUM_SHAFT = shaft("titanium_shaft", EV, TMS[4], GreateItemTags.SHAFTS_TITANIUM.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_TITANIUM_SHAFT = poweredShaft("powered_titanium_shaft", EV, TMS[4], Shafts.TITANIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_TITANIUM_SHAFT = andesiteEncasedShaft("andesite_encased_titanium_shaft", EV, TMS[4], Shafts.TITANIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_TITANIUM_SHAFT = brassEncasedShaft("brass_encased_titanium_shaft", EV, TMS[4], Shafts.TITANIUM_SHAFT);
    public static final BlockEntry<TieredShaftBlock> TUNGSTENSTEEL_SHAFT = shaft("tungstensteel_shaft", IV, TMS[5], GreateItemTags.SHAFTS_TUNGSTENSTEEL.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_TUNGSTENSTEEL_SHAFT = poweredShaft("powered_tungstensteel_shaft", IV, TMS[5], Shafts.TUNGSTENSTEEL_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT = andesiteEncasedShaft("andesite_encased_tungstensteel_shaft", IV, TMS[5], Shafts.TUNGSTENSTEEL_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_TUNGSTENSTEEL_SHAFT = brassEncasedShaft("brass_encased_tungstensteel_shaft", IV, TMS[5], Shafts.TUNGSTENSTEEL_SHAFT);
    public static final BlockEntry<TieredShaftBlock> PALLADIUM_SHAFT = shaft("palladium_shaft", LuV, TMS[6], GreateItemTags.SHAFTS_PALLADIUM.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_PALLADIUM_SHAFT = poweredShaft("powered_palladium_shaft", LuV, TMS[6], Shafts.PALLADIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_PALLADIUM_SHAFT = andesiteEncasedShaft("andesite_encased_palladium_shaft", LuV, TMS[6], Shafts.PALLADIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_PALLADIUM_SHAFT = brassEncasedShaft("brass_encased_palladium_shaft", LuV, TMS[6], Shafts.PALLADIUM_SHAFT);
    public static final BlockEntry<TieredShaftBlock> NAQUADAH_SHAFT = shaft("naquadah_shaft", ZPM, TMS[7], GreateItemTags.SHAFTS_NAQUADAH.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_NAQUADAH_SHAFT = poweredShaft("powered_naquadah_shaft", ZPM, TMS[7], Shafts.NAQUADAH_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_NAQUADAH_SHAFT = andesiteEncasedShaft("andesite_encased_naquadah_shaft", ZPM, TMS[7], Shafts.NAQUADAH_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_NAQUADAH_SHAFT = brassEncasedShaft("brass_encased_naquadah_shaft", ZPM, TMS[7], Shafts.NAQUADAH_SHAFT);
    public static final BlockEntry<TieredShaftBlock> DARMSTADTIUM_SHAFT = shaft("darmstadtium_shaft", UV, TMS[8], GreateItemTags.SHAFTS_DARMSTADTIUM.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_DARMSTADTIUM_SHAFT = poweredShaft("powered_darmstadtium_shaft", UV, TMS[8], Shafts.DARMSTADTIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_DARMSTADTIUM_SHAFT = andesiteEncasedShaft("andesite_encased_darmstadtium_shaft", UV, TMS[8], Shafts.DARMSTADTIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_DARMSTADTIUM_SHAFT = brassEncasedShaft("brass_encased_darmstadtium_shaft", UV, TMS[8], Shafts.DARMSTADTIUM_SHAFT);
    public static final BlockEntry<TieredShaftBlock> NEUTRONIUM_SHAFT = shaft("neutronium_shaft", UHV, TMS[9], GreateItemTags.SHAFTS_NEUTRONIUM.itemTag);
    public static final BlockEntry<TieredPoweredShaftBlock> POWERED_NEUTRONIUM_SHAFT = poweredShaft("powered_neutronium_shaft", UHV, TMS[9], Shafts.NEUTRONIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> ANDESITE_ENCASED_NEUTRONIUM_SHAFT = andesiteEncasedShaft("andesite_encased_neutronium_shaft", UHV, TMS[9], Shafts.NEUTRONIUM_SHAFT);
    public static final BlockEntry<TieredEncasedShaftBlock> BRASS_ENCASED_NEUTRONIUM_SHAFT = brassEncasedShaft("brass_encased_neutronium_shaft", UHV, TMS[9], Shafts.NEUTRONIUM_SHAFT);

    public static BlockEntry<TieredShaftBlock> shaft(String name, int tier, String materialType, TagKey<Item> shaftTag) {
        return REGISTRATE
                .block(name, TieredShaftBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
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

    public static BlockEntry<TieredPoweredShaftBlock> poweredShaft(String name, int tier, String materialType, BlockEntry<TieredShaftBlock> shaft) {
        return REGISTRATE
                .block(name, p -> new TieredPoweredShaftBlock(p, shaft::get))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(TagGen.pickaxeOnly())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .blockstate(GreateBlockStateGen.tieredPoweredShaftProvider())
                .loot((l, b) -> l.dropOther(b, shaft.get()))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static BlockEntry<TieredEncasedShaftBlock> andesiteEncasedShaft(String name, int tier, String materialType, BlockEntry<TieredShaftBlock> shaft) {
        return REGISTRATE
                .block(name, p -> new TieredEncasedShaftBlock(p, AllBlocks.ANDESITE_CASING::get, shaft::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(GreateBuilderTransformers.tieredAndesiteEncasedShaft(shaft, () -> AllSpriteShifts.ANDESITE_CASING))
                .transform(EncasingRegistry.addVariantTo(shaft))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static BlockEntry<TieredEncasedShaftBlock> brassEncasedShaft(String name, int tier, String materialType, BlockEntry<TieredShaftBlock> shaft) {
        return REGISTRATE
                .block(name, p -> new TieredEncasedShaftBlock(p, AllBlocks.BRASS_CASING::get, shaft::get))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(GreateBuilderTransformers.tieredBrassEncasedShaft(shaft, () -> AllSpriteShifts.BRASS_CASING))
                .transform(EncasingRegistry.addVariantTo(shaft))
                .transform(TagGen.axeOrPickaxe())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static void register() {}
}
