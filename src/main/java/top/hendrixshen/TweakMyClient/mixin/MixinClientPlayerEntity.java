package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AntiGhostItemsUtils;
import top.hendrixshen.TweakMyClient.util.AutoDropUtils;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Shadow
    private boolean usingItem;

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z",
                    ordinal = 0
            )
    )
    private boolean getUsingItemState(ClientPlayerEntity clientPlayerEntity) {
        if (Configs.Disable.DISABLE_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return this.usingItem;
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.Feature.FEATURE_AUTO_DROP.getBooleanValue()) {
            AutoDropUtils.doDrop();
        }
        if (Configs.Feature.FEATURE_ANTI_GHOST_ITEMS.getBooleanValue()) {
            if (AntiGhostItemsUtils.manualRefreshTimer > 0) {
                AntiGhostItemsUtils.manualRefreshTimer--;
            }
        }
    }
}
