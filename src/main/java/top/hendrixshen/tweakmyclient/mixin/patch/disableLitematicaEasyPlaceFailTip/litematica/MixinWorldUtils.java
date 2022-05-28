package top.hendrixshen.tweakmyclient.mixin.patch.disableLitematicaEasyPlaceFailTip.litematica;

import fi.dy.masa.litematica.util.WorldUtils;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.util.InfoUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "litematica"), not = @Dependency(value = "masa_gadget_mod", versionPredicate = ">=2.0.6"))
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
        if (!Configs.disableLitematicaEasyPlaceFailTip) {
            InfoUtils.showGuiOrInGameMessage(type, translationKey, args);
        }
    }
}
