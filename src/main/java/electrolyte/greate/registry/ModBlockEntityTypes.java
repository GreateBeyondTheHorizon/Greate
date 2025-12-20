package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import electrolyte.greate.GreateRegistries;
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
import net.minecraft.world.level.block.Block;

import java.util.Collection;


public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_BRACKETED_KINETIC = GreateRegistries.REGISTRATE
            .blockEntity("tiered_bracketed_kinetic", TieredBracketedKineticBlockEntity::new)
            .renderer(() -> TieredBracketedKineticBlockEntityRenderer::new)
            .validBlocks(getBlocks(Shafts.SHAFTS.values()))
            .validBlocks(getBlocks(Cogwheels.COGWHEELS.values()))
            .validBlocks(getBlocks(Cogwheels.LARGE_COGWHEELS.values()))
            .register();
    public static final BlockEntityEntry<TieredKineticBlockEntity> TIERED_ENCASED_SHAFT = GreateRegistries.REGISTRATE
            .blockEntity("tiered_encased_shaft", TieredKineticBlockEntity::new)
            .renderer(() -> TieredShaftRenderer::new)
            .validBlocks(getBlocks(Shafts.ANDESITE_ENCASED_SHAFTS.values()))
            .validBlocks(getBlocks(Shafts.BRASS_ENCASED_SHAFTS.values()))
            .validBlocks(getBlocks(Girders.GIRDERS.values()))
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_COGWHEEL = GreateRegistries.REGISTRATE
            .blockEntity("tiered_encased_cogwheel", TieredSimpleKineticBlockEntity::new)
            .renderer(() -> TieredEncasedCogRenderer::small)
            .validBlocks(getBlocks(Cogwheels.ANDESITE_ENCASED_COGWHEELS.values()))
            .validBlocks(getBlocks(Cogwheels.BRASS_ENCASED_COGWHEELS.values()))
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_LARGE_COGWHEEL = GreateRegistries.REGISTRATE
            .blockEntity("tiered_encased_large_cogwheel", TieredSimpleKineticBlockEntity::new)
            .renderer(() -> TieredEncasedCogRenderer::large)
            .validBlocks(getBlocks(Cogwheels.ANDESITE_ENCASED_LARGE_COGWHEELS.values()))
            .validBlocks(getBlocks(Cogwheels.BRASS_ENCASED_LARGE_COGWHEELS.values()))
            .register();

    public static final BlockEntityEntry<TieredGearboxBlockEntity> TIERED_GEARBOX = GreateRegistries.REGISTRATE
            .blockEntity("tiered_gearbox", TieredGearboxBlockEntity::new)
            .renderer(() -> TieredGearboxRenderer::new)
            .validBlocks(getBlocks(Gearboxes.GEARBOXES.values()))
            .register();

    public static final BlockEntityEntry<TieredPoweredShaftBlockEntity> TIERED_POWERED_SHAFT = GreateRegistries.REGISTRATE
            .blockEntity("tiered_powered_shaft", TieredPoweredShaftBlockEntity::new)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .validBlocks(getBlocks(Shafts.POWERED_SHAFTS.values()))
            .register();

    public static final BlockEntityEntry<TieredMillstoneBlockEntity> TIERED_MILLSTONE = GreateRegistries.REGISTRATE
            .blockEntity("tiered_millstone", TieredMillstoneBlockEntity::new)
            .validBlocks(Millstones.MILLSTONES)
            .renderer(() -> TieredMillstoneRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelBlockEntity> TIERED_CRUSHING_WHEEL = GreateRegistries.REGISTRATE
            .blockEntity("tiered_crushing_wheel", TieredCrushingWheelBlockEntity::new)
            .validBlocks(CrushingWheels.CRUSHING_WHEELS)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelControllerBlockEntity> TIERED_CRUSHING_WHEEL_CONTROLLER = GreateRegistries.REGISTRATE
            .blockEntity("tiered_crushing_wheel_controller", TieredCrushingWheelControllerBlockEntity::new)
            .validBlocks(CrushingWheels.CRUSHING_WHEEL_CONTROLLERS)
            .register();

    public static final BlockEntityEntry<TieredBeltBlockEntity> TIERED_BELT = GreateRegistries.REGISTRATE
            .blockEntity("tiered_belt", TieredBeltBlockEntity::new)
            .renderer(() -> TieredBeltRenderer::new)
            .validBlocks(getBlocks(Belts.BELTS.values()))
            .register();



    public static final BlockEntityEntry<TieredMechanicalPressBlockEntity> TIERED_MECHANICAL_PRESS = GreateRegistries.REGISTRATE
            .blockEntity("tiered_mechanical_press", TieredMechanicalPressBlockEntity::new)
            .validBlocks(MechanicalPresses.MECHANICAL_PRESSES)
            .renderer(() -> TieredMechanicalPressRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMechanicalMixerBlockEntity> TIERED_MECHANICAL_MIXER = GreateRegistries.REGISTRATE
            .blockEntity("tiered_mechanical_mixer", TieredMechanicalMixerBlockEntity::new)
            .validBlocks(MechanicalMixers.MECHANICAL_MIXERS)
            .renderer(() -> TieredMechanicalMixerRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredPumpBlockEntity> TIERED_PUMP = GreateRegistries.REGISTRATE
            .blockEntity("tiered_mechanical_pump", TieredPumpBlockEntity::new)
            .validBlocks(Pumps.MECHANICAL_PUMPS)
            .renderer(() -> TieredPumpRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredSawBlockEntity> TIERED_SAW = GreateRegistries.REGISTRATE
            .blockEntity("tiered_saw", TieredSawBlockEntity::new)
            .validBlocks(Saws.SAWS)
            .renderer(() -> TieredSawRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredEncasedFanBlockEntity> TIERED_FAN = GreateRegistries.REGISTRATE
            .blockEntity("tiered_encased_fan", TieredEncasedFanBlockEntity::new)
            .validBlocks(EncasedFans.FANS)
            .renderer(() -> TieredEncasedFanBlockRenderer::new)
            .register();

    @SuppressWarnings("unchecked")
    private static <T extends Block> NonNullSupplier<T>[] getBlocks(Collection<BlockEntry<T>> values) {
        return values.stream().map(b -> (NonNullSupplier<T>) b).toArray(NonNullSupplier[]::new);
    }

    public static void register() {}

    public static void registerAllVisuals() {
        SimpleBlockEntityVisualizer.builder(TIERED_BRACKETED_KINETIC.get())
                .factory(TieredBracketedKineticBlockEntityVisual::create)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_ENCASED_SHAFT.get())
                .factory(TieredSingleAxisRotatingVisual::shaft)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_ENCASED_COGWHEEL.get())
                .factory(TieredEncasedCogVisual::small)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_ENCASED_LARGE_COGWHEEL.get())
                .factory(TieredEncasedCogVisual::large)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_GEARBOX.get())
                .factory(TieredGearboxVisual::new)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_POWERED_SHAFT.get())
                .factory(TieredSingleAxisRotatingVisual::poweredShaft)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_MILLSTONE.get())
                .factory(TieredSingleAxisRotatingVisual::millstoneCog)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_CRUSHING_WHEEL.get())
                .factory(TieredSingleAxisRotatingVisual::crushingWheel)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_MECHANICAL_PRESS.get())
                .factory(TieredMechanicalPressVisual::new)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_MECHANICAL_MIXER.get())
                .factory(TieredMechanicalMixerVisual::new)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_PUMP.get())
                .factory(TieredSingleAxisRotatingVisual::pumpCog)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_SAW.get())
                .factory(TieredSawVisual::new)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();

        SimpleBlockEntityVisualizer.builder(TIERED_FAN.get())
                .factory(TieredEncasedFanVisual::new)
                .skipVanillaRender(p -> !p.renderNormally())
                .apply();
    }
}
