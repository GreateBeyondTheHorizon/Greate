package electrolyte.greate.content.kinetics.gearbox;

import electrolyte.greate.content.kinetics.base.TieredDirectionalShaftHalvesBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TieredGearboxBlockEntity extends TieredDirectionalShaftHalvesBlockEntity {
    public TieredGearboxBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected boolean isNoisy() {
        return false;
    }
}
