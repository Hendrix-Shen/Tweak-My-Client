package top.hendrixshen.tweakmyclient.impl.feature.breakingRestrictionBox;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.magiclib.api.event.minecraft.render.RenderLevelListener;
import top.hendrixshen.magiclib.api.render.context.LevelRenderContext;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.config.EitherUsageRestriction.EitherListType;
import top.hendrixshen.tweakmyclient.util.AreaBox;
import top.hendrixshen.tweakmyclient.util.RenderUtil;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12105
//$$ import fi.dy.masa.malilib.util.data.Color4f;
//#else
import fi.dy.masa.malilib.util.Color4f;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.multiplayer.ClientLevel;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RestrictionBoxRenderer implements RenderLevelListener {
    @Getter(lazy = true)
    private static final RestrictionBoxRenderer instance = new RestrictionBoxRenderer();
    private Color4f blacklistOutlineColor = Color4f.ZERO;
    private Color4f whitelistOutlineColor = Color4f.ZERO;

    @Override
    public void preRenderLevel(ClientLevel level, LevelRenderContext renderContext) {
        // NO-OP
    }

    @Override
    public void postRenderLevel(ClientLevel level, LevelRenderContext renderContext) {
        if (!Configs.breakingRestrictionBox.getBooleanValue()) {
            return;
        }

        EitherListType type = (EitherListType) Configs.breakingRestrictionBoxType.getOptionListValue();
        Color4f outlineColor = type == EitherListType.BLACKLIST ? this.blacklistOutlineColor : this.whitelistOutlineColor;
        Color4f overlayColor = type == EitherListType.BLACKLIST ? Configs.breakingRestrictionBoxBlacklistColor.getColor() : Configs.breakingRestrictionBoxWhitelistColor.getColor();

        for (AreaBox areaBox : Configs.breakingRestrictionBoxRestriction.getListForType(type)) {
            this.renderAreaBox(areaBox, outlineColor, overlayColor);
        }
    }

    public void renderAreaBox(AreaBox areaBox, Color4f outlineColor, Color4f fillColor) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(-1.0F, -1.0F);
        RenderUtil.renderAreaOutline(areaBox, outlineColor, false);
        RenderUtil.renderAreaOverlay(areaBox, fillColor, true);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public void updateOutlineColor() {
        this.blacklistOutlineColor = Color4f.fromColor(Configs.breakingRestrictionBoxBlacklistColor.getColor(), 1.0F);
        this.whitelistOutlineColor = Color4f.fromColor(Configs.breakingRestrictionBoxWhitelistColor.getColor(), 1.0F);
    }
}
