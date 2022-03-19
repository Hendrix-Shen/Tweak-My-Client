package top.hendrixshen.tweakmyclient.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.options.*;
import top.hendrixshen.magiclib.api.dependencyValidator.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependencyValidator.annotation.Dependency;
import top.hendrixshen.magiclib.api.malilib.annotation.Config;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.event.CallBacks;
import top.hendrixshen.tweakmyclient.fakeInterface.IMinecraft;
import top.hendrixshen.tweakmyclient.helper.AutoDropListType;
import top.hendrixshen.tweakmyclient.helper.EnderPortalRenderMode;
import top.hendrixshen.tweakmyclient.helper.TargetBlockPositionPrintMode;

import static top.hendrixshen.tweakmyclient.TweakMyClient.cm;

public class Configs {
    // Generic configs
    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigInteger autoReconnectTimer = cm.createInteger("autoReconnectTimer", 5, 0, 60);

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigInteger autoDropInterval = cm.createInteger("autoDropInterval", 0, 0, 1200);

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigString customWindowTitle = cm.createString("customWindowTitle", "Minecraft {mc_version} with TweakMyClient {tmc_version} | Player {mc_username} | FPS: {mc_fps}");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigString customWindowTitleWithActivity = cm.createString("customWindowTitleWithActivity", "Minecraft {mc_version} ({mc_activity}) with TweakMyClient {tmc_version} | Player {mc_username} | FPS: {mc_fps}");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigInteger daylightOverrideTime = cm.createInteger("daylightOverrideTime", 6000, 0, 24000);

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigOptionList enderPortalRenderMode = cm.createOptionList("enderPortalRenderMode", EnderPortalRenderMode.ACTUAL);

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigHotkey getTargetBlockPosition = cm.createHotkey("getTargetBlockPosition", "");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigDouble lowHealthThreshold = cm.createDouble("lowHealthThreshold", 6, 0, 1000);

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigHotkey memoryCleaner = cm.createHotkey("memoryCleaner", "");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigHotkey openConfigGui = cm.createHotkey("openConfigGui", "T,C");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigHotkey syncInventory = cm.createHotkey("syncInventory", "");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigHotkey syncBlocks = cm.createHotkey("syncBlocks", "");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigDouble targetBlockMaxTraceDistance = cm.createDouble("targetBlockMaxTraceDistance", 100, 0, 200);

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigString targetBlockPositionFormat = cm.createString("targetBlockPositionFormat", "I'm looking at [x: {X},y: {Y}, z: {Z}]");

    @Config(category = ConfigCategory.GENERIC)
    public static final ConfigOptionList targetBlockPositionPrintMode = cm.createOptionList("targetBlockPositionPrintMode", TargetBlockPositionPrintMode.PRIVATE);

    // Patch configs
    @Config(category = ConfigCategory.PATCH)
    public static final ConfigBoolean disableLitematicaEasyPlaceFailTip = cm.createBoolean("disableLitematicaEasyPlaceFailTip", false);

    @Config(category = ConfigCategory.PATCH)
    public static final ConfigBoolean endPortalRendererFix = cm.createBoolean("endPortalRendererFix", false);

    @Config(category = ConfigCategory.PATCH)
    public static final ConfigBoolean forceDebugInfoDetailed = cm.createBoolean("forceDebugInfoDetailed", false);

    @Config(category = ConfigCategory.PATCH)
    public static final ConfigBoolean forcePistonWithoutAffectByTool = cm.createBoolean("forcePistonWithoutAffectByTool", false);

    // List configs
    @Config(category = ConfigCategory.LIST)
    public static ConfigStringList listAutoDropBlackList = cm.createStringList("listAutoDropBlackList", ImmutableList.of("minecraft:bow", "minecraft:crossbow", "minecraft:diamond_axe", "minecraft:diamond_boots", "minecraft:diamond_chestplate", "minecraft:diamond_helmet", "minecraft:diamond_hoe", "minecraft:diamond_leggings", "minecraft:diamond_pickaxe", "minecraft:diamond_shovel", "minecraft:diamond_sword", "minecraft:elytra", "minecraft:enchanted_golden_apple", "minecraft:flint_and_steel", "minecraft:fishing_rod", "minecraft:golden_apple", "minecraft:golden_axe", "minecraft:golden_boots", "minecraft:golden_chestplate", "minecraft:golden_helmet", "minecraft:golden_hoe", "minecraft:golden_leggings", "minecraft:golden_pickaxe", "minecraft:golden_shovel", "minecraft:golden_sword", "minecraft:iron_axe", "minecraft:iron_boots", "minecraft:iron_chestplate", "minecraft:iron_helmet", "minecraft:iron_hoe", "minecraft:iron_leggings", "minecraft:iron_pickaxe", "minecraft:iron_shovel", "minecraft:iron_sword", "minecraft:netherite_axe", "minecraft:netherite_boots", "minecraft:netherite_chestplate", "minecraft:netherite_helmet", "minecraft:netherite_hoe", "minecraft:netherite_leggings", "minecraft:netherite_pickaxe", "minecraft:netherite_shovel", "minecraft:netherite_sword", "minecraft:shears", "minecraft:shield", "minecraft:totem_of_undying", "minecraft:trident", "minecraft:turtle_helmet"));

