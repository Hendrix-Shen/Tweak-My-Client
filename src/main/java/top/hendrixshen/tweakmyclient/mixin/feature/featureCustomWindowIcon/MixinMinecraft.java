package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomWindowIcon;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
//#if MC >= 11500
import org.spongepowered.asm.mixin.Final;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    //#if MC >= 11500
    @Final
    //#endif
    @Shadow
    //#if MC >= 11500
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

    private void updateIcon() {
        CustomWindowUtil.updateIcon();
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
    //$$     CustomWindowUtil.updateIcon();
    //$$ }
    //#endif
}
