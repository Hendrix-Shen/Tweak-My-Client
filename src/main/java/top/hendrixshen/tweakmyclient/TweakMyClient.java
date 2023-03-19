package top.hendrixshen.tweakmyclient;

import fi.dy.masa.malilib.event.RenderEventHandler;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.magiclib.tool.mixin.MixinAuditExecutor;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.event.RenderHandler;

public class TweakMyClient implements ClientModInitializer {
    @Getter
    @NotNull
    private static final Minecraft minecraftClient = Minecraft.getInstance();

    @Dependencies(
            //#if MC > 11902
            and = {
                    @Dependency(value = "jade", versionPredicate = ">=9.3.1", optional = true),
                    @Dependency(value = "wthit", versionPredicate = ">=5.10.0", optional = true)
            }
            //#elseif MC > 11701
            //$$ and = @Dependency(value = "wthit", versionPredicate = ">=4.12.0", optional = true)
            //#elseif MC > 11605
            //$$ and = @Dependency(value = "wthit", versionPredicate = ">=3.11.3", optional = true)
            //#elseif MC > 11502
            //$$ and = @Dependency(value = "wthit", versionPredicate = ">=2.10.15", optional = true)
            //#endif
    )
    @Override
    public void onInitializeClient() {
        MixinAuditExecutor.execute();
        Configs.init();
        RenderHandler.getInstance().init();

        TweakMyClientReference.getLogger().info("[{}]: Mod initialized - Version: {} ({})", TweakMyClientReference.getModName(), TweakMyClientReference.getModVersion(), TweakMyClientReference.getModVersionType());
    }
}
