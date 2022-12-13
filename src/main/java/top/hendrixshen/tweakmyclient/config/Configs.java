package top.hendrixshen.tweakmyclient.config;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.config.annotation.Config;
import top.hendrixshen.magiclib.config.annotation.Hotkey;
import top.hendrixshen.magiclib.config.annotation.Numeric;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientPredicate;
import top.hendrixshen.tweakmyclient.event.CallBacks;
import top.hendrixshen.tweakmyclient.helper.*;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;

import java.util.ArrayList;

public class Configs {
    // Generic configs
    @Numeric(maxValue = 1200, minValue = 0)
    @Config(category = ConfigCategory.GENERIC)
    public static int autoDropInterval = 0;

    @Numeric(maxValue = 60, minValue = 0)
    @Config(category = ConfigCategory.GENERIC)
    public static int autoReconnectTimer = 5;

    @Config(category = ConfigCategory.GENERIC, predicate = TweakMyClientPredicate.AllowBreakAnimation.class)
    public static BreakAnimationMode breakAnimationMode = BreakAnimationMode.NONE;

    @Config(category = ConfigCategory.GENERIC)
    public static CrystalBeamsDisableMode crystalBeamsDisableMode = CrystalBeamsDisableMode.ALL;

    @Config(category = ConfigCategory.GENERIC)
    public static boolean customBlockHitBoxOverlayDisableDepthTest = false;

    @Config(category = ConfigCategory.GENERIC)
    public static boolean customBlockHitBoxOverlayFillRainbow = false;

    @Numeric(maxValue = 100, minValue = 1)
    @Config(category = ConfigCategory.GENERIC)
    public static int customBlockHitBoxOverlayFillRainbowSpeed = 80;

    @Config(category = ConfigCategory.GENERIC)
    public static boolean customBlockHitBoxOverlayLinkedAdapter = true;

    @Config(category = ConfigCategory.GENERIC)
    public static boolean customBlockHitBoxOverlayOutlineRainbow = false;

    @Numeric(maxValue = 100, minValue = 1)
    @Config(category = ConfigCategory.GENERIC)
    public static int customBlockHitBoxOverlayOutlineRainbowSpeed = 80;

    @Config(category = ConfigCategory.GENERIC, dependencies = @Dependencies(not = @Dependency(value = "minecraft", versionPredicate = "<1.15")))
    public static boolean customWindowTitleEnableActivity = true;

    @Config(category = ConfigCategory.GENERIC)
    public static boolean customWindowTitleRandomly = false;

    @Numeric(maxValue = 24000, minValue = 0)
    @Config(category = ConfigCategory.GENERIC)
    public static int daylightOverrideTime = 6000;

    @Config(category = ConfigCategory.GENERIC)
    public static EnderPortalRenderMode enderPortalRenderMode = EnderPortalRenderMode.ACTUAL;

    @Hotkey()
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey getTargetBlockPosition;

    @Numeric(maxValue = 1000, minValue = 0)
    @Config(category = ConfigCategory.GENERIC)
    public static double lowHealthThreshold = 6;

    @Hotkey()
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey memoryCleaner;

    @Hotkey(hotkey = "T,C")
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey openConfigGui;

    @Hotkey()
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey syncInventory;

    @Hotkey()
    @Config(category = ConfigCategory.GENERIC)
    public static ConfigHotkey syncBlocks;

    @Numeric(maxValue = 200, minValue = 0)
    @Config(category = ConfigCategory.GENERIC)
    public static int targetBlockMaxTraceDistance = 100;

    @Config(category = ConfigCategory.GENERIC)
    public static String targetBlockPositionFormat = "I'm looking at [x: {X},y: {Y}, z: {Z}]";

    @Config(category = ConfigCategory.GENERIC)
    public static TargetBlockPositionPrintMode targetBlockPositionPrintMode = TargetBlockPositionPrintMode.PRIVATE;

    // Patch configs
    @Config(category = ConfigCategory.PATCH, dependencies = @Dependencies(not = @Dependency(value = "forgetmechunk")))
    public static boolean chunkEdgeLagFix = false;

