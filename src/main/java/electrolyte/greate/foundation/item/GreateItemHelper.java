package electrolyte.greate.foundation.item;

import net.createmod.catnip.data.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

public class GreateItemHelper {

    public static List<Pair<Ingredient, MutableInt>> condenseIngredients(NonNullList<Ingredient> recipeIngredients) {
        List<Pair<Ingredient, MutableInt>> actualIngredients = new ArrayList<>();
        Ingredients: for(Ingredient ing : recipeIngredients) {
            //TODO: check
            /*if(ing instanceof SizedIngredient si) {
                 actualIngredients.add(Pair.of(ing, new MutableInt(si.getAmount())));
            } else {*/
                for(Pair<Ingredient, MutableInt> pair : actualIngredients) {
                    ItemStack[] stacks = pair.getFirst().getItems();
                    ItemStack[] stacks2 = ing.getItems();
                    if(stacks.length != stacks2.length) continue;
                    for(int i = 0; i <= stacks.length; i++) {
                        if(i == stacks.length) {
                            pair.getSecond().increment();
                            continue Ingredients;
                        }
                        if(!ItemStack.matches(stacks[i], stacks2[i])) break;
                    }
                }
                actualIngredients.add(Pair.of(ing, new MutableInt(1)));
            //}
        }
        return actualIngredients;
    }
}
