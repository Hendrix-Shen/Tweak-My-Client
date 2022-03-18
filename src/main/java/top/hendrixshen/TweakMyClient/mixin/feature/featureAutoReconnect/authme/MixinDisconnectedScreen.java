package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoReconnect.authme;

import me.axieum.mcmod.authme.impl.gui.AuthMethodScreen;
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
import top.hendrixshen.magiclib.api.dependencyValidator.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependencyValidator.annotation.Dependency;
import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

@Dependencies(require = @Dependency(value = "authme", versionPredicates = ">=2.1.0"))
@Mixin(value = DisconnectedScreen.class, priority = 897)
public class MixinDisconnectedScreen extends Screen {
    @Shadow
    private int textHeight;
    @Shadow
    @Final
    private Component reason;
    @Shadow
    @Final
    private Screen parent;

    protected MixinDisconnectedScreen(Component component) {
        super(component);
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "TAIL"
            )
    )
    private void onInitDisconnectedScreen(CallbackInfo ci) {
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + textHeight / 2 + 9, height - 30);
        if (reason == null || AutoReconnectUtil.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            Configs.featureAutoReconnect.setBooleanValue(false);
            addRenderableWidget(new Button(backButtonX, 72 + backButtonY + AutoReconnectUtil.reAuthenticateButtonOffsetY, 200, 20,
                    new TextComponent(I18n.translate(String.format("%s.message.autoReconnect.reAuthenticateWithAuthMe", TweakMyClientReference.getModId()))), button -> {
                assert this.minecraft != null;
                this.minecraft.setScreen(new AuthMethodScreen(parent));
            }));
            AutoReconnectUtil.reAuthenticateButtonOffsetY += 24;
        }
    }
}
