package top.hendrixshen.tweakmyclient.mixin.feature.featureReconnectButtons;

import net.minecraft.client.Minecraft;
//#if MC <= 11802
//$$ import net.minecraft.client.multiplayer.ServerData;
//#endif
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.interfaces.IAutoReconnectScreen;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;
//#if MC <= 11802
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//#endif

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    //#if MC <= 11802
    //$$ @Inject(
    //$$         method = "setCurrentServer",
    //$$         at = @At(
    //$$                 "HEAD"
    //$$         )
    //$$ )
    //$$ private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
    //$$     AutoReconnectUtil.reconnectTimer = Configs.autoReconnectTimer * 20;
    //$$     if (serverData != null) {
    //$$         AutoReconnectUtil.setLastServer(serverData);
    //$$    }
    //$$ }
    //#endif

    @Shadow
    @Nullable
    public Screen screen;

    @Inject(
            method = "method_1572",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private void onScreenTick(CallbackInfo ci) {
        if (screen instanceof IAutoReconnectScreen) {
            AutoReconnectUtil.tickAutoReconnectButton(((IAutoReconnectScreen) screen).getParent());
            ci.cancel();
        }
    }
}
