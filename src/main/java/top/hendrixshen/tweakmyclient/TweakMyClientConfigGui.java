package top.hendrixshen.tweakmyclient;

import lombok.Getter;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;
import top.hendrixshen.magiclib.malilib.impl.gui.ConfigGui;
import top.hendrixshen.tweakmyclient.config.ConfigCategory;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public class TweakMyClientConfigGui extends ConfigGui {
    @Getter(lazy = true)
    private static final TweakMyClientConfigGui instance = new TweakMyClientConfigGui(TweakMyClientReference.getModIdentifier(), ConfigCategory.GENERIC, TweakMyClientReference.getConfigHandler().configManager);;

    public TweakMyClientConfigGui(String identifier, String defaultTab, ConfigManager configManager) {
        super(identifier, defaultTab, configManager, () ->
                StringUtil.tr("gui.title",
                        TweakMyClientReference.getModVersion(),
                        StringUtil.trVersionType(TweakMyClientReference.getModVersion())));
    }
}
