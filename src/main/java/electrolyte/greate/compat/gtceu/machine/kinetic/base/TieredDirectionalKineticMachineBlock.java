package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class TieredDirectionalKineticMachineBlock extends TieredKineticMachineBlock {
    public TieredDirectionalKineticMachineBlock(Properties properties, MachineDefinition definition, KineticBlock kineticBlock) {
        super(properties, definition, kineticBlock);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return kineticBlock.rotate(pState, pRotation);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return kineticBlock.mirror(pState, pMirror);
    }
}
