package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

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
}
