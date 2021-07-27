package top.hendrixshen.TweakMyClient.mixin.ias;

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
import the_fireplace.ias.gui.GuiAccountSelector;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.TweakMyClientMixinPlugin;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Mixin(value = DisconnectedScreen.class, priority = 898)
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
        if (reason == null || AutoReconnectUtils.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            Configs.Feature.FEATURE_AUTO_RECONNECT.setBooleanValue(false);
            addButton(new ButtonWidget(backButtonX, 72 + backButtonY + AutoReconnectUtils.reAuthenticateButtonOffsetY, 200, 20,
                    new LiteralText(StringUtils.translate(String.format("%s.message.autoReconnect.reAuthenticateWithInGameAccountSwitcher", PREFIX))), button -> {
                assert this.client != null;
                this.client.openScreen(new GuiAccountSelector(parent));
            }));
            AutoReconnectUtils.reAuthenticateButtonOffsetY += 24;
        }
    }
}
