package electrolyte.greate.content.processing.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.simibubi.create.Create;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class TieredProcessingOutput extends ProcessingOutput {

    private static final Random r = new Random();
    private final float extraTierChance;

    public TieredProcessingOutput(ItemStack stack, float chance, float extraTierChance) {
        super(stack, chance);
        this.extraTierChance = extraTierChance;
    }

    public float getExtraTierChance() {
        return extraTierChance;
    }

    @Override
    public ItemStack rollOutput() {
        int outputAmount = getStack().getCount();
        for (int roll = 0; roll < getStack().getCount(); roll++) {
            if(r.nextFloat() > getChance() + extraTierChance) {
                outputAmount--;
            }
        }
        if (outputAmount == 0) return ItemStack.EMPTY;
        ItemStack out = getStack().copy();
        out.setCount(outputAmount);
        return out;
    }

    @Override
    public JsonElement serialize() {
        JsonElement element = super.serialize();
        if(extraTierChance != 0) {
            element.getAsJsonObject().addProperty("extraTierChance", extraTierChance);
        }
        return element;
    }

    public static TieredProcessingOutput deserialize(JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new JsonSyntaxException("TieredProcessingOutput must be a json object");
        }

        JsonObject json = jsonElement.getAsJsonObject();
        String itemId = GsonHelper.getAsString(json, "item");
        int count = GsonHelper.getAsInt(json, "count", 1);
        float chance = GsonHelper.isValidNode(json, "chance") ? GsonHelper.getAsFloat(json, "chance") : 1;
        float extraTierChance = GsonHelper.isValidNode(json, "extraTierChance") ? GsonHelper.getAsFloat(json, "extraTierChance") : 0;
        ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)), count);

        if (GsonHelper.isValidNode(json, "nbt")) {
            try {
                JsonElement element = json.get("nbt");
                stack.setTag(TagParser.parseTag(element.isJsonObject()
                        ? Create.GSON.toJson(element)
                        : GsonHelper.convertToString(element, "nbt")));
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
        }
        return new TieredProcessingOutput(stack, chance, extraTierChance);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        super.write(buf);
        buf.writeFloat(getExtraTierChance());
    }

    public static TieredProcessingOutput read(FriendlyByteBuf buf) {
        return new TieredProcessingOutput(buf.readItem(), buf.readFloat(), buf.readFloat());
    }
}