    @Config(category = ConfigCategory.PATCH, dependencies = @Dependencies(and = @Dependency(value = "litematica"), not = @Dependency(value = "masa_gadget_mod", versionPredicate = ">=2.0.6")))
    public static boolean disableLitematicaEasyPlaceFailTip = false;

    @Config(category = ConfigCategory.PATCH, dependencies = @Dependencies(and = @Dependency(value = "litematica")))
    public static boolean disableLitematicaSchematicVersionCheck = false;

    @Config(category = ConfigCategory.PATCH)
    public static boolean disableResourcePackCompatCheck = false;

    @Config(category = ConfigCategory.PATCH)
    public static boolean endPortalRendererFix = false;

    @Config(category = ConfigCategory.PATCH)
    public static boolean forceDebugInfoDetailed = false;

    @Config(category = ConfigCategory.PATCH, dependencies = @Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16")))
    public static boolean forcePistonWithoutAffectByTool = false;

    @Config(category = ConfigCategory.PATCH,
            dependencies = @Dependencies(
                    and = @Dependency("litematica"),
                    or = {
                            @Dependency("jade"),
                            @Dependency("waila"),
                            @Dependency("wthit")
                    }
            )
    )
    public static boolean litematicaSchematicWailaCompat = false;

    @Config(category = ConfigCategory.PATCH, dependencies = @Dependencies(and = @Dependency("notenoughcrashes")))
    public static boolean notEnoughCrashesBlueScreenOfDeath = false;

    // List configs
    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listAutoDropBlackList = Lists.newArrayList("minecraft:bow", "minecraft:crossbow", "minecraft:diamond_axe", "minecraft:diamond_boots", "minecraft:diamond_chestplate", "minecraft:diamond_helmet", "minecraft:diamond_hoe", "minecraft:diamond_leggings", "minecraft:diamond_pickaxe", "minecraft:diamond_shovel", "minecraft:diamond_sword", "minecraft:elytra", "minecraft:enchanted_golden_apple", "minecraft:flint_and_steel", "minecraft:fishing_rod", "minecraft:golden_apple", "minecraft:golden_axe", "minecraft:golden_boots", "minecraft:golden_chestplate", "minecraft:golden_helmet", "minecraft:golden_hoe", "minecraft:golden_leggings", "minecraft:golden_pickaxe", "minecraft:golden_shovel", "minecraft:golden_sword", "minecraft:iron_axe", "minecraft:iron_boots", "minecraft:iron_chestplate", "minecraft:iron_helmet", "minecraft:iron_hoe", "minecraft:iron_leggings", "minecraft:iron_pickaxe", "minecraft:iron_shovel", "minecraft:iron_sword", "minecraft:netherite_axe", "minecraft:netherite_boots", "minecraft:netherite_chestplate", "minecraft:netherite_helmet", "minecraft:netherite_hoe", "minecraft:netherite_leggings", "minecraft:netherite_pickaxe", "minecraft:netherite_shovel", "minecraft:netherite_sword", "minecraft:shears", "minecraft:shield", "minecraft:totem_of_undying", "minecraft:trident", "minecraft:turtle_helmet");

    @Config(category = ConfigCategory.LIST)
    public static AutoDropListType listAutoDropType = AutoDropListType.WHITELIST;

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listAutoDropWhiteList = Lists.newArrayList("minecraft:stone", "minecraft:dirt", "minecraft:cobblestone", "minecraft:gravel", "minecraft:rotten_flesh");

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listBreakingRestrictionBoxBlacklist = Lists.newArrayList("-1 -1 -1 1 1 1");

    @Config(category = ConfigCategory.LIST)
    public static BreakingRestrictionBoxType listBreakingRestrictionBoxType = BreakingRestrictionBoxType.WHITELIST;

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listBreakingRestrictionBoxWhitelist = Lists.newArrayList("-1 -1 -1 1 1 1");

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listCustomWindowTitle = Lists.newArrayList("Minecraft {mc_version} with TweakMyClient {tmc_version} | Player {mc_username} | FPS: {mc_fps}");

    @Config(category = ConfigCategory.LIST, dependencies = @Dependencies(not = @Dependency(value = "minecraft", versionPredicate = "<1.15"), predicate = TweakMyClientPredicate.CustomWindowTitleEnableActivity.class))
    public static ArrayList<String> listCustomWindowTitleWithActivity = Lists.newArrayList("Minecraft {mc_version} ({mc_activity}) with TweakMyClient {tmc_version} | Player {mc_username} | FPS: {mc_fps}");

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listDisableAttackEntity = Lists.newArrayList("player");

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listDisableClientEntityUpdates = Lists.newArrayList("zombi");

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listDisableClientEntityRendering = Lists.newArrayList("zombi");

    @Config(category = ConfigCategory.LIST)
    public static ArrayList<String> listItemGlowingBlacklist = Lists.newArrayList("minecraft:enchanted_book", "potion");

    // Color configs
    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorBlockHitBoxOverlayOutline = Color4f.fromColor(StringUtils.getColor("#66000000", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorBlockHitBoxOverlayFill = Color4f.fromColor(StringUtils.getColor("#2CFFFF10", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorBreakingRestrictionBoxBlacklistMode = Color4f.fromColor(StringUtils.getColor("#7FFF0000", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorBreakingRestrictionBoxWhitelistMode = Color4f.fromColor(StringUtils.getColor("#7F00FF00", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorGuiStart = Color4f.fromColor(StringUtils.getColor("#C00F0F0F", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorGuiStop = Color4f.fromColor(StringUtils.getColor("#D00F0F0F", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorSidebarContent = Color4f.fromColor(StringUtils.getColor("#4C000000", 0));

    @Config(category = ConfigCategory.COLOR)
    public static Color4f colorSidebarTitle = Color4f.fromColor(StringUtils.getColor("#66000000", 0));

    @Config(category = ConfigCategory.COLOR, dependencies = @Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16")))
    public static Color4f colorWaterOpen = Color4f.fromColor(StringUtils.getColor("#7F00FF00", 0));

    @Config(category = ConfigCategory.COLOR, dependencies = @Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16")))
    public static Color4f colorWaterShallow = Color4f.fromColor(StringUtils.getColor("#7FFF0000", 0));

    // Feature configs
    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureAutoClimb = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureAutoDrop = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureAutoReconnect = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureAutoRespawn = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureAutoTotem = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureBreakingRestrictionBox = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureCustomBlockHitBoxOverlayFill = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureCustomBlockHitBoxOverlayOutline = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureCustomGuiBackgroundColor = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureCustomSidebarBackgroundColor = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureCustomWindowIcon = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureCustomWindowTitle = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureDaylightOverride = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureGlobalEventListener = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureLowHealthWarning = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE, dependencies = @Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16")))
    public static boolean featureOpenWaterHelper = false;

    @Hotkey()
    @Config(category = ConfigCategory.FEATURE)
    public static boolean featureUnfocusedCPU = false;

    // Disable configs
    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableAttackEntity = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientBlockEvents = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityInListUpdates = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityTNTUpdates = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityWitherUpdates = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityZombieVillagerUpdates = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityInListRendering = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityTNTRendering = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityWitherRendering = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableClientEntityZombieVillagerRendering = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableCrystalBeams = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableFovAffectedBySpeed = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableGuiShadowLayer = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableItemGlowing = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableRenderBossBar = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableRenderEffectBox = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableRenderOverlayFire = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE, dependencies = @Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.17")))
    public static boolean disableRenderOverlayPowderSnow = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableRenderOverlayPumpkin = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableRenderScoreboard = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableRenderToast = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableSlowdown = false;

    @Hotkey()
    @Config(category = ConfigCategory.DISABLE)
    public static boolean disableSwimming = false;

    // Debug config
    @Config(category = ConfigCategory.DEBUG)
    public static boolean debugMode = false;

    @Config(category = ConfigCategory.DEBUG, predicate = TweakMyClientPredicate.DebugMode.class)
    public static boolean debugExperimentalMode = false;

    @Config(category = ConfigCategory.DEBUG, dependencies = @Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.17")), predicate = TweakMyClientPredicate.ExperimentalMode.class)
    public static boolean expCustomBlockHitBoxOverlayLinkedAdapterSupportPointedDripstoneBlock = false;

    @Hotkey
    @Config(category = ConfigCategory.DEBUG, predicate = TweakMyClientPredicate.ExperimentalMode.class)
    public static ConfigHotkey expNullPointerExceptionTest;

    @Hotkey
    @Config(category = ConfigCategory.DEBUG, predicate = TweakMyClientPredicate.ExperimentalMode.class)
    public static ConfigHotkey expUnsafeIllegalPutTest;

    @Config(category = ConfigCategory.DEBUG, predicate = TweakMyClientPredicate.ExpXiBao.class)
    public static boolean expXiBao = false;

    public static void initCallbacks(ConfigManager cm) {
        // Set callback for all BooleanHotkeyed config.
        /* TODO
        Legacy MagicLib impl
        cm.forEach(
                iConfigBase -> {
                    if (iConfigBase instanceof ConfigBooleanHotkeyed) {
                        ((ConfigBooleanHotkeyed) iConfigBase).getKeybind().setCallback(new CallBacks.KeyCallbackToggleBooleanConfigWithMessage((IConfigBoolean) iConfigBase, iConfigBase.getConfigGuiDisplayName()));
                    }
                }
        );*/

        // Generic config callbacks.
        getTargetBlockPosition.getKeybind().setCallback(CallBacks::getTargetBlockPositionCallback);
        openConfigGui.getKeybind().setCallback(CallBacks::openConfigGuiCallback);
        memoryCleaner.getKeybind().setCallback(CallBacks::memoryCleanerCallback);
        syncBlocks.getKeybind().setCallback(CallBacks::syncBlocksCallback);
        syncInventory.getKeybind().setCallback(CallBacks::syncInventoryCallback);

        cm.setValueChangeCallback("customWindowTitleEnableActivity", CallBacks::customWindowTitleEnableActivityCallback);
        cm.setValueChangeCallback("customWindowTitleRandomly", CallBacks::featureCustomWindowTitleCallback);

        // List config callbacks.
        cm.setValueChangeCallback("listCustomWindowTitle", CallBacks::featureCustomWindowTitleCallback);
        cm.setValueChangeCallback("listCustomWindowTitleWithActivity", CallBacks::featureCustomWindowTitleCallback);

        // Feature config callbacks.
        cm.setValueChangeCallback("featureCustomBlockHitBoxOverlayFill", CallBacks::featureCustomBlockHitBoxOverlayFillCallBack);
        cm.setValueChangeCallback("featureCustomBlockHitBoxOverlayOutline", CallBacks::featureCustomBlockHitBoxOverlayOutlineCallBack);
        cm.setValueChangeCallback("featureCustomWindowIcon", option -> CustomWindowUtil.updateIcon());
        cm.setValueChangeCallback("featureCustomWindowTitle", CallBacks::featureCustomWindowTitleCallback);

        // Disable config callbacks.
        cm.setValueChangeCallback("disableRenderToast", CallBacks::disableRenderToastCallback);

        // Debug config callbacks.
        expNullPointerExceptionTest.getKeybind().setCallback(CallBacks::expNullPointerExceptionTestCallback);
        expUnsafeIllegalPutTest.getKeybind().setCallback(CallBacks::expUnsafeIllegalAllocateTestCallback);
        cm.setValueChangeCallback("debugMode", CallBacks::debugModeCallBack);
        cm.setValueChangeCallback("debugExperimentalMode", CallBacks::debugExperimentalModeCallBack);

        // Init
        CallBacks.featureCustomBlockHitBoxOverlayFillCallBack(null);
        CallBacks.featureCustomBlockHitBoxOverlayOutlineCallBack(null);
        CallBacks.debugModeCallBack(null);
    }
}
