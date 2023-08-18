package electrolyte.greate.mixin;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteamEngineBlock.class)
public abstract class MixinSteamEngineBlock extends FaceAttachedHorizontalDirectionalBlock
        implements SimpleWaterloggedBlock, IWrenchable, IBE<SteamEngineBlockEntity>  {

    public MixinSteamEngineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "isShaftValid", at = @At("HEAD"), cancellable = true)
    private static void greate_isShaftValid(BlockState state, BlockState shaft, CallbackInfoReturnable<Boolean> cir) {
        if((shaft.getBlock() instanceof TieredShaftBlock || shaft.getBlock() instanceof TieredPoweredShaftBlock) &&
                shaft.getValue(ShaftBlock.AXIS) != greate_getFacing(state).getAxis()) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private static Direction greate_getFacing(BlockState state) {
        return getConnectedDirection(state);
    }
}
