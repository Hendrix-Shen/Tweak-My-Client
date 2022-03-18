package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomWindowIcon;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.fakeInterface.IMinecraft;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMinecraft {
    @Shadow
    @Final
    private Window window;

    @Shadow
    public abstract ResourceManager getResourceManager();

    @Shadow
    public abstract ClientPackSource getClientPackSource();

    @Inject(
            method = "reloadResourcePacks(Z)Ljava/util/concurrent/CompletableFuture;",
            at = @At(
                    value = "RETURN"
            )
    )
    private void afterReloadResourcePacks(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        this.updateIcon();
    }

    private void updateIcon() {
        try {
            InputStream icon16x;
            InputStream icon32x;
            if (Configs.featureCustomWindowIcon.getBooleanValue()) {
                icon16x = this.getResourceManager().getResource(new ResourceLocation(TweakMyClientReference.getModId(), "icons/icon_16x16.png")).getInputStream();
                icon32x = this.getResourceManager().getResource(new ResourceLocation(TweakMyClientReference.getModId(), "icons/icon_32x32.png")).getInputStream();
            } else {
                icon16x = this.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
                icon32x = this.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
            }
            this.window.setIcon(icon16x, icon32x);
        } catch (IOException e) {
            TweakMyClient.getLogger().error("Couldn't set icon", e);
        }
    }

    @Override
    public void refreshIcon() {
        this.updateIcon();
    }
}
