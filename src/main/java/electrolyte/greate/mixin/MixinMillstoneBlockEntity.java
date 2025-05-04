package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(MillstoneBlockEntity.class)
public abstract class MixinMillstoneBlockEntity extends KineticBlockEntity {

    @Shadow(remap = false) private MillingRecipe lastRecipe;

    @Shadow(remap = false) public ItemStackHandler inputInv;

    public MixinMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/millstone/MillingRecipe;matches(Lnet/minecraftforge/items/wrapper/RecipeWrapper;Lnet/minecraft/world/level/Level;)Z"), remap = false)
    private boolean greate_tick(MillingRecipe instance, RecipeWrapper inv, Level worldIn) {
        if(((MillstoneBlockEntity) (Object) this) instanceof TieredMillstoneBlockEntity tmbe) {
            tmbe.setupRecipe();
        }
        return !lastRecipe.matches(new RecipeWrapper(inputInv), level);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/millstone/MillstoneBlockEntity;process()V"), remap = false)
    private void greate_process(MillstoneBlockEntity millstone) {
        if(millstone instanceof TieredMillstoneBlockEntity tmbe) {
            tmbe.processRecipe();
        }
    }

    @Redirect(method = "canProcess", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), remap = false)
    private boolean greate_canProcess(Optional<MillingRecipe> instance, ItemStack stack) {
        if(((MillstoneBlockEntity) (Object) this) instanceof TieredMillstoneBlockEntity tmbe) {
            tmbe.canProcess(stack);
        }
        return instance.isPresent();
    }
}
