package top.hendrixshen.TweakMyClient.util;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.TweakMyClient;

public class InfoUtils {
    public static void printActionBarMessage(String message) {
        printChatMessageOrActionBar(message, true);
    }

    public static void printChatMessage(String message) {
        printChatMessageOrActionBar(message, false);
    }

    public static void printChatMessageOrActionBar(String message, boolean useActionBar) {
        ClientPlayerEntity player = TweakMyClient.minecraftClient.player;
        assert player != null;
        player.sendMessage(new LiteralText(message), useActionBar);
    }

    public static void printBooleanConfigToggleMessage(String translatedName, boolean newValue)
    {
        String color = newValue ? GuiBase.TXT_GREEN : GuiBase.TXT_RED;
        String status = StringUtils.translate(String.format("%s.message.value.%s", Reference.MOD_ID, (newValue ? "enabled" : "disabled")));
        String message = StringUtils.translate(String.format("%s.message.toggled", Reference.MOD_ID), String.format("%s%s%s", color, status, GuiBase.TXT_RST), StringUtils.translate(translatedName));

        printActionBarMessage(message);
    }
}
