package top.hendrixshen.tweakmyclient.impl.feature.openWaterHelper;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.magiclib.api.event.minecraft.render.RenderLevelListener;
import top.hendrixshen.magiclib.api.render.context.RenderContext;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigColor;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.util.AreaBox;
import top.hendrixshen.tweakmyclient.mixin.accessor.FishingHookAccessor;
import top.hendrixshen.tweakmyclient.util.RenderUtil;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OpenWaterHelperRenderer implements RenderLevelListener {
    @Getter(lazy = true)
    private static final OpenWaterHelperRenderer instance = new OpenWaterHelperRenderer();

    @Override
    public void preRenderLevel(Level level, RenderContext renderContext, float partialTicks) {
    }

    @Override
    public void postRenderLevel(Level level, RenderContext renderContext, float partialTicks) {
        if (!Configs.openWaterHelper.getBooleanValue()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        assert player != null;
        FishingHook fishHook = player.fishing;

        if (fishHook == null) {
            return;
        }

        BlockPos fishHookPos = fishHook.blockPosition();
        MagicConfigColor color = ((FishingHookAccessor) fishHook).tmc$invokeCalculateOpenWater(fishHook.blockPosition()) ?
                Configs.openWaterColor : Configs.shallowWaterColor;
        AreaBox areaBox = new AreaBox(fishHookPos.getX() - 2, fishHookPos.getY() - 3, fishHookPos.getZ() - 2,
                fishHookPos.getX() + 2, fishHookPos.getY(), fishHookPos.getZ() + 2);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        RenderSystem.disableDepthTest();
        RenderUtil.renderAreaOutline(areaBox, color.getColor());
        RenderSystem.enableDepthTest();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
}
