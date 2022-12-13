package top.hendrixshen.tweakmyclient.util;

//#if MC >= 11900
import net.minecraft.Util;
//#endif
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
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

    public static void sendChatMessage(String message) {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        LocalPlayer localPlayer = minecraft.player;
        if (localPlayer != null) {
            //#if MC >= 11903
            localPlayer.connection.sendChat(message);
            //#else
            //$$ localPlayer.chat(message);
            //#endif
        }
    }
}
