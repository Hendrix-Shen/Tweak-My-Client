package top.hendrixshen.tweakmyclient.impl.feature.globalEventListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
import top.hendrixshen.magiclib.util.minecraft.InfoUtil;
import top.hendrixshen.tweakmyclient.SharedConstants;
import top.hendrixshen.tweakmyclient.game.Configs;

public class GlobalEventListener {
    public static void onGlobalEvent(int eventId, BlockPos pos) {
        if (Configs.globalEventListener.getBooleanValue()) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player == null) {
                return;
            }

            if (eventId == 1023) { // SoundEvents.ENTITY_WITHER_SPAWN
                GlobalEventListener.displayGlobalEvent(GlobalEventType.WITHER_SPAWN, pos);
            } else if (eventId == 1038) { // SoundEvents.BLOCK_END_PORTAL_SPAWN
                GlobalEventListener.displayGlobalEvent(GlobalEventType.END_PORTAL_SPAWN, pos);
            } else if (eventId == 1028) { // SoundEvents.ENTITY_ENDER_DRAGON_DEATH
                GlobalEventListener.displayGlobalEvent(GlobalEventType.ENDER_DRAGON_DEATH, pos);
            }
        }
    }

    private static void displayGlobalEvent(GlobalEventType event, BlockPos pos) {
        InfoUtil.displayChatMessage(ComponentCompat.literalCompat(
                SharedConstants.tr("feature.globalEventListener.message.prefix"))
                .append(ComponentCompat.literalCompat(SharedConstants.tr("feature.globalEventListener.message."
                        .concat(event.getKey()), pos.getX(), pos.getY(), pos.getZ()))));
    }

    @Getter
    @AllArgsConstructor
    private enum GlobalEventType {
        END_PORTAL_SPAWN("end_portal_spawn"),
        ENDER_DRAGON_DEATH("ender_dragon_death"),
        WITHER_SPAWN("wither_spawn");

        private final String key;
    }
}
