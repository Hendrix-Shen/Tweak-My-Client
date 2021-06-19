package top.hendrixshen.TweakMyClient.mixin;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Mixin(value = DisconnectedScreen.class, priority = 900)
public class MixinDisconnectedScreen extends Screen {
    private final String PREFIX = Reference.MOD_ID;
    @Shadow
    private int reasonHeight;
    @Shadow
    @Final
    private Screen parent;
    @Shadow
    @Final
    private Text reason;
    private ButtonWidget autoReconnectButton;

    protected MixinDisconnectedScreen(Text title) {
        super(title);
    }

    private static String getTranslationKey(Text component) {
        return component instanceof TranslatableText ? ((TranslatableText) component).getKey() : "";
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
        int backButtonY = Math.min(height / 2 + reasonHeight / 2 + 9, height - 30);

        addDrawableChild(new ButtonWidget(backButtonX, backButtonY + 24, 200, 20,
                new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.static", PREFIX))), b -> AutoReconnectUtils.reconnect(parent)));

        autoReconnectButton =
                addDrawableChild(new ButtonWidget(backButtonX, backButtonY + 48, 200, 20,
                        new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.toggle", PREFIX))), b -> onPressAutoReconnect()));
        if (reason == null || getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            Configs.Feature.FEATURE_AUTO_RECONNECT.setBooleanValue(false);
        }
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
            autoReconnectButton.setMessage(new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.toggle", PREFIX))));
            return;
        }
        autoReconnectButton.setMessage(new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.timer", PREFIX), (int) Math.ceil(AutoReconnectUtils.ReconnectTimer / 20.0))));

        if (AutoReconnectUtils.ReconnectTimer > 0) {
            AutoReconnectUtils.ReconnectTimer--;
            return;
        }
        AutoReconnectUtils.reconnect(parent);
    }
}
