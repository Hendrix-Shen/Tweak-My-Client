package top.hendrixshen.tweakmyclient.mixin.feature.featureGlobalEventListener;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.compat.minecraft.network.chat.ComponentCompatApi;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.InfoUtil;
import top.hendrixshen.tweakmyclient.util.StringUtil;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(
            method = "globalLevelEvent",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int i, CallbackInfo ci) {
        if (Configs.featureGlobalEventListener) {
            LocalPlayer player = TweakMyClient.getMinecraftClient().player;
            if (player != null) {
                if (eventId == 1023) { // SoundEvents.ENTITY_WITHER_SPAWN
                    InfoUtil.displayChatMessage(ComponentCompatApi.literal(StringUtil.tr("message.globalEventListener.witherSpawn", pos.getX(), pos.getY(), pos.getZ())));
                } else if (eventId == 1038) { // SoundEvents.BLOCK_END_PORTAL_SPAWN
                    InfoUtil.displayChatMessage(ComponentCompatApi.literal(StringUtil.tr("message.globalEventListener.endPortalSpawn", pos.getX(), pos.getY(), pos.getZ())));
                } else if (eventId == 1028) { // SoundEvents.ENTITY_ENDER_DRAGON_DEATH
                    InfoUtil.displayChatMessage(ComponentCompatApi.literal(StringUtil.tr("message.globalEventListener.enderDragonDeath", pos.getX(), pos.getY(), pos.getZ())));
                }
            }
        }
    }
}
