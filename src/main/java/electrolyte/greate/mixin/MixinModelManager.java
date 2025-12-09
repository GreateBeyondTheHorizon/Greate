package electrolyte.greate.mixin;

import electrolyte.greate.Greate;
import electrolyte.greate.foundation.client.models.BeltConnectorModel;
import electrolyte.greate.foundation.client.models.BeltModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.ModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ModelManager.class)
public class MixinModelManager {

    @Inject(method = "reload", at = @At("HEAD"))
    private void greate_reload(PreparationBarrier pPreparationBarrier, ResourceManager pResourceManager, ProfilerFiller pPreparationsProfiler, ProfilerFiller pReloadProfiler, Executor pBackgroundExecutor, Executor pGameExecutor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if(!ModLoader.isLoadingStateValid()) return;
        long startTime = System.currentTimeMillis();
        BeltModel.reinitModels();
        BeltConnectorModel.reinitModels();
        Greate.LOGGER.info("Greate model loading took {}ms", System.currentTimeMillis() - startTime);
    }
}
