package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BasinBlockEntity.class)
public abstract class MixinBasinBlockEntity extends SmartBlockEntity {

    @Shadow(remap = false) public SmartFluidTankBehaviour inputTank;
    @Shadow(remap = false) protected SmartFluidTankBehaviour outputTank;
    @Shadow(remap = false) private boolean contentsChanged;

    @Shadow(remap = false) public abstract boolean isEmpty();

    public MixinBasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "addBehaviours", at = @At("RETURN"), remap = false)
    private void greate_addBehaviors(List<BlockEntityBehaviour> behaviours, CallbackInfo ci) {
        behaviours.remove(inputTank);
        behaviours.remove(outputTank);
        inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, 16000, true).whenFluidUpdates(() -> contentsChanged = true);
        outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 2, 16000, true).whenFluidUpdates(() -> contentsChanged = true).forbidInsertion();
        behaviours.add(inputTank);
        behaviours.add(outputTank);
    }

    @WrapOperation(method = "addToGoggleTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDescriptionId()Ljava/lang/String;"), remap = false)
    private String greate_addToGoggleTooltip(ItemStack instance, Operation<String> original) {
        return instance.getHoverName().getString();
    }
}
