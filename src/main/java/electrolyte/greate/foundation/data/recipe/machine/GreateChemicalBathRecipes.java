package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.common.data.GTRecipeCategories;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.Create;
import electrolyte.greate.Greate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.CHEMICAL_DYES;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Chlorine;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.CHEMICAL_BATH_RECIPES;

public class GreateChemicalBathRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        for(DyeColor color : DyeColor.values()) {
            String dyeName = color.getName();

            if(color != DyeColor.WHITE) {
                CHEMICAL_BATH_RECIPES
                        .recipeBuilder(Greate.id(dyeName + "_seat"))
                        .category(GTRecipeCategories.CHEM_DYES)
                        .inputItems(AllItemTags.SEATS.tag)
                        .inputFluids(CHEMICAL_DYES[color.ordinal()].getFluid(L))
                        .outputItems(new ItemStack(BuiltInRegistries.ITEM.get(Create.asResource(dyeName + "_seat"))))
                        .duration(20)
                        .EUt(VA[LV])
                        .save(provider);

                CHEMICAL_BATH_RECIPES
                        .recipeBuilder(Greate.id(dyeName + "_postbox"))
                        .category(GTRecipeCategories.CHEM_DYES)
                        .inputItems(AllItemTags.POSTBOXES.tag)
                        .inputFluids(CHEMICAL_DYES[color.ordinal()].getFluid(L))
                        .outputItems(new ItemStack(BuiltInRegistries.ITEM.get(Create.asResource(dyeName + "_postbox"))))
                        .duration(20)
                        .EUt(VA[LV])
                        .save(provider);
            }

            CHEMICAL_BATH_RECIPES
                    .recipeBuilder(Greate.id(dyeName + "_valve_handle"))
                    .category(GTRecipeCategories.CHEM_DYES)
                    .inputItems(AllBlocks.COPPER_VALVE_HANDLE.asStack())
                    .inputFluids(CHEMICAL_DYES[color.ordinal()].getFluid(L))
                    .outputItems(new ItemStack(BuiltInRegistries.ITEM.get(Create.asResource(dyeName + "_valve_handle"))))
                    .duration(20)
                    .EUt(VA[LV])
                    .save(provider);
        }

        CHEMICAL_BATH_RECIPES
                .recipeBuilder(Greate.id("decolor_seat"))
                .category(GTRecipeCategories.CHEM_DYES)
                .inputItems(AllItemTags.SEATS.tag)
                .inputFluids(Chlorine.getFluid(20))
                .outputItems(AllBlocks.SEATS.get(DyeColor.WHITE))
                .duration(400)
                .EUt(2)
                .save(provider);

        CHEMICAL_BATH_RECIPES
                .recipeBuilder(Greate.id("decolor_postbox"))
                .category(GTRecipeCategories.CHEM_DYES)
                .inputItems(AllItemTags.POSTBOXES.tag)
                .inputFluids(Chlorine.getFluid(20))
                .outputItems(AllBlocks.PACKAGE_POSTBOXES.get(DyeColor.WHITE))
                .duration(400)
                .EUt(2)
                .save(provider);

        CHEMICAL_BATH_RECIPES
                .recipeBuilder(Greate.id("decolor_valve_handle"))
                .category(GTRecipeCategories.CHEM_DYES)
                .inputItems(AllItemTags.VALVE_HANDLES.tag)
                .inputFluids(Chlorine.getFluid(20))
                .outputItems(AllBlocks.COPPER_VALVE_HANDLE)
                .duration(400)
                .EUt(2)
                .save(provider);
    }
}
