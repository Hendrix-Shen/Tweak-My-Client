package top.hendrixshen.tweakmyclient;

import fi.dy.masa.malilib.util.StringUtils;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.gui.ConfigGui;
import top.hendrixshen.tweakmyclient.config.ConfigCategory;

public class TweakMyClientConfigGui extends ConfigGui {
    private static final TweakMyClientConfigGui INSTANCE;

    public TweakMyClientConfigGui(String identifier, String defaultTab, ConfigManager configManager) {
        super(identifier, defaultTab, configManager, StringUtils.translate("tweakmyclient.gui.title", TweakMyClientReference.getModVersion(), StringUtils.translate(String.format("tweakmyclient.misc.versionType.%s", TweakMyClientReference.getModVersionType()))));
    }

    static {
        INSTANCE = new TweakMyClientConfigGui(TweakMyClientReference.getModId(), ConfigCategory.GENERIC, TweakMyClient.cm);
    }

    public static TweakMyClientConfigGui getInstance() {
        return INSTANCE;
    }
}
