package top.hendrixshen.tweakmyclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import top.hendrixshen.tweakmyclient.TweakMyClient;

//#if MC > 11802 && MC < 11903
//$$ import net.minecraft.Util;
//$$ import net.minecraft.client.gui.chat.ClientChatPreview;
//$$ import net.minecraft.network.chat.Component;
//#endif

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

    public static void sendChatMessage(String message) {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        LocalPlayer localPlayer = minecraft.player;
        if (localPlayer != null) {
            //#if MC > 11902
            localPlayer.connection.sendChat(message);
            //#elseif MC > 11802
            //$$ ClientChatPreview ccp = new ClientChatPreview(Minecraft.getInstance());
            //$$ Component component = Util.mapNullable(ccp.pull(message), ClientChatPreview.Preview::response);
            //$$ localPlayer.chatSigned(message, component);
            //#else
            //$$ localPlayer.chat(message);
            //#endif
        }
    }
}
