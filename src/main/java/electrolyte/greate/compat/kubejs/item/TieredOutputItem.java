package electrolyte.greate.compat.kubejs.item;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.OutputReplacement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;

public class TieredOutputItem extends OutputItem {

    public static final TieredOutputItem EMPTY = new TieredOutputItem(ItemStack.EMPTY, Double.NaN, Double.NaN, null);

    public static TieredOutputItem of(ItemStack item, double chance, double extraTierChance) {
        return item.isEmpty()
                ? EMPTY
                : new TieredOutputItem(item, chance, extraTierChance, null);
    }

    public static TieredOutputItem of(Object from) {
        if(from instanceof TieredOutputItem tieredOutputItem) {
            return tieredOutputItem;
        } else if(from instanceof ItemStack stack) {
            return of(stack, Double.NaN, Double.NaN);
        }

        ItemStack itemStack = ItemStackJS.of(from);
        if(itemStack.isEmpty()) {
            return EMPTY;
        }

        double chance = Double.NaN;
        double extraTierChance = Double.NaN;
        IntProvider rolls = null;
        if(from instanceof JsonObject jsonObject) {
            if(jsonObject.has("chance")) {
                chance = jsonObject.get("chance").getAsDouble();
            }
            if(jsonObject.has("extraTierChance")) {
                extraTierChance = jsonObject.get("extraTierChance").getAsDouble();
            }
            if(jsonObject.has("minRolls") && jsonObject.has("maxRolls")) {
                rolls = UniformInt.of(jsonObject.get("minRolls").getAsInt(), jsonObject.get("maxRolls").getAsInt());
            }
        }

        return new TieredOutputItem(itemStack, chance, extraTierChance, rolls);
    }

    public final double extraTierChance;

    protected TieredOutputItem(ItemStack item, double chance, double extraTierChance, IntProvider rolls) {
        super(item, chance, rolls);
        this.extraTierChance = extraTierChance;
    }

    @Override
    public OutputItem withCount(int count) {
        return new TieredOutputItem(item.copyWithCount(count), chance, extraTierChance, rolls);
    }

    @Override
    public OutputItem withChance(double chance) {
        return new TieredOutputItem(item.copy(), chance, extraTierChance, rolls);
    }

    public TieredOutputItem withExtraTierChance(double extraChance) {
        return new TieredOutputItem(item.copy(), chance, extraChance, rolls);
    }

    @Override
    public OutputItem withRolls(IntProvider rolls) {
        return new TieredOutputItem(item.copy(), chance, extraTierChance, rolls);
    }

    public double getExtraTierChance() {
        return extraTierChance;
    }

    public boolean hasExtraTierChance() {
        return !Double.isNaN(extraTierChance);
    }

    @Override
    public Object replaceOutput(RecipeJS recipe, ReplacementMatch match, OutputReplacement original) {
        if(original instanceof TieredOutputItem outputItem) {
            TieredOutputItem replacement = new TieredOutputItem(item.copy(), outputItem.chance, outputItem.extraTierChance, outputItem.rolls);
            replacement.item.setCount(outputItem.getCount());
            return replacement;
        }
        return new TieredOutputItem(item.copy(), Double.NaN, Double.NaN, null);
    }
}
