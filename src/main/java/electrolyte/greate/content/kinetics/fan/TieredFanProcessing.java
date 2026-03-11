package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredHauntingType;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredSplashingType;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredSplashingType.SPLASHING_RECIPE_CACHE_KEY;

public class TieredFanProcessing {

    public static boolean canProcess(ItemEntity entity, FanProcessingType type, int machineTier, BlockEntity be) {
        if(!(be instanceof TieredEncasedFanBlockEntity tefbe)) return false;
        if(!Greate.CONFIG.processItemEntitiesWithFan) return false;
        if(entity.getPersistentData().contains("CreateData")) {
            CompoundTag compound = entity.getPersistentData().getCompound("CreateData");
            if(compound.contains("Processing")) {
                CompoundTag processing = compound.getCompound("Processing");

                if(AllFanProcessingTypes.parseLegacy(processing.getString("Type")) != type) {
                    return type.canProcess(entity.getItem(), entity.level());
                }
                if(GreateFanProcessingTypes.parseLegacy(processing.getString("Type")) != type) {
                    if(type instanceof TieredHauntingType th) {
                        return th.canProcess(entity.getItem(), entity.level(), machineTier);
                    } else if(type instanceof TieredSplashingType ts) {
                        return ts.canProcess(entity.getItem(), entity.level(), machineTier, tefbe);
                    } else return false;
                }
                else if (processing.getInt("Time") >= 0) return true;
                else if (processing.getInt("Time") == -1) return false;
            }
        }
        if(type instanceof TieredHauntingType th) {
            return th.canProcess(entity.getItem(), entity.level(), machineTier);
        } else if(type instanceof TieredSplashingType ts) {
            return ts.canProcess(entity.getItem(), entity.level(), machineTier, tefbe);
        }
        return type.canProcess(entity.getItem(), entity.level());
    }

