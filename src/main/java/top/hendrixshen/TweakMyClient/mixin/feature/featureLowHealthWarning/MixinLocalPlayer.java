package top.hendrixshen.TweakMyClient.mixin.feature.featureLowHealthWarning;

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

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    @Shadow public abstract void displayClientMessage(Component component, boolean bl);

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
        if (Configs.Feature.FEATURE_LOW_HEALTH_WARNING.getBooleanValue() && this.getHealth() <= Configs.Generic.LOW_HEALTH_THRESHOLD.getDoubleValue()) {
            this.displayClientMessage(new TextComponent(StringUtils.translate("tweakmyclient.message.lowHealthWarning.warningMessage", String.format("%.2f", Configs.Generic.LOW_HEALTH_THRESHOLD.getDoubleValue()))), true);
        }
    }
}
