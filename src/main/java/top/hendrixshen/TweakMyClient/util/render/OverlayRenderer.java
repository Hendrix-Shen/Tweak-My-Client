package top.hendrixshen.TweakMyClient.util.render;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.interfaces.IFishingHookEntity;

public class OverlayRenderer {
    private static final OverlayRenderer INSTANCE = new OverlayRenderer();

    public static OverlayRenderer getInstance() {
        return INSTANCE;
    }

    public void renderOpenWater(Minecraft minecraft) {
        assert minecraft.player != null;
        FishingHook fishHook = minecraft.player.fishing;
        if (fishHook != null) {
            BlockPos fishHookPos = fishHook.blockPosition();
            Color4f color = isInOpenWater(fishHook) ? Configs.Color.COLOR_WATER_OPEN.getColor() : Configs.Color.COLOR_WATER_SHALLOW.getColor();
            BlockPos pos1 = new BlockPos(fishHookPos.getX() - 2, fishHookPos.getY() - 3, fishHookPos.getZ() - 2);
            BlockPos pos2 = new BlockPos(fishHookPos.getX() + 2, fishHookPos.getY(), fishHookPos.getZ() + 2);
            RenderUtils.renderAreaOutline(pos1, pos2, 3.0f, color, color, color, minecraft);
        }
    }

    private boolean isInOpenWater(FishingHook fishHook) {
        return ((IFishingHookEntity) fishHook).checkOpenWaterAround(fishHook.blockPosition());
    }
}
