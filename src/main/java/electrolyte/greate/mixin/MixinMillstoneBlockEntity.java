package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import electrolyte.greate.content.kinetics.millstone.TieredMillstoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(MillstoneBlockEntity.class)
public abstract class MixinMillstoneBlockEntity extends KineticBlockEntity {

    @Shadow(remap = false) private MillingRecipe lastRecipe;

    @Shadow(remap = false) public ItemStackHandler inputInv;

    @Shadow(remap = false) private void process() {}

    public MixinMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/millstone/MillingRecipe;matches(Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Z"), remap = false)
    private boolean greate$tick(MillingRecipe instance, RecipeInput inv, Level worldIn) {
        if(((MillstoneBlockEntity) (Object) this) instanceof TieredMillstoneBlockEntity tmbe) {
            tmbe.setupRecipe();
            return false;
        }
        return !lastRecipe.matches(new RecipeWrapper(inputInv), level);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/millstone/MillstoneBlockEntity;process()V"), remap = false)
    private void greate$process(MillstoneBlockEntity millstone) {
        if(millstone instanceof TieredMillstoneBlockEntity tmbe) {
            tmbe.processRecipe();
        } else {
            process();
        }
    }

    @Redirect(method = "canProcess", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), remap = false)
    private boolean greate$canProcess(Optional<MillingRecipe> instance, ItemStack stack) {
        if(((MillstoneBlockEntity) (Object) this) instanceof TieredMillstoneBlockEntity tmbe) {
            return tmbe.canProcess(stack);
        }
        return instance.isPresent();
    }
}
