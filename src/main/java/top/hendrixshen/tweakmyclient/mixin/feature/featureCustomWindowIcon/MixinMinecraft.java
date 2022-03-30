package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomWindowIcon;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.fakeInterface.IMinecraft;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMinecraft {
    @Shadow @Final private Window window;

    @Inject(
            method = "reloadResourcePacks",
            at = @At(
                    value = "RETURN"
            )
    )
    private void afterReloadResourcePacks(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        this.updateIcon();
    }

    private void updateIcon() {
        CustomWindowUtil.updateIcon(this.getWindow());
    }

    @Override
    public Window getWindow() {
        return this.window;
    }
}
