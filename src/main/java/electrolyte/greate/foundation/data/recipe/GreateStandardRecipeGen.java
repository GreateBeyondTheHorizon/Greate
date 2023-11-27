package electrolyte.greate.foundation.data.recipe;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.Greate;
import electrolyte.greate.registry.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class GreateStandardRecipeGen extends GreateRecipeProvider {
    public GreateStandardRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Greate's Recipes";
    }

    //todo: remove once recipes have been properly updated
    private Marker CONVERSION_TEMP = enterFolder("conversion");

    GeneratedRecipe

            SHAFT_CYCLE = conversionCycle(ImmutableList.of(AllBlocks.SHAFT, Shafts.ANDESITE_SHAFT)),
            COGWHEEL_CYCLE = conversionCycle(ImmutableList.of(AllBlocks.COGWHEEL, Cogwheels.ANDESITE_COGWHEEL)),
            LARGE_COGWHEEL_CYCLE = conversionCycle(ImmutableList.of(AllBlocks.LARGE_COGWHEEL, Cogwheels.LARGE_ANDESITE_COGWHEEL)),
            CRUSHING_WHEEL_CYCLE = conversionCycle(ImmutableList.of(AllBlocks.CRUSHING_WHEEL, CrushingWheels.ANDESITE_CRUSHING_WHEEL)),
            BELT_CYCLE = conversionCycle(ImmutableList.of(AllItems.BELT_CONNECTOR, Belts.RUBBER_BELT_CONNECTOR)),
            MECHANICAL_PRESS_CYCLE = conversionCycle(ImmutableList.of(AllBlocks.MECHANICAL_PRESS, MechanicalPresses.ANDESITE_MECHANICAL_PRESS)),
            MECHANICAL_MIXER_CYCLE = conversionCycle(ImmutableList.of(AllBlocks.MECHANICAL_MIXER, MechanicalMixers.ANDESITE_MECHANICAL_MIXER));

    private Marker MATERIALS = enterFolder("materials");

    GeneratedRecipe
            ANDESITE_ALLOY = create(AllItems.ANDESITE_ALLOY).unlockedByTag(GreateTags.forgeItemTag("nuggets/iron"))
            .viaShaped(b -> b.define('N', GreateTags.forgeItemTag("nuggets/iron"))
                    .define('A', Blocks.ANDESITE)
                    .define('F', GreateTags.forgeItemTag("tools/files"))
                    .define('H', GreateTags.forgeItemTag("tools/hammers"))
                    .pattern("NA")
                    .pattern("AN")
                    .pattern("FH")),
            ANDESITE_ALLOY_FROM_ZINC = create(AllItems.ANDESITE_ALLOY).withSuffix("_from_zinc").unlockedByTag(GreateTags.forgeItemTag("nuggets/zinc"))
                    .viaShaped(b -> b.define('N', GreateTags.forgeItemTag("nuggets/zinc"))
                            .define('A', Blocks.ANDESITE)
                            .define('F', GreateTags.forgeItemTag("tools/files"))
                            .define('H', GreateTags.forgeItemTag("tools/hammers"))
                            .pattern("NA")
                            .pattern("AN")
                            .pattern("FH")),
            STEEL_ALLOY = createMaterialAlloyRecipe(ModItems.STEEL_ALLOY),
            ALUMINIUM_ALLOY = createMaterialAlloyRecipe(ModItems.ALUMINIUM_ALLOY),
            STAINLESS_STEEL_ALLOY = createMaterialAlloyRecipe(ModItems.STAINLESS_STEEL_ALLOY),
            TITANIUM_ALLOY = createMaterialAlloyRecipe(ModItems.TITANIUM_ALLOY),
            TUNGSTENSTEEL_ALLOY = createMaterialAlloyRecipe(ModItems.TUNGSTENSTEEL_ALLOY),
            PALLADIUM_ALLOY = createMaterialAlloyRecipe(ModItems.PALLADIUM_ALLOY),
            NAQUADAH_ALLOY = createMaterialAlloyRecipe(ModItems.NAQUADAH_ALLOY),
            DARMSTADTIUM_ALLOY = createMaterialAlloyRecipe(ModItems.DARMSTADTIUM_ALLOY),
            NEUTRONIUM_ALLOY = createMaterialAlloyRecipe(ModItems.NEUTRONIUM_ALLOY);

    private Marker KINETICS = enterFolder("kinetics");

    GeneratedRecipe
            ANDESITE_SHAFT = create(Shafts.ANDESITE_SHAFT).returns(4).unlockedBy(AllItems.ANDESITE_ALLOY::get)
            .viaShaped(b -> b.define('A', AllItems.ANDESITE_ALLOY.get())
                    .define('S', GreateTags.forgeItemTag("tools/saws"))
                    .pattern("S ")
                    .pattern(" A")),
            STEEL_SHAFT = createMaterialShaftRecipe(Shafts.STEEL_SHAFT),
            ALUMINIUM_SHAFT = createMaterialShaftRecipe(Shafts.ALUMINIUM_SHAFT),
            STAINLESS_STEEL_SHAFT = createMaterialShaftRecipe(Shafts.STAINLESS_STEEL_SHAFT),
            TITANIUM_SHAFT = createMaterialShaftRecipe(Shafts.TITANIUM_SHAFT),
            TUNGSTENSTEEL_SHAFT = createMaterialShaftRecipe(Shafts.TUNGSTENSTEEL_SHAFT),
            PALLADIUM_SHAFT = createMaterialShaftRecipe(Shafts.PALLADIUM_SHAFT),
            NAQUADAH_SHAFT = createMaterialShaftRecipe(Shafts.NAQUADAH_SHAFT),
            DARMSTADTIUM_SHAFT = createMaterialShaftRecipe(Shafts.DARMSTADTIUM_SHAFT),
            NEUTRONIUM_SHAFT = createMaterialShaftRecipe(Shafts.NEUTRONIUM_SHAFT),

            ANDESITE_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.ANDESITE_COGWHEEL, Shafts.ANDESITE_SHAFT, "wood"),
            STEEL_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.STEEL_COGWHEEL, Shafts.STEEL_SHAFT, "stone"),
            ALUMINIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.ALUMINIUM_COGWHEEL, Shafts.ALUMINIUM_SHAFT, "steel"),
            STAINLESS_STEEL_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.STAINLESS_STEEL_COGWHEEL, Shafts.STAINLESS_STEEL_SHAFT, "aluminium"),
            TITANIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.TITANIUM_COGWHEEL, Shafts.TITANIUM_SHAFT, "stainless_steel"),
            TUNGSTENSTEEL_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.TUNGSTENSTEEL_COGWHEEL, Shafts.TUNGSTENSTEEL_SHAFT, "titanium"),
            PALLADIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.PALLADIUM_COGWHEEL, Shafts.PALLADIUM_SHAFT, "tungstensteel"),
            NAQUADAH_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.NAQUADAH_COGWHEEL, Shafts.NAQUADAH_SHAFT, "palladium"),
            DARMSTADTIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.DARMSTADTIUM_COGWHEEL, Shafts.DARMSTADTIUM_SHAFT, "naquadah"),
            NEUTRONIUM_COGWHEEL = createMaterialCogwheelRecipe(Cogwheels.NEUTRONIUM_COGWHEEL, Shafts.NEUTRONIUM_SHAFT, "darmstadtium"),

            LARGE_ANDESITE_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_ANDESITE_COGWHEEL, Shafts.ANDESITE_SHAFT, "wood"),
            LARGE_STEEL_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_STEEL_COGWHEEL, Shafts.STEEL_SHAFT, "stone"),
            LARGE_ALUMINIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_ALUMINIUM_COGWHEEL, Shafts.ALUMINIUM_SHAFT, "steel"),
            LARGE_STAINLESS_STEEL_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Shafts.STAINLESS_STEEL_SHAFT, "aluminium"),
            LARGE_TITANIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_TITANIUM_COGWHEEL, Shafts.TITANIUM_SHAFT, "stainless_steel"),
            LARGE_TUNGSTENSTEEL_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Shafts.TUNGSTENSTEEL_SHAFT, "titanium"),
            LARGE_PALLADIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_PALLADIUM_COGWHEEL, Shafts.PALLADIUM_SHAFT, "tungstensteel"),
            LARGE_NAQUADAH_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_NAQUADAH_COGWHEEL, Shafts.NAQUADAH_SHAFT, "palladium"),
            LARGE_DARMSTADTIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL, Shafts.DARMSTADTIUM_SHAFT, "naquadah"),
            LARGE_NEUTRONIUM_COGWHEEL = createMaterialLargeCogwheelRecipe(Cogwheels.LARGE_NEUTRONIUM_COGWHEEL, Shafts.NEUTRONIUM_SHAFT, "darmstadtium"),

            LARGE_ANDESITE_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_ANDESITE_COGWHEEL, Cogwheels.ANDESITE_COGWHEEL, "wood"),
            LARGE_STEEL_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_STEEL_COGWHEEL, Cogwheels.STEEL_COGWHEEL, "stone"),
            LARGE_ALUMINIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_ALUMINIUM_COGWHEEL, Cogwheels.ALUMINIUM_COGWHEEL, "steel"),
            LARGE_STAINLESS_STEEL_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_STAINLESS_STEEL_COGWHEEL, Cogwheels.STAINLESS_STEEL_COGWHEEL, "aluminium"),
            LARGE_TITANIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_TITANIUM_COGWHEEL, Cogwheels.TITANIUM_COGWHEEL, "stainless_steel"),
            LARGE_TUNGSTENSTEEL_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_TUNGSTENSTEEL_COGWHEEL, Cogwheels.TUNGSTENSTEEL_COGWHEEL, "titanium"),
            LARGE_PALLADIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_PALLADIUM_COGWHEEL, Cogwheels.PALLADIUM_COGWHEEL, "tungstensteel"),
            LARGE_NAQUADAH_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_NAQUADAH_COGWHEEL, Cogwheels.NAQUADAH_COGWHEEL, "palladium"),
            LARGE_DARMSTADTIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_DARMSTADTIUM_COGWHEEL, Cogwheels.DARMSTADTIUM_COGWHEEL, "naquadah"),
            LARGE_NEUTRONIUM_COGWHEEL_FROM_LITTLE = createMaterialLargeCogwheelFromSmallRecipe(Cogwheels.LARGE_NEUTRONIUM_COGWHEEL, Cogwheels.NEUTRONIUM_COGWHEEL, "darmstadtium"),

            ANDESITE_GEARBOX = createMaterialGearboxRecipe(Gearboxes.ANDESITE_GEARBOX),
            STEEL_GEARBOX = createMaterialGearboxRecipe(Gearboxes.STEEL_GEARBOX),
            ALUMINIUM_GEARBOX = createMaterialGearboxRecipe(Gearboxes.ALUMINIUM_GEARBOX),
            STAINLESS_STEEL_GEARBOX = createMaterialGearboxRecipe(Gearboxes.STAINLESS_STEEL_GEARBOX),
            TITANIUM_GEARBOX = createMaterialGearboxRecipe(Gearboxes.TITANIUM_GEARBOX),
            TUNGSTENSTEEL_GEARBOX = createMaterialGearboxRecipe(Gearboxes.TUNGSTENSTEEL_GEARBOX),
            PALLADIUM_GEARBOX = createMaterialGearboxRecipe(Gearboxes.PALLADIUM_GEARBOX),
            NAQUADAH_GEARBOX = createMaterialGearboxRecipe(Gearboxes.NAQUADAH_GEARBOX),
            DARMSTADTIUM_GEARBOX = createMaterialGearboxRecipe(Gearboxes.DARMSTADTIUM_GEARBOX),
            NEUTRONIUM_GEARBOX = createMaterialGearboxRecipe(Gearboxes.NEUTRONIUM_GEARBOX),

            ANDESITE_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.ANDESITE_GEARBOX, Gearboxes.ANDESITE_VERTICAL_GEARBOX)),
            STEEL_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.STEEL_GEARBOX, Gearboxes.STEEL_VERTICAL_GEARBOX)),
            ALUMINIUM_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.ALUMINIUM_GEARBOX, Gearboxes.ALUMINIUM_VERTICAL_GEARBOX)),
            STAINLESS_STEEL_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.STAINLESS_STEEL_GEARBOX, Gearboxes.STAINLESS_STEEL_VERTICAL_GEARBOX)),
            TITANIUM_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.TITANIUM_GEARBOX, Gearboxes.TITANIUM_VERTICAL_GEARBOX)),
            TUNGSTENSTEEL_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.TUNGSTENSTEEL_GEARBOX, Gearboxes.TUNGSTENSTEEL_VERTICAL_GEARBOX)),
            PALLADIUM_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.PALLADIUM_GEARBOX, Gearboxes.PALLADIUM_VERTICAL_GEARBOX)),
            NAQUADAH_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.NAQUADAH_GEARBOX, Gearboxes.NAQUADAH_VERTICAL_GEARBOX)),
            DARMSTADTIUM_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.DARMSTADTIUM_GEARBOX, Gearboxes.DARMSTADTIUM_VERTICAL_GEARBOX)),
            NEUTRONIUM_GEARBOX_CONVERSION = conversionCycle(ImmutableList.of(Gearboxes.NEUTRONIUM_GEARBOX, Gearboxes.NEUTRONIUM_VERTICAL_GEARBOX)),

            ANDESITE_MILLSTONE = createMaterialMillstoneRecipe(Millstones.ANDESITE_MILLSTONE, "ulv"),
            STEEL_MILLSTONE = createMaterialMillstoneRecipe(Millstones.STEEL_MILLSTONE, "lv"),
            ALUMINIUM_MILLSTONE = createMaterialMillstoneRecipe(Millstones.ALUMINIUM_MILLSTONE, "mv"),
            STAINLESS_STEEL_MILLSTONE = createMaterialMillstoneRecipe(Millstones.STAINLESS_STEEL_MILLSTONE, "hv"),
            TITANIUM_MILLSTONE = createMaterialMillstoneRecipe(Millstones.TITANIUM_MILLSTONE, "ev"),
            TUNGSTENSTEEL_MILLSTONE = createMaterialMillstoneRecipe(Millstones.TUNGSTENSTEEL_MILLSTONE, "iv"),
            PALLADIUM_MILLSTONE = createMaterialMillstoneRecipe(Millstones.PALLADIUM_MILLSTONE, "luv"),
            NAQUADAH_MILLSTONE = createMaterialMillstoneRecipe(Millstones.NAQUADAH_MILLSTONE, "zpm"),
            DARMSTADTIUM_MILLSTONE = createMaterialMillstoneRecipe(Millstones.DARMSTADTIUM_MILLSTONE, "uv"),
            NEUTRONIUM_MILLSTONE = createMaterialMillstoneRecipe(Millstones.NEUTRONIUM_MILLSTONE, "uhv"),

            RUBBER_BELT_CONNECTOR = createBeltConnectorRecipe(Belts.RUBBER_BELT_CONNECTOR),
            SILICONE_RUBBER_BELT_CONNECTOR = createBeltConnectorRecipe(Belts.SILICONE_RUBBER_BELT_CONNECTOR),
            POLYETHYLENE_BELT_CONNECTOR = createBeltConnectorRecipe(Belts.POLYETHYLENE_BELT_CONNECTOR),
            POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR = createBeltConnectorRecipe(Belts.POLYTETRAFLUOROETHYLENE_BELT_CONNECTOR),
            POLYBENZIMIDAZOLE_BELT_CONNECTOR = createBeltConnectorRecipe(Belts.POLYBENZIMIDAZOLE_BELT_CONNECTOR),

            ANDESITE_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.ANDESITE_MECHANICAL_PRESS, "ulv"),
            STEEL_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.STEEL_MECHANICAL_PRESS, "lv"),
            ALUMINIUM_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.ALUMINIUM_MECHANICAL_PRESS, "mv"),
            STAINLESS_STEEL_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.STAINLESS_STEEL_MECHANICAL_PRESS, "hv"),
            TITANIUM_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.TITANIUM_MECHANICAL_PRESS, "ev"),
            TUNGSTENSTEEL_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.TUNGSTENSTEEL_MECHANICAL_PRESS, "iv"),
            PALLADIUM_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.PALLADIUM_MECHANICAL_PRESS, "luv"),
            NAQUADAH_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.NAQUADAH_MECHANICAL_PRESS, "zpm"),
            DARMSTADTIUM_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.DARMSTADTIUM_MECHANICAL_PRESS, "uv"),
            NEUTRONIUM_MECHANICAL_PRESS = createMaterialMechanicalPressRecipe(MechanicalPresses.NEUTRONIUM_MECHANICAL_PRESS, "uhv"),


            ANDESITE_WHISK = create(AllItems.WHISK).unlockedBy(AllItems.ANDESITE_ALLOY::asItem)
                    .viaShaped(b -> b.define('F', GreateTags.forgeItemTag("tools/files"))
                            .define('S', GreateTags.forgeItemTag("tools/screwdrivers"))
                            .define('A', AllItems.ANDESITE_ALLOY)
                            .define('P', GreateTags.forgeItemTag("plates/iron"))
                            .pattern("FAS")
                            .pattern("PAP")
                            .pattern("PPP")),
            STEEL_WHISK = createMaterialWhiskRecipe(ModItems.STEEL_WHISK),
            ALUMINIUM_WHISK = createMaterialWhiskRecipe(ModItems.ALUMINIUM_WHISK),
            STAINLESS_STEEL_WHISK = createMaterialWhiskRecipe(ModItems.STAINLESS_STEEL_WHISK),
            TITANIUM_WHISK = createMaterialWhiskRecipe(ModItems.TITANIUM_WHISK),
            TUNGSTENSTEEL_WHISK = createMaterialWhiskRecipe(ModItems.TUNGSTENSTEEL_WHISK),
            PALLADIUM_WHISK = createMaterialWhiskRecipe(ModItems.PALLADIUM_WHISK),
            NAQUADAH_WHISK = createMaterialWhiskRecipe(ModItems.NAQUADAH_WHISK),
            DARMSTADTIUM_WHISK = createMaterialWhiskRecipe(ModItems.DARMSTADTIUM_WHISK),
            NEUTRONIUM_WHISK = createMaterialWhiskRecipe(ModItems.NEUTRONIUM_WHISK),

            ANDESITE_MIXER = create(MechanicalMixers.ANDESITE_MECHANICAL_MIXER).unlockedBy(GTBlocks.MACHINE_CASING_ULV::asItem)
                    .viaShaped(b -> b.define('C', Cogwheels.ANDESITE_COGWHEEL)
                            .define('I', GreateTags.forgeItemTag("circuits/ulv"))
                            .define('A', GTBlocks.MACHINE_CASING_ULV)
                            .define('W', AllItems.WHISK)
                            .pattern(" C ")
                            .pattern("IAI")
                            .pattern(" W ")),

            STEEL_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.STEEL_MECHANICAL_MIXER, "lv"),
            ALUMINIUM_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.ALUMINIUM_MECHANICAL_MIXER, "mv"),
            STAINLESS_STEEL_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.STAINLESS_STEEL_MECHANICAL_MIXER, "hv"),
            TITANIUM_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.TITANIUM_MECHANICAL_MIXER, "ev"),
            TUNGSTENSTEEL_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.TUNGSTENSTEEL_MECHANICAL_MIXER, "iv"),
            PALLADIUM_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.PALLADIUM_MECHANICAL_MIXER, "luv"),
            NAQUADAH_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.NAQUADAH_MECHANICAL_MIXER, "zpm"),
            DARMSTADTIUM_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.DARMSTADTIUM_MECHANICAL_MIXER, "uv"),
            NEUTRONIUM_MECHANICAL_MIXER = createMaterialMechanicalMixerRecipe(MechanicalMixers.NEUTRONIUM_MECHANICAL_MIXER, "uhv");

    private GeneratedRecipe createMaterialAlloyRecipe(ItemProviderEntry<? extends ItemLike> alloy) {
        String material = alloy.getId().getPath().substring(0, alloy.getId().getPath().length() - 6);
        return create(alloy).unlockedByTag(GreateTags.forgeItemTag("ingots/" + material))
                .viaShaped(b -> b.define('N', GreateTags.forgeItemTag("nuggets/" + material))
                        .define('A', Blocks.ANDESITE)
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .define('H', GreateTags.forgeItemTag("tools/hammers"))
                        .pattern("NA")
                        .pattern("AN")
                        .pattern("FH"));
    }

    private GeneratedRecipe createMaterialShaftRecipe(ItemProviderEntry<? extends ItemLike> shaft) {
        String material = shaft.getId().getPath().substring(0, shaft.getId().getPath().length() - 6);
        return create(shaft).returns(4).unlockedBy(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Greate.MOD_ID, material + "_alloy"))::asItem)
                .viaShaped(b -> b.define('A', ForgeRegistries.ITEMS.getValue(new ResourceLocation(Greate.MOD_ID, material + "_alloy")))
                        .define('S', GreateTags.forgeItemTag("tools/saws"))
                        .pattern("S ")
                        .pattern(" A"));
    }

    private GeneratedRecipe createMaterialCogwheelRecipe(ItemProviderEntry<? extends ItemLike> cogwheel, ItemProviderEntry<? extends ItemLike> shaft, String material) {
        return create(cogwheel).unlockedBy(shaft::get)
                .viaShaped(b -> b.define('S', shaft.get())
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .pattern("SP")
                        .pattern("F "));
    }

    private GeneratedRecipe createMaterialLargeCogwheelRecipe(ItemProviderEntry<? extends ItemLike> cogwheel, ItemProviderEntry<? extends ItemLike> shaft, String material) {
        return create(cogwheel).unlockedBy(shaft::get)
                .viaShaped(b -> b.define('S', shaft.get())
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .pattern("SP")
                        .pattern("PF"));
    }

    private GeneratedRecipe createMaterialLargeCogwheelFromSmallRecipe(ItemProviderEntry<? extends ItemLike> cogwheel, ItemProviderEntry<? extends ItemLike> smallCogwheel, String material) {
        return create(cogwheel).withSuffix("from_little").unlockedBy(smallCogwheel::get)
                .viaShaped(b -> b.define('S', smallCogwheel.get())
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .pattern("SP")
                        .pattern("F "));
    }

    private GeneratedRecipe createMaterialMillstoneRecipe(ItemProviderEntry<? extends ItemLike> millstone, String tier) {
        String material = millstone.getId().getPath().substring(0, millstone.getId().getPath().length() - 10);
        return create(millstone).unlockedBy(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", tier + "_machine_hull"))::asItem)
                .viaShaped(b -> b.define('W', GreateTags.greateItemTag("cogwheels/" + material))
                        .define('S', GreateTags.mcItemTag("wooden_slabs"))
                        .define('M', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", tier + "_machine_hull")))
                        .define('C', GreateTags.forgeItemTag("circuits/" + tier))
                        .define('A', GreateTags.greateItemTag("shafts/" + material))
                        .pattern(" W ")
                        .pattern("SMS")
                        .pattern("CAC"));
    }

    private GeneratedRecipe createMaterialGearboxRecipe(ItemProviderEntry<? extends ItemLike> gearbox) {
        String material = gearbox.getId().getPath().substring(0, gearbox.getId().getPath().length() - 8);
        return create(gearbox).unlockedByTag(GreateTags.greateItemTag("cogwheels/" + material))
                .viaShaped(b -> b.define('S', GreateTags.greateItemTag("shafts/" + material))
                        .define('M', AllBlocks.ANDESITE_CASING.get())
                        .define('F', GreateTags.forgeItemTag("tools/files"))
                        .define('H', GreateTags.forgeItemTag("tools/hammers"))
                        .pattern(" S ")
                        .pattern("SMS")
                        .pattern("FSH"));
    }

    private GeneratedRecipe createBeltConnectorRecipe(ItemProviderEntry<? extends ItemLike> belt) {
        String beltMaterial = belt.getId().getPath().substring(0, belt.getId().getPath().length() - 15);
        return create(belt).unlockedBy(ForgeRegistries.ITEMS.getValue(new ResourceLocation("gtceu", beltMaterial + "_foil"))::asItem)
                .viaShaped(b -> b.define('F', ForgeRegistries.ITEMS.getValue(new ResourceLocation("gtceu", beltMaterial + "_foil")))
                        .pattern("FFF")
                        .pattern("FFF"));
    }

    private GeneratedRecipe createMaterialMechanicalPressRecipe(ItemProviderEntry<? extends ItemLike> mechanicalPress, String tier) {
        String material = mechanicalPress.getId().getPath().substring(0, mechanicalPress.getId().getPath().length() - 17);
        return create(mechanicalPress).unlockedBy(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", tier + "_machine_hull"))::asItem)
                .viaShaped(b -> b.define('M', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", tier + "_machine_hull")))
                        .define('C', GreateTags.forgeItemTag("circuits/" + tier))
                        .define('S', GreateTags.greateItemTag("shafts/" + material))
                        .define('B', GreateTags.forgeItemTag("storage_blocks/" + material))
                        .pattern(" S ")
                        .pattern("CMC")
                        .pattern(" B "));
    }

    private GeneratedRecipe createMaterialWhiskRecipe(ItemProviderEntry<? extends ItemLike> whisk) {
        String material = whisk.getId().getPath().substring(0, whisk.getId().getPath().length() - 6);
        return create(whisk).unlockedBy(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Greate.MOD_ID, material + "_alloy"))::asItem)
                .viaShaped(b -> b.define('F', GreateTags.forgeItemTag("tools/files"))
                        .define('S', GreateTags.forgeItemTag("tools/screwdrivers"))
                        .define('A', ForgeRegistries.ITEMS.getValue(new ResourceLocation(Greate.MOD_ID, material + "_alloy")))
                        .define('P', GreateTags.forgeItemTag("plates/" + material))
                        .pattern("FAS")
                        .pattern("PAP")
                        .pattern("PPP"));
    }

    private GeneratedRecipe createMaterialMechanicalMixerRecipe(ItemProviderEntry<? extends ItemLike> mixer, String tier) {
        String material = mixer.getId().getPath().substring(0, mixer.getId().getPath().length() - 17);
        return create(mixer).unlockedBy(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", tier + "_machine_hull"))::asItem)
                .viaShaped(b -> b.define('C', GreateTags.greateItemTag("cogwheels/" + material))
                        .define('I', GreateTags.forgeItemTag("circuits/" + tier))
                        .define('A', ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", tier + "_machine_hull"))::asItem)
                        .define('W', ForgeRegistries.ITEMS.getValue(new ResourceLocation(Greate.MOD_ID, material + "_whisk")))
                        .pattern(" C ")
                        .pattern("IAI")
                        .pattern(" W "));
    }

    String currentFolder = "";

    Marker enterFolder(String folder) {
        currentFolder = folder;
        return new Marker();
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return create(result::get);
    }

    GeneratedRecipe conversionCycle(List<ItemProviderEntry<? extends ItemLike>> cycle) {
        GeneratedRecipe result = null;
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            result = create(nextEntry).withSuffix("_from_conversion")
                    .unlockedBy(currentEntry::get)
                    .viaShapeless(b -> b.requires(currentEntry.get()));
        }
        return result;
    }

    class GeneratedRecipeBuilder {

        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ICondition> recipeConditions;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder unlockedByTag(TagKey<Item> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag)
                    .build();
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }

        GreateStandardRecipeGen.GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b = builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createGreateLocation("crafting"));
            });
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b = builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createGreateLocation("crafting"));
            });
        }

        GeneratedRecipe viaSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
            return register(consumer -> {
                SmithingTransformRecipeBuilder b =
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(base.get()),
                                upgradeMaterial.get(), RecipeCategory.COMBAT, result.get().asItem());
                b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(base.get())
                        .build()));
                b.save(consumer, createGreateLocation("crafting"));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return new ResourceLocation("greate", recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createGreateLocation(String recipeType) {
            return new ResourceLocation("greate",recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? RegisteredObjects.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }

        GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }

        GeneratedCookingRecipeBuilder viaCookingTag(TagKey<Item> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag));
        }

        GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new GeneratedCookingRecipeBuilder(ingredient);
        }

        class GeneratedCookingRecipeBuilder {

            private Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            private final RecipeSerializer<? extends AbstractCookingRecipe>
                    FURNACE = RecipeSerializer.SMELTING_RECIPE,
                    SMOKER = RecipeSerializer.SMOKING_RECIPE,
                    BLAST = RecipeSerializer.BLASTING_RECIPE,
                    CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(FURNACE, builder, 1);
            }

            GeneratedRecipe inSmoker() {
                return inSmoker(b -> b);
            }

            GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                create(CAMPFIRE, builder, 3);
                return create(SMOKER, builder, .5f);
            }

            GeneratedRecipe inBlastFurnace() {
                return inBlastFurnace(b -> b);
            }

            GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                return create(BLAST, builder, .5f);
            }

            private GeneratedRecipe create(RecipeSerializer<? extends AbstractCookingRecipe> serializer,
                                           UnaryOperator<SimpleCookingRecipeBuilder> builder, float cookingTimeModifier) {
                return register(consumer -> {
                    boolean isOtherMod = compatDatagenOutput != null;

                    SimpleCookingRecipeBuilder b = builder.apply(
                            SimpleCookingRecipeBuilder.generic(ingredient.get(), RecipeCategory.MISC,
                                    isOtherMod ? Items.DIRT : result.get(),
                                    exp, (int) (cookingTime * cookingTimeModifier), serializer));
                    if (unlockedBy != null)
                        b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                    b.save(result -> {
                        consumer.accept(
                                isOtherMod ? new ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
                                        : result);
                    }, createSimpleLocation(RegisteredObjects.getKeyOrThrow(serializer).getPath()));
                });
            }
        }
    }

    private static class ModdedCookingRecipeResult implements FinishedRecipe {

        private FinishedRecipe wrapped;
        private ResourceLocation outputOverride;
        private List<ICondition> conditions;

        public ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride,
                                         List<ICondition> conditions) {
            this.wrapped = wrapped;
            this.outputOverride = outputOverride;
            this.conditions = conditions;
        }

        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());

            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            object.add("conditions", conds);
        }
    }
}
