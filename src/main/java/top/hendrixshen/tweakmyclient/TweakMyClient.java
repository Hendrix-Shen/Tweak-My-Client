package top.hendrixshen.tweakmyclient;

import fi.dy.masa.malilib.event.RenderEventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.ConfigHandler;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.event.RenderHandler;

public class TweakMyClient implements ClientModInitializer {
    private static final Logger logger = LogManager.getLogger(TweakMyClientReference.getModId());
    @NotNull
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    public static @NotNull Minecraft getMinecraftClient() {
        return minecraftClient;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Dependencies(
            //#if MC >= 11900
            and = @Dependency(value = "wthit", versionPredicate = ">=5.6.1", optional = true)
            //#elseif MC >= 11800
            //$$ and = @Dependency(value = "wthit", versionPredicate = ">=4.11.0", optional = true)
            //#elseif MC >= 11700
            //$$ and = @Dependency(value = "wthit", versionPredicate = ">=3.11.3", optional = true)
            //#elseif MC >= 11600
            //$$ and = @Dependency(value = "wthit", versionPredicate = ">=2.10.15", optional = true)
            //#endif
    )
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
