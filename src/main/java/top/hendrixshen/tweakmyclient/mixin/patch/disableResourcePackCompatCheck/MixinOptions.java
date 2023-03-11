package top.hendrixshen.tweakmyclient.mixin.patch.disableResourcePackCompatCheck;

import net.minecraft.client.Options;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.util.Iterator;
import java.util.Set;

@Mixin(Options.class)
public class MixinOptions {
    @Inject(
            method = "loadSelectedResourcePacks",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Iterator;remove()V",
                    ordinal = 1,
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onRemovePack(PackRepository packRepository, CallbackInfo ci, @NotNull Set<String> set, Iterator<String> iterator, String string, @NotNull Pack pack) {
        // We forcefully prevent the removal of incompatible resource packages here
        // without doing any configuration checks, because at this point Malilib
        // has not yet loaded the configuration file.
        set.add(pack.getId());
        TweakMyClient.getLogger().warn("Prevented the removal of incompatible resource pack {} from options", iterator);
    }
}
