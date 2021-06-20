package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AntiGhostItemsUtils;
import top.hendrixshen.TweakMyClient.util.AutoDropUtils;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Shadow
    private boolean usingItem;

    @Shadow @Final protected MinecraftClient client;

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
            Configs.AntiGhostItemsMode mode = (Configs.AntiGhostItemsMode) Configs.Generic.ANTI_GHOST_ITEMS_MODE.getOptionListValue();
            switch (mode) {
                case MANUAL:
                    if (AntiGhostItemsUtils.manualRefreshTimer > 0) {
                        AntiGhostItemsUtils.manualRefreshTimer--;
                    }
                    break;
                case AUTOMATIC:
                    if (AntiGhostItemsUtils.automaticRefreshTimer > 0) {
                        AntiGhostItemsUtils.automaticRefreshTimer--;
                    } else {
                        AntiGhostItemsUtils.refreshInventory();
                        AntiGhostItemsUtils.automaticRefreshTimer = Configs.Generic.ANTI_GHOST_ITEMS_AUTO_TRIGGER_INTERVAL.getIntegerValue() * 20;
                    }
                    break;
            }
        }
    }
}
