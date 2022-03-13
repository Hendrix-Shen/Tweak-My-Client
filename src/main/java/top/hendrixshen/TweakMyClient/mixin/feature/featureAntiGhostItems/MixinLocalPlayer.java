package top.hendrixshen.TweakMyClient.mixin.feature.featureAntiGhostItems;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AntiGhostItemsUtils;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.Feature.FEATURE_ANTI_GHOST_ITEMS.getBooleanValue()) {
            Configs.AntiGhostItemsMode mode = (Configs.AntiGhostItemsMode) Configs.Generic.ANTI_GHOST_BLOCKS_MODE.getOptionListValue();
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
                        AntiGhostItemsUtils.automaticRefreshTimer = Configs.Generic.ANTI_GHOST_BLOCKS_AUTO_TRIGGER_INTERVAL.getIntegerValue() * 20;
                    }
                    break;
            }
        }
    }
}
