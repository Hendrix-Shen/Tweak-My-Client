package top.hendrixshen.TweakMyClient.mixin.compat.ias;

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
import the_fireplace.ias.gui.GuiAccountSelector;
import top.hendrixshen.TweakMyClient.TweakMyClientReference;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;
import top.hendrixshen.magiclib.untils.dependency.Dependencies;
import top.hendrixshen.magiclib.untils.dependency.Dependency;
import top.hendrixshen.magiclib.untils.language.I18n;

@Dependencies(dependencyList = @Dependency(modid = "ias", version = ">=7.1.3"))
@Mixin(value = DisconnectedScreen.class, priority = 898)
public class MixinDisconnectedScreen extends Screen {
    private final String PREFIX = TweakMyClientReference.getModId();
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
        if (reason == null || AutoReconnectUtils.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            Configs.Feature.FEATURE_AUTO_RECONNECT.setBooleanValue(false);
            addButton(new Button(backButtonX, 72 + backButtonY + AutoReconnectUtils.reAuthenticateButtonOffsetY, 200, 20,
                    new TextComponent(I18n.translate(String.format("%s.message.autoReconnect.reAuthenticateWithInGameAccountSwitcher", PREFIX))), button -> {
                assert this.minecraft != null;
                this.minecraft.setScreen(new GuiAccountSelector(parent));
            }));
            AutoReconnectUtils.reAuthenticateButtonOffsetY += 24;
        }
    }
}
