package top.hendrixshen.tweakmyclient.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.fakeInterface.IScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoReconnectUtil {
    public static int ReconnectTimer;
    public static int reAuthenticateButtonOffsetY;
    private static boolean initialized = false;
    private static ServerData lastServer;
    private static Button autoReconnectButton;
    private final static AutoReconnectUtil INSTANCE = new AutoReconnectUtil();

    public static AutoReconnectUtil getInstance() {
        return AutoReconnectUtil.INSTANCE;
    }

    private static int compatReAuthenticateMods = 0;

    private static final LinkedHashMap<String, Screen> modHashMap = Maps.newLinkedHashMap();

    private void init(Screen parent) {
        if (TweakMyClientReference.isAuthMeLoaded) {
            Screen screen = null;

            try {
                // AuthMe 2.2.0
                screen = (Screen) Class.forName("me.axieum.mcmod.authme.impl.gui.AuthMethodScreen").getDeclaredConstructor(Screen.class).newInstance(parent);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                try {
                    // AuthMe 2.1.0
                    screen = (Screen) Class.forName("me.axieum.mcmod.authme.impl.AuthMe").getDeclaredConstructor().newInstance(); // 2.1.0
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException ex) {
                    try {
                        // AuthMe for mc 114
                        screen = (Screen) Class.forName("me.axieum.mcmod.authme.gui.AuthScreen").getDeclaredConstructor(Screen.class).newInstance(parent);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException exc) {
                        TweakMyClient.getLogger().error("Can't invoke Authme Screen");
                    }
                }
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("authme", screen);
                AutoReconnectUtil.compatReAuthenticateMods++;
            }
        }

        if (TweakMyClientReference.isInGameAccountSwitcherLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) Class.forName("the_fireplace.ias.gui.GuiAccountSelector").getDeclaredConstructor(Screen.class).newInstance(parent);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                TweakMyClient.getLogger().error("Can't invoke In-Game Account Switcher Screen");
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("ias", screen);
                AutoReconnectUtil.compatReAuthenticateMods++;
            }
        }

        if (TweakMyClientReference.isOauthLoaded) {
            Screen screen = null;

            try {
                // For MC 1.17
                screen = (Screen) Class.forName("com.sintinium.oauthfabric.gui.profile.ProfileSelectionScreen").getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                try {
                    // For MC 1.16 & 1.18
                    screen = (Screen) Class.forName("com.sintinium.oauth.oauthfabric.gui.LoginTypeScreen").getDeclaredConstructor(Screen.class).newInstance(parent);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                    TweakMyClient.getLogger().error("Can't invoke OAuth Screen");
                }
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("oauth", screen);
                AutoReconnectUtil.compatReAuthenticateMods++;
            }
        }

        if (TweakMyClientReference.isReAuthLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) Class.forName("technicianlp.reauth.gui.AuthScreen").getDeclaredConstructor(Screen.class).newInstance(parent);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                TweakMyClient.getLogger().error("Can't invoke Reauth Screen");
            }

            if (screen != null) {
                AutoReconnectUtil.modHashMap.put("reauth", screen);
                AutoReconnectUtil.compatReAuthenticateMods++;
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
        return component instanceof TranslatableComponent ? ((TranslatableComponent) component).getKey() : "";
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

        //#if MC >= 11600
        ((IScreen) current).tmc$addButton(new Button(backButtonX, backButtonY + 24, 98, 20,
                new TextComponent(StringUtil.tr("message.autoReconnect.static")), button -> AutoReconnectUtil.reconnect(parent)));
        AutoReconnectUtil.autoReconnectButton = ((IScreen) current).tmc$addButton(new Button(backButtonX + 102, backButtonY + 24, 98, 20,
                new TextComponent(StringUtil.tr("message.autoReconnect.toggle")), AutoReconnectUtil::onPressAutoReconnect));
        //#else
        //$$ ((IScreen) current).tmc$addButton(new Button(backButtonX, backButtonY + 24, 98, 20,
        //$$         StringUtil.tr("message.autoReconnect.static"), button -> AutoReconnectUtil.reconnect(parent)));
        //$$ AutoReconnectUtil.autoReconnectButton = ((IScreen) current).tmc$addButton(new Button(backButtonX + 102, backButtonY + 24, 98, 20,
        //$$         StringUtil.tr("message.autoReconnect.toggle"), AutoReconnectUtil::onPressAutoReconnect));
        //#endif

        AutoReconnectUtil.reAuthenticateButtonOffsetY = 0;

        if (reason == null || AutoReconnectUtil.getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            // Auto disable autoReconnect
            TweakMyClientReference.getConfigHandler().configManager.setValue("featureAutoReconnect", false);

            AtomicInteger offsetX = new AtomicInteger();
            int buttonWidth = (200 - 4 * (AutoReconnectUtil.compatReAuthenticateMods - 1)) / AutoReconnectUtil.compatReAuthenticateMods;

            AutoReconnectUtil.modHashMap.forEach(
                    (modId, screen) -> {
                        ((IScreen) current).tmc$addButton(new Button(backButtonX + offsetX.intValue(),
                                        48 + backButtonY,
                                        buttonWidth,
                                        20,
                                        //#if MC >= 11600
                                        new TextComponent(StringUtil.tr(String.format("message.autoReconnect.authenticate.%s", modId))),
                                        //#else
                                        //$$ StringUtil.tr(String.format("message.autoReconnect.authenticate.%s", modId)),
                                        //#endif
                                        b -> TweakMyClient.getMinecraftClient().setScreen(screen)));
                        offsetX.getAndAdd(buttonWidth + 4);
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
            AutoReconnectUtil.autoReconnectButton.setMessage(new TextComponent(StringUtil.tr("message.autoReconnect.toggle")));
            //#else
            //$$ AutoReconnectUtil.autoReconnectButton.setMessage(StringUtil.tr("message.autoReconnect.toggle"));
            //#endif
            return;
        }

        //#if MC >= 11600
        AutoReconnectUtil.autoReconnectButton.setMessage(new TextComponent(StringUtil.tr("message.autoReconnect.timer", (int) Math.ceil(AutoReconnectUtil.ReconnectTimer / 20.0))));
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
