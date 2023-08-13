package electrolyte.greate.content.kinetics.millstone;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;

public class TieredMillstoneCogInstance extends TieredSingleRotatingInstance<TieredMillstoneBlockEntity> {
    public TieredMillstoneCogInstance(MaterialManager materialManager, TieredMillstoneBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(AllPartialModels.MILLSTONE_COG, blockEntity.getBlockState());
    }
}
