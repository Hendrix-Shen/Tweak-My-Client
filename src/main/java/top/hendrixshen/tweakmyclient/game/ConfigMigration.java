package top.hendrixshen.tweakmyclient.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.Nullable;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigHandler;
import top.hendrixshen.magiclib.api.malilib.config.migration.ConfigMigrator;
import top.hendrixshen.magiclib.api.malilib.config.option.ConfigVec3iTupleList;
import top.hendrixshen.magiclib.api.malilib.config.option.ConfigVec3iTupleList.Entry;
import top.hendrixshen.magiclib.impl.malilib.config.MagicConfigHandlerImpl;
import top.hendrixshen.magiclib.impl.malilib.config.migration.ConfigRelocationMigrator;
import top.hendrixshen.magiclib.impl.malilib.config.migration.ConfigRelocationMigrator.MigrationMapping;
import top.hendrixshen.magiclib.impl.malilib.config.migration.VersionMigrator;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigVec3iTupleList;
import top.hendrixshen.tweakmyclient.SharedConstants;
import top.hendrixshen.tweakmyclient.game.Configs.ConfigCategory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConfigMigration {
    private static class V1ConfigCategory {
        public static final String GENERIC = "generic";
        public static final String PATCH = "patch";
        public static final String LIST = "list";
        public static final String COLOR = "color";
        public static final String FEATURE = "feature";
        public static final String DISABLE = "disable";
        public static final String DEBUG = "debug";
    }

    private static final ImmutableList<MigrationMapping> V1_TO_V2 = ImmutableList.of(
            // Generic
            new MigrationMapping(V1ConfigCategory.GENERIC, "autoDropInterval", ConfigCategory.FEATURE, "autoDropInterval"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "autoReconnectTimer", ConfigCategory.FEATURE, "autoReconnectInterval"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "breakAnimationMode", ConfigCategory.FEATURE, "customBlockHitBoxBreakAnimation"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "crystalBeamsDisableMode", ConfigCategory.FEATURE, "crystalBeamRenderRestrictionType"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "customBlockHitBoxOverlayDisableDepthTest", ConfigCategory.FEATURE, "customBlockHitBoxDepthTest"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "customBlockHitBoxOverlayFillRainbow", ConfigCategory.FEATURE, "customBlockHitBoxOverlayRainbow"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "customBlockHitBoxOverlayLinkedAdapter", ConfigCategory.FEATURE, "customBlockHitBoxLinkedAdapter"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "customBlockHitBoxOverlayOutlineRainbow", ConfigCategory.FEATURE, "customBlockHitBoxOutlineRainbow"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "customWindowTitleEnableActivity", ConfigCategory.FEATURE, "customWindowTitleActivitySupport"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "customWindowTitleRandomly", ConfigCategory.FEATURE, "customWindowTitleRandomly"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "daylightOverrideTime", ConfigCategory.FEATURE, "daylightOverrideTime"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "enderPortalRenderMode", ConfigCategory.PATCH, "endPortalRenderMode"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "lowHealthThreshold", ConfigCategory.GENERIC, "lowHealthWarningThreshold"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "targetBlockMaxTraceDistance", ConfigCategory.GENERIC, "getTargetBlockPositionMaxDistance"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "targetBlockPositionFormat", ConfigCategory.GENERIC, "getTargetBlockPositionMessage"),
            new MigrationMapping(V1ConfigCategory.GENERIC, "targetBlockPositionPrintMode", ConfigCategory.GENERIC, "getTargetBlockPositionMessageMode"),
            // Patch
            // NO-OP
            // List
            new MigrationMapping(V1ConfigCategory.LIST, "listAutoDropBlackList", ConfigCategory.FEATURE, "autoDropBlackList"),
            new MigrationMapping(V1ConfigCategory.LIST, "listAutoDropType", ConfigCategory.FEATURE, "autoDropType"),
            new MigrationMapping(V1ConfigCategory.LIST, "listAutoDropWhiteList", ConfigCategory.FEATURE, "listAutoDropWhiteList"),
            new MigrationMapping(V1ConfigCategory.LIST, "listBreakingRestrictionBoxType", ConfigCategory.FEATURE, "breakingRestrictionBoxType"),
            new MigrationMapping(V1ConfigCategory.LIST, "listCustomWindowTitle", ConfigCategory.FEATURE, "customWindowTitleList"),
            new MigrationMapping(V1ConfigCategory.LIST, "listCustomWindowTitleWithActivity", ConfigCategory.FEATURE, "customWindowTitleWithActivityList"),
            new MigrationMapping(V1ConfigCategory.LIST, "listDisableAttackEntity", ConfigCategory.FEATURE, "attackEntityRestrictionBlacklist"),
            new MigrationMapping(V1ConfigCategory.LIST, "listDisableClientEntityRendering", ConfigCategory.FEATURE, "entityRenderingRestrictionBlacklist"),
            new MigrationMapping(V1ConfigCategory.LIST, "listDisableClientEntityUpdates", ConfigCategory.FEATURE, "clientEntityUpdateRestrictionBlacklist"),
            new MigrationMapping(V1ConfigCategory.LIST, "listItemGlowingBlacklist", ConfigCategory.FEATURE, "itemGlintRestrictionBlacklist"),
            // Color
            new MigrationMapping(V1ConfigCategory.COLOR, "colorBlockHitBoxOverlayFill", ConfigCategory.FEATURE, "customBlockHitBoxOverlayColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorBlockHitBoxOverlayOutline", ConfigCategory.FEATURE, "customBlockHitBoxOutlineColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorBreakingRestrictionBoxBlacklistMode", ConfigCategory.FEATURE, "breakingRestrictionBoxBlacklistColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorBreakingRestrictionBoxWhitelistMode", ConfigCategory.FEATURE, "breakingRestrictionBoxWhitelistColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorGuiStart", ConfigCategory.FEATURE, "customGuiBackgroundStartColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorGuiStop", ConfigCategory.FEATURE, "customGuiBackgroundStopColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorSidebarContent", ConfigCategory.FEATURE, "customSidebarContentColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorSidebarTitle", ConfigCategory.FEATURE, "customSidebarTitleColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorWaterOpen", ConfigCategory.FEATURE, "openWaterColor"),
            new MigrationMapping(V1ConfigCategory.COLOR, "colorWaterShallow", ConfigCategory.FEATURE, "shallowWaterColor"),
            // Feature
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureAutoClimb", ConfigCategory.FEATURE, "autoClimb"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureAutoDrop", ConfigCategory.FEATURE, "autoDrop"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureAutoReconnect", ConfigCategory.FEATURE, "autoReconnect"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureAutoRespawn", ConfigCategory.FEATURE, "autoRespawn"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureAutoTotem", ConfigCategory.FEATURE, "autoTotem"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureBreakingRestrictionBox", ConfigCategory.FEATURE, "breakingRestrictionBox"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureCustomBlockHitBoxOverlayFill", ConfigCategory.FEATURE, "customBlockHitBoxOverlay"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureCustomBlockHitBoxOverlayOutline", ConfigCategory.FEATURE, "customBlockHitBoxOutline"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureCustomGuiBackgroundColor", ConfigCategory.FEATURE, "customGuiBackgroundColor"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureCustomSidebarBackgroundColor", ConfigCategory.FEATURE, "customSidebarBackgroundColor"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureCustomWindowIcon", ConfigCategory.FEATURE, "customWindowIcon"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureCustomWindowTitle", ConfigCategory.FEATURE, "customWindowTitle"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureDaylightOverride", ConfigCategory.FEATURE, "daylightOverride"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureGlobalEventListener", ConfigCategory.FEATURE, "globalEventListener"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureLowHealthWarning", ConfigCategory.FEATURE, "lowHealthWarning"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureOpenWaterHelper", ConfigCategory.FEATURE, "openWaterHelper"),
            new MigrationMapping(V1ConfigCategory.FEATURE, "featureUnfocusedCPU", ConfigCategory.FEATURE, "unfocusedCPU"),
            // Disable
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableAttackEntity", ConfigCategory.FEATURE, "attackEntityRestriction"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableClientBlockEvents", ConfigCategory.DISABLE, "disableClientBlockEvent"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableClientEntityInListRendering", ConfigCategory.FEATURE, "entityRenderingRestriction"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableClientEntityInListUpdates", ConfigCategory.FEATURE, "clientEntityUpdateRestriction"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableCrystalBeams", ConfigCategory.FEATURE, "crystalBeamRenderRestriction"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableItemGlowing", ConfigCategory.FEATURE, "itemGlintRestriction"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderBossBar", ConfigCategory.DISABLE, "disableBossBarRender"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderEffectBox", ConfigCategory.DISABLE, "disableEffectBoxRender"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderOverlayFire", ConfigCategory.DISABLE, "disableFireOverlayRender"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderOverlayPowderSnow", ConfigCategory.DISABLE, "disablePowderSnowOverlayRender"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderOverlayPumpkin", ConfigCategory.DISABLE, "disablePumpkinOverlayRender"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderScoreboard", ConfigCategory.DISABLE, "disableScoreboardRender"),
            new MigrationMapping(V1ConfigCategory.DISABLE, "disableRenderToast", ConfigCategory.DISABLE, "disableToastRender")
    );

    public static void setup() {
        MagicConfigHandlerImpl configHandler = SharedConstants.getConfigHandler();
        Supplier<Integer> oldVersionSuppler = () -> VersionMigrator.tryGetConfigVersion(configHandler.getLoadedJson());
        configHandler.registerMigrator(new ConfigRelocationMigrator(ConfigMigration.V1_TO_V2, handler -> oldVersionSuppler.get() == 1));
        configHandler.registerMigrator(new V1MigratorPatch());
        configHandler.registerMigrator(new VersionMigrator(2, handler -> oldVersionSuppler.get() == 1));
    }

    private static class V1MigratorPatch implements ConfigMigrator {
        @Override
        public boolean migrate(MagicConfigHandler configHandler) {
            JsonObject root = configHandler.getLoadedJson();
            boolean migrated = false;
            migrated |= this.migrantList(root);
            migrated |= this.migrantEnum(root);
            return migrated;
        }

        @Override
        public boolean shouldMigrate(MagicConfigHandler configHandler) {
            return VersionMigrator.tryGetConfigVersion(configHandler.getLoadedJson()) == 1;
        }

        private boolean migrantEnum(JsonObject root) {
            JsonObject jsonObj = JsonUtils.getNestedObject(root, "generic", false);

            if (jsonObj == null) {
                return false;
            }

            JsonElement element = jsonObj.get("crystalBeamsDisableMode");

            if (element == null || !element.isJsonPrimitive()) {
                return false;
            }

            JsonObject featureObj = JsonUtils.getNestedObject(root, "feature", true);
            Map<String, String> map = ImmutableMap.of(
                    "all", "block",
                    "fixed", "tracking_only",
                    "tracking", "fixed_only");
            String newValue = map.getOrDefault(element.getAsString(), "none");
            assert featureObj != null;
            featureObj.addProperty("crystalBeamRenderRestrictionType", newValue);
            root.add("feature", featureObj);
            return true;
        }

        private boolean migrantList(JsonObject root) {
            JsonObject jsonObj = JsonUtils.getNestedObject(root, "list", false);
            JsonObject featureObj = JsonUtils.getNestedObject(root, "feature", true);

            if (jsonObj == null) {
                return false;
            }

            boolean migrated = false;
            migrated |= this.migrantList(jsonObj, "listBreakingRestrictionBoxBlacklist", featureObj, "breakingRestrictionBoxBlacklist");
            migrated |= this.migrantList(jsonObj, "listBreakingRestrictionBoxWhitelist", featureObj, "breakingRestrictionBoxWhitelist");
            root.add("feature", featureObj);
            return migrated;
        }

        private boolean migrantList(JsonObject sourceListObj, String oldName, JsonObject destinationListObj, String newName) {
            JsonElement element = this.parseVec3iTupleList(sourceListObj, oldName);

            if (element == null) {
                return false;
            }

            destinationListObj.add(newName, element);
            SharedConstants.getLogger().info("[V1MigratorPatch-{}]Migrated {} to {}",
                    SharedConstants.getModName(), oldName, newName);
            return true;
        }

        @Nullable
        private JsonElement parseVec3iTupleList(JsonObject jsonObject, String name) {
            JsonElement element = jsonObject.get(name);

            if (element == null || !element.isJsonArray()) {
                return null;
            }

            List<Entry> collect = StreamSupport.stream(element.getAsJsonArray().spliterator(), false)
                    .filter(Objects::nonNull)
                    .filter(JsonElement::isJsonPrimitive)
                    .map(JsonElement::getAsJsonPrimitive)
                    .map(JsonElement::getAsString)
                    .map(this::parseVec3iTupleList)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            // Create a dummy config to transform json obj
            @SuppressWarnings("UnstableApiUsage")
            JsonElement result = new MagicConfigVec3iTupleList(null, null,
                    ImmutableList.copyOf(collect)).getAsJsonElement();
            return result;
        }

        private ConfigVec3iTupleList.Entry parseVec3iTupleList(String str) {
            String[] split = str.split(" ");

            if (split.length == 6) {
                try {
                    return new ConfigVec3iTupleList.Entry(
                            new Vec3i(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])),
                            new Vec3i(Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5])));
                } catch (NumberFormatException e) {
                    SharedConstants.getLogger().warn("[V1MigratorPatch-{}]Failed to parse vec3iTuple from str: {}",
                            SharedConstants.getModName(), str);
                }
            }

            return null;
        }
    }
}
