package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11502
import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_19;
//#else
//$$ import top.hendrixshen.tweakmyclient.util.HwylaUtil;
//#endif

//#if MC > 11701
import top.hendrixshen.tweakmyclient.util.JadeUtil;
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
                //#if MC > 11701
                JadeUtil.tick();
                //#endif
            }

            if (TweakMyClientReference.isWthitLoaded) {
                //#if MC > 11502
                WthitUtil_1_19.tick();
                //#else
                //$$ HwylaUtil.tick();
                //#endif
            }
        }
    }
}
