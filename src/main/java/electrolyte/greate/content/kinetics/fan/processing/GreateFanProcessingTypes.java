package electrolyte.greate.content.kinetics.fan.processing;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes.HauntingType;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes.SplashingType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import electrolyte.greate.Greate;
import electrolyte.greate.foundation.recipe.TieredRecipeApplier;
import electrolyte.greate.registry.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GreateFanProcessingTypes {

    public static final TieredHauntingType TIERED_HAUNTING = register("haunting", new TieredHauntingType());
    public static final TieredSplashingType TIERED_SPLASHING = register("splashing", new TieredSplashingType());

    private static final Map<String, FanProcessingType> LEGACY_NAME_MAP;

    static {
        Object2ReferenceOpenHashMap<String, FanProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("TIERED_HAUNTING", TIERED_HAUNTING);
        map.put("TIERED_SPLASHING", TIERED_SPLASHING);
        map.trim();
        LEGACY_NAME_MAP = map;
    }

    private static <T extends FanProcessingType> T register(String id, T type) {
        return Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, Greate.id(id), type);
    }

    @Nullable
    public static FanProcessingType ofLegacyName(String name) {
        return LEGACY_NAME_MAP.get(name);
    }

    public static FanProcessingType parseLegacy(String name) {
        FanProcessingType type = ofLegacyName(name);
        if(type != null) {
            return type;
        }
        return FanProcessingType.parse(name);
    }

    public static void register() {}

    public static class TieredHauntingType extends HauntingType {

        @Override
        public int getPriority() {
            return 350;
        }

        public boolean canProcess(ItemStack stack, Level level, int machineTier) {
            if(super.canProcess(stack, level)) return true;
            Optional<RecipeHolder<Recipe<SingleRecipeInput>>> tieredRecipe = ModRecipeTypes.HAUNTING.find(new SingleRecipeInput(stack), level, machineTier);
            return tieredRecipe.isPresent();
        }

        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level, int machineTier) {
            List<ItemStack> result = super.process(stack, level);
            if(result != null) return result;
            Optional<RecipeHolder<Recipe<SingleRecipeInput>>> tieredRecipe = ModRecipeTypes.HAUNTING.find(new SingleRecipeInput(stack), level, machineTier);
            return tieredRecipe.map(tieredHauntingRecipe ->
                    TieredRecipeApplier.applyRecipeOn(level, stack, tieredHauntingRecipe.value(), machineTier, true)).orElse(null);
        }
    }

    public static class TieredSplashingType extends SplashingType {

        @Override
        public int getPriority() {
            return 450;
        }

        public boolean canProcess(ItemStack stack, Level level, int machineTier) {
            if(super.canProcess(stack, level)) return true;
            Optional<RecipeHolder<Recipe<SingleRecipeInput>>> tieredRecipe = ModRecipeTypes.SPLASHING.find(new SingleRecipeInput(stack), level, machineTier);
            return tieredRecipe.isPresent();
        }

        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level, int machineTier) {
            List<ItemStack> result = super.process(stack, level);
            if(result != null) return result;
            Optional<RecipeHolder<Recipe<SingleRecipeInput>>> tieredRecipe = ModRecipeTypes.SPLASHING.find(new SingleRecipeInput(stack), level, machineTier);
            return tieredRecipe.map(tieredSplashingRecipe ->
                    TieredRecipeApplier.applyRecipeOn(level, stack, tieredSplashingRecipe.value(), machineTier, true)).orElse(null);
        }
    }
}
