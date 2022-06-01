package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWAILACompat;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;
//#if MC < 11600
//$$ import top.hendrixshen.tweakmyclient.util.HwylaUtil;
//#else
import top.hendrixshen.tweakmyclient.util.WthitUtil;
//#endif

@Dependencies(or = {@Dependency(value = "waila"), @Dependency(value = "wthit")})
@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer {
    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.litematicaSchematicWAILACompat) {
            //#if MC >= 11600
            WthitUtil.tick();
            //#else
            //$$ HwylaUtil.tick();
            //#endif
        }
    }
}
