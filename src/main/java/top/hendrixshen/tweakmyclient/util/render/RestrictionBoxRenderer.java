package top.hendrixshen.tweakmyclient.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.AreaBox;
import top.hendrixshen.tweakmyclient.helper.Cache;

public class RestrictionBoxRenderer implements IRenderer {
    private static final RestrictionBoxRenderer INSTANCE = new RestrictionBoxRenderer();

    public static RestrictionBoxRenderer getInstance() {
        return RestrictionBoxRenderer.INSTANCE;
    }

    @Override
    public void render() {
        switch (Configs.listBreakingRestrictionBoxType) {
            case BLACKLIST:
                for (AreaBox areaBox : Cache.getInstance().getBreakingRestrictionBoxBlacklist()) {
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    RenderSystem.disableDepthTest();
                    RenderUtil.renderAreaOutline(areaBox, Cache.getInstance().getBreakingRestrictionBoxBlacklistModeOutlineColor());
                    RenderSystem.enableDepthTest();
                    RenderUtil.renderAreaOverlay(areaBox, Configs.colorBreakingRestrictionBoxBlacklistMode);
                    GL11.glDisable(GL11.GL_LINE_SMOOTH);
                }
                break;
            case WHITELIST:
                for (AreaBox areaBox : Cache.getInstance().getBreakingRestrictionBoxWhiteList()) {
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    RenderSystem.disableDepthTest();
                    RenderUtil.renderAreaOutline(areaBox, Cache.getInstance().getBreakingRestrictionBoxWhitelistModeOutlineColor());
                    RenderSystem.enableDepthTest();
                    RenderUtil.renderAreaOverlay(areaBox, Configs.colorBreakingRestrictionBoxWhitelistMode);
                    GL11.glDisable(GL11.GL_LINE_SMOOTH);
                }
                break;
        }
    }

    @Override
    public boolean shouldRender() {
        return Configs.featureBreakingRestrictionBox && TweakMyClient.getMinecraftClient().level != null;
    }
}
