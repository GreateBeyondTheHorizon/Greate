package electrolyte.greate.mixin;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Optional;

import static electrolyte.greate.registry.Shafts.POWERED_SHAFTS;

@Mixin(SteamEngineBlock.class)
public abstract class MixinSteamEngineBlock extends FaceAttachedHorizontalDirectionalBlock
        implements SimpleWaterloggedBlock, IWrenchable, IBE<SteamEngineBlockEntity>  {

    @Shadow
    public static BlockPos getShaftPos(BlockState sideState, BlockPos pos) {
        throw new IllegalStateException("Mixin did not apply!");
    }

    @Shadow
    public static boolean isShaftValid(BlockState state, BlockState shaft) {
        throw new IllegalStateException("Mixin did not apply!");
    }

    @Shadow
    public static Direction getFacing(BlockState sideState) {
        throw new IllegalStateException("Mixin did not apply!");
    }

    public MixinSteamEngineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "isShaftValid", at = @At("HEAD"), remap = false, cancellable = true)
    private static void greate_isShaftValid(BlockState state, BlockState shaft, CallbackInfoReturnable<Boolean> cir) {
        if((shaft.getBlock() instanceof ShaftBlock || shaft.getBlock() instanceof PoweredShaftBlock) &&
                shaft.getValue(ShaftBlock.AXIS) != getConnectedDirection(state).getAxis()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onPlace", at = @At("HEAD"), cancellable = true)
    private void greate_onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving, CallbackInfo ci) {
        FluidTankBlock.updateBoilerState(pState, pLevel, pPos.relative(getFacing(pState).getOpposite()));
        BlockPos shaftPos = getShaftPos(pState, pPos);
        BlockState shaftState = pLevel.getBlockState(shaftPos);
        if(shaftState.getBlock() instanceof TieredShaftBlock tsb) {
            if(isShaftValid(pState, shaftState)) {
                Optional<BlockEntry<TieredPoweredShaftBlock>> poweredShaftBlock = Arrays.stream(POWERED_SHAFTS).filter(p -> p.get().getShaft().equals(tsb)).findFirst();
                if(poweredShaftBlock.isPresent()) {
                    pLevel.setBlock(shaftPos, TieredPoweredShaftBlock.getEquivalent(poweredShaftBlock.get(), shaftState), 3);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "onRemove", at = @At("TAIL"))
    private void greate_onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving, CallbackInfo ci) {
        BlockPos shaftPos = getShaftPos(pState, pPos);
        BlockState shaftState = pLevel.getBlockState(shaftPos);
        if(!(shaftState.getBlock() instanceof TieredPoweredShaftBlock tpsb)) return;
        pLevel.scheduleTick(shaftPos, tpsb, 1);
    }
}
