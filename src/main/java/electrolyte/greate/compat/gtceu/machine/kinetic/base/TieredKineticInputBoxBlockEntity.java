package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TieredKineticInputBoxBlockEntity extends TieredKineticBlockEntity {

    @Getter
    @Setter
    private float recipeStress = 0;

    public TieredKineticInputBoxBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public float calculateStressApplied() {
        return recipeStress;
    }
}