    @Config(category = ConfigCategory.LIST)
    public static ConfigOptionList listAutoDropType = cm.createOptionList("listAutoDropType", AutoDropListType.WHITELIST);

    @Config(category = ConfigCategory.LIST)
    public static ConfigStringList listAutoDropWhiteList = cm.createStringList("listAutoDropWhiteList", ImmutableList.of("minecraft:stone", "minecraft:dirt", "minecraft:cobblestone", "minecraft:gravel", "minecraft:rotten_flesh"));

    @Config(category = ConfigCategory.LIST)
    public static ConfigStringList listDisableAttackEntity = cm.createStringList("listDisableAttackEntity", ImmutableList.of("player"));

    @Config(category = ConfigCategory.LIST)
    public static ConfigStringList listDisableClientEntityUpdates = cm.createStringList("listDisableClientEntityUpdates", ImmutableList.of("zombi"));

    @Config(category = ConfigCategory.LIST)
    public static ConfigStringList listDisableClientEntityRendering = cm.createStringList("listDisableClientEntityRendering", ImmutableList.of("zombi"));

    @Config(category = ConfigCategory.LIST)
    public static ConfigStringList listItemGlowingBlacklist = cm.createStringList("listItemGlowingBlacklist", ImmutableList.of("minecraft:enchanted_book", "potion"));

    // Color configs
    @Config(category = ConfigCategory.COLOR)
    public static ConfigColor colorBlockOutside = cm.createColor("colorBlockOutside", "#66000000");

    @Config(category = ConfigCategory.COLOR)
    public static ConfigColor colorGuiStart = cm.createColor("colorGuiStart", "#C00F0F0F");

    @Config(category = ConfigCategory.COLOR)
    public static ConfigColor colorGuiStop = cm.createColor("colorGuiStop", "#D00F0F0F");

    @Config(category = ConfigCategory.COLOR)
    public static ConfigColor colorSidebarContent = cm.createColor("colorSidebarContent", "#4C000000");

    @Config(category = ConfigCategory.COLOR)
    public static ConfigColor colorSidebarTitle = cm.createColor("colorSidebarTitle", "#66000000");

