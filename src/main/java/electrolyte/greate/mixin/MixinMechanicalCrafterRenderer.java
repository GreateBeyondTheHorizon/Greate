package electrolyte.greate.mixin;

import com.jozufozu.flywheel.core.PartialModel;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import electrolyte.greate.content.kinetics.crafter.TieredMechanicalCrafterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static electrolyte.greate.registry.GreatePartialModels.COGWHEEL_SHAFTLESS_MODELS;

@Mixin(MechanicalCrafterRenderer.class)
public abstract class MixinMechanicalCrafterRenderer {

    @WrapOperation(method = "renderFast", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/render/CachedBufferer;partial(Lcom/jozufozu/flywheel/core/PartialModel;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/simibubi/create/foundation/render/SuperByteBuffer;"), remap = false)
    private SuperByteBuffer greate_renderFast(PartialModel partial, BlockState referenceState, Operation<SuperByteBuffer> original) {
        if(referenceState.getBlock() instanceof TieredMechanicalCrafterBlock tmcb) {
            int tier = tmcb.getTier();
            return CachedBufferer.partial(COGWHEEL_SHAFTLESS_MODELS[tier], referenceState);
        }
        return CachedBufferer.partial(AllPartialModels.SHAFTLESS_COGWHEEL, referenceState);
    }
}