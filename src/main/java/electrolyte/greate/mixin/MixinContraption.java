package electrolyte.greate.mixin;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlock;
import electrolyte.greate.content.kinetics.steamEngine.TieredPoweredShaftBlockEntity;
import electrolyte.greate.registry.GreateTagPrefixes;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Contraption.class)
public class MixinContraption {

    @WrapOperation(method = "addBlocksToWorld", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private boolean greate_addBlocksToWorld(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof ShaftBlock;
    }

    @WrapOperation(method = "addBlocksToWorld", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/simpleRelays/ShaftBlock;pickCorrectShaftType(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), remap = false)
    private BlockState greate_addBlocksToWorld(BlockState stateForPlacement, Level level, BlockPos pos, Operation<BlockState> original) {
        if(stateForPlacement.getBlock() instanceof TieredShaftBlock tsb) {
            return TieredShaftBlock.pickShaftType(stateForPlacement, level, pos, tsb.getMaterial());
        }
        return original.call(stateForPlacement, level, pos);
    }

    @WrapOperation(method = "removeBlocksFromWorld", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;is(Ljava/lang/Object;)Z"), remap = false)
    private boolean greate_removeBlocksFromWorld(BlockEntry<?> instance, Object o, Operation<Boolean> original, @Local(name = "blockIn") Block blockIn) {
        return blockIn instanceof PoweredShaftBlock;
    }

    @WrapOperation(method = "removeBlocksFromWorld", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private boolean greate_removeBlocksFromWorld(BlockEntry<?> instance, BlockState state, Operation<Boolean> original, @Local(name = "block") StructureBlockInfo block) {
        return block.state().getBlock() instanceof ShaftBlock;
    }

    @WrapOperation(method = "capture", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1), remap = false)
    private boolean greate_capture(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof PoweredShaftBlock;
    }

    @WrapOperation(method = "capture", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BlockHelper;copyProperties(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"), remap = false)
    private BlockState greate_capture(BlockState fromState, BlockState toState, Operation<BlockState> original) {
        if(fromState.getBlock() instanceof TieredPoweredShaftBlock tpsb) {
            return BlockHelper.copyProperties(fromState, ChemicalHelper.getBlock(GreateTagPrefixes.shaft, tpsb.getMaterial()).defaultBlockState());
        }
        return original.call(fromState, toState);
    }

    @WrapOperation(method = "capture", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntityEntry;create(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), remap = false)
    private BlockEntity greate_capture(BlockEntityEntry<?> instance, BlockPos pos, BlockState state, Operation<BlockEntity> original, @Local(name = "blockEntity") BlockEntity blockEntity) {
        if(blockEntity instanceof TieredPoweredShaftBlockEntity) {
            return ModBlockEntityTypes.TIERED_BRACKETED_KINETIC.create(pos, state);
        }
        return original.call(instance, pos, state);
    }
}
