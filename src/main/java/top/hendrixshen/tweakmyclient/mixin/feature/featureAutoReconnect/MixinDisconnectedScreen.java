package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoReconnect;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
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
import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

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
        if (Configs.featureAutoReconnect.getBooleanValue()) {
            AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer.getIntegerValue() * 20;
        }
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + textHeight / 2 + 9, height - 30);

        addButton(new Button(backButtonX, backButtonY + 24, 200, 20,
                new TextComponent(I18n.translate(String.format("%s.message.autoReconnect.static", PREFIX))), b -> AutoReconnectUtil.reconnect(parent)));

        autoReconnectButton =
                addButton(new Button(backButtonX, backButtonY + 48, 200, 20,
                        new TextComponent(I18n.translate(String.format("%s.message.autoReconnect.toggle", PREFIX))), b -> onPressAutoReconnect()));
        AutoReconnectUtil.reAuthenticateButtonOffsetY = 0;
        ci.cancel();
    }

    private void onPressAutoReconnect() {
        ConfigBooleanHotkeyed featureAutoReconnect = Configs.featureAutoReconnect;
        featureAutoReconnect.setBooleanValue(!featureAutoReconnect.getBooleanValue());

        if (featureAutoReconnect.getBooleanValue()) {
            AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer.getIntegerValue() * 20;
        }
    }

    @Override
    public void tick() {
        if (!Configs.featureAutoReconnect.getBooleanValue()) {
            autoReconnectButton.setMessage(new TextComponent(I18n.translate(String.format("%s.message.autoReconnect.toggle", PREFIX))));
            return;
        }
        autoReconnectButton.setMessage(new TextComponent(I18n.translate(String.format("%s.message.autoReconnect.timer", PREFIX), (int) Math.ceil(AutoReconnectUtil.ReconnectTimer / 20.0))));

        if (AutoReconnectUtil.ReconnectTimer > 0) {
            AutoReconnectUtil.ReconnectTimer--;
            return;
        }
        AutoReconnectUtil.reconnect(parent);
    }
}
