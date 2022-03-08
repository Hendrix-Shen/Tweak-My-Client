package top.hendrixshen.TweakMyClient;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.event.InputHandler;
import top.hendrixshen.magiclib.untils.language.I18n;

public class TweakMyClient implements ModInitializer {
    private static final Logger logger = LogManager.getLogger(TweakMyClientReference.getModId());
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    public static Minecraft getMinecraftClient() {
        return minecraftClient;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void onInitialize() {
        ConfigManager.getInstance().registerConfigHandler(TweakMyClientReference.getModId(), new Configs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());

        I18n.getInstance().register(TweakMyClientReference.getModId(), "en_us");
        I18n.getInstance().register(TweakMyClientReference.getModId(), "zh_cn");

        String modName = TweakMyClientReference.getModName();
        logger.info(String.format("[%s]: Mod initialized - Version: %s (%s)", modName, TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType()));
        logger.info(String.format("[%s]: AuthMe was %sdetected.", modName, (TweakMyClientReference.isAuthMeLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: InGameAccountSwitcher was %sdetected.", modName, (TweakMyClientReference.isInGameAccountSwitcherLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: Litematica was %sdetected.", modName, (TweakMyClientReference.isLitematicaLoaded ? "" : "not ")));
        logger.info(String.format("[%s]: ReAuth was %sdetected.", modName, (TweakMyClientReference.isReAuthLoaded ? "" : "not ")));
    }
}
