package top.hendrixshen.TweakMyClient.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.gui.GuiConfigs;

import java.io.File;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static class Generic {
        private static final String PREFIX = String.format("%s.config.generic", Reference.MOD_ID);
        public static final ConfigInteger DAYLIGHT_OVERRIDE_TIME = new TranslatableConfigInteger(PREFIX, "daylightOverrideTime", 6000, 0, 24000);
        public static final ConfigHotkey OPEN_CONFIG_GUI = new TranslatableConfigHotkey(PREFIX, "openConfigGui", "T,C");
        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                DAYLIGHT_OVERRIDE_TIME,
                OPEN_CONFIG_GUI
        );

        public static final ImmutableList<ConfigHotkey> HOTKEYS = ImmutableList.of(
                OPEN_CONFIG_GUI
        );

        static {
            OPEN_CONFIG_GUI.getKeybind().setCallback((keyAction, iKeybind) -> {
                GuiConfigs.openGui(new GuiConfigs());
                return true;
            });
        }
    }

    public static class Feature {
        private static final String PREFIX = String.format("%s.config.feature_toggle", Reference.MOD_ID);
        public static final ConfigBooleanHotkeyed FEATURE_DAYLIGHT_OVERRIDE = new TranslatableConfigBooleanHotkeyed(PREFIX, "featureDaylightOverride", false, "");
        public static final ImmutableList<ConfigBooleanHotkeyed> OPTIONS = ImmutableList.of(
                FEATURE_DAYLIGHT_OVERRIDE
        );
    }

    public static class Disable {
        private static final String PREFIX = String.format("%s.config.disable_toggle", Reference.MOD_ID);
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_ENTITY_TNT_UPDATES = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientEntityTNTUpdates", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_ENTITY_WITHER_UPDATES = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientEntityWitherUpdates", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientEntityZombieVillagerUpdates", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_ENTITY_TNT_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableEntityTNTRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_ENTITY_WITHER_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableEntityWitherRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_ENTITY_ZOMBIE_VILLAGER_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableEntityZombieVillagerRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_RENDER_OVERLAY_FIRE = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableRenderOverlayFire", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_RENDER_OVERLAY_PUMPKIN = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableRenderOverlayPumpkin", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_SCOREBOARD_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableScoreboardRendering", false, "");

        public static final ImmutableList<ConfigBooleanHotkeyed> OPTIONS = ImmutableList.of(
                DISABLE_CLIENT_ENTITY_TNT_UPDATES,
                DISABLE_CLIENT_ENTITY_WITHER_UPDATES,
                DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES,
                DISABLE_ENTITY_TNT_RENDERING,
                DISABLE_ENTITY_ZOMBIE_VILLAGER_RENDERING,
                DISABLE_ENTITY_WITHER_RENDERING,
                DISABLE_RENDER_OVERLAY_FIRE,
                DISABLE_RENDER_OVERLAY_PUMPKIN,
                DISABLE_SCOREBOARD_RENDERING
        );
    }

    public static void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                ConfigUtils.readHotkeyToggleOptions(root, "DisableHotkey", "Disable", Disable.OPTIONS);
            }
        }
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkey", "Disable", Disable.OPTIONS);
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}
