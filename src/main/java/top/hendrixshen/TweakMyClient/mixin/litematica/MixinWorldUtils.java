package top.hendrixshen.TweakMyClient.mixin.litematica;

import fi.dy.masa.litematica.util.WorldUtils;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.util.InfoUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(WorldUtils.class)
public class MixinWorldUtils {
    @Redirect(
            method = "handleEasyPlace",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/InfoUtils;showGuiOrInGameMessage(Lfi/dy/masa/malilib/gui/Message$MessageType;Ljava/lang/String;[Ljava/lang/Object;)V"
            ),
            remap = false
    )
    private static void onHandleEasyPlace(Message.MessageType type, String translationKey, Object[] args) {
        if (!Configs.Patch.DISABLE_LITEMATICA_EASY_PLACE_FAIL_TIP.getBooleanValue()) {
            InfoUtils.showGuiOrInGameMessage(type, translationKey, args);
        }
    }
}
