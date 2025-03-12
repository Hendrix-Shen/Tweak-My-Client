package top.hendrixshen.tweakmyclient.impl.feature.autoReconnect;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

//#if MC > 12004
//$$ import net.minecraft.client.multiplayer.TransferState;
//$$ import org.jetbrains.annotations.Nullable;
//#endif

//#if MC > 11902
//$$ import com.google.common.collect.Lists;
//$$ import java.util.List;
//$$ import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
//#else
import net.minecraft.network.chat.TranslatableComponent;
//#endif

//#if MC > 11605
//$$ import net.minecraft.client.multiplayer.resolver.ServerAddress;
//#endif

public class AutoReconnectUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    @Getter
    @Setter
    private static ServerData lastServer;
    //#if MC > 11904
    //$$ @Setter
    //$$ private static boolean lastQuickPlay = false;
    //#endif
    //#if MC > 12004
    //$$ @Setter
    //$$ @Nullable
    //$$ private static TransferState lastTransferState = null;
    //#endif
    //#if MC > 11902
    //$$ public static final List<Component> RE_AUTH_MESSAGES = Lists.newArrayList(
    //$$         ComponentCompat.translatable("disconnect.loginFailedInfo", ComponentCompat.translatable("disconnect.loginFailedInfo.insufficientPrivileges")).plainCopy(),
    //$$         ComponentCompat.translatable("disconnect.loginFailedInfo", ComponentCompat.translatable("disconnect.loginFailedInfo.invalidSession")).plainCopy(),
    //$$         ComponentCompat.translatable("disconnect.loginFailedInfo", ComponentCompat.translatable("disconnect.loginFailedInfo.serversUnavailable")).plainCopy(),
    //$$         ComponentCompat.translatable("disconnect.loginFailedInfo", ComponentCompat.translatable("disconnect.loginFailedInfo.userBanned")).plainCopy());
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
            //$$         , AutoReconnectUtil.lastQuickPlay
            //#endif
            //#if MC > 12004
            //$$         , AutoReconnectUtil.lastTransferState
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
