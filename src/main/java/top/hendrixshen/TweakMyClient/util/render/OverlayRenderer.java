package top.hendrixshen.TweakMyClient.util.render;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.interfaces.IFishingBobberEntity;

public class OverlayRenderer {
    private static final OverlayRenderer INSTANCE = new OverlayRenderer();

    public static OverlayRenderer getInstance() {
        return INSTANCE;
    }

    public void renderOpenWater(MinecraftClient mc) {
        FishingBobberEntity fishHook = mc.player.fishHook;
        if (fishHook != null) {
            BlockPos fishHookPos = fishHook.getBlockPos();
            Color4f color = isInOpenWater(fishHook) ? Configs.Color.COLOR_WATER_OPEN.getColor() : Configs.Color.COLOR_WATER_SHALLOW.getColor();
            BlockPos pos1 = new BlockPos(fishHookPos.getX() - 2, fishHookPos.getY() - 3, fishHookPos.getZ() - 2);
            BlockPos pos2 = new BlockPos(fishHookPos.getX() + 2, fishHookPos.getY(), fishHookPos.getZ() + 2);
            RenderUtils.renderAreaOutline(pos1, pos2, 3.0f, color, color, color, mc);
        }
    }
    private boolean isInOpenWater(FishingBobberEntity fishHook)
    {
        return ((IFishingBobberEntity)fishHook).checkOpenWaterAround(fishHook.getBlockPos());
    }


}
