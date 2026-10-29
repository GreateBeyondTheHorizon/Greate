package electrolyte.greate.compat.gtceu.machine.kinetic.multiblock.part;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.simibubi.create.foundation.block.IBE;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.compat.gtceu.common.data.blocks.KineticBoxBlock;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachine;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.TieredKineticMachineBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TieredKineticBoxMachineBlock extends TieredKineticMachineBlock implements IBE<TieredKineticMachine> {
    public TieredKineticBoxMachineBlock(MachineDefinition definition, BlockEntry<KineticBoxBlock> kineticBlock) {
        super(Properties.copy(kineticBlock.get()), definition, kineticBlock.get());
    }

    @Override
    public Class<TieredKineticMachine> getBlockEntityClass() {
        return TieredKineticMachine.class;
    }

    @Override
    public BlockEntityType<? extends TieredKineticMachine> getBlockEntityType() {
        return (BlockEntityType<? extends TieredKineticMachine>) getDefinition().getBlockEntityType();
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return super.getTicker(level, state, blockEntityType);
    }
}
