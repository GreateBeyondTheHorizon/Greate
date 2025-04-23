package electrolyte.greate.content.kinetics.base;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.minecraft.core.Direction;

import static electrolyte.greate.registry.GreatePartialModels.*;

public class TieredSingleAxisRotatingVisual extends SingleAxisRotatingVisual<KineticBlockEntity> {

    public TieredSingleAxisRotatingVisual(VisualizationContext context, KineticBlockEntity blockEntity, float partialTick, Model model) {
        this(context, blockEntity, partialTick, Direction.UP, model);
    }

    public TieredSingleAxisRotatingVisual(VisualizationContext context, KineticBlockEntity blockEntity, float partialTick, Direction from, Model model) {
        super(context, blockEntity, partialTick, model);
    }

    public static <T extends KineticBlockEntity> SimpleBlockEntityVisualizer.Factory<T> of(PartialModel partial) {
        return (context, blockEntity, partialTick) -> new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, Models.partial(partial));
    }

    public static <T extends KineticBlockEntity> SingleAxisRotatingVisual<T> poweredShaft(VisualizationContext context, T blockEntity, float partialTick) {
        return new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, Models.partial(POWERED_SHAFT_MODELS[((ITieredBlock) blockEntity.getBlockState().getBlock()).getTier()]));
    }

    public static <T extends KineticBlockEntity> SingleAxisRotatingVisual<T> shaft(VisualizationContext context, T blockEntity, float partialTick) {
        return new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, Models.partial(SHAFT_MODELS[((ITieredBlock) blockEntity.getBlockState().getBlock()).getTier()]));
    }

    public static <T extends KineticBlockEntity> SingleAxisRotatingVisual<T> pumpCog(VisualizationContext context, T blockEntity, float partialTick) {
        return new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, Direction.SOUTH, Models.partial(MECHANICAL_PUMP_COG_MODELS[((ITieredBlock)blockEntity.getBlockState().getBlock()).getTier()]));
    }

    public static <T extends KineticBlockEntity> SingleAxisRotatingVisual<T> millstoneCog(VisualizationContext context, T blockEntity, float partialTick) {
        return new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, Models.partial(MILLSTONE_INNER_MODELS[((ITieredBlock)blockEntity.getBlockState().getBlock()).getTier()]));
    }

    public static <T extends KineticBlockEntity> SingleAxisRotatingVisual<T> crushingWheel(VisualizationContext context, T blockEntity, float partialTick) {
        return new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, Models.partial(CRUSHING_WHEEL_MODELS[((ITieredBlock)blockEntity.getBlockState().getBlock()).getTier()]));
    }
}
