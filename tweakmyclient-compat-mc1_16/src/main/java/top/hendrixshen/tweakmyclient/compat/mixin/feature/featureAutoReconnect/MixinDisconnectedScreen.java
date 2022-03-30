package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureAutoReconnect;

import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

@Mixin(value = DisconnectedScreen.class, priority = 900)
public class MixinDisconnectedScreen extends Screen {
    @Shadow
    @Final
    private Screen parent;

    @Shadow
    private int textHeight;

    @Shadow @Final private Component reason;

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
    private void onInitDisconnectedScreen(CallbackInfo ci) {
        AutoReconnectUtil.getInstance().initDisconnectedScreen(this, parent, width, height, textHeight, reason);
        ci.cancel();
    }

    @Override
    public void tick() {
        AutoReconnectUtil.tickAutoReconnectButton(parent);
    }
}
