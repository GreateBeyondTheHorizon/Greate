package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.content.kinetics.base.TieredShaftInstance;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import electrolyte.greate.content.kinetics.belt.TieredBeltInstance;
import electrolyte.greate.content.kinetics.belt.TieredBeltRenderer;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelBlockEntity;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlockEntity;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlockEntity;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxInstance;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxRenderer;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlockEntity;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneCogInstance;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneRenderer;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlockEntity;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerInstance;
import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerRenderer;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressBlockEntity;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressInstance;
import electrolyte.greate.content.kinetics.press.TieredMechanicalPressRenderer;
import electrolyte.greate.content.fluids.pump.TieredPumpBlockEntity;
import electrolyte.greate.content.fluids.pump.TieredPumpCogInstance;
import electrolyte.greate.content.fluids.pump.TieredPumpRenderer;
import electrolyte.greate.content.kinetics.saw.TieredSawBlockEntity;
import electrolyte.greate.content.kinetics.saw.TieredSawInstance;
import electrolyte.greate.content.kinetics.saw.TieredSawRenderer;
import electrolyte.greate.content.kinetics.simpleRelays.*;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogInstance;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogRenderer;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlockEntity;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_BRACKETED_KINETIC = REGISTRATE
            .blockEntity("tiered_bracketed_kinetic", TieredBracketedKineticBlockEntity::new)
            .instance(() -> TieredBracketedKineticBlockEntityInstance::new, false)
            .validBlocks(Shafts.SHAFTS)
            .validBlocks(Cogwheels.COGWHEELS)
            .validBlocks(Cogwheels.LARGE_COGWHEELS)
            .renderer(() -> TieredBracketedKineticBlockEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<TieredKineticBlockEntity> TIERED_ENCASED_SHAFT = REGISTRATE
            .blockEntity("tiered_encased_shaft", TieredKineticBlockEntity::new)
            .instance(() -> TieredShaftInstance::new, false)
            .validBlocks(Shafts.ANDESITE_ENCASED_SHAFTS)
            .validBlocks(Shafts.BRASS_ENCASED_SHAFTS)
            .validBlocks(Girders.METAL_GIRDER_ENCASED_SHAFTS)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_cogwheel", TieredSimpleKineticBlockEntity::new)
            .instance(() -> TieredEncasedCogInstance::small, false)
            .validBlocks(Cogwheels.ANDESITE_ENCASED_COGWHEELS)
            .validBlocks(Cogwheels.BRASS_ENCASED_COGWHEELS)
            .renderer(() -> TieredEncasedCogRenderer::small)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_LARGE_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_large_cogwheel", TieredSimpleKineticBlockEntity::new)
            .instance(() -> TieredEncasedCogInstance::large, false)
            .validBlocks(Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS)
            .validBlocks(Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS)
            .renderer(() -> TieredEncasedCogRenderer::large)
            .register();

    public static final BlockEntityEntry<TieredGearboxBlockEntity> TIERED_GEARBOX = REGISTRATE
            .blockEntity("tiered_gearbox", TieredGearboxBlockEntity::new)
            .instance(() -> TieredGearboxInstance::new, false)
            .validBlocks(Gearboxes.GEARBOXES)
            .renderer(() -> TieredGearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredPoweredShaftBlockEntity> TIERED_POWERED_SHAFT = REGISTRATE
            .blockEntity("tiered_powered_shaft", TieredPoweredShaftBlockEntity::new)
            .instance(() -> TieredSingleRotatingInstance::new)
            .validBlocks(Shafts.POWERED_SHAFTS)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMillstoneBlockEntity> TIERED_MILLSTONE = REGISTRATE
            .blockEntity("tiered_millstone", TieredMillstoneBlockEntity::new)
            .instance(() -> TieredMillstoneCogInstance::new, false)
            .validBlocks(Millstones.MILLSTONES)
            .renderer(() -> TieredMillstoneRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelBlockEntity> TIERED_CRUSHING_WHEEL = REGISTRATE
            .blockEntity("tiered_crushing_wheel", TieredCrushingWheelBlockEntity::new)
            .instance(() -> CutoutRotatingInstance::new, false)
            .validBlocks(CrushingWheels.CRUSHING_WHEELS)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelControllerBlockEntity> TIERED_CRUSHING_WHEEL_CONTROLLER = REGISTRATE
            .blockEntity("tiered_crushing_wheel_controller", TieredCrushingWheelControllerBlockEntity::new)
            .validBlocks(CrushingWheels.CRUSHING_WHEEL_CONTROLLERS)
            .register();

    public static final BlockEntityEntry<TieredBeltBlockEntity> TIERED_BELT = REGISTRATE
            .blockEntity("tiered_belt", TieredBeltBlockEntity::new)
            .instance(() -> TieredBeltInstance::new, TieredBeltBlockEntity::shouldRenderNormally)
            .validBlocks(Belts.BELTS)
            .renderer(() -> TieredBeltRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMechanicalPressBlockEntity> TIERED_MECHANICAL_PRESS = REGISTRATE
            .blockEntity("tiered_mechanical_press", TieredMechanicalPressBlockEntity::new)
            .instance(() -> TieredMechanicalPressInstance::new)
            .validBlocks(MechanicalPresses.MECHANICAL_PRESSES)
            .renderer(() -> TieredMechanicalPressRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMechanicalMixerBlockEntity> TIERED_MECHANICAL_MIXER = REGISTRATE
            .blockEntity("tiered_mechanical_mixer", TieredMechanicalMixerBlockEntity::new)
            .instance(() -> TieredMechanicalMixerInstance::new)
            .validBlocks(MechanicalMixers.MECHANICAL_MIXERS)
            .renderer(() -> TieredMechanicalMixerRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredPumpBlockEntity> TIERED_PUMP = REGISTRATE
            .blockEntity("tiered_mechanical_pump", TieredPumpBlockEntity::new)
            .instance(() -> TieredPumpCogInstance::new, false)
            .validBlocks(Pumps.MECHANICAL_PUMPS)
            .renderer(() -> TieredPumpRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredSawBlockEntity> TIERED_SAW = REGISTRATE
            .blockEntity("tiered_saw", TieredSawBlockEntity::new)
            .instance(() -> TieredSawInstance::new)
            .validBlocks(Saws.SAWS)
            .renderer(() -> TieredSawRenderer::new)
            .register();

    public static void register() {}
}
