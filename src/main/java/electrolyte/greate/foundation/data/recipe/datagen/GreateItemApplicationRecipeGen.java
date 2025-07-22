package electrolyte.greate.foundation.data.recipe.datagen;

import com.gregtechceu.gtceu.data.block.GTBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;
import electrolyte.greate.Greate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class GreateItemApplicationRecipeGen extends ItemApplicationRecipeGen {

    public GreateItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Greate.MOD_ID);
    }

    GeneratedRecipe
        ANDESITE = woodCasingIngredient("andesite", () -> Ingredient.of(AllItems.ANDESITE_ALLOY.get()), AllBlocks.ANDESITE_CASING::get),
        BRASS = woodCasingIngredient("brass", () -> Ingredient.of(AllTags.commonItemTag("ingots/brass")), AllBlocks.BRASS_CASING::get),
        COPPER = woodCasingIngredient("copper", () -> Ingredient.of(Items.COPPER_INGOT), AllBlocks.COPPER_CASING::get);

    @Override
    protected GeneratedRecipe woodCasingIngredient(String type, Supplier<Ingredient> ingredient, Supplier<ItemLike> output) {
        create(type + "_casing_from_log", b ->
                b.require(CompoundIngredient.of(Ingredient.of(Tags.Items.STRIPPED_LOGS), Ingredient.of(GTBlocks.STRIPPED_RUBBER_LOG.asItem())))
			.require(ingredient.get())
			.output(output.get()));
		return create(type + "_casing_from_wood", b ->
                b.require(CompoundIngredient.of(Ingredient.of(Tags.Items.STRIPPED_WOODS), Ingredient.of(GTBlocks.STRIPPED_RUBBER_WOOD.asItem())))
			.require(ingredient.get())
			.output(output.get()));
    }
}
