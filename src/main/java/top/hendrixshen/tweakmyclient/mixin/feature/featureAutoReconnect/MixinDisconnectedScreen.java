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
import top.hendrixshen.tweakmyclient.gui.autoReconnect.PatchedDisconnectedScreen;

@Mixin(value = DisconnectedScreen.class, priority = 900)
public class MixinDisconnectedScreen extends Screen {
    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    private Component reason;

    protected MixinDisconnectedScreen(Component component) {
        super(component);
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11904
    private void onInitDisconnectedScreen(@NotNull CallbackInfo ci) {
    //#else
    //$$ private void onInitDisconnectedScreen(@NotNull CallbackInfo ci) {
    //#endif
        TweakMyClient.getMinecraftClient().setScreen(new PatchedDisconnectedScreen(this.parent, this.title, this.reason));
        ci.cancel();
    }
}
