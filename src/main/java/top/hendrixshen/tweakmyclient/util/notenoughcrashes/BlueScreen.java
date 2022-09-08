package top.hendrixshen.tweakmyclient.util.notenoughcrashes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;
import fudge.notenoughcrashes.gui.ProblemScreen;
//#if MC > 11502
import com.mojang.math.Matrix4f;
import fudge.notenoughcrashes.platform.CommonModMetadata;
import fudge.notenoughcrashes.stacktrace.ModIdentifier;
//#else
//$$ import fudge.notenoughcrashes.patches.PatchedCrashReport;
//$$ import net.fabricmc.loader.api.metadata.ModMetadata;
//#endif
import net.minecraft.CrashReport;
import net.minecraft.client.gui.screens.TitleScreen;
//#if MC > 11605
import net.minecraft.client.renderer.GameRenderer;
//#else
//$$ import org.lwjgl.opengl.GL11;
//#endif
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.util.Locale;
import java.util.Set;

public class BlueScreen extends ProblemScreen {
    private static final Color4f WIN_XP = Color4f.fromColor(StringUtils.getColor("#FF000888", 0));
    private static final Color4f FONT = Color4f.fromColor(StringUtils.getColor("#FFFFFFFF", 0));
    private final CrashReport crashreport;

    public BlueScreen(CrashReport report) {
        super(report);
        this.crashreport = report;
    }

    @Override
    public ProblemScreen construct(CrashReport crashReport) {
        return new BlueScreen(report);
    }

    @Override
    //#if MC > 11502
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.fillGradient(poseStack, 0, 0, this.width, this.height, BlueScreen.WIN_XP);
        int y = 10;
        this.drawString(poseStack, "A problem has been detected and Minecraft not shut down to damage to your computer.", 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, String.format("The problem seems to caused by the following mods:%s", this.getSuspectedMods()), 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, this.toErrorStyle(), 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, "If this is the first time you've seen this Stop error screen, continue your game. If this screen appears again,follow these steps:", 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, "Check to make sure any new mods is properly installed. If this a new installation, ask the mod author for mod update you might need.", 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, "If problem continue,disable or remove any newly installed mods.", 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, "Technical information:", 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        this.drawString(poseStack, String.format("*** STOP:%s", this.getErrorCode()), 5, y, 0.5F, BlueScreen.FONT.intValue);
        y += 20;
        boolean stackFirst = true;
        for (String string : this.crashreport.getExceptionMessage().split("\n")) {
            if (stackFirst) {
                this.drawString(poseStack, String.format("*** Stacktrace: %s", string.replaceAll("\r", "")
                        .replaceAll("\t", "    ")), 5, y, 0.5F, BlueScreen.FONT.intValue);
                stackFirst = false;
            } else {
                this.drawString(poseStack, string.replaceAll("\r", "")
                        .replaceAll("\t", "    "), 5, y, 0.5F, BlueScreen.FONT.intValue);
            }
            y += 10;
        }
    //#else
    //$$ public void render(int mouseX, int mouseY, float delta) {
    //$$     this.fillGradient(0, 0, this.width, this.height, BlueScreen.WIN_XP);
    //$$     int y = 15;
    //$$     this.drawString("A problem has been detected and Minecraft not shut down to damage to your computer.", 20, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString(String.format("The problem seems to caused by the following mods:%s", this.getSuspectedMods()), 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString(this.toErrorStyle(), 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString("If this is the first time you've seen this Stop error screen, continue your game. If this screen appears again,follow these steps:", 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString("Check to make sure any new mods is properly installed. If this a new installation, ask the mod author for mod update you might need.", 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString("If problem continue,disable or remove any newly installed mods.", 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString("Technical information:", 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     this.drawString(String.format("*** STOP:%s", this.getErrorCode()), 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$     y += 20;
    //$$     boolean stackFirst = true;
    //$$     for (String string : this.crashreport.getExceptionMessage().split("\n")) {
    //$$         if (stackFirst) {
    //$$             this.drawString(String.format("*** Stacktrace: %s", string.replaceAll("\r", "")
    //$$                     .replaceAll("\t", "    ")), 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$             stackFirst = false;
    //$$         } else {
    //$$             this.drawString(string.replaceAll("\r", "")
    //$$                     .replaceAll("\t", "    "), 5, y, 0.5F, BlueScreen.FONT.intValue);
    //$$         }
    //$$         y += 10;
    //$$     }
    //#endif
    }

