package electrolyte.greate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlockEntity;
import electrolyte.greate.content.kinetics.crafter.TieredMechanicalCrafterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MechanicalCrafterBlockEntity.class)
public abstract class MixinMechanicalCrafterBlockEntity extends KineticBlockEntity {

    @Unique private int greate_lowestCrafterTier = 0;

    public MixinMechanicalCrafterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "begin", at = @At("TAIL"), remap = false, cancellable = true)
    private void greate_begin(CallbackInfo ci) {
        if(this.getBlockState().getBlock() instanceof TieredMechanicalCrafterBlock tmcb) {
            if(greate_lowestCrafterTier > tmcb.getTier()) {
                greate_lowestCrafterTier = tmcb.getTier();
            }
        }
    }

    @Unique
    public int greate_getLowestCrafterTier() {
        return greate_lowestCrafterTier;
    }
}
