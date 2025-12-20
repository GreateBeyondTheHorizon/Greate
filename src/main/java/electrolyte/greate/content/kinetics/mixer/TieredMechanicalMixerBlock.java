package electrolyte.greate.content.kinetics.mixer;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlock;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static electrolyte.greate.GreateValues.TM;

public class TieredMechanicalMixerBlock extends MechanicalMixerBlock implements ITieredBlock {

    private int tier;

    public TieredMechanicalMixerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public BlockEntityType<? extends MechanicalMixerBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MECHANICAL_MIXER.get();
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public Material getMaterial() {
        return TM[tier];
    }
}
