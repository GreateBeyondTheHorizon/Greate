package electrolyte.greate.registry;

import com.google.common.collect.ImmutableSet;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import electrolyte.greate.Greate;
import electrolyte.greate.GreateEnums.TIER;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingRecipe;
import electrolyte.greate.content.kinetics.millstone.TieredMillingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeSerializer;
import io.github.fabricators_of_create.porting_lib.util.ShapedRecipeUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public enum ModRecipeTypes implements IRecipeTypeInfo {

	MILLING(TieredMillingRecipe::new),
	CRUSHING(TieredCrushingRecipe::new);

	private final ResourceLocation id;
	private final RecipeSerializer<?> serializerObject;
	@Nullable
	private final RecipeType<?> typeObject;
	private final Supplier<RecipeType<?>> type;

	ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
		String name = Lang.asId(name());
		id = Create.asResource(name);
		serializerObject = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializerSupplier.get());
		if (registerType) {
			typeObject = typeSupplier.get();
			Registry.register(BuiltInRegistries.RECIPE_TYPE, id, typeObject);
        } else {
			typeObject = null;
        }
        type = typeSupplier;
    }

	ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
		String name = Lang.asId(name());
		id = new ResourceLocation(Greate.MOD_ID, name);
		serializerObject = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializerSupplier.get());
		typeObject = simpleType(id);
		Registry.register(BuiltInRegistries.RECIPE_TYPE, id, typeObject);
		type = () -> typeObject;
	}

	ModRecipeTypes(TieredProcessingRecipeFactory<?> processingFactory) {
		this(() -> new TieredProcessingRecipeSerializer<>(processingFactory));
	}

	public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
		String s = id.toString();
		return new RecipeType<>() {
            @Override
            public String toString() {
                return s;
            }
        };
	}

	public static void register() {
		ShapedRecipeUtil.setCraftingSize(9, 9);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeSerializer<?>> T getSerializer() {
		return (T) serializerObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeType<?>> T getType() {
		return (T) type.get();
	}

	public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world, TIER tier) {
		Optional<T> recipe = world.getRecipeManager().getRecipeFor(getType(), inv, world);
		if(recipe.isPresent()) {
			if(recipe.get() instanceof TieredProcessingRecipe<?> tieredRecipe) {
				if(tieredRecipe.getRecipeTier().compareTo(tier) <= 0) {
					return recipe;
				}
			}
		}
		return Optional.empty();
	}

	public static final Set<ResourceLocation> RECIPE_DENY_SET =
		ImmutableSet.of(new ResourceLocation("occultism", "spirit_trade"), new ResourceLocation("occultism", "ritual"));

	public static boolean shouldIgnoreInAutomation(Recipe<?> recipe) {
		RecipeSerializer<?> serializer = recipe.getSerializer();
		if (serializer != null && RECIPE_DENY_SET.contains(RegisteredObjects.getKeyOrThrow(serializer)))
			return true;
		return recipe.getId()
			.getPath()
			.endsWith("_manual_only");
	}
}
