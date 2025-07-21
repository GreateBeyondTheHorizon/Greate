package electrolyte.greate.foundation.data.recipe.machine;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.Create;
import electrolyte.greate.Greate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.data.material.GTMaterials.CHEMICAL_DYES;
import static com.gregtechceu.gtceu.data.material.GTMaterials.Chlorine;
import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.CHEMICAL_BATH_RECIPES;

public class GreateChemicalBathRecipes {

    public static void register(RecipeOutput provider) {
        for(DyeColor color : DyeColor.values()) {
            String dyeName = color.getName();
            CHEMICAL_BATH_RECIPES
                    .recipeBuilder(Greate.id(dyeName + "_seat"))
                    .inputItems(AllItemTags.SEATS.tag)
                    .inputFluids(CHEMICAL_DYES[color.ordinal()].getFluid(L))
                    .outputItems(new ItemStack(BuiltInRegistries.ITEM.get(Create.asResource(dyeName + "_seat"))))
                    .duration(20)
                    .EUt(VA[LV])
                    .save(provider);

            CHEMICAL_BATH_RECIPES
                    .recipeBuilder(Greate.id(dyeName + "_valve_handle"))
                    .inputItems(AllBlocks.COPPER_VALVE_HANDLE.asStack())
                    .inputFluids(CHEMICAL_DYES[color.ordinal()].getFluid(L))
                    .outputItems(new ItemStack(BuiltInRegistries.ITEM.get(Create.asResource(dyeName + "_valve_handle"))))
                    .duration(20)
                    .EUt(VA[LV])
                    .save(provider);
        }

        CHEMICAL_BATH_RECIPES
                .recipeBuilder(Greate.id("decolor_seat"))
                .inputItems(AllItemTags.SEATS.tag)
                .inputFluids(Chlorine.getFluid(20))
                .outputItems(AllBlocks.SEATS.get(DyeColor.WHITE))
                .duration(400)
                .EUt(2)
                .save(provider);

        CHEMICAL_BATH_RECIPES
                .recipeBuilder(Greate.id("decolor_valve_handle"))
                .inputItems(AllItemTags.VALVE_HANDLES.tag)
                .inputFluids(Chlorine.getFluid(20))
                .outputItems(AllBlocks.COPPER_VALVE_HANDLE)
                .duration(400)
                .EUt(2)
                .save(provider);
    }
}
