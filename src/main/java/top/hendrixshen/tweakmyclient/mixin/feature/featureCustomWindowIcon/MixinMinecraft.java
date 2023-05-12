package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomWindowIcon;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.IconUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

//#if MC > 11404
import org.spongepowered.asm.mixin.Final;
//#else
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#endif

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    //#if MC > 11404
    @Final
    //#endif
    @Shadow
    //#if MC > 11404
    private Window window;
    //#else
    //$$ public Window window;
    //#endif

    @Inject(
            method = "reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;",
            at = @At(
                    value = "RETURN"
            )
    )
    private void afterReloadResourcePacks(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        this.updateIcon();
    }

    //#if MC < 11500
    //$$ @Inject(
    //$$         method = "run",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/Minecraft;init()V",
    //$$                 shift = At.Shift.AFTER
    //$$         )
    //$$ )
    //$$ private void afterInit(CallbackInfo ci) {
    //$$     this.updateIcon();
    //$$ }
    //#endif

    private void updateIcon() {
        if (Configs.featureCustomWindowIcon) {
            IconUtil.updateIcon();
        }
    }
}
