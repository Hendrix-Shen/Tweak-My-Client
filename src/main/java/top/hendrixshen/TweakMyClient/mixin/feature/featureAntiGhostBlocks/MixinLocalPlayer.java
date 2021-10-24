package top.hendrixshen.TweakMyClient.mixin.feature.featureAntiGhostBlocks;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
    }
}
