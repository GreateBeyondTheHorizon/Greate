package electrolyte.greate.content.kinetics.simpleRelays;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TieredCogwheelBlock extends CogWheelBlock implements ITieredBlock {

    private int tier;
    private Material material;

    protected TieredCogwheelBlock(boolean large, Properties properties, Material material) {
        super(large, properties);
        this.material = material;
    }

    public static TieredCogwheelBlock small(Properties properties, Material material) {
        return new TieredCogwheelBlock(false, properties, material);
    }

    public static TieredCogwheelBlock large(Properties properties, Material material) {
        return new TieredCogwheelBlock(true, properties, material);
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
    public Material getMaterial() {
        return material;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_BRACKETED_KINETIC.get();
    }
}
