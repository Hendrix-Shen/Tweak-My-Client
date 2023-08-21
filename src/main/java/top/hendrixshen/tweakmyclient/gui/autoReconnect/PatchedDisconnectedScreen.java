package top.hendrixshen.tweakmyclient.gui.autoReconnect;

import com.google.common.collect.Maps;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import top.hendrixshen.magiclib.compat.minecraft.api.client.gui.components.ButtonCompatApi;
import top.hendrixshen.magiclib.compat.minecraft.api.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.util.ReflectUtil;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;
import top.hendrixshen.tweakmyclient.util.StringUtil;

import java.util.LinkedHashMap;

//#if MC > 11904
import net.minecraft.network.chat.CommonComponents;
import org.jetbrains.annotations.NotNull;
//#endif

//#if MC > 11502
//#if MC < 12000
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineLabel;
//#else
//$$ import net.minecraft.client.resources.language.I18n;
//$$ import java.util.List;
//#endif

public class PatchedDisconnectedScreen extends Screen {
    //#if MC > 11502
    private MultiLineLabel message = MultiLineLabel.EMPTY;
    //#else
    //$$ private List<String> lines;
    //#endif
    private final Component reason;
    private final Screen parent;
    private int textHeight;
    private Button autoReconnectButton;
    private int reconnectTimer = Configs.autoReconnectTimer * 20;
    private final LinkedHashMap<String, Screen> modHashMap = Maps.newLinkedHashMap();

    public PatchedDisconnectedScreen(Screen parent, Component title, Component reason) {
        super(title);
        this.reason = reason;
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.initModMap();
        //#if MC > 11502
        this.message = MultiLineLabel.create(this.font, this.reason, this.width - 50);
        this.textHeight = this.message.getLineCount() * 9;
        //#else
        //$$ this.lines = this.font.split(this.reason.getColoredString(), this.width - 50);
        //$$ this.textHeight = this.lines.size() * 9;
        //#endif
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(this.height / 2 + this.textHeight / 2 + 9, this.height - 30);
        Button backButton = ButtonCompatApi.builder(
                ComponentCompatApi.translatable("gui.toMenu"),
                        button -> TweakMyClient.getMinecraftClient().setScreen(this.parent))
                .bounds(backButtonX, backButtonY, 200, 20)
                .build();
        Button staticButton = ButtonCompatApi.builder(
                ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.static")),
                        button -> AutoReconnectUtil.reconnect(this.parent))
                .bounds(backButtonX, backButtonY + 24, 98, 20)
                .build();
        this.autoReconnectButton = ButtonCompatApi.builder(
                ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.toggle")),
                        this::onPressAutoReconnect)
                .bounds(backButtonX + 102, backButtonY + 24, 98, 20)
                .build();
        this.addRenderableWidgetCompat(backButton);
        this.addRenderableWidgetCompat(staticButton);
        this.addRenderableWidgetCompat(this.autoReconnectButton);

        //#if MC > 11902
        if (this.reason == null || AutoReconnectUtil.RE_AUTH_MESSAGES.stream().anyMatch(component -> component.getString().equals(this.reason.getString()))) {
            //#else
            //$$ if (reason == null || AutoReconnectUtil.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            //#endif
            // Auto disable autoReconnect
            TweakMyClientReference.getConfigHandler().configManager.setValue("featureAutoReconnect", false);

            if (this.modHashMap.size() < 1) {
                return;
            }

            int offsetX = 0;
            int buttonWidth = (200 - 4 * (this.modHashMap.size() - 1)) / this.modHashMap.size();

            for (String modId : this.modHashMap.keySet()) {
                this.addRenderableWidgetCompat(ButtonCompatApi.builder(
                                ComponentCompatApi.literal(StringUtil.tr(String.format("message.autoReconnect.authenticate.%s", modId))),
                                button -> TweakMyClient.getMinecraftClient().setScreen(this.modHashMap.get(modId)))
                        .pos(backButtonX + offsetX, 48 + backButtonY)
                        .size(buttonWidth, 20).build());
                offsetX += buttonWidth + 4;
            }
        }
    }

    //#if MC > 11904
    @Override
    public @NotNull Component getNarrationMessage() {
        return CommonComponents.joinForNarration(this.title, this.reason);
    }
    //#endif

