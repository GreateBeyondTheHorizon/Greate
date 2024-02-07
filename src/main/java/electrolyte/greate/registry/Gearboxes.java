package electrolyte.greate.registry;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateValues.MATERIAL_TYPE;
import electrolyte.greate.GreateValues.TIER;
import electrolyte.greate.content.kinetics.TieredBlockMaterials;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredVerticalGearboxItem;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static electrolyte.greate.Greate.REGISTRATE;

public class Gearboxes {

    static {
        REGISTRATE.setCreativeTab(Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredGearboxBlock> ANDESITE_GEARBOX = gearbox("andesite_gearbox", TIER.ULTRA_LOW, MATERIAL_TYPE.ANDESITE, GreatePartialModels.ANDESITE_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> ANDESITE_VERTICAL_GEARBOX = verticalGearbox("andesite_vertical_gearbox", ANDESITE_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> STEEL_GEARBOX = gearbox("steel_gearbox", TIER.LOW, MATERIAL_TYPE.STEEL, GreatePartialModels.STEEL_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> STEEL_VERTICAL_GEARBOX = verticalGearbox("steel_vertical_gearbox", STEEL_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> ALUMINIUM_GEARBOX = gearbox("aluminium_gearbox", TIER.MEDIUM, MATERIAL_TYPE.ALUMINIUM, GreatePartialModels.ALUMINIUM_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> ALUMINIUM_VERTICAL_GEARBOX = verticalGearbox("aluminium_vertical_gearbox", ALUMINIUM_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> STAINLESS_STEEL_GEARBOX = gearbox("stainless_steel_gearbox", TIER.HIGH, MATERIAL_TYPE.STAINLESS_STEEL, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> STAINLESS_STEEL_VERTICAL_GEARBOX = verticalGearbox("stainless_steel_vertical_gearbox", STAINLESS_STEEL_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> TITANIUM_GEARBOX = gearbox("titanium_gearbox", TIER.EXTREME, MATERIAL_TYPE.TITANIUM, GreatePartialModels.TITANIUM_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> TITANIUM_VERTICAL_GEARBOX = verticalGearbox("titanium_vertical_gearbox", TITANIUM_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> TUNGSTENSTEEL_GEARBOX = gearbox("tungstensteel_gearbox", TIER.INSANE, MATERIAL_TYPE.TUNGSTENSTEEL, GreatePartialModels.TUNGSTENSTEEL_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> TUNGSTENSTEEL_VERTICAL_GEARBOX = verticalGearbox("tungstensteel_vertical_gearbox", TUNGSTENSTEEL_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> PALLADIUM_GEARBOX = gearbox("palladium_gearbox", TIER.LUDICRIOUS, MATERIAL_TYPE.PALLADIUM, GreatePartialModels.PALLADIUM_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> PALLADIUM_VERTICAL_GEARBOX = verticalGearbox("palladium_vertical_gearbox", PALLADIUM_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> NAQUADAH_GEARBOX = gearbox("naquadah_gearbox", TIER.ZPM, MATERIAL_TYPE.NAQUADAH, GreatePartialModels.NAQUADAH_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> NAQUADAH_VERTICAL_GEARBOX = verticalGearbox("naquadah_vertical_gearbox", NAQUADAH_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> DARMSTADTIUM_GEARBOX = gearbox("darmstadtium_gearbox", TIER.ULTIMATE, MATERIAL_TYPE.DARMSTADTIUM, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> DARMSTADTIUM_VERTICAL_GEARBOX = verticalGearbox("darmstadtium_vertical_gearbox", DARMSTADTIUM_GEARBOX);
    public static final BlockEntry<TieredGearboxBlock> NEUTRONIUM_GEARBOX = gearbox("neutronium_gearbox", TIER.ULTIMATE_HIGH, MATERIAL_TYPE.NEUTRONIUM, GreatePartialModels.NEUTRONIUM_SHAFT_HALF);
    public static final ItemEntry<TieredVerticalGearboxItem> NEUTRONIUM_VERTICAL_GEARBOX = verticalGearbox("neutronium_vertical_gearbox", NEUTRONIUM_GEARBOX);

    public static BlockEntry<TieredGearboxBlock> gearbox(String name, TIER tier, MATERIAL_TYPE materialType, PartialModel halfShaftModel) {
        return REGISTRATE
                .block(name, p -> new TieredGearboxBlock(p, halfShaftModel))
                .initialProperties(SharedProperties::stone)
                .properties(Properties::noOcclusion)
                .properties(p -> p.mapColor(MapColor.PODZOL).pushReaction(PushReaction.PUSH_ONLY))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(TagGen.axeOrPickaxe())
                .transform(GreateBuilderTransformers.tieredGearbox())
                .transform(TieredBlockMaterials.setMaterialTypeForBlock(materialType))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
                .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                        (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
                .onRegister(c -> c.setTier(tier))
                .register();
    }

    public static ItemEntry<TieredVerticalGearboxItem> verticalGearbox(String name, BlockEntry<TieredGearboxBlock> gearbox) {
        return REGISTRATE
                .item(name, p -> new TieredVerticalGearboxItem(p, gearbox.get()))
                .transform(GreateBuilderTransformers.tieredGearboxVertical())
                .register();
    }

    public static void register() {}
}
