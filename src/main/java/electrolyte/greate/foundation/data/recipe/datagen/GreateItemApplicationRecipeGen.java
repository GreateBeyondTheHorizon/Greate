package electrolyte.greate.foundation.data.recipe.datagen;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CompoundIngredient;

import java.util.function.Supplier;

public class GreateItemApplicationRecipeGen extends ItemApplicationRecipeGen {

    public GreateItemApplicationRecipeGen(PackOutput output, String defaultNamespace) {
        super(output, defaultNamespace);
    }

    GeneratedRecipe
        ANDESITE = woodCasingIngredient("andesite", () -> Ingredient.of(AllItems.ANDESITE_ALLOY.get()), AllBlocks.ANDESITE_CASING::get),
        BRASS = woodCasingIngredient("brass", () -> Ingredient.of(AllTags.forgeItemTag("ingots/brass")), AllBlocks.BRASS_CASING::get),
        COPPER = woodCasingIngredient("copper", () -> Ingredient.of(Items.COPPER_INGOT), AllBlocks.COPPER_CASING::get);

    @Override
    protected GeneratedRecipe woodCasingIngredient(String type, Supplier<Ingredient> ingredient, Supplier<ItemLike> output) {
        create(type + "_casing_from_log", b ->
                b.require(CompoundIngredient.of(Ingredient.of(AllItemTags.STRIPPED_LOGS.tag), Ingredient.of(GTBlocks.STRIPPED_RUBBER_LOG.asItem())))
			.require(ingredient.get())
			.output(output.get()));
		return create(type + "_casing_from_wood", b ->
                b.require(CompoundIngredient.of(Ingredient.of(AllItemTags.STRIPPED_WOOD.tag), Ingredient.of(GTBlocks.STRIPPED_RUBBER_WOOD.asItem())))
			.require(ingredient.get())
			.output(output.get()));
    }
}
