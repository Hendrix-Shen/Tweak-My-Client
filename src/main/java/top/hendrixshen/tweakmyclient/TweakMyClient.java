package top.hendrixshen.tweakmyclient;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hendrixshen.magiclib.MagicLibReference;
import top.hendrixshen.magiclib.config.ConfigHandler;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.tweakmyclient.config.Configs;

public class TweakMyClient implements ModInitializer {
    private static final Logger logger = LogManager.getLogger(TweakMyClientReference.getModId());
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    public static ConfigManager cm = ConfigManager.get(TweakMyClientReference.getModId());

    public static Minecraft getMinecraftClient() {
        return minecraftClient;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void onInitialize() {
        cm.parseConfigClass(Configs.class);
        ConfigHandler.register(new ConfigHandler(TweakMyClientReference.getModId(), cm, TweakMyClientReference.getConfigVersion(), null, null));
        Configs.initCallbacks(cm);

        logger.info("[{}]: Mod initialized - Version: {} ({})", TweakMyClientReference.getModName(), TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType());
    }
}
