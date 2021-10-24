package top.hendrixshen.TweakMyClient.mixin;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AntiGhostBlocksUtils;
import top.hendrixshen.TweakMyClient.util.AntiGhostItemsUtils;
import top.hendrixshen.TweakMyClient.util.AutoDropUtils;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    @Shadow
    private boolean startedUsingItem;

    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract void displayClientMessage(Component component, boolean bl);

    @Inject(
            method = "aiStep",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onClimbable(CallbackInfo ci) {
        if (Configs.Feature.FEATURE_AUTO_CLIMB.getBooleanValue() && this.onClimbable() && this.xRot <= -50f && !this.isCrouching()) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, 0.1176D, vec3.z);
        }
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/player/LocalPlayer.isUsingItem ()Z",
                    ordinal = 0
            )
    )
    private boolean getUsingItemState(LocalPlayer instance) {
        if (Configs.Disable.DISABLE_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return this.startedUsingItem;
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.Feature.FEATURE_AUTO_DROP.getBooleanValue()) {
            if (AutoDropUtils.waitTime > 0) {
                AutoDropUtils.waitTime--;
            } else {
                AutoDropUtils.doDrop();
                AutoDropUtils.waitTime = Configs.Generic.AUTO_DROP_INTERVAL.getIntegerValue();
            }
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
        if (Configs.Feature.FEATURE_ANTI_GHOST_BLOCKS.getBooleanValue()) {
            Configs.AntiGhostBlocksMode mode = (Configs.AntiGhostBlocksMode) Configs.Generic.ANTI_GHOST_BLOCKS_MODE.getOptionListValue();
            switch (mode) {
                case MANUAL:
                    if (AntiGhostBlocksUtils.manualRefreshTimer > 0) {
                        AntiGhostBlocksUtils.manualRefreshTimer--;
                    }
                    break;
                case AUTOMATIC:
                    if (AntiGhostBlocksUtils.automaticRefreshTimer > 0) {
                        AntiGhostBlocksUtils.automaticRefreshTimer--;
                    } else {
                        AntiGhostBlocksUtils.refreshBlocksAroundPlayer();
                        AntiGhostBlocksUtils.automaticRefreshTimer = Configs.Generic.ANTI_GHOST_BLOCKS_AUTO_TRIGGER_INTERVAL.getIntegerValue() * 20;
                    }
                    break;
            }
        }
        if (Configs.Feature.FEATURE_LOW_HEALTH_WARNING.getBooleanValue() && this.getHealth() <= Configs.Generic.LOW_HEALTH_THRESHOLD.getDoubleValue()) {
            this.displayClientMessage(new TextComponent(StringUtils.translate("tweakmyclient.message.lowHealthWarning.warningMessage", String.format("%.2f", Configs.Generic.LOW_HEALTH_THRESHOLD.getDoubleValue()))), true);
        }
    }
}
