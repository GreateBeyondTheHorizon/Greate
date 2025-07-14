package electrolyte.greate.foundation.data.recipe.machine;

import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import electrolyte.greate.Greate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Items;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static electrolyte.greate.registry.GreateMaterials.AndesiteAlloy;

public class GreateAssemblerRecipes {

    public static void register(Consumer<FinishedRecipe> provider) {
        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id("hopper_iron"))
                .inputItems(plate, Iron, 5)
                .inputItems(Items.CHESTS_WOODEN)
                .outputItems(Blocks.HOPPER)
                .circuitMeta(10)
                .duration(20 * 40)
                .EUt(2)
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id("hopper_wrought_iron"))
                .inputItems(plate, WroughtIron, 5)
                .inputItems(Items.CHESTS_WOODEN)
                .outputItems(Blocks.HOPPER)
                .circuitMeta(10)
                .duration(20 * 40)
                .EUt(2)
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.CHUTE.getId().getPath()).withSuffix("_iron"))
                .inputItems(plate, Iron, 5)
                .inputItems(Tags.Items.CHESTS_WOODEN)
                .outputItems(AllBlocks.CHUTE)
                .circuitMeta(11)
                .duration(20 * 40)
                .EUt(2)
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.CHUTE.getId().getPath()).withSuffix("_wrought_iron"))
                .inputItems(plate, WroughtIron, 5)
                .inputItems(Tags.Items.CHESTS_WOODEN)
                .outputItems(AllBlocks.CHUTE)
                .circuitMeta(11)
                .duration(20 * 40)
                .EUt(2)
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.BASIN.getId().getPath()))
                .inputItems(plate, AndesiteAlloy, 5)
                .outputItems(AllBlocks.BASIN)
                .circuitMeta(1)
                .duration(20 * 35)
                .EUt(4)
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllItems.BRASS_HAND.getId().getPath()))
                .inputItems(plate, AndesiteAlloy)
                .inputItems(plate, Brass, 4)
                .outputItems(AllItems.BRASS_HAND)
                .circuitMeta(2)
                .duration(20 * 15)
                .EUt(VA[ULV])
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.DEPOT.getId().getPath()))
                .inputItems(plate, AndesiteAlloy)
                .inputItems(AllBlocks.ANDESITE_CASING)
                .outputItems(AllBlocks.DEPOT)
                .circuitMeta(3)
                .duration(20 * 15)
                .EUt(VA[ULV])
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.FLUID_TANK.getId().getPath()).withSuffix("_copper"))
                .inputItems(screw, Copper, 2)
                .inputItems(GTMachines.WOODEN_DRUM)
                .inputFluids(Glass.getFluid(L * 2))
                .outputItems(AllBlocks.FLUID_TANK)
                .circuitMeta(17)
                .duration(20 * 20)
                .EUt(VA[ULV])
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.FLUID_TANK.getId().getPath()).withSuffix("_annealed_copper"))
                .inputItems(screw, AnnealedCopper, 2)
                .inputItems(GTMachines.WOODEN_DRUM)
                .inputFluids(Glass.getFluid(L * 2))
                .outputItems(AllBlocks.FLUID_TANK, 2)
                .circuitMeta(17)
                .duration(20 * 20)
                .EUt(VA[ULV])
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.ITEM_VAULT.getId().getPath()).withSuffix("_iron"))
                .inputItems(screw, Iron, 2)
                .inputItems(GTMachines.WOODEN_CRATE)
                .inputItems(plate, Iron, 2)
                .outputItems(AllBlocks.ITEM_VAULT, 2)
                .circuitMeta(1)
                .duration(20 * 20)
                .EUt(VA[ULV])
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllBlocks.ITEM_VAULT.getId().getPath()).withSuffix("_wrought_iron"))
                .inputItems(screw, WroughtIron, 2)
                .inputItems(GTMachines.WOODEN_CRATE)
                .inputItems(plate, WroughtIron, 2)
                .outputItems(AllBlocks.ITEM_VAULT, 2)
                .circuitMeta(1)
                .duration(20 * 20)
                .EUt(VA[ULV])
                .save(provider);

        ASSEMBLER_RECIPES
                .recipeBuilder(Greate.id(AllItems.ELECTRON_TUBE.getId().getPath()))
                .inputItems(wireGtSingle, Steel, 2)
                .inputItems(GTItems.GLASS_TUBE)
                .inputItems(AllItems.POLISHED_ROSE_QUARTZ)
                .outputItems(AllItems.ELECTRON_TUBE, 2)
                .circuitMeta(1)
                .duration(20 * 20)
                .EUt(VA[ULV])
                .save(provider);
    }
}
