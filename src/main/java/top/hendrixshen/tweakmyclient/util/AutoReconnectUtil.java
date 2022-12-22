package top.hendrixshen.tweakmyclient.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import top.hendrixshen.magiclib.compat.minecraft.blaze3d.vertex.VertexFormatCompatApi;
import top.hendrixshen.magiclib.compat.minecraft.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.util.ReflectUtil;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.fakeInterface.IScreen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoReconnectUtil {
    private static final ResourceLocation resourceLocation = new ResourceLocation(TweakMyClientReference.getModId(), "texture/gui/xibao.png");
    public static int ReconnectTimer;
    public static int reAuthenticateButtonOffsetY;
    private static boolean initialized = false;
    private static ServerData lastServer;
    private static Button autoReconnectButton;
    //#if MC >= 11903
    private static final List<Component> reAuthMessages = Lists.newArrayList(
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.insufficientPrivileges")).plainCopy(),
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.invalidSession")).plainCopy(),
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.serversUnavailable")).plainCopy(),
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.userBanned")).plainCopy());
    //#endif

    private final static AutoReconnectUtil INSTANCE = new AutoReconnectUtil();

    public static AutoReconnectUtil getInstance() {
        return AutoReconnectUtil.INSTANCE;
    }

    private static final LinkedHashMap<String, Screen> modHashMap = Maps.newLinkedHashMap();

    //#if MC < 11700
    //$$ @SuppressWarnings("deprecation")
    //#endif
    public static void renderXibao(Screen screen) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        //#if MC > 11605
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, AutoReconnectUtil.resourceLocation);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //#else
        //$$ TweakMyClient.getMinecraftClient().getTextureManager().bind(AutoReconnectUtil.resourceLocation);
        //$$ RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //#endif
        bufferbuilder.begin(VertexFormatCompatApi.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, screen.height, 0.0D).uv(0F, 1F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(screen.width, screen.height, 0.0D).uv(1F, 1F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(screen.width, 0.0D, 0.0D).uv(1F, 0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0F, 0F).color(255, 255, 255, 255).endVertex();
        tesselator.end();
    }

    private void init(Screen parent) {
        if (TweakMyClientReference.isAuthMeLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) ReflectUtil.newInstance("me.axieum.mcmod.authme.impl.gui.AuthMethodScreen", new Class[]{Screen.class}, parent);
            } catch (RuntimeException e) {
                try {
                    screen = (Screen) ReflectUtil.newInstance("me.axieum.mcmod.authme.impl.AuthMe", 0, (Object) null);
                } catch (RuntimeException ex) {
                    try {
                        screen = (Screen) ReflectUtil.newInstance("me.axieum.mcmod.authme.gui.AuthScreen", new Class[]{Screen.class}, parent);
                    } catch (RuntimeException exc) {
                        TweakMyClient.getLogger().error("Can't invoke AuthMe Screen");
                    }
                }
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("authme", screen);
            }
        }

        if (TweakMyClientReference.isInGameAccountSwitcherLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) ReflectUtil.newInstance("the_fireplace.ias.gui.GuiAccountSelector", new Class[]{Screen.class}, parent);
            } catch (RuntimeException e) {
                try {
                    screen = (Screen) ReflectUtil.newInstance("the_fireplace.ias.gui.AccountListScreen", new Class[]{Screen.class}, parent);
                } catch (RuntimeException ex) {
                    TweakMyClient.getLogger().error("Can't invoke In-Game Account Switcher Screen");
                }
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("ias", screen);
            }
        }

        if (TweakMyClientReference.isOAuthLoaded) {
            Screen screen = null;

            try {
                // For MC 1.17
                screen = (Screen) ReflectUtil.newInstance("com.sintinium.oauthfabric.gui.profile.ProfileSelectionScreen", 0, (Object) null);
            } catch (RuntimeException e) {
                try {
                    screen = (Screen) ReflectUtil.newInstance("com.sintinium.oauth.oauthfabric.gui.LoginTypeScreen", new Class[]{Screen.class}, parent);
                } catch (RuntimeException ex) {
                    TweakMyClient.getLogger().error("Can't invoke OAuth Screen");
                }
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("oauth", screen);
            }
        }

        if (TweakMyClientReference.isReAuthLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) ReflectUtil.newInstance("technicianlp.reauth.gui.AuthScreen", new Class[]{Screen.class}, parent);
            } catch (RuntimeException e) {
                TweakMyClient.getLogger().error("Can't invoke Reauth Screen");
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("reauth", screen);
            }
        }

        AutoReconnectUtil.initialized = true;
    }

    public static ServerData getLastServer() {
        return AutoReconnectUtil.lastServer;
    }

    public static void setLastServer(ServerData serverInfo) {
        AutoReconnectUtil.lastServer = serverInfo;
    }

    public static void reconnect(Screen screen) {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        ServerData serverInfo = AutoReconnectUtil.getLastServer();
        if (AutoReconnectUtil.lastServer != null) {
            //#if MC >= 11700
            ConnectScreen.startConnecting(screen, minecraft, ServerAddress.parseString(serverInfo.ip), serverInfo);
            //#else
            //$$ minecraft.setScreen(new ConnectScreen(screen, minecraft, serverInfo));
            //#endif
        }
    }

    public static String getTranslationKey(Component component) {
        return component instanceof TranslatableContents ? ((TranslatableContents) component).getKey() : "";
    }

    public void initDisconnectedScreen(Screen current, Screen parent, int width, int height, int textHeight, Component reason) {
        if (!AutoReconnectUtil.initialized) {
            this.init(parent);
        }

        if (Configs.featureAutoReconnect) {
            AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer * 20;
        }
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + textHeight / 2 + 9, height - 30);

        //#if MC >= 11903
        ((IScreen) current).tmc$addButton(
                Button.builder(ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.static")),
                        button -> AutoReconnectUtil.reconnect(parent)).pos(backButtonX, backButtonY + 24).size(98, 20).build());

        AutoReconnectUtil.autoReconnectButton = ((IScreen) current).tmc$addButton(
                Button.builder(ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.toggle")),
                        AutoReconnectUtil::onPressAutoReconnect).pos(backButtonX + 102, backButtonY + 24).size(98, 20).build());

        //#elseif MC >= 11600
        //$$ ((IScreen) current).tmc$addButton(new Button(backButtonX, backButtonY + 24, 98, 20,
        //$$         ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.static")), button -> AutoReconnectUtil.reconnect(parent)));
        //$$ AutoReconnectUtil.autoReconnectButton = ((IScreen) current).tmc$addButton(new Button(backButtonX + 102, backButtonY + 24, 98, 20,
        //$$         ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.toggle")), AutoReconnectUtil::onPressAutoReconnect));
        //#else
        //$$ ((IScreen) current).tmc$addButton(new Button(backButtonX, backButtonY + 24, 98, 20,
        //$$         StringUtil.tr("message.autoReconnect.static"), button -> AutoReconnectUtil.reconnect(parent)));
        //$$ AutoReconnectUtil.autoReconnectButton = ((IScreen) current).tmc$addButton(new Button(backButtonX + 102, backButtonY + 24, 98, 20,
        //$$         StringUtil.tr("message.autoReconnect.toggle"), AutoReconnectUtil::onPressAutoReconnect));
        //#endif

        AutoReconnectUtil.reAuthenticateButtonOffsetY = 0;


        //#if MC >= 11903
        if (reason == null || AutoReconnectUtil.reAuthMessages.stream().anyMatch(component -> component.getString().equals(reason.getString()))) {
        //#else
        //$$ if (reason == null || AutoReconnectUtil.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
        //#endif
            // Auto disable autoReconnect
            TweakMyClientReference.getConfigHandler().configManager.setValue("featureAutoReconnect", false);

            if (AutoReconnectUtil.modHashMap.size() < 1) {
                return;
            }

            AtomicInteger offsetX = new AtomicInteger();
            int buttonWidth = (200 - 4 * (AutoReconnectUtil.modHashMap.size() - 1)) / AutoReconnectUtil.modHashMap.size();

            AutoReconnectUtil.modHashMap.forEach(
                    (modId, screen) -> {
                        //#if MC >= 11903
                        ((IScreen) current).tmc$addButton(
                                Button.builder(ComponentCompatApi.literal(StringUtil.tr(String.format("message.autoReconnect.authenticate.%s", modId))),
                                button -> TweakMyClient.getMinecraftClient().setScreen(screen))
                                .pos(backButtonX + offsetX.intValue(), 48 + backButtonY)
                                .size(buttonWidth, 20).build());
                        //#else
                        //$$ ((IScreen) current).tmc$addButton(new Button(backButtonX + offsetX.intValue(),
                        //$$         48 + backButtonY,
                        //$$         buttonWidth,
                        //$$         20,
                        //#if MC >= 11600
                        //$$         ComponentCompatApi.literal(StringUtil.tr(String.format("message.autoReconnect.authenticate.%s", modId))),
                        //#else
                        //$$         StringUtil.tr(String.format("message.autoReconnect.authenticate.%s", modId)),
                        //#endif
                        //$$         b -> TweakMyClient.getMinecraftClient().setScreen(screen)));
                        //$$ offsetX.getAndAdd(buttonWidth + 4);
                        //#endif
                    }
            );
        }
    }

    private static void onPressAutoReconnect(Button button) {
        TweakMyClientReference.getConfigHandler().configManager.setValue("featureAutoReconnect", !Configs.featureAutoReconnect);

        if (Configs.featureAutoReconnect) {
            AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer * 20;
        }
    }

    public static void tickAutoReconnectButton(Screen parent) {
        if (!Configs.featureAutoReconnect) {
            //#if MC >= 11600
            AutoReconnectUtil.autoReconnectButton.setMessage(ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.toggle")));
            //#else
            //$$ AutoReconnectUtil.autoReconnectButton.setMessage(StringUtil.tr("message.autoReconnect.toggle"));
            //#endif
            return;
        }

        //#if MC >= 11600
        AutoReconnectUtil.autoReconnectButton.setMessage(ComponentCompatApi.literal(StringUtil.tr("message.autoReconnect.timer", (int) Math.ceil(AutoReconnectUtil.ReconnectTimer / 20.0))));
        //#else
        //$$ AutoReconnectUtil.autoReconnectButton.setMessage(StringUtil.tr("message.autoReconnect.timer", (int) Math.ceil(AutoReconnectUtil.ReconnectTimer / 20.0)));
        //#endif

        if (AutoReconnectUtil.ReconnectTimer > 0) {
            AutoReconnectUtil.ReconnectTimer--;
            return;
        }

        AutoReconnectUtil.reconnect(parent);
    }
}
