package electrolyte.greate.mixin.belt;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.schematics.cannon.LaunchedItem.ForBlockState;
import com.tterrag.registrate.util.entry.BlockEntry;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import electrolyte.greate.content.kinetics.belt.item.TieredBeltConnectorItem;
import electrolyte.greate.content.kinetics.simpleRelays.TieredShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "com/simibubi/create/content/schematics/cannon/LaunchedItem$ForBelt")
public abstract class MixinLaunchedItem$ForBelt extends ForBlockState {

    private MixinLaunchedItem$ForBelt(BlockPos start, BlockPos target, ItemStack stack, BlockState state, CompoundTag data) {
        super(start, target, stack, state, data);
    }

    @WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;getDefaultState()Lnet/minecraft/world/level/block/state/BlockState;"), remap = false)
    private BlockState greate_place(BlockEntry<?> instance, Operation<BlockState> original, @Local(argsOnly = true) Level level, @Local(name = "axis") Axis axis, @Local(name = "i") int i, @Local(name = "offset") BlockPos offset) {
        if(state.getBlock() instanceof TieredBeltBlock) {
            BlockPos end = target.offset(offset.getX() * i, offset.getY() * i, offset.getZ() * i);
            BlockState shaftState = level.getBlockState(target);
            if(!(shaftState.getBlock() instanceof TieredShaftBlock)) shaftState = level.getBlockState(end);
            return shaftState.getBlock().defaultBlockState().setValue(AbstractSimpleShaftBlock.AXIS, axis);
        }
        return original.call(instance);
    }

    @WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/belt/item/BeltConnectorItem;createBelts(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)V"), remap = false)
    private void greate_place(Level world, BlockPos start, BlockPos end, Operation<Void> original) {
        if(state.getBlock() instanceof TieredBeltBlock tbb) {
            TieredBeltConnectorItem.createBelts(world, target, end,
                    ((TieredShaftBlock) world.getBlockState(target).getBlock()).getMaterial(), tbb.getBeltMaterial());
        } else original.call(world, start, end);
    }
}
