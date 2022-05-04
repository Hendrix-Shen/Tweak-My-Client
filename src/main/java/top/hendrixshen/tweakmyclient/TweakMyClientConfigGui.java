package top.hendrixshen.tweakmyclient;

import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.gui.ConfigGui;
import top.hendrixshen.tweakmyclient.config.ConfigCategory;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public class TweakMyClientConfigGui extends ConfigGui {
    private final static TweakMyClientConfigGui INSTANCE = new TweakMyClientConfigGui(TweakMyClientReference.getModId(), ConfigCategory.GENERIC, TweakMyClientReference.getConfigHandler().configManager);

    public TweakMyClientConfigGui(String identifier, String defaultTab, ConfigManager configManager) {
        super(identifier, defaultTab, configManager, () -> StringUtil.tr("gui.title", TweakMyClientReference.getModVersion(), StringUtil.tr(String.format("misc.versionType.%s", TweakMyClientReference.getModVersionType()))));
    }

    public static TweakMyClientConfigGui getInstance() {
        return INSTANCE;
    }
}
