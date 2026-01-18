package electrolyte.greate.compat.createfactorylogistics;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import ru.zznty.create_factory_logistics.logistics.composite.CompositePackageItem;

public class CreateFactoryLogisticsCompat {

    public static ItemStackHandler getPackageContents(ItemStack packageStack) {
        return CompositePackageItem.getContents(packageStack);
    }

    public static boolean isCompositePackage(ItemStack packageStack) {
        return packageStack.getItem() instanceof CompositePackageItem;
    }
}
