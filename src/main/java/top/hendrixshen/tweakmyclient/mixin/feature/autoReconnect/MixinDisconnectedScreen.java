package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.PatchedDisconnectedScreen;

@Mixin(value = DisconnectedScreen.class, priority = 900)
public abstract class MixinDisconnectedScreen extends Screen {
    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    private Component reason;

    protected MixinDisconnectedScreen(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void onInitDisconnectedScreen(@NotNull CallbackInfo ci) {
        Minecraft.getInstance().setScreen(new PatchedDisconnectedScreen(this.parent, this.title, this.reason));
        ci.cancel();
    }
}
