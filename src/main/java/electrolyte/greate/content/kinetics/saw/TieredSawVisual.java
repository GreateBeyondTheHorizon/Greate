package electrolyte.greate.content.kinetics.saw;

import com.simibubi.create.foundation.render.VirtualRenderHelper;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import electrolyte.greate.content.kinetics.base.TieredShaftVisual;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class TieredSawVisual extends TieredShaftVisual<TieredSawBlockEntity> {

    public TieredSawVisual(VisualizationContext context, TieredSawBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
    }

    @Override
    protected Model getModel() {
        if(blockState.getValue(BlockStateProperties.FACING).getAxis().isHorizontal()) {
            BlockState refState = blockState.rotate(blockEntity.getLevel(), blockEntity.getBlockPos(), Rotation.CLOCKWISE_180);
            Direction dir = refState.getValue(BlockStateProperties.FACING);
            int tier = ((TieredSawBlock) blockState.getBlock()).getTier();
            return Models.partial(SHAFT_HALF_MODELS[tier], dir);
        } else {
            return VirtualRenderHelper.blockModel(shaft());
        }
    }
}
