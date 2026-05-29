package electrolyte.greate.mixin.belt;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlockEntity;
import electrolyte.greate.registry.GreateTagPrefixes;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SchematicannonBlockEntity.class)
public class MixinSchematicannonBlockEntity {

    @WrapOperation(method = "shouldIgnoreBlockState", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private boolean greate_shouldIgnoreBlockState(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "launchBlockOrBelt", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false)
    private boolean greate_launchBlockOrBelt(BlockEntry<?> instance, BlockState state, Operation<Boolean> original) {
        return state.getBlock() instanceof BeltBlock;
    }

    @WrapOperation(method = "launchBlockOrBelt", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/cannon/SchematicannonBlockEntity;stripBeltIfNotLast(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"), remap = false)
    private BlockState greate_launchBlockOrBelt(BlockState blockState, Operation<BlockState> original, @Local(argsOnly = true) BlockEntity blockEntity) {
        if(blockState.getBlock() instanceof TieredBeltBlock) {
            return greate_stripBeltIfNotLast(blockState, blockEntity);
        }
        return original.call(blockState);
    }

    @Unique
    private static BlockState greate_stripBeltIfNotLast(BlockState blockState, BlockEntity blockEntity) {
        BeltPart part = blockState.getValue(BeltBlock.PART);
        if (part == BeltPart.MIDDLE)
            return Blocks.AIR.defaultBlockState();

        boolean isLastSegment = false;
        Direction facing = blockState.getValue(BeltBlock.HORIZONTAL_FACING);
        BeltSlope slope = blockState.getValue(BeltBlock.SLOPE);
        boolean positive = facing.getAxisDirection() == AxisDirection.POSITIVE;
        boolean start = part == BeltPart.START;
        boolean end = part == BeltPart.END;

        isLastSegment = switch(slope) {
            case DOWNWARD ->
                    start;
            case UPWARD ->
                    end;
            default ->
                    positive && end || !positive && start;
        };
        if (isLastSegment) return blockState;
        Material shaftMaterial = ((TieredBeltBlockEntity) blockEntity).getShaftMaterial();
        return ChemicalHelper.getBlock(GreateTagPrefixes.shaft, shaftMaterial).defaultBlockState()
                .setValue(AbstractSimpleShaftBlock.AXIS, slope == BeltSlope.SIDEWAYS ? Axis.Y :
                        facing.getClockWise().getAxis());
    }

    @WrapOperation(method = "launchBelt", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;asStack()Lnet/minecraft/world/item/ItemStack;"), remap = false)
    private ItemStack greate_launchBelt(ItemEntry<?> instance, Operation<ItemStack> original, @Local(argsOnly = true) BlockState state) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            return ChemicalHelper.get(GreateTagPrefixes.beltConnector, tbb.getBeltMaterial());
        }
        return original.call(instance);
    }
}
