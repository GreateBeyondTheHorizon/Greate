package electrolyte.greate.registry;

import com.mojang.serialization.Codec;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags.AllRecipeSerializerTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.kinetics.fan.processing.TieredHauntingRecipe;
import electrolyte.greate.content.kinetics.fan.processing.TieredSplashingRecipe;
import electrolyte.greate.content.kinetics.millstone.TieredMillingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredBrewingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredCompactingRecipe;
import electrolyte.greate.content.kinetics.mixer.TieredMixingRecipe;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.kinetics.saw.TieredCuttingRecipe;
import electrolyte.greate.content.processing.basin.TieredBasinRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredStandardProcessingRecipe.Factory;
import electrolyte.greate.content.processing.recipe.TieredStandardProcessingRecipe.Serializer;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public enum ModRecipeTypes implements IRecipeTypeInfo, StringRepresentable {

	MILLING(TieredMillingRecipe::new),
	CRUSHING(TieredCrushingRecipe::new),
	PRESSING(TieredPressingRecipe::new),
	BASIN(TieredBasinRecipe::new),
	BREWING(TieredBrewingRecipe::new),
	MIXING(TieredMixingRecipe::new),
	COMPACTING(TieredCompactingRecipe::new),
	CUTTING(TieredCuttingRecipe::new),
	SPLASHING(TieredSplashingRecipe::new),
	HAUNTING(TieredHauntingRecipe::new);

	public final ResourceLocation id;
	public final Supplier<RecipeSerializer<?>> serializerSupplier;
	private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializerObject;
	@Nullable
	private final DeferredHolder<RecipeType<?>, RecipeType<?>> typeObject;
	private final Supplier<RecipeType<?>> type;

	private boolean isProcessingRecipe;
	public static final Codec<ModRecipeTypes> CODEC = StringRepresentable.fromEnum(ModRecipeTypes::values);

	ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
		String name = Lang.asId(name());
		id = Create.asResource(name);
		this.serializerSupplier = serializerSupplier;
		serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
		if (registerType) {
			typeObject = Registers.TYPE_REGISTER.register(name, typeSupplier);
			type = typeObject;
		} else {
			typeObject = null;
			type = typeSupplier;
		}
		isProcessingRecipe = false;
	}

	ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
		String name = Lang.asId(name());
		id = Greate.id(name);
		this.serializerSupplier = serializerSupplier;
		serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
		typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
		type = typeObject;
		isProcessingRecipe = false;
	}

	ModRecipeTypes(Factory<?> processingFactory) {
		this(() -> new Serializer<>(processingFactory));
		isProcessingRecipe = true;
	}

	public static void register(IEventBus modEventBus) {
		ShapedRecipePattern.setCraftingSize(9, 9);
		Registers.SERIALIZER_REGISTER.register(modEventBus);
		Registers.TYPE_REGISTER.register(modEventBus);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeSerializer<?>> T getSerializer() {
		return (T) serializerObject.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() {
		return (RecipeType<R>) type.get();
	}

	public <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> find(I inv, Level world, int tier) {
		Optional<RecipeHolder<R>> recipe = world.getRecipeManager().getRecipeFor(getType(), inv, world);
		if(recipe.isPresent()) {
			if(recipe.get().value() instanceof TieredProcessingRecipe<?, ?> tieredRecipe) {
				if(tieredRecipe.getRecipeTier() <= tier) {
					return recipe;
				}
			}
		}
		return Optional.empty();
	}

	public static boolean shouldIgnoreInAutomation(RecipeHolder<?> recipe) {
		RecipeSerializer<?> serializer = recipe.value().getSerializer();
		if (serializer != null && AllRecipeSerializerTags.AUTOMATION_IGNORE.matches(serializer))
			return true;
		return !AllRecipeTypes.CAN_BE_AUTOMATED.test(recipe);
	}

	@Override
	public @NotNull String getSerializedName() {
		return id.toString();
	}

	private static class Registers {
		private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Greate.MOD_ID);
		private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, Greate.MOD_ID);
	}

}