    public static boolean applyProcessing(float speed, ItemEntity entity, FanProcessingType type, int machineTier, TieredEncasedFanBlockEntity fanBE) {
        int maxItemsProcessed = getMaxItemsProcessedCount(type, fanBE, entity.getItem());
        if(decrementProcessingTime(speed, entity, type, maxItemsProcessed) != 0) return false;
        List<ItemStack> stacks;
        if(type instanceof TieredHauntingType th) {
            stacks = th.process(entity.getItem(), entity.level(), machineTier, fanBE);
        } else if(type instanceof TieredSplashingType ts) {
            stacks = ts.process(entity.getItem(), entity.level(), machineTier, fanBE);
        } else {
            stacks = type.process(entity.getItem(), entity.level());
        }
        if(stacks == null) return false;
        if(stacks.isEmpty()) {
            entity.discard();
            return false;
        }
        int remainder = entity.getItem().getCount() - maxItemsProcessed;
        entity.setItem(entity.getItem().copyWithCount(remainder));
        for(ItemStack additional : stacks) {
            ItemEntity entityIn = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), additional);
            entityIn.setDeltaMovement(entityIn.getDeltaMovement());
            entity.level().addFreshEntity(entityIn);
        }
        return true;
    }

    public static TransportedResult applyProcessing(float speed, TransportedItemStack transported, Level level, FanProcessingType type, int machineTier, TieredEncasedFanBlockEntity fanBE) {
        TransportedResult ignore = TransportedResult.doNothing();
        if(transported.processedBy != type) {
            transported.processedBy = type;
            int maxItemsProcessed = getMaxItemsProcessedCount(type, fanBE, transported.stack);
            transported.processingTime = getProcessingTime(maxItemsProcessed, speed);
            if(type instanceof TieredHauntingType tht) {
                if(!tht.canProcess(transported.stack, level, machineTier)) {
                    transported.processingTime = -1;
                }
            } else if(type instanceof TieredSplashingType tst) {
                if(!tst.canProcess(transported.stack, level, machineTier, fanBE)) {
                    transported.processingTime = -1;
                }
            } else if(!type.canProcess(transported.stack, level)) {
                transported.processingTime = -1;
            }
            return ignore;
        }
        if(transported.processingTime == -1) return ignore;
        if(transported.processingTime-- > 0) return ignore;

        List<ItemStack> stacks;
        if(type instanceof TieredHauntingType th) {
            stacks = th.process(transported.stack, level, machineTier, fanBE);
        } else if(type instanceof TieredSplashingType ts) {
            stacks = ts.process(transported.stack, level, machineTier, fanBE);
        } else {
            stacks = type.process(transported.stack, level);
        }
        if(stacks == null) return ignore;

        List<TransportedItemStack> transportedItemStacks = new ArrayList<>();
        for(ItemStack additional : stacks) {
            TransportedItemStack newTransported = transported.getSimilar();
            newTransported.stack = additional.copy();
            transportedItemStacks.add(newTransported);
        }
        if(type instanceof TieredSplashingType) {
            transported.stack = transported.stack.copyWithCount(transported.stack.getCount() - stacks.get(0).getCount());
            return TransportedResult.convertToAndLeaveHeld(transportedItemStacks, transported);
        }
        return TransportedResult.convertTo(transportedItemStacks);
    }

    private static int decrementProcessingTime(float speed, ItemEntity entity, FanProcessingType type, int maxItemsProcessed) {
        CompoundTag nbt = entity.getPersistentData();

        if (!nbt.contains("CreateData"))
            nbt.put("CreateData", new CompoundTag());
        CompoundTag createData = nbt.getCompound("CreateData");

        if (!createData.contains("Processing"))
            createData.put("Processing", new CompoundTag());
        CompoundTag processing = createData.getCompound("Processing");

        if (!processing.contains("Type") || (AllFanProcessingTypes.parseLegacy(processing.getString("Type")) != type && GreateFanProcessingTypes.parseLegacy(processing.getString("Type")) != type)) {
            ResourceLocation key = CreateBuiltInRegistries.FAN_PROCESSING_TYPE.getKey(type);
            if(key == null) {
                throw new IllegalArgumentException("Could not get id for FanProcessingType " + type + "!");
            }
            processing.putString("Type", key.toString());
            int processingTime = getProcessingTime(maxItemsProcessed, speed);
            processing.putInt("Time", processingTime);
        }

        int value = processing.getInt("Time") - 1;
        processing.putInt("Time", value);
        return value;
    }

    private static int getProcessingTime(int entityCount, float speed) {
        int timeModifierForStackSize = ((entityCount - 1) / 16) + 1;
        int timeModifierForSpeed = (int) Math.max(0, speed * Greate.CONFIG.fanSpeedMultiplier);
        return Math.max(1, ((AllConfigs.server().kinetics.fanProcessingTime.get() - timeModifierForSpeed) * timeModifierForStackSize) + 1);
    }

    private static int getMaxItemsProcessedCount(FanProcessingType type, TieredEncasedFanBlockEntity fanBE, ItemStack stack) {
        int maxItemsProcessedCount = stack.getCount();
        if(type == GreateFanProcessingTypes.TIERED_SPLASHING) {
            if(fanBE != null && fanBE.getFluidInTank() != null) {
                int fluidAmountInTank = fanBE.getFluidInTank().getAmount();
                if(fluidAmountInTank == 0) return 0;
                RecipeWrapper wrapper = new RecipeWrapper(new ItemStackHandler(1));
                wrapper.setItem(0, stack);
                List<Recipe<?>> recipes = RecipeFinder.get(SPLASHING_RECIPE_CACHE_KEY, fanBE.getLevel(), p -> p.getType() == ModRecipeTypes.SPLASHING.getType());
                Optional<Recipe<?>> validRecipe = recipes.stream()
                        .filter(TieredRecipeConditions.firstIngredientMatches(wrapper.getItem(0)))
                        .filter(TieredRecipeConditions.firstIngredientCountMatches(wrapper.getItem(0)))
                        .filter(TieredRecipeConditions.firstFluidMatches(fanBE.getFluidInTank()))
                        .filter(TieredRecipeConditions.isEqualOrAboveTier(fanBE.getTier()))
                        .filter(TieredRecipeConditions.circuitMatches(fanBE.getTargetCircuit().getValue()))
                        .findFirst();
                if(validRecipe.isEmpty()) return 0;
                int requiredAmount = ((TieredSplashingRecipe) validRecipe.get()).getFluidIngredients().get(0).getRequiredAmount();
                int fanMaxItemsProcessed = fluidAmountInTank / requiredAmount;
                maxItemsProcessedCount = Math.min(stack.getCount(), fanMaxItemsProcessed);
            }
        }
        return maxItemsProcessedCount;
    }
}