    @Override
    //#if MC > 11904
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (Configs.expXiBao) {
            AutoReconnectUtil.renderXibao(this);
        } else {
            this.renderBackground(
                    //#if MC > 12001
                    //$$ guiGraphics, mouseX, mouseY, delta
                    //#else
                    guiGraphics
                    //#endif
            );
        }

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 0xAAAAAA);
        this.message.renderCentered(guiGraphics, this.width / 2, this.height / 2 - this.textHeight / 2);
        super.render(guiGraphics, mouseX, mouseY, delta);
    //#elseif MC > 11502
    //$$ public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
    //$$     if (Configs.expXiBao) {
    //$$         AutoReconnectUtil.renderXibao(this);
    //$$     } else {
    //$$         this.renderBackground(poseStack);
    //$$     }
    //$$
    //$$     GuiComponent.drawCenteredString(poseStack, this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 0xAAAAAA);
    //$$     this.message.renderCentered(poseStack, this.width / 2, this.height / 2 - this.textHeight / 2);
    //$$     super.render(poseStack, mouseX, mouseY, delta);
    //#else
    //$$ public void render(int mouseX, int mouseY, float delta) {
    //$$     if (Configs.expXiBao) {
    //$$         AutoReconnectUtil.renderXibao(this);
    //$$     } else {
    //$$         this.renderBackground();
    //$$     }
    //$$
    //$$     this.drawCenteredString(this.font, this.title.getColoredString(), this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 0xAAAAAA);
    //$$     int k = this.height / 2 - this.textHeight / 2;
    //$$
    //$$     if (this.lines != null) {
    //$$         for(String string : this.lines) {
    //$$             this.drawCenteredString(this.font, string, this.width / 2, k, 0xFFFFFF);
    //$$             k += 9;
    //$$         }
    //$$     }
    //$$
    //#endif
    }

    @Override
    public void tick() {
        if (!Configs.featureAutoReconnect) {
            //#if MC > 11502
            this.autoReconnectButton.setMessage(ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.toggle")));
            //#else
            //$$ this.autoReconnectButton.setMessage(StringUtil.tr("message.autoReconnect.toggle"));
            //#endif
            return;
        }

        //#if MC > 11502
        this.autoReconnectButton.setMessage(ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.timer", (int) Math.ceil(this.reconnectTimer / 20.0))));
        //#else
        //$$ this.autoReconnectButton.setMessage(StringUtil.tr("message.autoReconnect.timer", (int) Math.ceil(this.reconnectTimer / 20.0)));
        //#endif

        if (this.reconnectTimer > 0) {
            this.reconnectTimer--;
            return;
        }

        AutoReconnectUtil.reconnect(parent);
    }

    private void initModMap() {
        if (TweakMyClientReference.isAuthMeLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) ReflectUtil.newInstance("me.axieum.mcmod.authme.impl.gui.AuthMethodScreen", new Class[]{Screen.class}, parent).orElseThrow(RuntimeException::new);
            } catch (RuntimeException e) {
                try {
                    screen = (Screen) ReflectUtil.newInstance("me.axieum.mcmod.authme.impl.AuthMe", 0, (Object) null).orElseThrow(RuntimeException::new);
                } catch (RuntimeException ex) {
                    try {
                        screen = (Screen) ReflectUtil.newInstance("me.axieum.mcmod.authme.gui.AuthScreen", new Class[]{Screen.class}, parent).orElseThrow(RuntimeException::new);
                    } catch (RuntimeException exc) {
                        TweakMyClientReference.getLogger().error("Can't invoke AuthMe Screen");
                    }
                }
            }

            if (screen != null) {
                this.modHashMap.put("authme", screen);
            }
        }

        if (TweakMyClientReference.isInGameAccountSwitcherLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) ReflectUtil.newInstance("the_fireplace.ias.gui.GuiAccountSelector", new Class[]{Screen.class}, parent).orElseThrow(RuntimeException::new);
            } catch (RuntimeException e) {
                try {
                    screen = (Screen) ReflectUtil.newInstance("the_fireplace.ias.gui.AccountListScreen", new Class[]{Screen.class}, parent).orElseThrow(RuntimeException::new);
                } catch (RuntimeException ex) {
                    TweakMyClientReference.getLogger().error("Can't invoke In-Game Account Switcher Screen");
                }
            }

            if (screen != null) {
                this.modHashMap.put("ias", screen);
            }
        }

        if (TweakMyClientReference.isOAuthLoaded) {
            Screen screen = null;

            try {
                // For MC 1.17
                screen = (Screen) ReflectUtil.newInstance("com.sintinium.oauthfabric.gui.profile.ProfileSelectionScreen", 0, (Object) null).orElseThrow(RuntimeException::new);
            } catch (RuntimeException e) {
                try {
                    screen = (Screen) ReflectUtil.newInstance("com.sintinium.oauth.oauthfabric.gui.LoginTypeScreen", new Class[]{Screen.class}, parent).orElseThrow(RuntimeException::new);
                } catch (RuntimeException ex) {
                    TweakMyClientReference.getLogger().error("Can't invoke OAuth Screen");
                }
            }

            if (screen != null) {
                this.modHashMap.put("oauth", screen);
            }
        }

        if (TweakMyClientReference.isReAuthLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) ReflectUtil.newInstance("technicianlp.reauth.gui.AuthScreen", new Class[]{Screen.class}, parent).orElseThrow(RuntimeException::new);
            } catch (RuntimeException e) {
                TweakMyClientReference.getLogger().error("Can't invoke Reauth Screen");
            }

            if (screen != null) {
                this.modHashMap.put("reauth", screen);
            }
        }
    }

    private void onPressAutoReconnect(Button button) {
        TweakMyClientReference.getConfigHandler().configManager.setValue("featureAutoReconnect", !Configs.featureAutoReconnect);

        if (Configs.featureAutoReconnect) {
            this.reconnectTimer = Configs.autoReconnectTimer * 20;
        }
    }
}
