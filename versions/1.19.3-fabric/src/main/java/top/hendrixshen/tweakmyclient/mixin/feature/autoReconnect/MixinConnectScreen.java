package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.AutoReconnectUtil;

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
            CallbackInfo ci
    ) {
        //#if MC > 11904
        //$$ AutoReconnectUtil.setLastQuickPlay(lastQuickPlay);
        //#endif
        AutoReconnectUtil.setLastServer(serverData);
    }
}
