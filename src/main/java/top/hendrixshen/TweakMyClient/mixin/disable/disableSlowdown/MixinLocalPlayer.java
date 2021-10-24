package top.hendrixshen.TweakMyClient.mixin.disable.disableSlowdown;

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
}
