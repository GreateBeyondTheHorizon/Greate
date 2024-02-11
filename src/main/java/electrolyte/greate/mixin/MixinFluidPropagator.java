package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.registry.Pumps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(FluidPropagator.class)
public class MixinFluidPropagator {
	@WrapOperation(method = "propagateChangedPipe", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
	private static boolean greate_propagateChangedPipe(BlockEntry<PumpBlock> instance, BlockState targetState, Operation<Boolean> original) {
		boolean isMechanicalPump = original.call(instance, targetState);
		boolean isTieredPump = Arrays.stream(Pumps.MECHANICAL_PUMPS)
				.map(o -> (Block) o.get())
				.anyMatch(o -> o.equals(targetState.getBlock()));
		return isMechanicalPump || isTieredPump;
	}
}