    //#if MC > 11605
    private void fillGradient(@NotNull PoseStack poseStack, int startX, int startY, int stopX, int stopY, Color4f color) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        this.fillGradient(poseStack.last().pose(), bufferBuilder, startX, startY, stopX, stopY, color, this.getBlitOffset());
        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    //#elseif MC > 11502
    //$$ private void fillGradient(@NotNull PoseStack poseStack, int startX, int startY, int stopX, int stopY, Color4f color) {
    //$$     RenderSystem.disableTexture();
    //$$     RenderSystem.enableBlend();
    //$$     RenderSystem.disableAlphaTest();
    //$$     RenderSystem.defaultBlendFunc();
    //$$     RenderSystem.shadeModel(GL11.GL_SMOOTH);
    //$$     Tesselator tesselator = Tesselator.getInstance();
    //$$     BufferBuilder bufferBuilder = tesselator.getBuilder();
    //$$     bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormat.POSITION_COLOR);
    //$$     fillGradient(poseStack.last().pose(), bufferBuilder, startX, startY, stopX, stopY, color, this.getBlitOffset());
    //$$     tesselator.end();
    //$$     RenderSystem.shadeModel(GL11.GL_FLAT);
    //$$     RenderSystem.disableBlend();
    //$$     RenderSystem.enableAlphaTest();
    //$$     RenderSystem.enableTexture();
    //#else
    //$$ private void fillGradient(int startX, int startY, int stopX, int stopY, Color4f color) {
    //$$     RenderSystem.disableTexture();
    //$$     RenderSystem.enableBlend();
    //$$     RenderSystem.disableAlphaTest();
    //$$     RenderSystem.defaultBlendFunc();
    //$$     RenderSystem.shadeModel(GL11.GL_SMOOTH);
    //$$     Tesselator tesselator = Tesselator.getInstance();
    //$$     BufferBuilder bufferBuilder = tesselator.getBuilder();
    //$$     bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormat.POSITION_COLOR);
    //#if MC > 11404
    //$$     this.fillGradient(bufferBuilder, startX, startY, stopX, stopY, color, this.getBlitOffset());
    //#else
    //$$     this.fillGradient(bufferBuilder, startX, startY, stopX, stopY, color, this.blitOffset);
    //#endif
    //$$     tesselator.end();
    //$$     RenderSystem.shadeModel(GL11.GL_FLAT);
    //$$     RenderSystem.disableBlend();
    //$$     RenderSystem.enableAlphaTest();
    //$$     RenderSystem.enableTexture();
    //#endif
    }

    //#if MC > 11502
    private void fillGradient(Matrix4f matrix4f, @NotNull BufferBuilder bufferBuilder, int startX, int startY, int stopX, int stopY, @NotNull Color4f color, int blitOffset) {
        bufferBuilder.vertex(matrix4f, startX, startY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
        bufferBuilder.vertex(matrix4f, startX, stopY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
        bufferBuilder.vertex(matrix4f, stopX, stopY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
        bufferBuilder.vertex(matrix4f, stopX, startY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
    //#else
    //$$ private void fillGradient(@NotNull BufferBuilder bufferBuilder, int startX, int startY, int stopX, int stopY, @NotNull Color4f color, int blitOffset) {
    //$$     bufferBuilder.vertex(startX, startY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
    //$$     bufferBuilder.vertex(startX, stopY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
    //$$     bufferBuilder.vertex(stopX, stopY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
    //$$     bufferBuilder.vertex(stopX, startY, blitOffset).color(color.r, color.g, color.b, color.a).endVertex();
    //#endif
    }

    //#if MC > 11605
    private void drawString(PoseStack poseStack, String string, int x, int y, float scale, int color) {
        PoseStack globalStack = RenderSystem.getModelViewStack();
        globalStack.pushPose();
        globalStack.scale(scale, scale,1.0F);
        RenderSystem.applyModelViewMatrix();
        this.font.draw(poseStack, string, x, y, color);
        globalStack.popPose();
        RenderSystem.applyModelViewMatrix();
    //#elseif MC > 11502
    //$$ private void drawString(PoseStack poseStack, String string, int x, int y, float scale, int color) {
    //$$     RenderSystem.pushMatrix();
    //$$     RenderSystem.scaled(scale, scale, 1.0);
    //$$     this.font.draw(poseStack, string, x, y, color);
    //$$     RenderSystem.popMatrix();
    //#else
    //$$ private void drawString(String string, int x, int y, float scale, int color) {
    //$$     RenderSystem.pushMatrix();
    //$$     RenderSystem.scaled(scale, scale, 1.0);
    //$$     this.font.draw(string, x, y, color);
    //$$     RenderSystem.popMatrix();
    //#endif
    }

    private String getSuspectedMods() {
        //#if MC > 11502
        Set<CommonModMetadata> suspectedMods = ModIdentifier.getSuspectedModsOf(this.report);
        //#else
        //$$ Set<ModMetadata> suspectedMods = ((PatchedCrashReport)this.report).getSuspectedMods();
        //#endif
        if (!suspectedMods.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            //#if MC > 11502
            for (CommonModMetadata suspectedMod : suspectedMods) {
                builder.append(suspectedMod.name());
            //#else
            //$$ for (ModMetadata suspectedMod : suspectedMods) {
            //$$     builder.append(suspectedMod.getName());
            //#endif
                builder.append(", ");
            }
            return builder.substring(0, builder.length() - 2);
        }
        return "";
    }

    private @NotNull String toErrorStyle() {
        String className = this.crashreport.getException().getClass().getName();
        className = className.split("\\.")[className.split("\\.").length - 1];

        boolean firstFound = false;
        StringBuilder builder = new StringBuilder();

        for (char c : className.toCharArray()) {
            String s = String.valueOf(c);
            if (s.toUpperCase(Locale.ROOT).equals(s)) {
                if (firstFound) {
                    builder.append("_");
                } else {
                    firstFound = true;
                }
                builder.append(s);
            } else if (s.equals("$")) {
                builder.append("_");
            } else {
                builder.append(s.toUpperCase(Locale.ROOT));
            }
        }
        return builder.toString();
    }

    private String getErrorCode() {
        return String.format("0x%08X (0x%08X,0x%08X,0x%08X,0x%08X)",
                this.toErrorStyle().hashCode(),
                this.getSuspectedMods().hashCode(),
                this.crashreport.getTitle().hashCode(),
                this.crashreport.getException().getMessage().hashCode(),
                this.crashreport.getException().getClass().getName().hashCode());
    }

    @Override
    public boolean keyPressed(int key, int oldkey, int mods) {
        if (key == 256) {
            TweakMyClient.getMinecraftClient().setScreen(new TitleScreen());
        }
        return super.keyPressed(key, oldkey, mods);
    }
}
