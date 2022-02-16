package top.hendrixshen.TweakMyClient.mixin.feature.featureCustomBlockOutsideColor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.magiclib.untils.language.I18n;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(
            method = "globalLevelEvent",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int i, CallbackInfo ci) {
        if (Configs.Feature.FEATURE_GLOBAL_EVENT_LISTENER.getBooleanValue()) {
            LocalPlayer player = TweakMyClient.getMinecraftClient().player;
            assert player != null;
            if (eventId == 1023) { //SoundEvents.ENTITY_WITHER_SPAWN
                player.displayClientMessage(new TextComponent(I18n.translate("tweakmyclient.message.globalEventListener.witherSpawn", pos.getX(), pos.getY(), pos.getZ())), false);
            } else if (eventId == 1038) { //SoundEvents.BLOCK_END_PORTAL_SPAWN
                player.displayClientMessage(new TextComponent(I18n.translate("tweakmyclient.message.globalEventListener.endPortalSpawn", pos.getX(), pos.getY(), pos.getZ())), false);
            } else if (eventId == 1028) { //SoundEvents.ENTITY_ENDER_DRAGON_DEATH
                player.displayClientMessage(new TextComponent(I18n.translate("tweakmyclient.message.globalEventListener.enderDragonDeath", pos.getX(), pos.getY(), pos.getZ())), false);
            }
        }
    }
}
