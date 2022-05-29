package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWAILACompat;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.WthitUtil;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer {
    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        //#if MC >= 11600
        if (Configs.litematicaSchematicWAILACompat) {
            WthitUtil.tick();
        }
        //#endif
    }
}
