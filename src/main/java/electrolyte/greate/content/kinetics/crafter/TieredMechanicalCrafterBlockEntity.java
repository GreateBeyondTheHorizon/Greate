package electrolyte.greate.content.kinetics.crafter;

import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TieredMechanicalCrafterBlockEntity extends MechanicalCrafterBlockEntity {

    public TieredMechanicalCrafterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
