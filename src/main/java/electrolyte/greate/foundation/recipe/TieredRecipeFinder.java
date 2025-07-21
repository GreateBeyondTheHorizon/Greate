package electrolyte.greate.foundation.recipe;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Modified version of {@link RecipeFinder}, to work with how Greate machines find recipes.
 */

public class TieredRecipeFinder {

    private static Cache<Object, List<Pair<ItemStack, List<RecipeHolder<? extends Recipe<?>>>>>> cachedSearches = CacheBuilder.newBuilder().build();
    private static boolean shouldRefreshRecipe = false;
    public static final ResourceManagerReloadListener LISTENER = r -> {
        cachedSearches.invalidateAll();
        shouldRefreshRecipe = true;
    };

    public static Optional<RecipeHolder<? extends Recipe<?>>> findRecipe(Object cacheKey, Level level, SingleRecipeInput wrapper, Predicate<RecipeHolder<? extends Recipe<?>>> typeAndIngCondition, Predicate<RecipeHolder<? extends Recipe<?>>> otherConditions) {
        if(!cachedSearches.asMap().containsKey(cacheKey)) {
            createPairList(cacheKey, level, wrapper, typeAndIngCondition);
        }
        List<Pair<ItemStack, List<RecipeHolder<? extends Recipe<?>>>>> recipes = cachedSearches.asMap().get(cacheKey);
        if(recipes.stream().noneMatch(r -> wrapper.getItem(0).is(r.getFirst().getItem()))) {
            createPairList(cacheKey, level, wrapper, typeAndIngCondition);
        }
        Optional<Pair<ItemStack, List<RecipeHolder<? extends Recipe<?>>>>> pair = recipes.stream().filter(r -> wrapper.getItem(0).is(r.getFirst().getItem())).findFirst();
        if(pair.isEmpty()) return Optional.empty();
        Pair<ItemStack, List<RecipeHolder<? extends Recipe<?>>>> recipe = pair.get();
        for(RecipeHolder<? extends Recipe<?>> r : recipe.getSecond()) {
            if(Optional.of(r).filter(otherConditions).isPresent()) return Optional.of(r);
        }
        return Optional.empty();
    }

    private static void createPairList(Object cacheKey, Level level, SingleRecipeInput wrapper, Predicate<RecipeHolder<? extends Recipe<?>>> conditions) {
        List<RecipeHolder<? extends Recipe<?>>> recipes = startSearch(level, conditions);
        List<Pair<ItemStack, List<RecipeHolder<? extends Recipe<?>>>>> pairList = cachedSearches.getIfPresent(cacheKey);
        if(pairList == null) {
            pairList = new ArrayList<>();
            List<RecipeHolder<? extends Recipe<?>>> recipeList = new ArrayList<>(recipes);
            pairList.add(new Pair<>(wrapper.getItem(0), recipeList));
            cachedSearches.put(cacheKey, pairList);
        } else {
            cachedSearches.asMap().get(cacheKey).add(new Pair<>(wrapper.getItem(0), new ArrayList<>(recipes)));
        }
    }

    private static List<RecipeHolder<? extends Recipe<?>>> startSearch(Level level, Predicate<? super RecipeHolder<? extends Recipe<?>>> conditions) {
        List<RecipeHolder<? extends Recipe<?>>> recipes = new ArrayList<>();
        for(RecipeHolder<? extends Recipe<?>> r : level.getRecipeManager().getRecipes()) {
            if(conditions.test(r)) {
                recipes.add(r);
            }
        }
        return recipes;
    }

    public static boolean shouldRefreshRecipe() {
        if(shouldRefreshRecipe) {
            shouldRefreshRecipe = false;
            return true;
        }
        return false;
    }
}