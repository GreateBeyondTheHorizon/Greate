package electrolyte.greate.content.kinetics.crusher;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

public class TieredCrushingWheelControllerBlockEntity extends CrushingWheelControllerBlockEntity {

    private RecipeWrapper wrapper;

    public TieredCrushingWheelControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        wrapper = new RecipeWrapper(inventory);
    }

    @Override
    public Optional<ProcessingRecipe<RecipeWrapper>> findRecipe() {
        TIER machineTier = ((TieredCrushingWheelControllerBlock) getBlockState().getBlock()).getTier();
        Optional<ProcessingRecipe<RecipeWrapper>> crushingRecipe = AllRecipeTypes.CRUSHING.find(wrapper, level);
        if(crushingRecipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> modifiedRecipe = TieredCrushingRecipe.convertNormalCrushing(crushingRecipe.get(), machineTier);
            return Optional.of(modifiedRecipe);
        }
        crushingRecipe = AllRecipeTypes.MILLING.find(wrapper, level);
        if(crushingRecipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> modifiedRecipe = TieredCrushingRecipe.convertNormalCrushing(crushingRecipe.get(), machineTier);
            return Optional.of(modifiedRecipe);
        }
        crushingRecipe = ModRecipeTypes.CRUSHING.find(wrapper, level, ((TieredCrushingWheelControllerBlock) getBlockState().getBlock()).getTier());
        if(crushingRecipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> modifiedRecipe = TieredCrushingRecipe.convertTieredCrushing(crushingRecipe.get(), machineTier);
            return Optional.of(modifiedRecipe);
        }
        Optional<GTRecipe> recipe = level.getRecipeManager().getAllRecipesFor(GTRecipeTypes.MACERATOR_RECIPES).stream().filter(r ->
                ((Ingredient) r.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).test(wrapper.getItem(0))).findFirst();
        if(recipe.isPresent()) {
            TieredProcessingRecipe<RecipeWrapper> convertedRecipe = TieredCrushingRecipe.convertGT(recipe.get(), machineTier);
            if(convertedRecipe.getRecipeTier().compareTo(((TieredCrushingWheelControllerBlock) getBlockState().getBlock()).getTier()) <= 0) {
                return Optional.of(convertedRecipe);
            }
        }
        return Optional.empty();
    }
}
