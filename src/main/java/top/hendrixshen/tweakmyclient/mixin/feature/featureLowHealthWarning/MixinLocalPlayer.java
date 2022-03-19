package top.hendrixshen.tweakmyclient.mixin.feature.featureLowHealthWarning;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract void displayClientMessage(Component component, boolean bl);

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.featureLowHealthWarning.getBooleanValue() && this.getHealth() <= Configs.lowHealthThreshold.getDoubleValue()) {
            this.displayClientMessage(new TextComponent(I18n.translate("tweakmyclient.message.lowHealthWarning.warningMessage", String.format("%.2f", Configs.lowHealthThreshold.getDoubleValue()))), true);
        }
    }
}
