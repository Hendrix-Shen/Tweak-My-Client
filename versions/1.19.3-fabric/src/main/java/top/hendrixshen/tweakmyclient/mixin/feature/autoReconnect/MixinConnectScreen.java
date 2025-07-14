package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.AutoReconnectUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 12004
//$$ import net.minecraft.client.multiplayer.TransferState;
//#endif
// CHECKSTYLE.ON: ImportOrder

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.19.2: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc1.19.3+        : subproject 1.21.3        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(ConnectScreen.class)
public abstract class MixinConnectScreen {
    @Inject(method = "startConnecting", at = @At("HEAD"))
    private static void onStartConnecting(
            Screen screen,
            Minecraft minecraft,
            ServerAddress serverAddress,
            ServerData serverData,
            //#if MC > 11904
            //$$ boolean lastQuickPlay,
            //#endif
            //#if MC > 12004
            //$$ TransferState transferState,
            //#endif
            CallbackInfo ci
    ) {
        //#if MC > 11904
        //$$ AutoReconnectUtil.setLastQuickPlay(lastQuickPlay);
        //#endif
        //#if MC > 12004
        //$$ AutoReconnectUtil.setLastTransferState(transferState);
        //#endif
        AutoReconnectUtil.setLastServer(serverData);
    }
}
