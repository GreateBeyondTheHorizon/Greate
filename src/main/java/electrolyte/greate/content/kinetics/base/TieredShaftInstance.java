package electrolyte.greate.content.kinetics.base;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TieredShaftInstance<T extends KineticBlockEntity> extends TieredSingleRotatingInstance<T> {

    protected BlockState state;
    protected RotatingData rotatingModel;

    public TieredShaftInstance(MaterialManager materialManager, T blockEntity) {
        super(materialManager, blockEntity);
        state = blockEntity.getBlockState().getBlock() instanceof ITieredShaftBlock shaftBlock ? shaftBlock.getShaft().defaultBlockState() : null;
    }

    @Override
    public void init() {
        rotatingModel = setup(getModel().createInstance());
    }

    @Override
    public void update() {
        updateRotation(rotatingModel);
    }

    @Override
    public void updateLight() {
        relight(pos, rotatingModel);
    }

    @Override
    protected void remove() {
        rotatingModel.delete();
    }

    @Override
    protected BlockState shaft(BlockState state) {
        return super.shaft(state);
    }

    @Override
    protected BlockState shaft() {
        return shaft(state);
    }

    @Override
    protected BlockState getRenderedBlockState() {
        return shaft();
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(getRenderedBlockState());
    }
}
