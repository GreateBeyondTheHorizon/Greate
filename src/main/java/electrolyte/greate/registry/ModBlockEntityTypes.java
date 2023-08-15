package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.content.kinetics.base.TieredShaftInstance;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxBlockEntity;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxInstance;
import electrolyte.greate.content.kinetics.gearbox.TieredGearboxRenderer;
import electrolyte.greate.content.kinetics.simpleRelays.*;
import electrolyte.greate.content.kinetics.simpleRelays.encased.TieredEncasedCogInstance;

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
                    Girders.ANDESITE_METAL_GIRDER_ENCASED_SHAFT, Girders.STEEL_METAL_GIRDER_ENCASED_SHAFT, Girders.ALUMINIUM_METAL_GIRDER_ENCASED_SHAFT, Girders.STAINLESS_STEEL_METAL_GIRDER_ENCASED_SHAFT,
                    Girders.TITANIUM_METAL_GIRDER_ENCASED_SHAFT, Girders.TUNGSTENSTEEL_METAL_GIRDER_ENCASED_SHAFT, Girders.PALLADIUM_METAL_GIRDER_ENCASED_SHAFT, Girders.NAQUADAH_METAL_GIRDER_ENCASED_SHAFT,
                    Girders.DARMSTADTIUM_METAL_GIRDER_ENCASED_SHAFT, Girders.NEUTRONIUM_METAL_GIRDER_ENCASED_SHAFT)
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
            .renderer(() -> EncasedCogRenderer::small)
            .register();

    public static final BlockEntityEntry<TieredSimpleKineticBlockEntity> TIERED_ENCASED_LARGE_COGWHEEL = REGISTRATE
            .blockEntity("tiered_encased_large_cogwheel", TieredSimpleKineticBlockEntity::new)
            .instance(() -> TieredEncasedCogInstance::large, false)
            .validBlocks(Cogwheels.LARGE_ANDESITE_ENCASED_ANDESITE_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_ANDESITE_COGWHEEL, Cogwheels.LARGE_ANDESITE_ENCASED_STEEL_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_STEEL_COGWHEEL,
                    Cogwheels.LARGE_ANDESITE_ENCASED_ALUMINIUM_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_ALUMINIUM_COGWHEEL, Cogwheels.LARGE_ANDESITE_ENCASED_STAINLESS_STEEL_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_STAINLESS_STEEL_COGWHEEL,
                    Cogwheels.LARGE_ANDESITE_ENCASED_TITANIUM_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_TITANIUM_COGWHEEL, Cogwheels.LARGE_ANDESITE_ENCASED_TUNGSTENSTEEL_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_TUNGSTENSTEEL_COGWHEEL,
                    Cogwheels.LARGE_ANDESITE_ENCASED_PALLADIUM_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_PALLADIUM_COGWHEEL, Cogwheels.LARGE_ANDESITE_ENCASED_NAQUADAH_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_NAQUADAH_COGWHEEL,
                    Cogwheels.LARGE_ANDESITE_ENCASED_DARMSTADTIUM_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_DARMSTADTIUM_COGWHEEL, Cogwheels.LARGE_ANDESITE_ENCASED_NEUTRONIUM_COGWHEEL, Cogwheels.LARGE_BRASS_ENCASED_NEUTRONIUM_COGWHEEL)
            .renderer(() -> EncasedCogRenderer::large)
            .register();

    public static final BlockEntityEntry<TieredGearboxBlockEntity> TIERED_GEARBOX = REGISTRATE
            .blockEntity("tiered_gearbox", TieredGearboxBlockEntity::new)
            .instance(() -> TieredGearboxInstance::new, false)
            .validBlocks(Gearboxes.ANDESITE_GEARBOX, Gearboxes.STEEL_GEARBOX, Gearboxes.ALUMINIUM_GEARBOX, Gearboxes.STAINLESS_STEEL_GEARBOX,
                    Gearboxes.TITANIUM_GEARBOX, Gearboxes.TUNGSTENSTEEL_GEARBOX, Gearboxes.PALLADIUM_GEARBOX, Gearboxes.NAQUADAH_GEARBOX,
                    Gearboxes.DARMSTADTIUM_GEARBOX, Gearboxes.NEUTRONIUM_GEARBOX)
            .renderer(() -> TieredGearboxRenderer::new)
            .register();

    /*public static final BlockEntityEntry<TieredMillstoneBlockEntity> TIERED_MILLSTONE = REGISTRATE
            .blockEntity("tiered_millstone", TieredMillstoneBlockEntity::new)
            .instance(() -> TieredMillstoneCogInstance::new, false)
            .validBlocks(Millstones.TEST_MILLSTONE)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();*/

    public static void register() {}
}
