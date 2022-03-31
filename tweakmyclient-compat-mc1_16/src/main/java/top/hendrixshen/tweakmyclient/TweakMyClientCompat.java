package top.hendrixshen.tweakmyclient;

import fi.dy.masa.malilib.event.RenderEventHandler;
import net.fabricmc.api.ModInitializer;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.compat.event.RenderHandler;
import top.hendrixshen.tweakmyclient.compat.proxy.client.SharedConstantCompatImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.client.WindowCompatImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.entity.EntityCompatImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.entity.PlayerCompatImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.parser.ItemStackCompatImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.render.BufferBuilderCompatImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.screen.ConnectionCompatScreenImpl;
import top.hendrixshen.tweakmyclient.compat.proxy.screen.ScreenCompatImpl;

public class TweakMyClientCompat implements ModInitializer {
    @Override
    public void onInitialize() {
        TweakMyClient.getLogger().info("[{}]: Compat module for {} loaded. Starting compat api...", TweakMyClientCompatReference.getModName(), TweakMyClientCompatReference.getCompatVersion());

        SharedConstantCompatImpl.initCompat();
        WindowCompatImpl.initCompat();

        EntityCompatImpl.initCompat();
        PlayerCompatImpl.initCompat();

        ItemStackCompatImpl.initCompat();

        BufferBuilderCompatImpl.initCompat();

        ConnectionCompatScreenImpl.initCompat();
        ScreenCompatImpl.initCompat();

        RenderEventHandler.getInstance().registerWorldLastRenderer(RenderHandler.getInstance());

        TweakMyClient.getLogger().info("[{}]: Compat initialized - Version: {} ({})", TweakMyClientCompatReference.getModName(), TweakMyClientCompatReference.getModVersion(), TweakMyClientCompatReference.getModVersionType());
    }
}
