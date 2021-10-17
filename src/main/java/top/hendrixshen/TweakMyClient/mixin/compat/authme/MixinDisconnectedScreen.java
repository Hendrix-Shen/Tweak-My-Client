package top.hendrixshen.TweakMyClient.mixin.compat.authme;

import MagicLib.untils.mixin.Dependencies;
import MagicLib.untils.mixin.Dependency;
import fi.dy.masa.malilib.util.StringUtils;
import me.axieum.mcmod.authme.gui.AuthScreen;
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
import top.hendrixshen.TweakMyClient.TweakMyClientReference;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Dependencies(dependencyList = @Dependency(modid = "reauth", version = "*"))
@Mixin(value = DisconnectedScreen.class, priority = 897)
public class MixinDisconnectedScreen extends Screen {
    private final String PREFIX = TweakMyClientReference.getModId();

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
        if (reason == null || AutoReconnectUtils.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            Configs.Feature.FEATURE_AUTO_RECONNECT.setBooleanValue(false);
            addButton(new ButtonWidget(backButtonX, 72 + backButtonY + AutoReconnectUtils.reAuthenticateButtonOffsetY, 200, 20,
                    new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.reAuthenticateWithAuthMe", PREFIX))), button -> {
                assert this.client != null;
                this.client.openScreen(new AuthScreen(parent));
            }));
            AutoReconnectUtils.reAuthenticateButtonOffsetY += 24;
        }
    }
}