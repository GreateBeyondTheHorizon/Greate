package electrolyte.greate.mixin.belt;

import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.kinetics.belt.item.BeltConnectorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BeltConnectorItem.class)
public interface MixinBeltConnectorItemAccessor {

    @Invoker(value = "getFacingFromTo")
    static Direction getFacingFromTo(BlockPos start, BlockPos end) { throw new IllegalStateException("Mixin did not apply!"); }

    @Invoker(value = "getSlopeBetween") static BeltSlope getSlopeBetween(BlockPos start, BlockPos end) { throw new IllegalStateException("Mixin did not apply!"); }
    @Invoker(value = "getBeltChainBetween") static List<BlockPos> getBeltChainBetween(BlockPos start, BlockPos end, BeltSlope slope, Direction dir) { throw new IllegalStateException("Mixin did not apply!"); }
}
