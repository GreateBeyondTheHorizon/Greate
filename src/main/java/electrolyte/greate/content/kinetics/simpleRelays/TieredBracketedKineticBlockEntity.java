package electrolyte.greate.content.kinetics.simpleRelays;

import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TieredBracketedKineticBlockEntity extends TieredSimpleKineticBlockEntity implements ITransformableBlockEntity {

    public TieredBracketedKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new BracketedBlockEntityBehaviour(this, state -> state.getBlock() instanceof AbstractSimpleShaftBlock));
        super.addBehaviours(behaviours);
    }

    @Override
    public void transform(StructureTransform transform) {
        BracketedBlockEntityBehaviour bracketBehavior = getBehaviour(BracketedBlockEntityBehaviour.TYPE);
        if(bracketBehavior != null) {
            bracketBehavior.transformBracket(transform);
        }
    }
}
