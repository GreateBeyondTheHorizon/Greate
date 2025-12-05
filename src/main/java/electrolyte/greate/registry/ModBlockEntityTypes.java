package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.content.fluids.pump.TieredPumpBlockEntity;
import electrolyte.greate.content.fluids.pump.TieredPumpRenderer;
import electrolyte.greate.content.kinetics.base.TieredShaftRenderer;
import electrolyte.greate.content.kinetics.base.TieredSingleAxisRotatingVisual;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import electrolyte.greate.content.kinetics.belt.TieredBeltRenderer;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlockEntity;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlockEntity;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlockEntity;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlockRenderer;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanVisual;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlockEntity;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxRenderer;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxVisual;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlockEntity;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneRenderer;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlockEntity;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerRenderer;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerVisual;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlockEntity;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressRenderer;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressVisual;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import electrolyte.greate.content.kinetics.saw.TieredSawRenderer;
import electrolyte.greate.content.kinetics.saw.TieredSawVisual;
import electrolyte.greate.content.kinetics.simpleRelays.*;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogRenderer;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogVisual;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlockEntity;

import java.util.ArrayList;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_BRACKETED_KINETIC = REGISTRATE
            .blockEntity("tiered_bracketed_kinetic", TieredBracketedKineticBlockEntity::new)
            .visual(() -> TieredBracketedKineticBlockEntityVisual::create, false)
            .validBlocksDeferred(() -> new ArrayList<>(Shafts.SHAFTS.values()))
            .validBlocksDeferred(() -> new ArrayList<>(Cogwheels.COGWHEELS.values()))
            .validBlocksDeferred(() -> new ArrayList<>(Cogwheels.LARGE_COGWHEELS.values()))
            .renderer(() -> TieredBracketedKineticBlockEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<TieredKineticBlockEntity> TIERED_ENCASED_SHAFT = REGISTRATE
            .blockEntity("tiered_encased_shaft", TieredKineticBlockEntity::new)
            .visual(() -> TieredSingleAxisRotatingVisual::shaft, false)
            .validBlocksDeferred(() -> new ArrayList<>(Shafts.ANDESITE_ENCASED_SHAFTS.values()))
            .validBlocksDeferred(() -> new ArrayList<>(Shafts.BRASS_ENCASED_SHAFTS.values()))
            .validBlocksDeferred(() -> new ArrayList<>(Girders.GIRDERS.values()))
            .renderer(() -> TieredShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_cogwheel", TieredSimpleKineticBlockEntity::new)
            .visual(() -> TieredEncasedCogVisual::small, false)
            .validBlocksDeferred(() -> new ArrayList<>(Cogwheels.ANDESITE_ENCASED_COGWHEELS.values()))
            .validBlocksDeferred(() -> new ArrayList<>(Cogwheels.BRASS_ENCASED_COGWHEELS.values()))
            .renderer(() -> TieredEncasedCogRenderer::small)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_LARGE_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_large_cogwheel", TieredSimpleKineticBlockEntity::new)
            .visual(() -> TieredEncasedCogVisual::large, false)
            .validBlocksDeferred(() -> new ArrayList<>(Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS.values()))
            .validBlocksDeferred(() -> new ArrayList<>(Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS.values()))
            .renderer(() -> TieredEncasedCogRenderer::large)
            .register();

    public static final BlockEntityEntry<TieredGearboxBlockEntity> TIERED_GEARBOX = REGISTRATE
            .blockEntity("tiered_gearbox", TieredGearboxBlockEntity::new)
            .visual(() -> TieredGearboxVisual::new, false)
            .validBlocksDeferred(() -> new ArrayList<>(Gearboxes.GEARBOXES.values()))
            .renderer(() -> TieredGearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredPoweredShaftBlockEntity> TIERED_POWERED_SHAFT = REGISTRATE
            .blockEntity("tiered_powered_shaft", TieredPoweredShaftBlockEntity::new)
            .visual(() -> TieredSingleAxisRotatingVisual::poweredShaft)
            .validBlocksDeferred(() -> new ArrayList<>(Shafts.POWERED_SHAFTS.values()))
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMillstoneBlockEntity> TIERED_MILLSTONE = REGISTRATE
            .blockEntity("tiered_millstone", TieredMillstoneBlockEntity::new)
            .visual(() -> TieredSingleAxisRotatingVisual::millstoneCog, false)
            .validBlocks(Millstones.MILLSTONES)
            .renderer(() -> TieredMillstoneRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelBlockEntity> TIERED_CRUSHING_WHEEL = REGISTRATE
            .blockEntity("tiered_crushing_wheel", TieredCrushingWheelBlockEntity::new)
            .visual(() -> TieredSingleAxisRotatingVisual::crushingWheel, false)
            .validBlocks(CrushingWheels.CRUSHING_WHEELS)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelControllerBlockEntity> TIERED_CRUSHING_WHEEL_CONTROLLER = REGISTRATE
            .blockEntity("tiered_crushing_wheel_controller", TieredCrushingWheelControllerBlockEntity::new)
            .validBlocks(CrushingWheels.CRUSHING_WHEEL_CONTROLLERS)
            .register();

    public static final BlockEntityEntry<TieredBeltBlockEntity> TIERED_BELT = REGISTRATE
            .blockEntity("tiered_belt", TieredBeltBlockEntity::new)
            .validBlocksDeferred(() -> new ArrayList<>(Belts.BELTS.values()))
            .renderer(() -> TieredBeltRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMechanicalPressBlockEntity> TIERED_MECHANICAL_PRESS = REGISTRATE
            .blockEntity("tiered_mechanical_press", TieredMechanicalPressBlockEntity::new)
            .visual(() -> TieredMechanicalPressVisual::new)
            .validBlocks(MechanicalPresses.MECHANICAL_PRESSES)
            .renderer(() -> TieredMechanicalPressRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMechanicalMixerBlockEntity> TIERED_MECHANICAL_MIXER = REGISTRATE
            .blockEntity("tiered_mechanical_mixer", TieredMechanicalMixerBlockEntity::new)
            .visual(() -> TieredMechanicalMixerVisual::new)
            .validBlocks(MechanicalMixers.MECHANICAL_MIXERS)
            .renderer(() -> TieredMechanicalMixerRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredPumpBlockEntity> TIERED_PUMP = REGISTRATE
            .blockEntity("tiered_mechanical_pump", TieredPumpBlockEntity::new)
            .visual(() -> TieredSingleAxisRotatingVisual::pumpCog)
            .validBlocks(Pumps.MECHANICAL_PUMPS)
            .renderer(() -> TieredPumpRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredSawBlockEntity> TIERED_SAW = REGISTRATE
            .blockEntity("tiered_saw", TieredSawBlockEntity::new)
            .visual(() -> TieredSawVisual::new)
            .validBlocks(Saws.SAWS)
            .renderer(() -> TieredSawRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredEncasedFanBlockEntity> TIERED_FAN = REGISTRATE
            .blockEntity("tiered_encased_fan", TieredEncasedFanBlockEntity::new)
            .visual(() -> TieredEncasedFanVisual::new, false)
            .validBlocks(EncasedFans.FANS)
            .renderer(() -> TieredEncasedFanBlockRenderer::new)
            .register();

    public static void register() {}
}
