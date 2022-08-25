package top.hendrixshen.tweakmyclient.config;

import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.tweakmyclient.helper.Cache;

public class ConfigHandler extends top.hendrixshen.magiclib.config.ConfigHandler {
    public ConfigHandler(String modId, ConfigManager configManager, int configVersion) {
        super(modId, configManager, configVersion);
    }

    @Override
    public void load() {
        super.load();
        Cache.getInstance().rebuildAllCache();
    }
}
