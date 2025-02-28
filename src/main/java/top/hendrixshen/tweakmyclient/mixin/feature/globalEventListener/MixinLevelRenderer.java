package top.hendrixshen.tweakmyclient.mixin.feature.globalEventListener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
import top.hendrixshen.magiclib.util.minecraft.InfoUtil;
import top.hendrixshen.tweakmyclient.SharedConstants;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(method = "globalLevelEvent", at = @At("HEAD"))
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int i, CallbackInfo ci) {
        if (Configs.globalEventListener.getBooleanValue()) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player == null) {
                return;
            }

            if (eventId == 1023) { // SoundEvents.ENTITY_WITHER_SPAWN
                InfoUtil.displayChatMessage(ComponentCompat.literalCompat(SharedConstants.tr("message.globalEventListener.witherSpawn", pos.getX(), pos.getY(), pos.getZ())));
            } else if (eventId == 1038) { // SoundEvents.BLOCK_END_PORTAL_SPAWN
                InfoUtil.displayChatMessage(ComponentCompat.literalCompat(SharedConstants.tr("message.globalEventListener.endPortalSpawn", pos.getX(), pos.getY(), pos.getZ())));
            } else if (eventId == 1028) { // SoundEvents.ENTITY_ENDER_DRAGON_DEATH
                InfoUtil.displayChatMessage(ComponentCompat.literalCompat(SharedConstants.tr("message.globalEventListener.enderDragonDeath", pos.getX(), pos.getY(), pos.getZ())));
            }
        }
    }
}
