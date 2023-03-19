package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoReconnect;

import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientPredicate;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

//#if MC > 11903
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(value = DisconnectedScreen.class, priority = 900)
public class MixinDisconnectedScreen extends Screen {
    @Shadow
    @Final
    private Screen parent;

    @Shadow
    private int textHeight;

    @Shadow
    @Final
    private Component reason;

    protected MixinDisconnectedScreen(Component component) {
        super(component);
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "TAIL"
            ),
            cancellable = true
    )
    private void onInitDisconnectedScreen(@NotNull CallbackInfo ci) {
        AutoReconnectUtil.getInstance().initDisconnectedScreen(this, this.parent, this.width, this.height, this.textHeight, this.reason);
        ci.cancel();
    }

    @Dynamic
    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference", "target"})
    @Inject(
            method = "tick()V",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        AutoReconnectUtil.tickAutoReconnectButton(this.parent);
    }

    @Dynamic
    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference", "target"})
    @Inject(
            method = "renderDirtBackground(I)V",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderDirtBackground(CallbackInfo ci) {
        if (Configs.expXiBao && TweakMyClientPredicate.xibaoLang.contains(TweakMyClient.getMinecraftClient().options.languageCode)) {
            AutoReconnectUtil.renderXibao(this);
            ci.cancel();
        }
    }

    @Intrinsic
    @Override
    public void tick() {
        super.tick();
    }

    @Intrinsic
    @Override
    //#if MC > 11903
    public void renderDirtBackground(@NotNull PoseStack poseStack) {
        super.renderDirtBackground(poseStack);
    //#else
    //$$ public void renderDirtBackground(int i) {
    //$$     super.renderDirtBackground(i);
    //#endif
    }
}
