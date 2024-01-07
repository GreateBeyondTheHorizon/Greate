package electrolyte.greate.content.kinetics.saw;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredShaftInstance;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TieredSawInstance extends TieredShaftInstance<TieredSawBlockEntity> {

    private PartialModel halfShaftModel;

    public TieredSawInstance(MaterialManager materialManager, TieredSawBlockEntity blockEntity) {
        super(materialManager, blockEntity);
        halfShaftModel = ((ITieredPartialModel) blockState.getBlock()).getPartialModel();
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        if(blockState.getValue(BlockStateProperties.FACING).getAxis().isHorizontal()) {
            BlockState refState = blockState.rotate(blockEntity.getLevel(), blockEntity.getBlockPos(), Rotation.CLOCKWISE_180);
            Direction dir = refState.getValue(BlockStateProperties.FACING);
            return getRotatingMaterial().getModel(halfShaftModel, refState, dir);
        } else {
            return getRotatingMaterial().getModel(shaft());
        }
    }
}
