package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoReconnect;

//#if MC >= 11903
import net.minecraft.client.Minecraft;
//#endif
import net.minecraft.client.gui.screens.ConnectScreen;
//#if MC >= 11903
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11903
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;
//#endif

@Mixin(ConnectScreen.class)
public class MixinConnectScreen {
    //#if MC >= 11903
    @Inject(
            method = "startConnecting",
            at = @At(
                    value = "HEAD"
            )
    )
    private static void onStartConnecting(Screen screen, Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, CallbackInfo ci) {
        AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer * 20;
        if (serverData != null) {
            AutoReconnectUtil.setLastServer(serverData);
        }
    }
    //#endif
}
