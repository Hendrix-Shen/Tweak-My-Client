package top.hendrixshen.tweakmyclient.impl.feature.autoReconnect;

import lombok.Getter;
import lombok.Setter;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 12004
//$$ import org.jetbrains.annotations.Nullable;
//#endif

//#if MC > 11902
//$$ import com.google.common.collect.Lists;
//#else
import org.jetbrains.annotations.NotNull;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 12004
//$$ import net.minecraft.client.multiplayer.TransferState;
//#endif

//#if MC > 11902
//$$ import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
//#else
import net.minecraft.network.chat.TranslatableComponent;
//#endif

//#if MC > 11605
//$$ import net.minecraft.client.multiplayer.resolver.ServerAddress;
//#endif
// CHECKSTYLE.ON: ImportOrder

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11902
//$$ import java.util.List;
//#endif
// CHECKSTYLE.ON: ImportOrder

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
            //$$         // CHECKSTYLE.OFF: NoWhitespaceBefore
            //$$         // CHECKSTYLE.OFF: SeparatorWrap
            //$$         screen,
            //$$         AutoReconnectUtil.mc,
            //$$         ServerAddress.parseString(serverInfo.ip),
            //$$         serverInfo,
            //$$         AutoReconnectUtil.lastQuickPlay
            //$$         //#if MC > 12004
            //$$         //$$ , AutoReconnectUtil.lastTransferState
            //$$         //#endif
            //$$         // CHECKSTYLE.ON: SeparatorWrap
            //$$         // CHECKSTYLE.ON: NoWhitespaceBefore
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
