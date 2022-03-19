package top.hendrixshen.tweakmyclient.config;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.helper.ListCache;
import top.hendrixshen.tweakmyclient.util.StringUtil;

import java.io.File;
import java.util.List;

public class ConfigStorage implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = TweakMyClientReference.getModId() + ".json";

    public String getToggleLabel(String category) {
        return String.format("%s_toggle", category);
    }

    public String getHotkeyLabel(String category) {
        return String.format("%s_hotkey", category);
    }

    public List<ConfigBooleanHotkeyed> getCoveredList(List<IConfigBase> category) {
        List<ConfigBooleanHotkeyed> list = Lists.newArrayList();
        for (IConfigBase iConfigBase : category) {
            if (iConfigBase instanceof ConfigBooleanHotkeyed) {
                list.add((ConfigBooleanHotkeyed) iConfigBase);
            }
        }
        return list;
    }

    public void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();
                ConfigUtils.readConfigBase(root, ConfigCategory.COLOR, TweakMyClient.cm.getConfigsByCategory(ConfigCategory.COLOR));
                ConfigUtils.readConfigBase(root, ConfigCategory.GENERIC, TweakMyClient.cm.getConfigsByCategory(ConfigCategory.GENERIC));
                ConfigUtils.readConfigBase(root, ConfigCategory.LIST, TweakMyClient.cm.getConfigsByCategory(ConfigCategory.LIST));
                ConfigUtils.readHotkeyToggleOptions(root, getHotkeyLabel(ConfigCategory.DISABLE), getToggleLabel(ConfigCategory.DISABLE), getCoveredList(TweakMyClient.cm.getConfigsByCategory(ConfigCategory.DISABLE)));
            }
        }
    }

    public void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, ConfigCategory.COLOR, TweakMyClient.cm.getConfigsByCategory(ConfigCategory.COLOR));
            ConfigUtils.writeConfigBase(root, ConfigCategory.GENERIC, TweakMyClient.cm.getConfigsByCategory(ConfigCategory.GENERIC));
            ConfigUtils.writeConfigBase(root, ConfigCategory.LIST, TweakMyClient.cm.getConfigsByCategory(ConfigCategory.LIST));
            ConfigUtils.writeHotkeyToggleOptions(root, getHotkeyLabel(ConfigCategory.DISABLE), getToggleLabel(ConfigCategory.DISABLE), getCoveredList(TweakMyClient.cm.getConfigsByCategory(ConfigCategory.DISABLE)));
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
        ListCache.itemGlowingBlacklist = StringUtil.getItemStackSets(Configs.listItemGlowingBlacklist.getStrings());
        ListCache.itemAutoDropBlackList = StringUtil.getItemStackSets(Configs.listAutoDropBlackList.getStrings());
        ListCache.itemAutoDropWhiteList = StringUtil.getItemStackSets(Configs.listAutoDropWhiteList.getStrings());
    }

    @Override
    public void save() {
        saveToFile();
    }
}
