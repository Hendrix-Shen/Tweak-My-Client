package top.hendrixshen.TweakMyClient.mixin;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.TweakMyClientReference;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Mixin(value = DisconnectedScreen.class, priority = 900)
public class MixinDisconnectedScreen extends Screen {
    private final String PREFIX = TweakMyClientReference.getModId();
    @Shadow
    @Final
    private Screen parent;
    @Shadow
    private int textHeight;
    private Button autoReconnectButton;

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
        if (Configs.Feature.FEATURE_AUTO_RECONNECT.getBooleanValue()) {
            AutoReconnectUtils.ReconnectTimer = Configs.Generic.AUTO_RECONNECT_TIMER.getIntegerValue() * 20;
        }
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + textHeight / 2 + 9, height - 30);

        addButton(new Button(backButtonX, backButtonY + 24, 200, 20,
                new TextComponent(StringUtils.translate(String.format("%s.message.autoReconnect.static", PREFIX))), b -> AutoReconnectUtils.reconnect(parent)));

        autoReconnectButton =
                addButton(new Button(backButtonX, backButtonY + 48, 200, 20,
                        new TextComponent(StringUtils.translate(String.format("%s.message.autoReconnect.toggle", PREFIX))), b -> onPressAutoReconnect()));
        AutoReconnectUtils.reAuthenticateButtonOffsetY = 0;
        ci.cancel();
    }

    private void onPressAutoReconnect() {
        ConfigBooleanHotkeyed featureAutoReconnect = Configs.Feature.FEATURE_AUTO_RECONNECT;
        featureAutoReconnect.setBooleanValue(!featureAutoReconnect.getBooleanValue());

        if (featureAutoReconnect.getBooleanValue()) {
            AutoReconnectUtils.ReconnectTimer = Configs.Generic.AUTO_RECONNECT_TIMER.getIntegerValue() * 20;
        }
    }

    @Override
    public void tick() {
        if (!Configs.Feature.FEATURE_AUTO_RECONNECT.getBooleanValue()) {
            autoReconnectButton.setMessage(new TextComponent(StringUtils.translate(String.format("%s.message.autoReconnect.toggle", PREFIX))));
            return;
        }
        autoReconnectButton.setMessage(new TextComponent(StringUtils.translate(String.format("%s.message.autoReconnect.timer", PREFIX), (int) Math.ceil(AutoReconnectUtils.ReconnectTimer / 20.0))));

        if (AutoReconnectUtils.ReconnectTimer > 0) {
            AutoReconnectUtils.ReconnectTimer--;
            return;
        }
        AutoReconnectUtils.reconnect(parent);
    }
}
