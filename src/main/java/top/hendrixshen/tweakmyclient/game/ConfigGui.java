package top.hendrixshen.tweakmyclient.game;

import fi.dy.masa.malilib.gui.GuiBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.hendrixshen.magiclib.impl.malilib.config.gui.MagicConfigGui;
import top.hendrixshen.magiclib.util.collect.ValueContainer;
import top.hendrixshen.tweakmyclient.SharedConstants;

public class ConfigGui extends MagicConfigGui {
    @Nullable
    private static ConfigGui currentInstance = null;

    public ConfigGui() {
        super(
                SharedConstants.getModIdentifier(),
                SharedConstants.getConfigManager(),
                SharedConstants.tr("config.gui.title", SharedConstants.getModVersion(), SharedConstants.getTranslatedModVersionType())
        );
    }

    @Override
    public void init() {
        super.init();
        ConfigGui.currentInstance = this;
    }

    @Override
    public void removed() {
        super.removed();
        ConfigGui.currentInstance = null;
    }

    @Override
    public boolean isDebug() {
        return Configs.debugMode.getBooleanValue();
    }

    public static void openGui() {
        GuiBase.openGui(new ConfigGui());
    }

    @Override
    public boolean hideUnAvailableConfigs() {
        return Configs.hideUnavailableConfigs.getBooleanValue();
    }

    public static @NotNull ValueContainer<ConfigGui> getCurrentInstance() {
        return ValueContainer.ofNullable(ConfigGui.currentInstance);
    }
}
