package top.hendrixshen.tweakmyclient.impl.generic.targetBlockPosition;

import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
import top.hendrixshen.magiclib.util.minecraft.InfoUtil;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class TargetBlockPositionPrinter {
    public static void print() {
        Minecraft mc = Minecraft.getInstance();
        //#if MC >= 12110
        //$$ Entity cameraEntity = mc.getCameraEntity();
        //#else
        Entity cameraEntity = mc.cameraEntity;
        //#endif
        MultiPlayerGameMode multiPlayerGameMode = mc.gameMode;

        if (cameraEntity == null || multiPlayerGameMode == null) {
            return;
        }

        HitResult hitResult = cameraEntity.pick(
                Configs.getTargetBlockPositionMaxDistance.getIntegerValue(),
                //#if MC > 12101
                //$$ mc.getDeltaTracker().getRealtimeDeltaTicks(),
                //#elseif MC > 12006
                //$$ mc.getTimer().getRealtimeDeltaTicks(),
                //#else
                mc.getFrameTime(),
                //#endif
                false
        );

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
        String str = Configs.getTargetBlockPositionMessage.getStringValue();
        str = str.replace("{X}", String.format("%d", blockPos.getX()));
        str = str.replace("{Y}", String.format("%d", blockPos.getY()));
        str = str.replace("{Z}", String.format("%d", blockPos.getZ()));

        switch ((TargetBlockPositionPrintMode) Configs.getTargetBlockPositionMessageMode.getOptionListValue()) {
            case PUBLIC:
                InfoUtil.sendChat(str);
                break;
            case PRIVATE:
                InfoUtil.displayChatMessage(ComponentCompat.literal(str));
                break;
        }
    }
}
