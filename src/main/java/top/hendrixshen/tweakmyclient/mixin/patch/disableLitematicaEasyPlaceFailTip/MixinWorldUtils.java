package top.hendrixshen.tweakmyclient.mixin.patch.disableLitematicaEasyPlaceFailTip;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import fi.dy.masa.litematica.util.WorldUtils;
import fi.dy.masa.malilib.gui.Message.MessageType;
import fi.dy.masa.malilib.util.InfoUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.game.Configs;

@Dependencies(
        require = @Dependency(value = "litematica"),
        conflict = @Dependency(value = "masa_gadget_mod", versionPredicates = ">=2.0.6")
)
@Mixin(WorldUtils.class)
public abstract class MixinWorldUtils {
    @WrapWithCondition(
            method = "handleEasyPlace",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/InfoUtils;showGuiOrInGameMessage(Lfi/dy/masa/malilib/gui/Message$MessageType;Ljava/lang/String;[Ljava/lang/Object;)V"
            ),
            remap = false
    )
    private static boolean onHandleEasyPlace(MessageType type, String translationKey, Object[] args) {
        return !Configs.disableLitematicaEasyPlaceFailTip.getBooleanValue();
    }
}