    @Config(category = ConfigCategory.COLOR, dependencies = @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16")))
    public static ConfigColor colorWaterOpen = cm.createColor("colorWaterOpen", "#7F00FF00");

    @Config(category = ConfigCategory.COLOR, dependencies = @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16")))
    public static ConfigColor colorWaterShallow = cm.createColor("colorWaterShallow", "#7FFF0000");

    // Feature configs
    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureAutoClimb = cm.createBooleanHotkeyed("featureAutoClimb", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureAutoDrop = cm.createBooleanHotkeyed("featureAutoDrop", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureAutoReconnect = cm.createBooleanHotkeyed("featureAutoReconnect", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureAutoRespawn = cm.createBooleanHotkeyed("featureAutoRespawn", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureCustomBlockOutsideColor = cm.createBooleanHotkeyed("featureCustomBlockOutsideColor", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureCustomGuiBackgroundColor = cm.createBooleanHotkeyed("featureCustomGuiBackgroundColor", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureCustomSidebarBackgroundColor = cm.createBooleanHotkeyed("featureCustomSidebarBackgroundColor", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureCustomWindowIcon = cm.createBooleanHotkeyed("featureCustomWindowIcon", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureCustomWindowTitle = cm.createBooleanHotkeyed("featureCustomWindowTitle", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureDaylightOverride = cm.createBooleanHotkeyed("featureDaylightOverride", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureGlobalEventListener = cm.createBooleanHotkeyed("featureGlobalEventListener", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureLowHealthWarning = cm.createBooleanHotkeyed("featureLowHealthWarning", false, "");

    @Config(category = ConfigCategory.FEATURE, dependencies = @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16")))
    public static ConfigBooleanHotkeyed featureOpenWaterHelper = cm.createBooleanHotkeyed("featureOpenWaterHelper", false, "");

    @Config(category = ConfigCategory.FEATURE)
    public static ConfigBooleanHotkeyed featureUnfocusedCPU = cm.createBooleanHotkeyed("featureUnfocusedCPU", false, "");

    // Disable configs
    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableAttackEntity = cm.createBooleanHotkeyed("disableAttackEntity", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientBlockEvents = cm.createBooleanHotkeyed("disableClientBlockEvents", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityInListUpdates = cm.createBooleanHotkeyed("disableClientEntityInListUpdates", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityTNTUpdates = cm.createBooleanHotkeyed("disableClientEntityTNTUpdates", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityWitherUpdates = cm.createBooleanHotkeyed("disableClientEntityWitherUpdates", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityZombieVillagerUpdates = cm.createBooleanHotkeyed("disableClientEntityZombieVillagerUpdates", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityInListRendering = cm.createBooleanHotkeyed("disableClientEntityInListRendering", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityTNTRendering = cm.createBooleanHotkeyed("disableClientEntityTNTRendering", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityWitherRendering = cm.createBooleanHotkeyed("disableClientEntityWitherRendering", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableClientEntityZombieVillagerRendering = cm.createBooleanHotkeyed("disableClientEntityZombieVillagerRendering", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableFovAffectedBySpeed = cm.createBooleanHotkeyed("disableFovAffectedBySpeed", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableGuiShadowLayer = cm.createBooleanHotkeyed("disableGuiShadowLayer", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableItemGlowing = cm.createBooleanHotkeyed("disableItemGlowing", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableRenderBossBar = cm.createBooleanHotkeyed("disableRenderBossBar", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableRenderOverlayFire = cm.createBooleanHotkeyed("disableRenderOverlayFire", false, "");

    @Config(category = ConfigCategory.DISABLE, dependencies = @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.17")))
    public static ConfigBooleanHotkeyed disableRenderOverlayPowderSnow = cm.createBooleanHotkeyed("disableRenderOverlayPowderSnow", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableRenderOverlayPumpkin = cm.createBooleanHotkeyed("disableRenderOverlayPumpkin", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableRenderScoreboard = cm.createBooleanHotkeyed("disableRenderScoreboard", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableRenderToast = cm.createBooleanHotkeyed("disableRenderToast", false, "");

    @Config(category = ConfigCategory.DISABLE)
    public static ConfigBooleanHotkeyed disableSlowdown = cm.createBooleanHotkeyed("disableSlowdown", false, "");

    // Debug config
    @Config(category = ConfigCategory.DEBUG)
    public static ConfigBoolean debugMode = cm.createBoolean("debugMode", false);

    @Config(category = ConfigCategory.DEBUG, debug = true, devOnly = true)
    public static ConfigBoolean displayDevelopmentOnlyConfig = cm.createBoolean("displayDevelopmentOnlyConfig", false);

    @Config(category = ConfigCategory.DEBUG, debug = true)
    public static ConfigBoolean displayDisabledConfig = cm.createBoolean("displayDisabledConfig", false);

    @Config(category = ConfigCategory.DEBUG, debug = true)
    public static ConfigBoolean displayUnSupportMinecraftConfig = cm.createBoolean("displayUnSupportMinecraftConfig", false);

    public static void initCallbacks() {
        // Set callback for all BooleanHotkeyed config.
        cm.getAllConfigOptionStream().forEach(
                iConfigBase -> {
                    if (iConfigBase instanceof ConfigBooleanHotkeyed) {
                        ((ConfigBooleanHotkeyed) iConfigBase).getKeybind().setCallback(new CallBacks.KeyCallbackToggleBooleanConfigWithMessage((IConfigBoolean) iConfigBase, iConfigBase.getConfigGuiDisplayName()));
                    }
                }
        );

        // Generic config callbacks.
        getTargetBlockPosition.getKeybind().setCallback(CallBacks::getTargetBlockPositionCallback);
        openConfigGui.getKeybind().setCallback(CallBacks::openConfigGuiCallback);
        memoryCleaner.getKeybind().setCallback(CallBacks::memoryCleanerCallback);
        syncInventory.getKeybind().setCallback(CallBacks::syncInventoryCallback);
        syncBlocks.getKeybind().setCallback(CallBacks::syncBlocksCallback);

        // Feature config callbacks.
        featureCustomWindowIcon.setValueChangeCallback((callback) -> ((IMinecraft) TweakMyClient.getMinecraftClient()).refreshIcon());
        featureCustomWindowTitle.setValueChangeCallback(CallBacks::featureCustomWindowTitleCallback);

        // Disable config callbacks.
        disableRenderToast.setValueChangeCallback(CallBacks::disableRenderToastCallback);

        // Debug config callbacks.
        debugMode.setValueChangeCallback(CallBacks::debugModeCallBack);
        displayDevelopmentOnlyConfig.setValueChangeCallback(CallBacks::displayDevelopmentOnlyConfigCallBack);
        displayDisabledConfig.setValueChangeCallback(CallBacks::displayDisabledConfigConfigCallBack);
        displayUnSupportMinecraftConfig.setValueChangeCallback(CallBacks::displayUnSupportMinecraftConfigCallBack);

    }
}
