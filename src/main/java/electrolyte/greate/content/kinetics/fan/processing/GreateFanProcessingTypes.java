package electrolyte.greate.content.kinetics.fan.processing;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes.HauntingType;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes.SplashingType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.fan.TieredEncasedFanBlockEntity;
import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe.TieredHauntingWrapper;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe.TieredSplashingWrapper;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.foundation.recipe.TieredRecipeApplier;
import electrolyte.greate.registry.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

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
        private static final TieredHauntingWrapper TIERED_HAUNTING_WRAPPER = new TieredHauntingWrapper();

        @Override
        public int getPriority() {
            return 350;
        }

        public boolean canProcess(ItemStack stack, Level level, int machineTier) {
            if(super.canProcess(stack, level)) return true;
            TIERED_HAUNTING_WRAPPER.setItem(0, stack);
            Optional<TieredHauntingRecipe> tieredRecipe = ModRecipeTypes.HAUNTING.find(TIERED_HAUNTING_WRAPPER, level, machineTier);
            return tieredRecipe.isPresent();
        }

        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level, int machineTier, TieredEncasedFanBlockEntity fanBE) {
            List<ItemStack> result = super.process(stack, level);
            if(result != null) return result;
            TIERED_HAUNTING_WRAPPER.setItem(0, stack);
            Optional<TieredHauntingRecipe> tieredRecipe = ModRecipeTypes.HAUNTING.find(TIERED_HAUNTING_WRAPPER, level, machineTier);
            return tieredRecipe.map(tieredHauntingRecipe ->
                    TieredRecipeApplier.applyRecipeOn(level, stack, tieredHauntingRecipe, machineTier, true)).orElse(null);
        }
    }

    public static class TieredSplashingType extends SplashingType {

        private static final TieredSplashingWrapper TIERED_SPLASHING_WRAPPER = new TieredSplashingWrapper();
        private static final Object SPLASHING_RECIPE_CACHE_KEY = new Object();

        @Override
        public int getPriority() {
            return 450;
        }

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            BlockEntity fanBE = level.getBlockEntity(pos);
            if(fanBE instanceof TieredEncasedFanBlockEntity fan) {
                FluidStack fluid = fan.getFluidInTank();
                if(fluid != null) return fluid.getAmount() > 0;
            }
            return false;
        }

        public boolean canProcess(ItemStack stack, Level level, int machineTier, TieredEncasedFanBlockEntity fanBE) {
            if(super.canProcess(stack, level)) return true;
            if(fanBE == null) return false;
            TIERED_SPLASHING_WRAPPER.setItem(0, stack);
            List<Recipe<?>> recipes = RecipeFinder.get(SPLASHING_RECIPE_CACHE_KEY, level, p -> p.getType() == ModRecipeTypes.SPLASHING.getType());
            List<Recipe<?>> validRecipes = recipes.stream()
                    .filter(TieredRecipeConditions.firstIngredientMatches(TIERED_SPLASHING_WRAPPER.getItem(0)))
                    .filter(TieredRecipeConditions.firstIngredientCountMatches(TIERED_SPLASHING_WRAPPER.getItem(0)))
                    .filter(TieredRecipeConditions.firstFluidMatches(fanBE.getFluidInTank()))
                    .filter(TieredRecipeConditions.isEqualOrAboveTier(machineTier))
                    .filter(TieredRecipeConditions.circuitMatches(fanBE.getTargetCircuit().getValue()))
                    .toList();
            return !validRecipes.isEmpty();
        }

        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level, int machineTier, TieredEncasedFanBlockEntity fanBE) {
            List<ItemStack> result = super.process(stack, level);
            if(result != null) return result;
            if(fanBE == null) return null;
            TIERED_SPLASHING_WRAPPER.setItem(0, stack);
            List<Recipe<?>> recipes = RecipeFinder.get(SPLASHING_RECIPE_CACHE_KEY, level, p -> p.getType() == ModRecipeTypes.SPLASHING.getType());
            Optional<Recipe<?>> validRecipe = recipes.stream()
                    .filter(TieredRecipeConditions.firstIngredientMatches(TIERED_SPLASHING_WRAPPER.getItem(0)))
                    .filter(TieredRecipeConditions.firstIngredientCountMatches(TIERED_SPLASHING_WRAPPER.getItem(0)))
                    .filter(TieredRecipeConditions.firstFluidMatches(fanBE.getFluidInTank()))
                    .filter(TieredRecipeConditions.isEqualOrAboveTier(machineTier))
                    .filter(TieredRecipeConditions.circuitMatches(fanBE.getTargetCircuit().getValue()))
                    .findFirst();
            return validRecipe.map(recipe ->
                    TieredRecipeApplier.applyRecipeOn(level, stack, recipe, machineTier, true, fanBE)).orElse(null);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {}

        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random, TieredEncasedFanBlockEntity fanBE) {
            particleAccess.setColor(getColor(random, fanBE));
            particleAccess.setAlpha(1f);
			if (random.nextFloat() < 1 / 32f)
				particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE, .125f);
			if (random.nextFloat() < 1 / 32f)
				particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE_POP, .125f);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {}

        public void spawnProcessingParticles(Level level, Vec3 pos, TieredEncasedFanBlockEntity fanBE) {
            if (level.random.nextInt(8) != 0) return;
            Vector3f color3f = new Color(getColor(level.random, fanBE)).asVectorF();
			level.addParticle(new DustParticleOptions(color3f, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
				pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
			level.addParticle(ParticleTypes.SPIT, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
				pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);

        }

        private int getColor(RandomSource random, TieredEncasedFanBlockEntity fanBE) {
            int color = Color.mixColors(0x4499FF, 0x2277FF, random.nextFloat());
            if(fanBE != null && fanBE.getFluidInTank() != null) {
                Material fluidMaterial = ChemicalHelper.getMaterial(fanBE.getFluidInTank().getFluid());
                if(!fluidMaterial.isNull()) {
                    if(fluidMaterial.getMaterialSecondaryRGB() != -1) {
                        color = Color.mixColors(fluidMaterial.getMaterialRGB(), fluidMaterial.getMaterialSecondaryRGB(), random.nextFloat());
                    } else color = fluidMaterial.getMaterialRGB();
                }
            }
            return color;
        }
    }
}
