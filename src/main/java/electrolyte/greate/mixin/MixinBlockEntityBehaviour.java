package electrolyte.greate.mixin;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockEntityBehaviour.class)
public class MixinBlockEntityBehaviour {
	@Shadow
	public SmartBlockEntity blockEntity;
}
