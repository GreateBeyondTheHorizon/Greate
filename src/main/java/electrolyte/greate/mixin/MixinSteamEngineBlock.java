package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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

import static electrolyte.greate.registry.GreateTagPrefixes.poweredShaft;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

@Mixin(SteamEngineBlock.class)
public abstract class MixinSteamEngineBlock extends FaceAttachedHorizontalDirectionalBlock
        implements SimpleWaterloggedBlock, IWrenchable, IBE<SteamEngineBlockEntity>  {

    @Shadow(remap = false)
    public static BlockPos getShaftPos(BlockState sideState, BlockPos pos) {
        throw new IllegalStateException("Mixin did not apply!");
    }

    @Shadow(remap = false)
    public static boolean isShaftValid(BlockState state, BlockState shaft) {
        throw new IllegalStateException("Mixin did not apply!");
    }

    @Shadow(remap = false)
    public static Direction getFacing(BlockState sideState) {
        throw new IllegalStateException("Mixin did not apply!");
    }

    public MixinSteamEngineBlock(Properties pProperties) {
        super(pProperties);
    }

    @WrapOperation(method = "isShaftValid", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0), remap = false)
    private static boolean greate_isShaftValid(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof ShaftBlock;
    }

    @WrapOperation(method = "isShaftValid", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1), remap = false)
    private static boolean greate_isPoweredShaftValid(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof PoweredShaftBlock;
    }

    @Inject(method = "onPlace", at = @At("HEAD"), cancellable = true)
    private void greate_onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving, CallbackInfo ci) {
        FluidTankBlock.updateBoilerState(pState, pLevel, pPos.relative(getFacing(pState).getOpposite()));
        BlockPos shaftPos = getShaftPos(pState, pPos);
        BlockState shaftState = pLevel.getBlockState(shaftPos);
        if(shaftState.getBlock() instanceof TieredShaftBlock tsb) {
            if(isShaftValid(pState, shaftState)) {
                Material mat = tsb.getMaterial();
                pLevel.setBlock(shaftPos, ChemicalHelper.getBlock(poweredShaft, mat).defaultBlockState()
                        .setValue(PoweredShaftBlock.AXIS, shaftState.getValue(ShaftBlock.AXIS))
                        .setValue(WATERLOGGED, shaftState.getValue(WATERLOGGED)), 3);
                ci.cancel();
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
