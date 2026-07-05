package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.api.compat.minecraft.client.MinecraftCompat;
import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.PatchedDisconnectedScreen;

import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 12006
//$$ import net.minecraft.network.DisconnectionDetails;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DisconnectedScreen.class, priority = 900)
public abstract class MixinDisconnectedScreen extends Screen {
    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    //#if MC > 12006
    //$$ private DisconnectionDetails details;
    //#else
    private Component reason;
    //#endif

    protected MixinDisconnectedScreen(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void onInitDisconnectedScreen(@NotNull CallbackInfo ci) {
        MinecraftCompat.getInstance().setScreen(new PatchedDisconnectedScreen(
                this.parent,
                this.title,
                //#if MC > 12006
                //$$ this.details.reason()
                //#else
                this.reason
                //#endif
        ));
        ci.cancel();
    }
}
