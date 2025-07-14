package top.hendrixshen.tweakmyclient.mixin.feature.lowHealthWarning;

import fi.dy.masa.malilib.util.InfoUtils;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (Configs.lowHealthWarning.getBooleanValue() && this.getHealth() <= Configs.lowHealthWarningThreshold.getDoubleValue()) {
            InfoUtils.printActionbarMessage("tweakmyclient.message.lowHealthWarning.warningMessage", String.format("%.2f", Configs.lowHealthWarningThreshold.getDoubleValue()));
        }
    }
}
