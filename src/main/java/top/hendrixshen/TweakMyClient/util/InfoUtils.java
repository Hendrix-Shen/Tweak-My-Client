package top.hendrixshen.TweakMyClient.util;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.TweakMyClientReference;

public class InfoUtils {
    public static void printActionBarMessage(String message) {
        printChatMessageOrActionBar(message, true);
    }

    public static void printChatMessage(String message) {
        printChatMessageOrActionBar(message, false);
    }

    public static void printChatMessageOrActionBar(String message, boolean useActionBar) {
        LocalPlayer localPlayer = TweakMyClient.getMinecraftClient().player;
        if (localPlayer != null) {
            localPlayer.displayClientMessage(new TextComponent(message), useActionBar);
        }
    }

    public static void printBooleanConfigToggleMessage(String translatedName, boolean newValue) {
        String color = newValue ? GuiBase.TXT_GREEN : GuiBase.TXT_RED;
        String status = StringUtils.translate(String.format("%s.message.value.%s", TweakMyClientReference.getModId(), (newValue ? "enabled" : "disabled")));
        String message = StringUtils.translate(String.format("%s.message.toggled", TweakMyClientReference.getModId()), String.format("%s%s%s", color, status, GuiBase.TXT_RST), StringUtils.translate(translatedName));

        printActionBarMessage(message);
    }
}
