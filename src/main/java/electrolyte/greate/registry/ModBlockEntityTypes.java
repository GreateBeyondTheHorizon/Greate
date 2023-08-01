package electrolyte.greate.registry;

import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import electrolyte.greate.be.AndesiteBracketedKineticBlockEntity;
import electrolyte.greate.client.AndesiteBracketedKineticBlockEntityRenderer;

import static electrolyte.greate.Greate.REGISTRATE;

public class ModBlockEntityTypes {

    public static final BlockEntityEntry<AndesiteBracketedKineticBlockEntity> ANDESITE_SHAFT = REGISTRATE
            .blockEntity("andesite_shaft", AndesiteBracketedKineticBlockEntity::new)
            .instance(() -> SingleRotatingInstance::new, false)
            .validBlocks(ModBlocks.ANDESITE_SHAFT)
            .renderer(() -> AndesiteBracketedKineticBlockEntityRenderer::new)
            .register();

    public static void register() {}
}
