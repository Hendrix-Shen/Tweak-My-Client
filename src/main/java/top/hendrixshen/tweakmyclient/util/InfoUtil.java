package top.hendrixshen.tweakmyclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import top.hendrixshen.tweakmyclient.TweakMyClient;

public class InfoUtil {
    private final static Minecraft mc = TweakMyClient.getMinecraftClient();

    public static void displayClientMessage(Component component, boolean useActionBar) {
        if (mc.player != null) {
            mc.player.displayClientMessage(component, useActionBar);
        }
    }

    public static void displayActionBarMessage(Component component) {
        displayClientMessage(component, true);
    }

    public static void displayChatMessage(Component component) {
        displayClientMessage(component, false);
    }
}
