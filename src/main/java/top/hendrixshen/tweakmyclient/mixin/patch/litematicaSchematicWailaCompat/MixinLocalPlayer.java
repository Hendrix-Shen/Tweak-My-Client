package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
//#if MC >= 11800
import top.hendrixshen.tweakmyclient.util.JadeUtil;
//#endif
//#if MC >= 11900
import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_19;
//#elseif MC >= 11800
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_18;
//#elseif MC >= 11700
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_17;
//#elseif MC >= 11600
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_16;
//#else
//$$ import top.hendrixshen.tweakmyclient.util.HwylaUtil;
//#endif

@Dependencies(
        or = {
                @Dependency("jade"),
                @Dependency("waila"),
                @Dependency("wthit")
        },
        and = @Dependency("litematica")
)
@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer {
    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.litematicaSchematicWailaCompat) {
            if (TweakMyClientReference.isJadeLoaded) {
                //#if MC >= 11800
                JadeUtil.tick();
                //#endif
            }
            if (TweakMyClientReference.isWthitLoaded) {
                //#if MC >= 11900
                WthitUtil_1_19.tick();
                //#elseif MC >= 11800
                //$$ WthitUtil_1_18.tick();
                //#elseif MC >= 11700
                //$$ WthitUtil_1_17.tick();
                //#elseif MC >= 11600
                //$$ WthitUtil_1_16.tick();
            }
            //#else
            //$$ HwylaUtil.tick();
            //#endif
        }
    }
}
