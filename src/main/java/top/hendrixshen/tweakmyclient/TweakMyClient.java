package top.hendrixshen.tweakmyclient;

import fi.dy.masa.malilib.event.RenderEventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hendrixshen.tweakmyclient.config.ConfigHandler;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.event.RenderHandler;

public class TweakMyClient implements ClientModInitializer {
    private static final Logger logger = LogManager.getLogger(TweakMyClientReference.getModId());
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    public static Minecraft getMinecraftClient() {
        return minecraftClient;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void onInitializeClient() {
        ConfigHandler configHandler = TweakMyClientReference.getConfigHandler();
        configHandler.configManager.parseConfigClass(Configs.class);
        ConfigHandler.register(configHandler);
        Configs.initCallbacks(configHandler.configManager);
        RenderEventHandler.getInstance().registerWorldLastRenderer(RenderHandler.getInstance());

        logger.info("[{}]: Mod initialized - Version: {} ({})", TweakMyClientReference.getModName(), TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType());
    }
}
