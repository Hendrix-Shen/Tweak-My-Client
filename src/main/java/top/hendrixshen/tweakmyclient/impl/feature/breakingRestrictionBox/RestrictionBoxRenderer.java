package top.hendrixshen.tweakmyclient.impl.feature.breakingRestrictionBox;

import fi.dy.masa.malilib.util.Color4f;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.world.level.Level;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.magiclib.api.event.minecraft.render.RenderLevelListener;
import top.hendrixshen.magiclib.api.render.context.RenderContext;
import top.hendrixshen.magiclib.impl.render.context.RenderGlobal;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.util.AreaBox;
import top.hendrixshen.tweakmyclient.impl.config.EitherUsageRestriction.EitherListType;
import top.hendrixshen.tweakmyclient.util.RenderUtil;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RestrictionBoxRenderer implements RenderLevelListener {
    @Getter(lazy = true)
    private static final RestrictionBoxRenderer instance = new RestrictionBoxRenderer();
    private Color4f blacklistOutlineColor = Color4f.ZERO;
    private Color4f whitelistOutlineColor = Color4f.ZERO;

    @Override
    public void preRenderLevel(Level level, RenderContext renderContext, float partialTicks) {
    }

    @Override
    public void postRenderLevel(Level level, RenderContext renderContext, float partialTicks) {
        if (!Configs.breakingRestrictionBox.getBooleanValue()) {
            return;
        }

        switch ((EitherListType) Configs.breakingRestrictionBoxType.getOptionListValue()) {
            case BLACKLIST:
                for (AreaBox areaBox : Configs.breakingRestrictionBoxRestriction.getListForType(EitherListType.WHITELIST)) {
                    this.renderAreaBox(areaBox, this.blacklistOutlineColor, Configs.breakingRestrictionBoxBlacklistColor.getColor());
                }
                break;
            case WHITELIST:
                for (AreaBox areaBox : Configs.breakingRestrictionBoxRestriction.getListForType(EitherListType.WHITELIST)) {
                    this.renderAreaBox(areaBox, this.whitelistOutlineColor, Configs.breakingRestrictionBoxWhitelistColor.getColor());
                }
                break;
        }
    }

    public void renderAreaBox(AreaBox areaBox, Color4f outlineColor, Color4f fillColor) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(-1.0F, -1.0F);
        RenderGlobal.disableDepthTest();
        RenderUtil.renderAreaOutline(areaBox, outlineColor);
        RenderGlobal.enableDepthTest();
        RenderUtil.renderAreaOverlay(areaBox, fillColor);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public void updateOutlineColor() {
        this.blacklistOutlineColor = Color4f.fromColor(Configs.breakingRestrictionBoxBlacklistColor.getColor(), 1.0F);
        this.whitelistOutlineColor = Color4f.fromColor(Configs.breakingRestrictionBoxWhitelistColor.getColor(), 1.0F);
    }
}
