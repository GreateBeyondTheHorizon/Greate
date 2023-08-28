package electrolyte.greate.registry;

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
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlock;
import electrolyte.greate.content.kinetics.gearbox.TieredVerticalGearboxItem;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MaterialColor;

import static electrolyte.greate.Greate.REGISTRATE;

public class Gearboxes {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static final BlockEntry<TieredGearboxBlock> ANDESITE_GEARBOX = REGISTRATE
            .block("andesite_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.ANDESITE_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> ANDESITE_VERTICAL_GEARBOX = REGISTRATE
            .item("andesite_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, ANDESITE_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> STEEL_GEARBOX = REGISTRATE
            .block("steel_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.STEEL_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.LOW))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> STEEL_VERTICAL_GEARBOX = REGISTRATE
            .item("steel_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, STEEL_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> ALUMINIUM_GEARBOX = REGISTRATE
            .block("aluminium_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.ALUMINIUM_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.MEDIUM))
            .transform(GreateBuilderTransformers.tieredGearbox())
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> ALUMINIUM_VERTICAL_GEARBOX = REGISTRATE
            .item("aluminium_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, ALUMINIUM_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> STAINLESS_STEEL_GEARBOX = REGISTRATE
            .block("stainless_steel_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.STAINLESS_STEEL_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.HIGH))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> STAINLESS_STEEL_VERTICAL_GEARBOX = REGISTRATE
            .item("stainless_steel_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, STAINLESS_STEEL_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> TITANIUM_GEARBOX = REGISTRATE
            .block("titanium_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.TITANIUM_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.EXTREME))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> TITANIUM_VERTICAL_GEARBOX = REGISTRATE
            .item("titanium_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, TITANIUM_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> TUNGSTEN_STEEL_GEARBOX = REGISTRATE
            .block("tungsten_steel_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.TUNGSTEN_STEEL_SHAFT_HALF))
            .lang("Tungstensteel Gearbox")
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.INSANE))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> TUNGSTEN_STEEL_VERTICAL_GEARBOX = REGISTRATE
            .item("tungsten_steel_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, TUNGSTEN_STEEL_GEARBOX.get()))
            .lang("Tungstensteel Vertical Gearbox")
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> PALLADIUM_GEARBOX = REGISTRATE
            .block("palladium_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.PALLADIUM_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> PALLADIUM_VERTICAL_GEARBOX = REGISTRATE
            .item("palladium_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, PALLADIUM_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> NAQUADAH_GEARBOX = REGISTRATE
            .block("naquadah_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.NAQUADAH_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.ZPM))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> NAQUADAH_VERTICAL_GEARBOX = REGISTRATE
            .item("naquadah_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, NAQUADAH_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> DARMSTADTIUM_GEARBOX = REGISTRATE
            .block("darmstadtium_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.DARMSTADTIUM_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.ULTIMATE))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> DARMSTADTIUM_VERTICAL_GEARBOX = REGISTRATE
            .item("darmstadtium_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, DARMSTADTIUM_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static final BlockEntry<TieredGearboxBlock> NEUTRONIUM_GEARBOX = REGISTRATE
            .block("neutronium_gearbox", p -> new TieredGearboxBlock(p, GreatePartialModels.NEUTRONIUM_SHAFT_HALF))
            .initialProperties(SharedProperties::stone)
            .properties(Properties::noOcclusion)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(TagGen.axeOrPickaxe())
            .transform(GreateBuilderTransformers.tieredGearbox())
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(CreateRegistrate.casingConnectivity((block, c) -> c.make(block, AllSpriteShifts.ANDESITE_CASING,
                    (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
            .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
            .register();

    public static final ItemEntry<TieredVerticalGearboxItem> NEUTRONIUM_VERTICAL_GEARBOX = REGISTRATE
            .item("neutronium_vertical_gearbox", p -> new TieredVerticalGearboxItem(p, NEUTRONIUM_GEARBOX.get()))
            .transform(GreateBuilderTransformers.tieredGearboxVertical())
            .register();

    public static void register() {}
}
