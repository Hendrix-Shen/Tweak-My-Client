package top.hendrixshen.tweakmyclient;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.RenderEventHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.magiclib.util.malilib.ConfigManager;
import top.hendrixshen.tweakmyclient.config.ConfigStorage;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.event.InputHandler;
import top.hendrixshen.tweakmyclient.event.RenderHandler;

public class TweakMyClient implements ModInitializer {
    private static final Logger logger = LogManager.getLogger(TweakMyClientReference.getModId());
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    public static ConfigManager cm = new ConfigManager(TweakMyClientReference.getModId());

    public static Minecraft getMinecraftClient() {
        return minecraftClient;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void onInitialize() {
        InitializationHandler.getInstance().registerInitializationHandler(()-> {
            cm.parseConfigClass(Configs.class);
            fi.dy.masa.malilib.config.ConfigManager.getInstance().registerConfigHandler(TweakMyClientReference.getModId(), new ConfigStorage());
            InputEventHandler.getKeybindManager().registerKeybindProvider(new InputHandler());
            RenderEventHandler.getInstance().registerWorldLastRenderer(new RenderHandler());
            Configs.initCallbacks();
        });

        I18n.getInstance().register(TweakMyClientReference.getModId(), "en_us");
        I18n.getInstance().register(TweakMyClientReference.getModId(), "zh_cn");

        String modName = TweakMyClientReference.getModName();
        logger.info(String.format("[%s]: Mod initialized - Version: %s (%s)", modName, TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType()));
        logger.info(String.format("[%s]: AuthMe was %sdetected.", modName, (TweakMyClientReference.isAuthMeLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: InGameAccountSwitcher was %sdetected.", modName, (TweakMyClientReference.isInGameAccountSwitcherLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: Litematica was %sdetected.", modName, (TweakMyClientReference.isLitematicaLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: OhMyMinecraftClient was %sdetected.", modName, (TweakMyClientReference.isOMMCLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: ReAuth was %sdetected.", modName, (TweakMyClientReference.isReAuthLoaded ? "" : "not ")));
    }
}
