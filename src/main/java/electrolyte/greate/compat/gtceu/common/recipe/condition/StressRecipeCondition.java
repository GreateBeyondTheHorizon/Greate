package electrolyte.greate.compat.gtceu.common.recipe.condition;

import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import electrolyte.greate.compat.gtceu.common.data.GreateRecipeConditions;
import electrolyte.greate.compat.gtceu.common.machine.kinetic.IKineticMachine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
public class StressRecipeCondition extends RecipeCondition {

    public static final StressRecipeCondition INSTANCE = new StressRecipeCondition();

    private float stress;

    public StressRecipeCondition(boolean isReverse, float stress) {
        super(isReverse);
        this.stress = stress;
    }

    public StressRecipeCondition(float stress) {
        this.stress = stress;
    }

    public static final Codec<StressRecipeCondition> CODEC = RecordCodecBuilder.create(i -> RecipeCondition.isReverse(i)
            .and(Codec.FLOAT.fieldOf("stress").forGetter(v -> v.stress))
            .apply(i, StressRecipeCondition::new));

    @Override
    public RecipeConditionType<?> getType() {
        return GreateRecipeConditions.STRESS;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("greate.recipe.condition.stress", stress);
    }

    @Override
    public boolean test(@NotNull GTRecipe gtRecipe, @NotNull RecipeLogic recipeLogic) {
        if(recipeLogic.machine instanceof IMultiController controller) {
            for(IMultiPart part : controller.getParts()) {
                if(part instanceof IKineticMachine km) {
                    return Math.abs(km.getKineticHolder().getOrCreateNetwork().calculateStress()) >= stress;
                }
            }
        }
        return false;
    }

    @Override
    public RecipeCondition createTemplate() {
        return new StressRecipeCondition();
    }

    @NotNull
    @Override
    public JsonObject serialize() {
        JsonObject obj = super.serialize();
        obj.addProperty("stress", stress);
        return obj;
    }

    @Override
    public RecipeCondition deserialize(@NotNull JsonObject config) {
        super.deserialize(config);
        stress = GsonHelper.getAsFloat(config, "stress", 0);
        return this;
    }

    @Override
    public RecipeCondition fromNetwork(FriendlyByteBuf buf) {
        super.fromNetwork(buf);
        stress = buf.readFloat();
        return this;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        super.toNetwork(buf);
        buf.writeFloat(stress);
    }
}
