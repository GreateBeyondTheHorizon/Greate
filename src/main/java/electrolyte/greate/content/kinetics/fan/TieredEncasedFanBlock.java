package electrolyte.greate.content.kinetics.fan;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static electrolyte.greate.GreateValues.TM;
import static electrolyte.greate.registry.GreatePartialModels.FAN_INNER_MODELS;

public class TieredEncasedFanBlock extends EncasedFanBlock implements IBE<EncasedFanBlockEntity>, ITieredBlock {

    private int tier;
    private PartialModel[] models;

    public TieredEncasedFanBlock(Properties properties) {
        super(properties);
        this.models = new PartialModel[]{
                FAN_INNER_MODELS[tier],
                GreateModelUtils.getPartialModel(this, "/shaft_half")};
    }

    @Override
    public BlockEntityType<? extends EncasedFanBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_FAN.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    public PartialModel[] getModels() {
        return models;
    }

    @Override
    public Material getMaterial() {
        return TM[tier];
    }
}
