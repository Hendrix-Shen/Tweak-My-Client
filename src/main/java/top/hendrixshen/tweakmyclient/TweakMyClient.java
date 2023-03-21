package top.hendrixshen.tweakmyclient;

import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.tool.mixin.MixinAuditExecutor;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.event.RenderHandler;

public class TweakMyClient implements ClientModInitializer {
    @Getter
    @NotNull
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        MixinAuditExecutor.execute();
        Configs.init();
        RenderHandler.getInstance().init();

        TweakMyClientReference.getLogger().info("[{}]: Mod initialized - Version: {} ({})", TweakMyClientReference.getModName(), TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType());
    }
}
