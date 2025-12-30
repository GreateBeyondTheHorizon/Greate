package electrolyte.greate.foundation.data;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GreateRegistrate extends GTRegistrate {

    @Nullable
	protected Function<Item, TooltipModifier> currentTooltipModifierFactory;

    protected GreateRegistrate(String modId) {
        super(modId);
    }

    public static GreateRegistrate create(String modId) {
        return new GreateRegistrate(modId);
    }

    public GreateRegistrate setTooltipModifierFactory(Function<Item, TooltipModifier> factory) {
		currentTooltipModifierFactory = factory;
		return this;
    }

    @Override
	protected <R, T extends R> RegistryEntry<T> accept(String name, ResourceKey<? extends Registry<R>> type,
													   Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator,
													   NonNullFunction<RegistryObject<T>, ? extends RegistryEntry<T>> entryFactory) {
		RegistryEntry<T> entry = super.accept(name, type, builder, creator, entryFactory);
		if (type.equals(Registries.ITEM) && currentTooltipModifierFactory != null) {
			Function<Item, TooltipModifier> factory = currentTooltipModifierFactory;
			this.addRegisterCallback(name, Registries.ITEM, item -> {
				TooltipModifier modifier = factory.apply(item);
				TooltipModifier.REGISTRY.register(item, modifier);
			});
		}
		return entry;
	}
}
