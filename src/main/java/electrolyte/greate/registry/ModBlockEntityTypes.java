package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.BeltInstance;
import com.simibubi.create.content.kinetics.belt.BeltRenderer;
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
import electrolyte.greate.content.kinetics.simpleRelays.*;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogInstance;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogRenderer;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlockEntity;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<TieredBracketedKineticBlockEntity> TIERED_KINETIC = REGISTRATE
            .blockEntity("tiered_kinetic", TieredBracketedKineticBlockEntity::new)
            .instance(() -> TieredBracketedKineticBlockEntityInstance::new, false)
            .validBlocks(Shafts.ANDESITE_SHAFT, Shafts.STEEL_SHAFT, Shafts.ALUMINIUM_SHAFT, Shafts.STAINLESS_STEEL_SHAFT,
                    Shafts.TITANIUM_SHAFT, Shafts.TUNGSTENSTEEL_SHAFT, Shafts.PALLADIUM_SHAFT, Shafts.NAQUADAH_SHAFT,
                    Shafts.DARMSTADTIUM_SHAFT, Shafts.NEUTRONIUM_SHAFT, Cogwheels.ANDESITE_COGWHEEL, Cogwheels.LARGE_ANDESITE_COGWHEEL,
                    Cogwheels.STEEL_COGWHEEL, Cogwheels.LARGE_STEEL_COGWHEEL, Cogwheels.ALUMINIUM_COGWHEEL, Cogwheels.LARGE_ALUMINIUM_COGWHEEL,
                    Cogwheels.STAINLESS_STEEL_COGWHEEL, Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Cogwheels.TITANIUM_COGWHEEL, Cogwheels.LARGE_TITANIUM_COGWHEEL,
                    Cogwheels.TUNGSTENSTEEL_COGWHEEL, Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Cogwheels.PALLADIUM_COGWHEEL, Cogwheels.LARGE_PALLADIUM_COGWHEEL,
                    Cogwheels.NAQUADAH_COGWHEEL, Cogwheels.LARGE_NAQUADAH_COGWHEEL, Cogwheels.DARMSTADTIUM_COGWHEEL, Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL,
                    Cogwheels.NEUTRONIUM_COGWHEEL, Cogwheels.LARGE_NEUTRONIUM_COGWHEEL)
            .renderer(() -> TieredBracketedKineticBlockEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<TieredKineticBlockEntity> TIERED_ENCASED_SHAFT = REGISTRATE
            .blockEntity("tiered_encased_shaft", TieredKineticBlockEntity::new)
            .instance(() -> TieredShaftInstance::new, false)
            .validBlocks(Shafts.ANDESITE_ENCASED_ANDESITE_SHAFT, Shafts.BRASS_ENCASED_ANDESITE_SHAFT, Shafts.ANDESITE_ENCASED_STEEL_SHAFT, Shafts.BRASS_ENCASED_STEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_ALUMINIUM_SHAFT, Shafts.BRASS_ENCASED_ALUMINIUM_SHAFT, Shafts.ANDESITE_ENCASED_STAINLESS_STEEL_SHAFT, Shafts.BRASS_ENCASED_STAINLESS_STEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_TITANIUM_SHAFT, Shafts.BRASS_ENCASED_TITANIUM_SHAFT, Shafts.ANDESITE_ENCASED_TUNGSTENSTEEL_SHAFT, Shafts.BRASS_ENCASED_TUNGSTENSTEEL_SHAFT,
                    Shafts.ANDESITE_ENCASED_PALLADIUM_SHAFT, Shafts.BRASS_ENCASED_PALLADIUM_SHAFT, Shafts.ANDESITE_ENCASED_NAQUADAH_SHAFT, Shafts.BRASS_ENCASED_NAQUADAH_SHAFT,
                    Shafts.ANDESITE_ENCASED_DARMSTADTIUM_SHAFT, Shafts.BRASS_ENCASED_DARMSTADTIUM_SHAFT, Shafts.ANDESITE_ENCASED_NEUTRONIUM_SHAFT, Shafts.BRASS_ENCASED_NEUTRONIUM_SHAFT,
                    Girders.METAL_GIRDER_ENCASED_ANDESITE_SHAFT, Girders.METAL_GIRDER_ENCASED_STEEL_SHAFT, Girders.METAL_GIRDER_ENCASED_ALUMINIUM_SHAFT, Girders.METAL_GIRDER_ENCASED_STAINLESS_STEEL_SHAFT,
                    Girders.METAL_GIRDER_ENCASED_TITANIUM_SHAFT, Girders.METAL_GIRDER_ENCASED_TUNGSTENSTEEL_SHAFT, Girders.METAL_GIRDER_ENCASED_PALLADIUM_SHAFT, Girders.METAL_GIRDER_ENCASED_NAQUADAH_SHAFT,
                    Girders.METAL_GIRDER_ENCASED_DARMSTADTIUM_SHAFT, Girders.METAL_GIRDER_ENCASED_NEUTRONIUM_SHAFT)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_cogwheel", TieredSimpleKineticBlockEntity::new)
            .instance(() -> TieredEncasedCogInstance::small, false)
            .validBlocks(Cogwheels.ANDESITE_ENCASED_ANDESITE_COGWHEEL, Cogwheels.BRASS_ENCASED_ANDESITE_COGWHEEL, Cogwheels.ANDESITE_ENCASED_STEEL_COGWHEEL, Cogwheels.BRASS_ENCASED_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_ALUMINIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_ALUMINIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL, Cogwheels.BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_TITANIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_TITANIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL, Cogwheels.BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_PALLADIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_PALLADIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_NAQUADAH_COGWHEEL, Cogwheels.BRASS_ENCASED_NAQUADAH_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_DARMSTADTIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_NEUTRONIUM_COGWHEEL)
            .renderer(() -> TieredEncasedCogRenderer::small)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_LARGE_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_large_cogwheel", TieredSimpleKineticBlockEntity::new)
            .instance(() -> TieredEncasedCogInstance::large, false)
            .validBlocks(Cogwheels.ANDESITE_ENCASED_LARGE_ANDESITE_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_ANDESITE_COGWHEEL, Cogwheels.ANDESITE_ENCASED_LARGE_STEEL_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_ALUMINIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_ALUMINIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_TITANIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_TITANIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_PALLADIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_PALLADIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_LARGE_NAQUADAH_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_NAQUADAH_COGWHEEL,
                    Cogwheels.ANDESITE_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_DARMSTADTIUM_COGWHEEL, Cogwheels.ANDESITE_ENCASED_LARGE_NEUTRONIUM_COGWHEEL, Cogwheels.BRASS_ENCASED_LARGE_NEUTRONIUM_COGWHEEL)
            .renderer(() -> TieredEncasedCogRenderer::large)
            .register();

    public static final BlockEntityEntry<TieredGearboxBlockEntity> TIERED_GEARBOX = REGISTRATE
            .blockEntity("tiered_gearbox", TieredGearboxBlockEntity::new)
            .instance(() -> TieredGearboxInstance::new, false)
            .validBlocks(Gearboxes.ANDESITE_GEARBOX, Gearboxes.STEEL_GEARBOX, Gearboxes.ALUMINIUM_GEARBOX, Gearboxes.STAINLESS_STEEL_GEARBOX,
                    Gearboxes.TITANIUM_GEARBOX, Gearboxes.TUNGSTENSTEEL_GEARBOX, Gearboxes.PALLADIUM_GEARBOX, Gearboxes.NAQUADAH_GEARBOX,
                    Gearboxes.DARMSTADTIUM_GEARBOX, Gearboxes.NEUTRONIUM_GEARBOX)
            .renderer(() -> TieredGearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredPoweredShaftBlockEntity> TIERED_POWERED_SHAFT = REGISTRATE
            .blockEntity("tiered_powered_shaft", TieredPoweredShaftBlockEntity::new)
            .instance(() -> TieredSingleRotatingInstance::new)
            .validBlocks(Shafts.POWERED_ANDESITE_SHAFT, Shafts.POWERED_STEEL_SHAFT, Shafts.POWERED_ALUMINIUM_SHAFT, Shafts.POWERED_STAINLESS_STEEL_SHAFT,
                    Shafts.POWERED_TITANIUM_SHAFT, Shafts.POWERED_TUNGSTENSTEEL_SHAFT, Shafts.POWERED_PALLADIUM_SHAFT, Shafts.POWERED_NAQUADAH_SHAFT,
                    Shafts.POWERED_DARMSTADTIUM_SHAFT, Shafts.POWERED_NEUTRONIUM_SHAFT)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredMillstoneBlockEntity> TIERED_MILLSTONE = REGISTRATE
            .blockEntity("tiered_millstone", TieredMillstoneBlockEntity::new)
            .instance(() -> TieredMillstoneCogInstance::new, false)
            .validBlocks(Millstones.ANDESITE_MILLSTONE, Millstones.STEEL_MILLSTONE, Millstones.ALUMINIUM_MILLSTONE, Millstones.STAINLESS_STEEL_MILLSTONE,
                    Millstones.TITANIUM_MILLSTONE, Millstones.TUNGSTENSTEEL_MILLSTONE, Millstones.PALLADIUM_MILLSTONE, Millstones.NAQUADAH_MILLSTONE,
                    Millstones.DARMSTADTIUM_MILLSTONE, Millstones.NEUTRONIUM_MILLSTONE)
            .renderer(() -> TieredMillstoneRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelBlockEntity> TIERED_CRUSHING_WHEEL = REGISTRATE
            .blockEntity("tiered_crushing_wheel", TieredCrushingWheelBlockEntity::new)
            .instance(() -> CutoutRotatingInstance::new, false)
            .validBlocks(CrushingWheels.ANDESITE_CRUSHING_WHEEL, CrushingWheels.STEEL_CRUSHING_WHEEL, CrushingWheels.ALUMINIUM_CRUSHING_WHEEL, CrushingWheels.STAINLESS_STEEL_CRUSHING_WHEEL,
                    CrushingWheels.TITANIUM_CRUSHING_WHEEL, CrushingWheels.TUNGSTENSTEEL_CRUSHING_WHEEL, CrushingWheels.PALLADIUM_CRUSHING_WHEEL, CrushingWheels.NAQUADAH_CRUSHING_WHEEL,
                    CrushingWheels.DARMSTADTIUM_CRUSHING_WHEEL, CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TieredCrushingWheelControllerBlockEntity> TIERED_CRUSHING_WHEEL_CONTROLLER = REGISTRATE
            .blockEntity("tiered_crushing_wheel_controller", TieredCrushingWheelControllerBlockEntity::new)
            .validBlocks(CrushingWheels.ANDESITE_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.STEEL_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.ALUMINIUM_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.STAINLESS_STEEL_CRUSHING_WHEEL_CONTROLLER,
                    CrushingWheels.TITANIUM_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.TUNGSTENSTEEL_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.PALLADIUM_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.NAQUADAH_CRUSHING_WHEEL_CONTROLLER,
                    CrushingWheels.DARMSTADTIUM_CRUSHING_WHEEL_CONTROLLER, CrushingWheels.NEUTRONIUM_CRUSHING_WHEEL_CONTROLLER)
            .register();

    public static final BlockEntityEntry<TieredBeltBlockEntity> TIERED_BELT = REGISTRATE
            .blockEntity("tiered_belt", TieredBeltBlockEntity::new)
            .instance(() -> TieredBeltInstance::new, TieredBeltBlockEntity::shouldRenderNormally)
            .validBlocks(Belts.RUBBER_BELT, Belts.SILICON_BELT, Belts.POLYETHYLENE_BELT,
                    Belts.POLYTETRAFLUOROETHYLENE_BELT, Belts.POLYBENZIMIDAZOLE_BELT)
            .renderer(() -> TieredBeltRenderer::new)
            .register();

    public static void register() {}
}
