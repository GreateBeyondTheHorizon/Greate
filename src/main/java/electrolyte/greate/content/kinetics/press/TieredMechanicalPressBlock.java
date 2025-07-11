package electrolyte.greate.content.kinetics.press;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import static electrolyte.greate.registry.GreateTagPrefixes.shaft;

public class TieredMechanicalPressBlock extends MechanicalPressBlock implements ITieredBlock, ITieredShaftBlock {

    private int tier;
    private Supplier<Block> shaftBlock;

    public TieredMechanicalPressBlock(Properties properties, Material mat) {
        super(properties);
        this.shaftBlock = () -> ChemicalHelper.getBlock(shaft, mat);
    }

    @Override
    public BlockEntityType<? extends MechanicalPressBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MECHANICAL_PRESS.get();
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
    public Block getShaft() {
        return shaftBlock.get();
    }
}
