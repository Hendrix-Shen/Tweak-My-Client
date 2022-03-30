package top.hendrixshen.tweakmyclient.util;

import com.google.common.collect.Maps;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.compat.proxy.screen.ConnectionCompatScreenApi;
import top.hendrixshen.tweakmyclient.compat.proxy.screen.ScreenCompatApi;
import top.hendrixshen.tweakmyclient.config.Configs;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoReconnectUtil {
    public static int ReconnectTimer;
    public static int reAuthenticateButtonOffsetY;
    private static boolean initialized = false;
    private static ServerData lastServer;
    private static Button autoReconnectButton;
    private static AutoReconnectUtil INSTANCE;

    public static AutoReconnectUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AutoReconnectUtil();
        }
        return INSTANCE;
    }

    private static int compatReAuthenticateMods = 0;

    private static final LinkedHashMap<String, ReAuthenticateMod> modHashMap = Maps.newLinkedHashMap();

    private void init(Screen parent) {
        if (TweakMyClientReference.isAuthMeLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) Class.forName("me.axieum.mcmod.authme.impl.gui.AuthMethodScreen").getDeclaredConstructor(Screen.class).newInstance(parent); // 2.2.0
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                try {
                    screen = (Screen) Class.forName("me.axieum.mcmod.authme.impl.AuthMe").getDeclaredConstructor().newInstance(); // 2.1.0
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException ex) {
                    try {
                        screen = (Screen) Class.forName("me.axieum.mcmod.authme.gui.AuthScreen").getDeclaredConstructor(Screen.class).newInstance(parent); // For MC 1.14
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException exc) {
                        TweakMyClient.getLogger().error("Can't invoke Authme Screen");
                    }
                }
            }

            if (screen != null) {
                compatReAuthenticateMods++;
            }

            modHashMap.put("authme", new ReAuthenticateMod(screen, screen == null));
        }

        if (TweakMyClientReference.isInGameAccountSwitcherLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) Class.forName("the_fireplace.ias.gui.GuiAccountSelector").getDeclaredConstructor(Screen.class).newInstance(parent);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                TweakMyClient.getLogger().error("Can't invoke In-Game Account Switcher Screen");
            }

            if (screen != null) {
                compatReAuthenticateMods++;
            }

            modHashMap.put("ias", new ReAuthenticateMod(screen, screen == null));
        }

        if (TweakMyClientReference.isReAuthLoaded) {
            Screen screen = null;

            try {
                screen = (Screen) Class.forName("technicianlp.reauth.gui.AuthScreen").getDeclaredConstructor(Screen.class).newInstance(parent);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                TweakMyClient.getLogger().error("Can't invoke Reauth Screen");
            }

            if (screen != null) {
                compatReAuthenticateMods++;
            }

            modHashMap.put("reauth", new ReAuthenticateMod(screen, screen == null));
        }
        initialized = true;
    }

    public static ServerData getLastServer() {
        return lastServer;
    }

    public static void setLastServer(ServerData serverInfo) {
        lastServer = serverInfo;
    }

    public static void reconnect(Screen screen) {
        ServerData serverInfo = AutoReconnectUtil.getLastServer();
        if (lastServer != null) {
            ConnectionCompatScreenApi.getInstance().startConnect(screen, TweakMyClient.getMinecraftClient(), serverInfo);
        }
    }

    public static String getTranslationKey(Component component) {
        return component instanceof TranslatableComponent ? ((TranslatableComponent) component).getKey() : "";
    }

    public void initDisconnectedScreen(Screen current, Screen parent, int width, int height, int textHeight, Component reason) {
        if (!initialized) {
            this.init(parent);
        }

        if (Configs.featureAutoReconnect) {
            AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer * 20;
        }
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + textHeight / 2 + 9, height - 30);

        ScreenCompatApi.getInstance().addButton(current, ScreenCompatApi.getInstance().createButton(backButtonX, backButtonY + 24, 98, 20,
                StringUtils.translate("tweakmyclient.message.autoReconnect.static"), b -> AutoReconnectUtil.reconnect(parent)));

        autoReconnectButton = ScreenCompatApi.getInstance().addButton(current, ScreenCompatApi.getInstance().createButton(backButtonX + 102, backButtonY + 24, 98, 20,
                StringUtils.translate("tweakmyclient.message.autoReconnect.toggle"), AutoReconnectUtil::onPressAutoReconnect));
        reAuthenticateButtonOffsetY = 0;

        if (reason == null || getTranslationKey(reason).startsWith("disconnect.loginFailed")) {
            // Auto disable autoReconnect
            Configs.featureAutoReconnect = false;

            AtomicInteger offsetX = new AtomicInteger();
            modHashMap.forEach(
                    (id, reAuthenticateMod) -> {
                        if (!reAuthenticateMod.imCompat) {
                            ScreenCompatApi.getInstance().addButton(current,
                                    ScreenCompatApi.getInstance().createButton(backButtonX + offsetX.get(),
                                            48 + backButtonY,
                                            (200 / compatReAuthenticateMods) - (compatReAuthenticateMods - 1) * 2,
                                            20,
                                            StringUtils.translate(String.format("tweakmyclient.message.autoReconnect.authenticate.%s", id)),
                                            b -> TweakMyClient.getMinecraftClient().setScreen(reAuthenticateMod.authScreen)));
                            offsetX.addAndGet((200 / compatReAuthenticateMods + 2));
                        }
                    }
            );
        }
    }

    private static void onPressAutoReconnect(Button button) {
        Configs.featureAutoReconnect = !Configs.featureAutoReconnect;

        if (Configs.featureAutoReconnect) {
            AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer * 20;
        }
    }

    public static void tickAutoReconnectButton(Screen parent) {
        if (!Configs.featureAutoReconnect) {
            ScreenCompatApi.getInstance().setButtonMessage(autoReconnectButton, StringUtils.translate("tweakmyclient.message.autoReconnect.toggle"));
            return;
        }
        ScreenCompatApi.getInstance().setButtonMessage(autoReconnectButton, StringUtils.translate("tweakmyclient.message.autoReconnect.timer", (int) Math.ceil(AutoReconnectUtil.ReconnectTimer / 20.0)));

        if (AutoReconnectUtil.ReconnectTimer > 0) {
            AutoReconnectUtil.ReconnectTimer--;
            return;
        }
        AutoReconnectUtil.reconnect(parent);
    }

    public class ReAuthenticateMod {
        public Screen authScreen;
        public boolean imCompat;

        public ReAuthenticateMod(Screen screen, boolean imCompat) {
            this.authScreen = screen;
            this.imCompat = imCompat;
        }
    }
}
