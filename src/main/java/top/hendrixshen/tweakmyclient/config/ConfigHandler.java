package top.hendrixshen.tweakmyclient.config;

import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.tweakmyclient.helper.ListCache;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public class ConfigHandler extends top.hendrixshen.magiclib.config.ConfigHandler {
    public ConfigHandler(String modId, ConfigManager configManager, int configVersion) {
        super(modId, configManager, configVersion);
    }

    @Override
    public void load() {
        super.load();
        ListCache.itemAutoDropBlackList = StringUtil.getItemStackSets(Configs.listAutoDropBlackList);
        ListCache.itemAutoDropWhiteList = StringUtil.getItemStackSets(Configs.listAutoDropWhiteList);
        ListCache.itemGlowingBlacklist = StringUtil.getItemStackSets(Configs.listItemGlowingBlacklist);
    }
}
