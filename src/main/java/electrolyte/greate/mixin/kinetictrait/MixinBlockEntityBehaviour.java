package electrolyte.greate.mixin.kinetictrait;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import electrolyte.greate.compat.gtceu.machine.kinetic.base.ITieredKineticMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockEntityBehaviour.class)
public abstract class MixinBlockEntityBehaviour {
	@Shadow(remap = false) public SmartBlockEntity blockEntity;

	@WrapOperation(method = "get(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BlockGetter;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
	private static BlockEntity greate$get(BlockGetter reader, BlockPos pos, Operation<BlockEntity> original) {
		if(reader.getBlockEntity(pos) instanceof ITieredKineticMachine km) {
			return km.getKineticBlockEntity();
		}
		return original.call(reader, pos);
	}
}
