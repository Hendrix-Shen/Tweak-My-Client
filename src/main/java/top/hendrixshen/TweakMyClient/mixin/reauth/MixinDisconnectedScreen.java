package top.hendrixshen.TweakMyClient.mixin.reauth;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import technicianlp.reauth.gui.AuthScreen;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.TweakMyClientMixinPlugin;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnect;

@Mixin(value = DisconnectedScreen.class, priority = 899)
public class MixinDisconnectedScreen extends Screen {
    private final String PREFIX = Reference.MOD_ID;

    @Shadow
    @Final
    private Text reason;

    @Shadow
    private int reasonHeight;

    @Shadow
    @Final
    private Screen parent;

    protected MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "TAIL"
            )
    )
    private void onInitDisconnectedScreen(CallbackInfo ci) {
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + reasonHeight / 2 + 9, height - 30);
        if (reason == null || AutoReconnect.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            Configs.Feature.FEATURE_AUTO_RECONNECT.setBooleanValue(false);
            if (TweakMyClientMixinPlugin.isReAuthLoaded) {
                addButton(new ButtonWidget(backButtonX, backButtonY + (TweakMyClientMixinPlugin.isAuthMeLoaded ? 96 : 72), 200, 20,
                        new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.reAuthenticateWithReAuth", PREFIX))), button -> this.client.openScreen(new AuthScreen(parent))));
            }
        }
    }
}