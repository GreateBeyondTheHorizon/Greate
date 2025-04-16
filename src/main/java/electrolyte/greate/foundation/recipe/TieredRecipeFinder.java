package electrolyte.greate.foundation.recipe;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Modified version of {@link RecipeFinder}, to work with how Greate machines find recipes.
 */

public class TieredRecipeFinder {

    private static Cache<Object, List<Pair<ItemStack, List<Recipe<?>>>>> cachedSearches = CacheBuilder.newBuilder().build();
    private static boolean shouldRefreshRecipe = false;
    public static final ResourceManagerReloadListener LISTENER = r -> {
        cachedSearches.invalidateAll();
        shouldRefreshRecipe = true;
    };

    public static Optional<Recipe<?>> findRecipe(Object cacheKey, Level level, RecipeWrapper wrapper, Predicate<Recipe<?>> typeAndIngCondition, Predicate<Recipe<?>> otherConditions) {
        if(!cachedSearches.asMap().containsKey(cacheKey)) {
            createPairList(cacheKey, level, wrapper, typeAndIngCondition);
        }
        List<Pair<ItemStack, List<Recipe<?>>>> recipes = cachedSearches.asMap().get(cacheKey);
        if(recipes.stream().noneMatch(r -> wrapper.getItem(0).is(r.getFirst().getItem()))) {
            createPairList(cacheKey, level, wrapper, typeAndIngCondition);
        }
        Optional<Pair<ItemStack, List<Recipe<?>>>> pair = recipes.stream().filter(r -> wrapper.getItem(0).is(r.getFirst().getItem())).findFirst();
        if(pair.isEmpty()) return Optional.empty();
        Pair<ItemStack, List<Recipe<?>>> recipe = pair.get();
        for(Recipe<?> r : recipe.getSecond()) {
            if(Optional.of(r).filter(otherConditions).isPresent()) return Optional.of(r);
        }
        return Optional.empty();
    }

    private static void createPairList(Object cacheKey, Level level, RecipeWrapper wrapper, Predicate<Recipe<?>> conditions) {
        List<Recipe<?>> recipes = startSearch(level, conditions);
        List<Pair<ItemStack, List<Recipe<?>>>> pairList = cachedSearches.getIfPresent(cacheKey);
        if(pairList == null) {
            pairList = new ArrayList<>();
            List<Recipe<?>> recipeList = new ArrayList<>(recipes);
            pairList.add(new Pair<>(wrapper.getItem(0), recipeList));
            cachedSearches.put(cacheKey, pairList);
        } else {
            cachedSearches.asMap().get(cacheKey).add(new Pair<>(wrapper.getItem(0), new ArrayList<>(recipes)));
        }
    }

    private static List<Recipe<?>> startSearch(Level level, Predicate<? super Recipe<?>> conditions) {
        return level.getRecipeManager().getRecipes().stream().filter(conditions).collect(Collectors.toList());
    }

    public static boolean shouldRefreshRecipe() {
        if(shouldRefreshRecipe) {
            shouldRefreshRecipe = false;
            return true;
        }
        return false;
    }
}