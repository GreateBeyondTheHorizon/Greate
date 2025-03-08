package electrolyte.greate.content.kinetics.fan;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredHauntingType;
import electrolyte.greate.content.kinetics.fan.processing.GreateFanProcessingTypes.TieredSplashingType;
import electrolyte.greate.infrastructure.config.GreateConfigs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TieredFanProcessing {

    public static boolean canProcess(ItemEntity entity, FanProcessingType type, int machineTier) {
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
                        return ts.canProcess(entity.getItem(), entity.level(), machineTier);
                    } else return false;
                }
                else if (processing.getInt("Time") >= 0) return true;
                else if (processing.getInt("Time") == -1) return false;
            }
        }
        if(type instanceof TieredHauntingType th) {
            return th.canProcess(entity.getItem(), entity.level(), machineTier);
        } else if(type instanceof TieredSplashingType ts) {
            return ts.canProcess(entity.getItem(), entity.level(), machineTier);
        }
        return type.canProcess(entity.getItem(), entity.level());
    }

    public static boolean applyProcessing(float speed, ItemEntity entity, FanProcessingType type, int machineTier) {
        if(decrementProcessingTime(speed, entity, type) != 0) return false;
        List<ItemStack> stacks;
        if(type instanceof TieredHauntingType th) {
            stacks = th.process(entity.getItem(), entity.level(), machineTier);
        } else if(type instanceof TieredSplashingType ts) {
            stacks = ts.process(entity.getItem(), entity.level(), machineTier);
        } else {
            stacks = type.process(entity.getItem(), entity.level());
        }
        if(stacks == null) return false;
        if(stacks.isEmpty()) {
            entity.discard();
            return false;
        }
        entity.setItem(stacks.remove(0));
        for(ItemStack additional : stacks) {
            ItemEntity entityIn = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), additional);
            entityIn.setDeltaMovement(entityIn.getDeltaMovement());
            entity.level().addFreshEntity(entityIn);
        }
        return true;
    }

    public static TransportedResult applyProcessing(float speed, TransportedItemStack transported, Level level, FanProcessingType type, int machineTier) {
        TransportedResult ignore = TransportedResult.doNothing();
        if(transported.processedBy != type) {
            transported.processedBy = type;
            transported.processingTime = getProcessingTime(transported.stack.getCount(), speed);
            if(!type.canProcess(transported.stack, level)) {
                transported.processingTime = -1;
            }
            return ignore;
        }
        if(transported.processingTime == -1) return ignore;
        if(transported.processingTime-- > 0) return ignore;

        List<ItemStack> stacks;
        if(type instanceof TieredHauntingType th) {
            stacks = th.process(transported.stack, level, machineTier);
        } else if(type instanceof TieredSplashingType ts) {
            stacks = ts.process(transported.stack, level, machineTier);
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
        return TransportedResult.convertTo(transportedItemStacks);
    }

    private static int decrementProcessingTime(float speed, ItemEntity entity, FanProcessingType type) {
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
            int processingTime = getProcessingTime(entity.getItem().getCount(), speed);
            processing.putInt("Time", processingTime);
        }

        int value = processing.getInt("Time") - 1;
        processing.putInt("Time", value);
        return value;
    }

    private static int getProcessingTime(int entityCount, float speed) {
        int timeModifierForStackSize = ((entityCount - 1) / 16) + 1;
        int timeModifierForSpeed = (int) Math.max(0, speed * GreateConfigs.server().kinetics.fanSpeedMultiplier.get());
        return Math.max(1, ((AllConfigs.server().kinetics.fanProcessingTime.get() - timeModifierForSpeed) * timeModifierForStackSize) + 1);
    }
}
