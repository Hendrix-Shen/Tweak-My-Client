package top.hendrixshen.TweakMyClient.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.gui.GuiConfigs;
import top.hendrixshen.TweakMyClient.util.RayTraceUtils;

import java.io.File;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                ConfigUtils.readHotkeyToggleOptions(root, "DisableHotkey", "Disable", Disable.OPTIONS);
                ConfigUtils.readHotkeyToggleOptions(root, "FeatureHotkey", "Feature", Feature.OPTIONS);
            }
        }
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkey", "Disable", Disable.OPTIONS);
            ConfigUtils.writeHotkeyToggleOptions(root, "FeatureHotkey", "Feature", Feature.OPTIONS);
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

    private enum TargetBlockPositionPrintMode implements IConfigOptionListEntry {
        PUBLIC("public"),
        PRIVATE("private");

        private final String name;

        TargetBlockPositionPrintMode(String name) {
            this.name = name;
        }

        @Override
        public String getStringValue() {
            return this.name;
        }

        @Override
        public String getDisplayName() {
            return StringUtils.translate(String.format("%s.label.targetBlockPositionPrintMode.%s", Reference.MOD_ID, this.name));
        }

        @Override
        public IConfigOptionListEntry cycle(boolean forward) {
            int id = this.ordinal();

            if (forward) {
                if (++id >= values().length) {
                    id = 0;
                }
            } else {
                if (--id < 0) {
                    id = values().length - 1;
                }
            }
            return values()[id % values().length];
        }

        @Override
        public IConfigOptionListEntry fromString(String value) {
            for (TargetBlockPositionPrintMode mode : TargetBlockPositionPrintMode.values()) {
                if (mode.name.equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            return TargetBlockPositionPrintMode.PRIVATE;
        }
    }

    public static class Generic {
        private static final String PREFIX = String.format("%s.config.generic", Reference.MOD_ID);
        public static final ConfigInteger AUTO_RECONNECT_TIMER = new TranslatableConfigInteger(PREFIX, "autoReconnectTimer", 5, 0, 60);
        public static final ConfigInteger DAYLIGHT_OVERRIDE_TIME = new TranslatableConfigInteger(PREFIX, "daylightOverrideTime", 6000, 0, 24000);
        public static final ConfigHotkey GET_TARGET_BLOCK_POSITION = new TranslatableConfigHotkey(PREFIX, "getTargetBlockPosition", "");
        public static final ConfigHotkey OPEN_CONFIG_GUI = new TranslatableConfigHotkey(PREFIX, "openConfigGui", "T,C");
        public static final ConfigDouble TARGET_BLOCK_MAX_TRACE_DISTANCE = new TranslatableConfigDouble(PREFIX, "targetBlockMaxTraceDistance", 100, 0, 200);
        public static final ConfigString TARGET_BLOCK_POSITION_FORMAT = new TranslatableConfigString(PREFIX, "targetBlockPositionFormat", "I'm tracing this position [x: {X},y: {Y}, z: {Z}]");
        public static final ConfigOptionList TARGET_BLOCK_POSITION_PRINT_MODE = new TranslatableConfigOptionList(PREFIX, "targetBlockPositionPrintMode", TargetBlockPositionPrintMode.PRIVATE);

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                AUTO_RECONNECT_TIMER,
                DAYLIGHT_OVERRIDE_TIME,
                GET_TARGET_BLOCK_POSITION,
                OPEN_CONFIG_GUI,
                TARGET_BLOCK_MAX_TRACE_DISTANCE,
                TARGET_BLOCK_POSITION_FORMAT,
                TARGET_BLOCK_POSITION_PRINT_MODE
        );
        public static final ImmutableList<ConfigHotkey> HOTKEYS = ImmutableList.of(
                OPEN_CONFIG_GUI,
                GET_TARGET_BLOCK_POSITION
        );
        static {
            OPEN_CONFIG_GUI.getKeybind().setCallback((keyAction, iKeybind) -> {
                GuiConfigs.openGui(new GuiConfigs());
                return true;
            });
            GET_TARGET_BLOCK_POSITION.getKeybind().setCallback((action, key) -> {
                if (!Feature.FEATURE_GET_TARGET_BLOCK_POSITION.getBooleanValue()) {
                    return true;
                }
                MinecraftClient mc = TweakMyClient.minecraftClient;
                BlockPos blockPos = RayTraceUtils.getTargetedPosition(mc.world, mc.player, TARGET_BLOCK_MAX_TRACE_DISTANCE.getDoubleValue(), false);
                if (blockPos == null || mc.player == null) {
                    return false;
                }
                String str = Generic.TARGET_BLOCK_POSITION_FORMAT.getStringValue();
                str = str.replace("{X}", String.format("%d", blockPos.getX()));
                str = str.replace("{Y}", String.format("%d", blockPos.getY()));
                str = str.replace("{Z}", String.format("%d", blockPos.getZ()));
                TargetBlockPositionPrintMode mode = (TargetBlockPositionPrintMode) Generic.TARGET_BLOCK_POSITION_PRINT_MODE.getOptionListValue();
                switch (mode) {
                    case PUBLIC:
                        mc.player.sendChatMessage(str);
                        break;
                    case PRIVATE:
                        mc.player.networkHandler.onGameMessage(new GameMessageS2CPacket(new LiteralText(str), MessageType.CHAT, mc.player.getUuid()));
                        break;
                }
                return true;
            });
        }
    }

    public static class Feature {
        private static final String PREFIX = String.format("%s.config.feature_toggle", Reference.MOD_ID);
        public static final ConfigBooleanHotkeyed FEATURE_AUTO_RECONNECT = new TranslatableConfigBooleanHotkeyed(PREFIX, "featureAutoReconnect", false, "");
        public static final ConfigBooleanHotkeyed FEATURE_AUTO_RESPAWN = new TranslatableConfigBooleanHotkeyed(PREFIX, "featureAutoRespawn", false, "");
        public static final ConfigBooleanHotkeyed FEATURE_DAYLIGHT_OVERRIDE = new TranslatableConfigBooleanHotkeyed(PREFIX, "featureDaylightOverride", false, "");
        public static final ConfigBooleanHotkeyed FEATURE_GET_TARGET_BLOCK_POSITION = new TranslatableConfigBooleanHotkeyed(PREFIX, "featureGetTargetBlockPosition", false, "");

        public static final ImmutableList<ConfigBooleanHotkeyed> OPTIONS = ImmutableList.of(
                FEATURE_AUTO_RECONNECT,
                FEATURE_AUTO_RESPAWN,
                FEATURE_DAYLIGHT_OVERRIDE,
                FEATURE_GET_TARGET_BLOCK_POSITION
        );
    }

    public static class Disable {
        private static final String PREFIX = String.format("%s.config.disable_toggle", Reference.MOD_ID);
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_BLOCK_EVENTS = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientBlockEvents", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_ENTITY_TNT_UPDATES = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientEntityTNTUpdates", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_ENTITY_WITHER_UPDATES = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientEntityWitherUpdates", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableClientEntityZombieVillagerUpdates", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_ENTITY_TNT_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableEntityTNTRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_ENTITY_WITHER_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableEntityWitherRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_ENTITY_ZOMBIE_VILLAGER_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableEntityZombieVillagerRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_RENDER_OVERLAY_FIRE = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableRenderOverlayFire", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_RENDER_OVERLAY_PUMPKIN = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableRenderOverlayPumpkin", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_SCOREBOARD_RENDERING = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableScoreboardRendering", false, "");
        public static final ConfigBooleanHotkeyed DISABLE_SLOWDOWN = new TranslatableConfigBooleanHotkeyed(PREFIX, "disableSlowdown", false, "");

        public static final ImmutableList<ConfigBooleanHotkeyed> OPTIONS = ImmutableList.of(
                DISABLE_CLIENT_BLOCK_EVENTS,
                DISABLE_CLIENT_ENTITY_TNT_UPDATES,
                DISABLE_CLIENT_ENTITY_WITHER_UPDATES,
                DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES,
                DISABLE_ENTITY_TNT_RENDERING,
                DISABLE_ENTITY_ZOMBIE_VILLAGER_RENDERING,
                DISABLE_ENTITY_WITHER_RENDERING,
                DISABLE_RENDER_OVERLAY_FIRE,
                DISABLE_RENDER_OVERLAY_PUMPKIN,
                DISABLE_SCOREBOARD_RENDERING,
                DISABLE_SLOWDOWN
        );
    }
}
