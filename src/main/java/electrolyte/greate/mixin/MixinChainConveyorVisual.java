package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlockEntity;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorVisual;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import electrolyte.greate.content.kinetics.chainConveyor.TieredChainConveyorBlockEntity;
import electrolyte.greate.foundation.client.models.GreateModelUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChainConveyorVisual.class)
public class MixinChainConveyorVisual {

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/engine_room/flywheel/lib/model/Models;partial(Ldev/engine_room/flywheel/lib/model/baked/PartialModel;)Ldev/engine_room/flywheel/api/model/Model;"), remap = false)
    private static Model greate_init(PartialModel partial, Operation<Model> original, @Local(argsOnly = true) ChainConveyorBlockEntity chainConveyorBlockEntity) {
        if(chainConveyorBlockEntity instanceof TieredChainConveyorBlockEntity tccbe) {
            return Models.partial(GreateModelUtils.getPartialModel(tccbe.getBlockState().getBlock(), "/chain_conveyor_shaft"));
        }
        return original.call(partial);
    }
}
