package top.hendrixshen.tweakmyclient.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.compat.minecraft.api.blaze3d.vertex.VertexFormatCompatApi;
import top.hendrixshen.magiclib.compat.minecraft.api.network.chat.ComponentCompatApi;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

import java.util.List;

//#if MC < 11903
//$$ import net.minecraft.network.chat.contents.TranslatableContents;
//#endif

public class AutoReconnectUtil {
    private static final ResourceLocation XI_BAO_LOCATION = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/gui/xibao.png");
    @Getter
    @Setter
    private static ServerData lastServer;
    @Setter
    private static boolean isLastQuickPlay = false;
    //#if MC > 11902
    public static final List<Component> RE_AUTH_MESSAGES = Lists.newArrayList(
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.insufficientPrivileges")).plainCopy(),
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.invalidSession")).plainCopy(),
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.serversUnavailable")).plainCopy(),
            ComponentCompatApi.translatable("disconnect.loginFailedInfo", ComponentCompatApi.translatable("disconnect.loginFailedInfo.userBanned")).plainCopy());
    //#endif

    //#if MC < 11700
    //$$ @SuppressWarnings("deprecation")
    //#endif
    public static void renderXibao(@NotNull Screen screen) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        //#if MC > 11605
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, AutoReconnectUtil.XI_BAO_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //#else
        //$$ TweakMyClient.getMinecraftClient().getTextureManager().bind(AutoReconnectUtil.XI_BAO_LOCATION);
        //$$ RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //#endif
        bufferbuilder.begin(VertexFormatCompatApi.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, screen.height, 0.0D).uv(0F, 1F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(screen.width, screen.height, 0.0D).uv(1F, 1F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(screen.width, 0.0D, 0.0D).uv(1F, 0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0F, 0F).color(255, 255, 255, 255).endVertex();
        tesselator.end();
    }

    public static void reconnect(Screen screen) {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        ServerData serverInfo = AutoReconnectUtil.lastServer;

        if (AutoReconnectUtil.lastServer != null) {
            //#if MC > 11904
            //$$ ConnectScreen.startConnecting(screen, minecraft, ServerAddress.parseString(serverInfo.ip), serverInfo, AutoReconnectUtil.isLastQuickPlay);
            //#elseif MC > 11605
            ConnectScreen.startConnecting(screen, minecraft, ServerAddress.parseString(serverInfo.ip), serverInfo);
            //#else
            //$$ minecraft.setScreen(new ConnectScreen(screen, minecraft, serverInfo));
            //#endif
        }
    }

    //#if MC < 11903
    //$$ public static @NotNull String getTranslationKey(Component component) {
    //$$     return component instanceof TranslatableContents ? ((TranslatableContents) component).getKey() : "";
    //$$ }
    //#endif
}
