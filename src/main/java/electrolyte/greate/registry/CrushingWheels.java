package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlock;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlock;
import electrolyte.greate.foundation.data.GreateBlockStateGen;
import electrolyte.greate.foundation.data.GreateBuilderTransformers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class CrushingWheels {

    static {
        REGISTRATE.creativeModeTab(() -> Greate.GREATE_TAB);
    }

    public static ArrayList<TieredCrushingWheelBlock> CRUSHING_WHEELS = new ArrayList<>();

    public static final BlockEntry<TieredCrushingWheelBlock> ANDESITE_CRUSHING_WHEEL =
            REGISTRATE.block("andesite_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> ANDESITE_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("andesite_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, ANDESITE_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.ULTRA_LOW))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> STEEL_CRUSHING_WHEEL =
            REGISTRATE.block("steel_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.LOW))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> STEEL_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("steel_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, STEEL_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.LOW))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> ALUMINIUM_CRUSHING_WHEEL =
            REGISTRATE.block("aluminium_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.MEDIUM))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> ALUMINIUM_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("aluminium_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, ALUMINIUM_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.MEDIUM))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> STAINLESS_STEEL_CRUSHING_WHEEL =
            REGISTRATE.block("stainless_steel_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.HIGH))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> STAINLESS_STEEL_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("stainless_steel_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, STAINLESS_STEEL_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.HIGH))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> TITANIUM_CRUSHING_WHEEL =
            REGISTRATE.block("titanium_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.EXTREME))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> TITANIUM_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("titanium_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, TITANIUM_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.EXTREME))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> TUNGSTEN_STEEL_CRUSHING_WHEEL =
            REGISTRATE.block("tungsten_steel_crushing_wheel", TieredCrushingWheelBlock::new)
                    .lang("Tungstensteel Crushing Wheel")
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.INSANE))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> TUNGSTEN_STEEL_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("tungsten_steel_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, TUNGSTEN_STEEL_CRUSHING_WHEEL.get()))
                    .lang("Tungstensteel Crushing Wheel Controller")
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.INSANE))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> PALLADIUM_CRUSHING_WHEEL =
            REGISTRATE.block("palladium_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> PALLADIUM_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("palladium_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, PALLADIUM_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.LUDICRIOUS))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> NAQUADAH_CRUSHING_WHEEL =
            REGISTRATE.block("naquadah_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.ZPM))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> NAQUADAH_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("naquadah_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, NAQUADAH_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.ZPM))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> DARMSTADTIUM_CRUSHING_WHEEL =
            REGISTRATE.block("darmstadtium_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.HIGH))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> DARMSTADTIUM_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("darmstadtium_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, DARMSTADTIUM_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.HIGH))
                    .register();

    public static final BlockEntry<TieredCrushingWheelBlock> NEUTRONIUM_CRUSHING_WHEEL =
            REGISTRATE.block("neutronium_crushing_wheel", TieredCrushingWheelBlock::new)
                    .properties(p -> p.color(MaterialColor.METAL))
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(TagGen.pickaxeOnly())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .transform(GreateBuilderTransformers.tieredCrushingWheel())
                    .transform(BlockStressDefaults.setImpact(1.0))
                    .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
                    .register();

    public static final BlockEntry<TieredCrushingWheelControllerBlock> NEUTRONIUM_CRUSHING_WHEEL_CONTROLLER =
            REGISTRATE.block("neutronium_crushing_wheel_controller", p -> new TieredCrushingWheelControllerBlock(p, NEUTRONIUM_CRUSHING_WHEEL.get()))
                    .initialProperties(SharedProperties.CRUSHING_WHEEL_CONTROLLER_MATERIAL)
                    .properties(p -> p.color(MaterialColor.STONE))
                    .properties(p -> p.noOcclusion()
                            .noLootTable().air())
                    .blockstate(GreateBlockStateGen.tieredCrushingWheelControllerProvider())
                    .onRegister(c -> c.setTier(TIER.ULTIMATE_HIGH))
                    .register();

    public static void register() {}
}
