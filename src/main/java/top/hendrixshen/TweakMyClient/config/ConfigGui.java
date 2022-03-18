package top.hendrixshen.tweakmyclient.config;

import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.magiclib.util.malilib.ConfigManager;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

public class ConfigGui extends top.hendrixshen.magiclib.impl.malilib.ConfigGui {
    private static final ConfigGui INSTANCE;

    public ConfigGui(String identifier, String defaultTab, ConfigManager configManager) {
        super(identifier, defaultTab, configManager, I18n.translate("tweakmyclient.gui.title", TweakMyClientReference.getModVersion(), I18n.translate(String.format("tweakmyclient.misc.versionType.%s", TweakMyClientReference.getModVersionType()))));
    }

    static {
        INSTANCE = new ConfigGui(TweakMyClientReference.getModId(), ConfigCategory.GENERIC, TweakMyClient.cm);
    }

    public static ConfigGui getInstance() {
        return INSTANCE;
    }
}
