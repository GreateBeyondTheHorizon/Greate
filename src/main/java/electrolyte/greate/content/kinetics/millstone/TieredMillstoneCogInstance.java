package electrolyte.greate.content.kinetics.millstone;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredPartialModel;

public class TieredMillstoneCogInstance extends TieredSingleRotatingInstance<TieredMillstoneBlockEntity> {

    public TieredMillstoneCogInstance(MaterialManager materialManager, TieredMillstoneBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(((ITieredPartialModel) blockState.getBlock()).getPartialModel(), blockEntity.getBlockState());
    }
}
