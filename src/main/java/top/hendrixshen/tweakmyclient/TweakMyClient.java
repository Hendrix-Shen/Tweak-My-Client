package top.hendrixshen.tweakmyclient;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hendrixshen.magiclib.config.ConfigHandler;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
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

    @Dependencies(
            or = {
                    @Dependency(value = "minecraft", versionPredicate = "1.14.4"),
                    @Dependency(value = "minecraft", versionPredicate = "1.15.2"),
                    @Dependency(value = "minecraft", versionPredicate = "1.16.5"),
                    @Dependency(value = "minecraft", versionPredicate = "1.17.1"),
                    @Dependency(value = "minecraft", versionPredicate = "1.18.2"),
                    @Dependency(value = "minecraft", versionPredicate = "1.19.x"),
            }
    )
    @Override
    public void onInitialize() {
        cm.parseConfigClass(Configs.class);
        ConfigHandler.register(new ConfigHandler(TweakMyClientReference.getModId(), cm, TweakMyClientReference.getConfigVersion(), null, null));
        Configs.initCallbacks(cm);

        logger.info("[{}]: Mod initialized - Version: {} ({})", TweakMyClientReference.getModName(), TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType());
    }
}
