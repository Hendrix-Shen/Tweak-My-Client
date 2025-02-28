package top.hendrixshen.tweakmyclient.impl.feature.autoReconnect;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

//#if MC > 11902
//$$ import java.util.List;
//#else
import net.minecraft.network.chat.TranslatableComponent;
//#endif

public class AutoReconnectUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    @Getter
    @Setter
    private static ServerData lastServer;
    @Setter
    private static boolean isLastQuickPlay = false;
    //#if MC > 11902
    //$$ public static final List<Component> RE_AUTH_MESSAGES = Lists.newArrayList(
    //$$         ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.insufficientPrivileges")).plainCopy(),
    //$$         ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.invalidSession")).plainCopy(),
    //$$         ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.serversUnavailable")).plainCopy(),
    //$$         ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.userBanned")).plainCopy());
    //#endif

    public static void reconnect(Screen screen) {
        ServerData serverInfo = AutoReconnectUtil.lastServer;

        if (AutoReconnectUtil.lastServer != null) {
            //#if MC > 11904
            //$$ ConnectScreen.startConnecting(
            //$$         screen,
            //$$         AutoReconnectUtil.mc,
            //$$         ServerAddress.parseString(serverInfo.ip),
            //$$         serverInfo
            //#if MC > 11904
            //$$         , AutoReconnectUtil.isLastQuickPlay
            //#endif
            //$$ );
            //#elseif MC > 11605
            //$$ ConnectScreen.startConnecting(screen, AutoReconnectUtil.mc, ServerAddress.parseString(serverInfo.ip), serverInfo);
            //#else
            mc.setScreen(new ConnectScreen(screen, mc, serverInfo));
            //#endif
        }
    }

    //#if MC < 11903
    public static @NotNull String getTranslationKey(Component component) {
        return component instanceof TranslatableComponent ? ((TranslatableComponent) component).getKey() : "";
    }
    //#endif
}
