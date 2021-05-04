package top.hendrixshen.TweakMyClient;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Logger;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.event.InputHandler;

public class TweakMyClient implements ModInitializer {
    public static Logger LOGGER;
    public static MinecraftClient minecraftClient = MinecraftClient.getInstance();

    @Override
    public void onInitialize() {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());
        LOGGER = Reference.LOGGER;
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        LOGGER.info(Reference.MOD_NAME + "-" + Reference.MOD_VERSION + " initialized.");
    }
}
