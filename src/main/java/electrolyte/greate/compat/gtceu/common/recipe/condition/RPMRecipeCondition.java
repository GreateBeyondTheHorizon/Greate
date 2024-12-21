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
public class RPMRecipeCondition extends RecipeCondition {

    public static final RPMRecipeCondition INSTANCE = new RPMRecipeCondition();

    private float rpm;

    public RPMRecipeCondition(boolean isReverse, float rpm) {
        super(isReverse);
        this.rpm = rpm;
    }

    public RPMRecipeCondition(float rpm) {
        this.rpm = rpm;
    }

    public static final Codec<RPMRecipeCondition> CODEC = RecordCodecBuilder.create(i -> RecipeCondition.isReverse(i)
            .and(Codec.FLOAT.fieldOf("rpm").forGetter(v -> v.rpm))
            .apply(i, RPMRecipeCondition::new));

    @Override
    public RecipeConditionType<?> getType() {
        return GreateRecipeConditions.RPM;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("greate.recipe.condition.rpm", rpm);
    }

    @Override
    public boolean test(@NotNull GTRecipe gtRecipe, @NotNull RecipeLogic recipeLogic) {
        if(recipeLogic.machine instanceof IMultiController controller) {
            for(IMultiPart part : controller.getParts()) {
                if(part instanceof IKineticMachine km) {
                    return Math.abs(km.getKineticHolder().getSpeed()) >= rpm;
                }
            }
        }
        return false;
    }

    @Override
    public RecipeCondition createTemplate() {
        return new RPMRecipeCondition();
    }

    @NotNull
    @Override
    public JsonObject serialize() {
        JsonObject obj = super.serialize();
        obj.addProperty("rpm", rpm);
        return obj;
    }

    @Override
    public RecipeCondition deserialize(@NotNull JsonObject config) {
        super.deserialize(config);
        rpm = GsonHelper.getAsFloat(config, "rpm", 0);
        return this;
    }

    @Override
    public RecipeCondition fromNetwork(FriendlyByteBuf buf) {
        super.fromNetwork(buf);
        rpm = buf.readFloat();
        return this;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        super.toNetwork(buf);
        buf.writeFloat(rpm);
    }
}
