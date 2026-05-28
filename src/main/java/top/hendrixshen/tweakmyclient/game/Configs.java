package top.hendrixshen.tweakmyclient.game;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction.ListType;
import top.hendrixshen.magiclib.MagicLib;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.magiclib.api.event.minecraft.MinecraftListener;
import top.hendrixshen.magiclib.api.event.minecraft.render.RenderLevelListener;
import top.hendrixshen.magiclib.api.malilib.annotation.Config;
import top.hendrixshen.magiclib.api.malilib.annotation.Statistic;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigHandler;
import top.hendrixshen.magiclib.api.malilib.config.MagicConfigManager;
import top.hendrixshen.magiclib.api.malilib.config.option.ConfigVec3iTupleList.Entry;
import top.hendrixshen.magiclib.impl.malilib.config.MagicConfigFactory;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigBoolean;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigBooleanHotkeyed;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigColor;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigDouble;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigHotkey;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigInteger;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigOptionList;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigString;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigStringList;
import top.hendrixshen.magiclib.impl.malilib.config.option.MagicConfigVec3iTupleList;
import top.hendrixshen.magiclib.impl.malilib.config.restriction.EntityTypeRestriction;
import top.hendrixshen.tweakmyclient.SharedConstants;
import top.hendrixshen.tweakmyclient.api.event.LocalPlayerListener;
import top.hendrixshen.tweakmyclient.impl.config.AreaBoxEitherRestriction;
import top.hendrixshen.tweakmyclient.impl.config.EitherUsageRestriction.EitherListType;
import top.hendrixshen.tweakmyclient.impl.config.ItemStackRestriction;
import top.hendrixshen.tweakmyclient.impl.feature.autoDrop.AutoDropHandler;
import top.hendrixshen.tweakmyclient.impl.feature.autoTotem.AutoTotemHandler;
import top.hendrixshen.tweakmyclient.impl.feature.breakingRestrictionBox.RestrictionBoxRenderer;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamRenderRestriction.CrystalBeamRenderRestrictionMode;
import top.hendrixshen.tweakmyclient.impl.feature.customBlockHitBoxOverlay.BreakAnimationMode;
import top.hendrixshen.tweakmyclient.impl.feature.customBlockHitBoxOverlay.CustomBlockHitBoxRenderer;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowIcon.CustomIconHelper;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowTitle.CustomWindowTitleHandler;
import top.hendrixshen.tweakmyclient.impl.generic.memoryCleaner.MemoryCleaner;
import top.hendrixshen.tweakmyclient.impl.generic.syncBlocks.BlockRefresher;
import top.hendrixshen.tweakmyclient.impl.generic.syncInventory.InventoryRefresher;
import top.hendrixshen.tweakmyclient.impl.generic.targetBlockPosition.TargetBlockPositionPrintMode;
import top.hendrixshen.tweakmyclient.impl.generic.targetBlockPosition.TargetBlockPositionPrinter;
import top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix.EndPortalRenderMode;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11502
import top.hendrixshen.tweakmyclient.impl.feature.openWaterHelper.OpenWaterHelperRenderer;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;

@SuppressWarnings("UnstableApiUsage")
public class Configs {
    private static final MagicConfigManager cm = SharedConstants.getConfigManager();
    private static final MagicConfigFactory cf = Configs.cm.getConfigFactory();

    // Generics
    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigHotkey getTargetBlockPosition = Configs.cf.newConfigHotkey("getTargetBlockPosition");

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigInteger getTargetBlockPositionMaxDistance = Configs.cf.newConfigInteger("getTargetBlockPositionMaxDistance", 64, 0, 200);

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigString getTargetBlockPositionMessage = Configs.cf.newConfigString("getTargetBlockPositionMessage", "I'm looking at [x: {X}, y: {Y}, z: {Z}]");

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigOptionList getTargetBlockPositionMessageMode = Configs.cf.newConfigOptionList("getTargetBlockPositionMessageMode", TargetBlockPositionPrintMode.DEFAULT);

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigHotkey memoryCleaner = Configs.cf.newConfigHotkey("memoryCleaner");

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigHotkey openConfigGui = Configs.cf.newConfigHotkey("openConfigGui", "T,C");

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigHotkey syncInventory = Configs.cf.newConfigHotkey("syncInventory");

    @Config(category = ConfigCategory.GENERIC)
    public static final MagicConfigHotkey syncBlocks = Configs.cf.newConfigHotkey("syncBlocks");

    // Features
    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed attackEntityRestriction = Configs.cf.newConfigBooleanHotkeyed("attackEntityRestriction", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList attackEntityRestrictionBlacklist = Configs.cf.newConfigStringList("attackEntityRestrictionBlacklist", ImmutableList.of("player"));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList attackEntityRestrictionType = Configs.cf.newConfigOptionList("attackEntityRestrictionType", ListType.NONE);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList attackEntityRestrictionWhitelist = Configs.cf.newConfigStringList("attackEntityRestrictionWhitelist", ImmutableList.of("zombie"));

    public static final EntityTypeRestriction attackEntityRestrictionList = new EntityTypeRestriction();

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed autoClimb = Configs.cf.newConfigBooleanHotkeyed("autoClimb", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed autoDrop = Configs.cf.newConfigBooleanHotkeyed("autoDrop", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList autoDropBlackList = Configs.cf.newConfigStringList("autoDropBlackList", ImmutableList.of("minecraft:bow", "minecraft:crossbow", "minecraft:diamond_axe", "minecraft:diamond_boots", "minecraft:diamond_chestplate", "minecraft:diamond_helmet", "minecraft:diamond_hoe", "minecraft:diamond_leggings", "minecraft:diamond_pickaxe", "minecraft:diamond_shovel", "minecraft:diamond_sword", "minecraft:elytra", "minecraft:enchanted_golden_apple", "minecraft:flint_and_steel", "minecraft:fishing_rod", "minecraft:golden_apple", "minecraft:golden_axe", "minecraft:golden_boots", "minecraft:golden_chestplate", "minecraft:golden_helmet", "minecraft:golden_hoe", "minecraft:golden_leggings", "minecraft:golden_pickaxe", "minecraft:golden_shovel", "minecraft:golden_sword", "minecraft:iron_axe", "minecraft:iron_boots", "minecraft:iron_chestplate", "minecraft:iron_helmet", "minecraft:iron_hoe", "minecraft:iron_leggings", "minecraft:iron_pickaxe", "minecraft:iron_shovel", "minecraft:iron_sword", "minecraft:netherite_axe", "minecraft:netherite_boots", "minecraft:netherite_chestplate", "minecraft:netherite_helmet", "minecraft:netherite_hoe", "minecraft:netherite_leggings", "minecraft:netherite_pickaxe", "minecraft:netherite_shovel", "minecraft:netherite_sword", "minecraft:shears", "minecraft:shield", "minecraft:totem_of_undying", "minecraft:trident", "minecraft:turtle_helmet"));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigInteger autoDropInterval = Configs.cf.newConfigInteger("autoDropInterval", 0, 0, 1200);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList autoDropType = Configs.cf.newConfigOptionList("autoDropType", ListType.WHITELIST);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList autoDropWhiteList = Configs.cf.newConfigStringList("autoDropWhiteList", ImmutableList.of("minecraft:stone", "minecraft:dirt", "minecraft:cobblestone", "minecraft:gravel", "minecraft:rotten_flesh"));

    public static final ItemStackRestriction autoDropRestriction = new ItemStackRestriction();

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed autoReconnect = Configs.cf.newConfigBooleanHotkeyed("autoReconnect", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigInteger autoReconnectInterval = Configs.cf.newConfigInteger("autoReconnectInterval", 5, 0, 300);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed autoRespawn = Configs.cf.newConfigBooleanHotkeyed("autoRespawn", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed autoTotem = Configs.cf.newConfigBooleanHotkeyed("autoTotem", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed breakingRestrictionBox = Configs.cf.newConfigBooleanHotkeyed("breakingRestrictionBox", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigVec3iTupleList breakingRestrictionBoxBlacklist = Configs.cf.newConfigVec3iTupleList("breakingRestrictionBoxBlacklist", ImmutableList.of(new Entry(new Vec3i(-1, -1, -1), new Vec3i(1, 1, 1))));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor breakingRestrictionBoxBlacklistColor = Configs.cf.newConfigColor("breakingRestrictionBoxBlacklistColor", "#7FFF0000");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList breakingRestrictionBoxType = Configs.cf.newConfigOptionList("breakingRestrictionBoxType", EitherListType.WHITELIST);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigVec3iTupleList breakingRestrictionBoxWhitelist = Configs.cf.newConfigVec3iTupleList("breakingRestrictionBoxWhitelist", ImmutableList.of(new Entry(new Vec3i(-1, -1, -1), new Vec3i(1, 1, 1))));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor breakingRestrictionBoxWhitelistColor = Configs.cf.newConfigColor("breakingRestrictionBoxWhitelistColor", "#7F00FF00");

    public static final AreaBoxEitherRestriction breakingRestrictionBoxRestriction = new AreaBoxEitherRestriction();

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed breakingRestrictionFlat = Configs.cf.newConfigBooleanHotkeyed("breakingRestrictionFlat", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed clientEntityUpdateRestriction = Configs.cf.newConfigBooleanHotkeyed("clientEntityUpdateRestriction", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList clientEntityUpdateRestrictionBlacklist = Configs.cf.newConfigStringList("clientEntityUpdateRestrictionBlacklist", ImmutableList.of("zombie"));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList clientEntityUpdateRestrictionType = Configs.cf.newConfigOptionList("clientEntityUpdateRestrictionType", ListType.NONE);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList clientEntityUpdateRestrictionWhitelist = Configs.cf.newConfigStringList("clientEntityUpdateRestrictionWhitelist", ImmutableList.of("villager"));

    public static final EntityTypeRestriction clientEntityUpdateRestrictionList = new EntityTypeRestriction();

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed crystalBeamRenderRestriction = Configs.cf.newConfigBooleanHotkeyed("crystalBeamRenderRestriction", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList crystalBeamRenderRestrictionType = Configs.cf.newConfigOptionList("crystalBeamRenderRestrictionType", CrystalBeamRenderRestrictionMode.DEFAULT);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList customBlockHitBoxBreakAnimation = Configs.cf.newConfigOptionList("customBlockHitBoxBreakAnimation", BreakAnimationMode.DEFAULT);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBoolean customBlockHitBoxLinkedAdapter = Configs.cf.newConfigBoolean("customBlockHitBoxLinkedAdapter", true);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBoolean customBlockHitBoxDepthTest = Configs.cf.newConfigBoolean("customBlockHitBoxDepthTest", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed customBlockHitBoxOverlay = Configs.cf.newConfigBooleanHotkeyed("customBlockHitBoxOverlay", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor customBlockHitBoxOverlayColor = Configs.cf.newConfigColor("customBlockHitBoxOverlayColor", "#2CFFFF10");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBoolean customBlockHitBoxOverlayRainbow = Configs.cf.newConfigBoolean("customBlockHitBoxOverlayRainbow", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigInteger customBlockHitBoxOverlayRainbowSpeed = Configs.cf.newConfigInteger("customBlockHitBoxOverlayRainbowSpeed", 80, 1, 100);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed customBlockHitBoxOutline = Configs.cf.newConfigBooleanHotkeyed("customBlockHitBoxOutline", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor customBlockHitBoxOutlineColor = Configs.cf.newConfigColor("customBlockHitBoxOutlineColor", "#66000000");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBoolean customBlockHitBoxOutlineRainbow = Configs.cf.newConfigBoolean("customBlockHitBoxOutlineRainbow", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigInteger customBlockHitBoxOutlineRainbowSpeed = Configs.cf.newConfigInteger("customBlockHitBoxOutlineRainbowSpeed", 80, 1, 100);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed customGuiBackgroundColor = Configs.cf.newConfigBooleanHotkeyed("customGuiBackgroundColor", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor customGuiBackgroundStartColor = Configs.cf.newConfigColor("customGuiBackgroundStartColor", "#C00F0F0F");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor customGuiBackgroundStopColor = Configs.cf.newConfigColor("customGuiBackgroundStopColor", "#D00F0F0F");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed customSidebarBackgroundColor = Configs.cf.newConfigBooleanHotkeyed("customSidebarBackgroundColor", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor customSidebarContentColor = Configs.cf.newConfigColor("customSidebarContentColor", "#4C000000");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor customSidebarTitleColor = Configs.cf.newConfigColor("customSidebarTitleColor", "#66000000");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed customWindowIcon = Configs.cf.newConfigBooleanHotkeyed("customWindowIcon", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed customWindowTitle = Configs.cf.newConfigBooleanHotkeyed("customWindowTitle", false);

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">1.14.4"))
    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBoolean customWindowTitleActivitySupport = Configs.cf.newConfigBoolean("customWindowTitleActivitySupport", true);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList customWindowTitleList = Configs.cf.newConfigStringList("customWindowTitleList", ImmutableList.of("Minecraft {mc_version} with TweakMyClient {tmc_version} | Player {mc_username} | FPS: {mc_fps}"));

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.15"))
    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList customWindowTitleWithActivityList = Configs.cf.newConfigStringList("customWindowTitleWithActivityList", ImmutableList.of("Minecraft {mc_version} ({mc_activity}) with TweakMyClient {tmc_version} | Player {mc_username} | FPS: {mc_fps}"));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBoolean customWindowTitleRandomly = Configs.cf.newConfigBoolean("customWindowTitleRandomly", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed daylightOverride = Configs.cf.newConfigBooleanHotkeyed("daylightOverride", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigInteger daylightOverrideTime = Configs.cf.newConfigInteger("daylightOverrideTime", 6000, 0, 24000);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed entityRenderingRestriction = Configs.cf.newConfigBooleanHotkeyed("entityRenderingRestriction", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList entityRenderingRestrictionBlacklist = Configs.cf.newConfigStringList("entityRenderingRestrictionBlacklist", ImmutableList.of("zombie"));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList entityRenderingRestrictionType = Configs.cf.newConfigOptionList("entityRenderingRestrictionType", ListType.NONE);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList entityRenderingRestrictionWhitelist = Configs.cf.newConfigStringList("entityRenderingRestrictionWhitelist", ImmutableList.of("villager"));

    public static final EntityTypeRestriction entityRenderingRestrictionList = new EntityTypeRestriction();

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed globalEventListener = Configs.cf.newConfigBooleanHotkeyed("globalEventListener", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed itemGlintRestriction = Configs.cf.newConfigBooleanHotkeyed("itemGlintRestriction", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList itemGlintRestrictionBlacklist = Configs.cf.newConfigStringList("itemGlintRestrictionBlacklist", ImmutableList.of("minecraft:enchanted_book", "potion"));

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigOptionList itemGlintRestrictionType = Configs.cf.newConfigOptionList("itemGlintRestrictionType", ListType.WHITELIST);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigStringList itemGlintRestrictionWhiteList = Configs.cf.newConfigStringList("itemGlintRestrictionWhiteList");

    public static final ItemStackRestriction itemGlintRestrictionList = new ItemStackRestriction();

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed lowHealthWarning = Configs.cf.newConfigBooleanHotkeyed("lowHealthWarning", false);

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigDouble lowHealthWarningThreshold = Configs.cf.newConfigDouble("lowHealthWarningThreshold", 6, 0, Integer.MAX_VALUE);

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16"))
    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed openWaterHelper = Configs.cf.newConfigBooleanHotkeyed("openWaterHelper", false);

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16"))
    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor openWaterColor = Configs.cf.newConfigColor("openWaterColor", "#7F00FF00");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed preventIntentionalGameDesign = Configs.cf.newConfigBooleanHotkeyed("preventIntentionalGameDesign", false);

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16"))
    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigColor shallowWaterColor = Configs.cf.newConfigColor("shallowWaterColor", "#2CFFFF10");

    @Config(category = ConfigCategory.FEATURE)
    public static final MagicConfigBooleanHotkeyed unfocusedCPU = Configs.cf.newConfigBooleanHotkeyed("unfocusedCPU", false);

    // Patches
    @Dependencies(
            require = @Dependency(value = "minecraft", versionPredicates = "<1.20"),
            conflict = @Dependency(value = "forgetmechunk")
    )
    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean chunkEdgeLagFix = Configs.cf.newConfigBoolean("chunkEdgeLagFix", false);

    @Dependencies(
            require = @Dependency(value = "litematica"),
            conflict = @Dependency(value = "masa_gadget_mod", versionPredicates = ">=2.0.6")
    )
    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean disableLitematicaEasyPlaceFailTip = Configs.cf.newConfigBoolean("disableLitematicaEasyPlaceFailTip", false);

    @Dependencies(require = @Dependency(value = "litematica"))
    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean disableLitematicaSchematicVersionCheck = Configs.cf.newConfigBoolean("disableLitematicaSchematicVersionCheck", false);

    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean disableResourcePackIncompatibleTip = Configs.cf.newConfigBoolean("disableResourcePackIncompatibleTip", false);

    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean endPortalRendererFix = Configs.cf.newConfigBoolean("endPortalRendererFix", false);

    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigOptionList endPortalRenderMode = Configs.cf.newConfigOptionList("endPortalRenderMode", EndPortalRenderMode.DEFAULT);

    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean forceDebugInfoDetailed = Configs.cf.newConfigBoolean("forceDebugInfoDetailed", false);

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.16"))
    @Config(category = ConfigCategory.PATCH)
    public static final MagicConfigBoolean forcePistonWithoutAffectByTool = Configs.cf.newConfigBoolean("forcePistonWithoutAffectByTool", false);

    // Disables
    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableBossBarRender = Configs.cf.newConfigBooleanHotkeyed("disableBossBarRender", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableClientBlockEvent = Configs.cf.newConfigBooleanHotkeyed("disableClientBlockEvent", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableEffectBoxRender = Configs.cf.newConfigBooleanHotkeyed("disableEffectBoxRender", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableFireOverlayRender = Configs.cf.newConfigBooleanHotkeyed("disableFireOverlayRender", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableFovAffectedBySpeed = Configs.cf.newConfigBooleanHotkeyed("disableFovAffectedBySpeed", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableGuiShadowLayer = Configs.cf.newConfigBooleanHotkeyed("disableGuiShadowLayer", false);

    @Dependencies(require = @Dependency(value = "minecraft", versionPredicates = ">=1.17"))
    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disablePowderSnowOverlayRender = Configs.cf.newConfigBooleanHotkeyed("disablePowderSnowOverlayRender", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disablePumpkinOverlayRender = Configs.cf.newConfigBooleanHotkeyed("disablePumpkinOverlayRender", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableScoreboardRender = Configs.cf.newConfigBooleanHotkeyed("disableScoreboardRender", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableSlowdown = Configs.cf.newConfigBooleanHotkeyed("disableSlowdown", false);

    @Config(category = ConfigCategory.DISABLE)
    public static final MagicConfigBooleanHotkeyed disableToastRender = Configs.cf.newConfigBooleanHotkeyed("disableToastRender", false);

    // Debug
    @Config(category = ConfigCategory.DEBUG)
    public static final MagicConfigBoolean debugMode = Configs.cf.newConfigBoolean("debugMode", false);

    @Config(category = ConfigCategory.DEBUG, debugOnly = true)
    public static final MagicConfigBoolean hideUnavailableConfigs = Configs.cf.newConfigBoolean("hideUnavailableConfigs", true);

    @Statistic(hotkey = false)
    @Config(category = ConfigCategory.DEBUG, debugOnly = true)
    public static final MagicConfigHotkey resetConfigStatistic = Configs.cf.newConfigHotkey("resetConfigStatistic");

    public static void postDeserialize(MagicConfigHandler configHandler) {
        Configs.autoDropRestriction.setListType((ListType) Configs.autoDropType.getOptionListValue());
        Configs.autoDropRestriction.setListContents(Configs.autoDropBlackList.getStrings(), Configs.autoDropWhiteList.getStrings());
        Configs.breakingRestrictionBoxRestriction.setListType((EitherListType) Configs.breakingRestrictionBoxType.getOptionListValue());
        Configs.breakingRestrictionBoxRestriction.setListContents(Configs.breakingRestrictionBoxBlacklist.getVec3iTupleList(), Configs.breakingRestrictionBoxWhitelist.getVec3iTupleList());
        Configs.attackEntityRestrictionList.setListType((ListType) Configs.attackEntityRestrictionType.getOptionListValue());
        Configs.attackEntityRestrictionList.setListContents(Configs.attackEntityRestrictionBlacklist.getStrings(), Configs.attackEntityRestrictionWhitelist.getStrings());
        Configs.entityRenderingRestrictionList.setListType((ListType) Configs.entityRenderingRestrictionType.getOptionListValue());
        Configs.entityRenderingRestrictionList.setListContents(Configs.entityRenderingRestrictionBlacklist.getStrings(), Configs.entityRenderingRestrictionWhitelist.getStrings());
        Configs.clientEntityUpdateRestrictionList.setListType((ListType) Configs.clientEntityUpdateRestrictionType.getOptionListValue());
        Configs.clientEntityUpdateRestrictionList.setListContents(Configs.clientEntityUpdateRestrictionBlacklist.getStrings(), Configs.clientEntityUpdateRestrictionWhitelist.getStrings());
        Configs.itemGlintRestrictionList.setListType((ListType) Configs.itemGlintRestrictionType.getOptionListValue());
        Configs.itemGlintRestrictionList.setListContents(Configs.itemGlintRestrictionBlacklist.getStrings(), Configs.itemGlintRestrictionWhiteList.getStrings());
        RestrictionBoxRenderer.getInstance().updateOutlineColor();
    }

    public static void init() {
        Configs.cm.parseConfigClass(Configs.class);
        ConfigMigration.setup();
        SharedConstants.getConfigHandler().setPostDeserializeCallback(Configs::postDeserialize);

        // Common callbacks
        IValueChangeCallback<ConfigBoolean> redrawConfigGui = newValue -> ConfigGui.getCurrentInstance()
                .ifPresent(ConfigGui::reDraw);

        // Hotkey callbacks
        MagicConfigManager.setHotkeyCallback(Configs.openConfigGui, ConfigGui::openGui, true);
        MagicConfigManager.setHotkeyCallback(Configs.getTargetBlockPosition, TargetBlockPositionPrinter::print, true);
        MagicConfigManager.setHotkeyCallback(Configs.memoryCleaner, MemoryCleaner::clean, true);
        MagicConfigManager.setHotkeyCallback(Configs.syncBlocks, BlockRefresher::refresh, true);
        MagicConfigManager.setHotkeyCallback(Configs.syncInventory, InventoryRefresher::refresh, true);

        // Value listeners
        Configs.customWindowIcon.setValueChangeCallback(bl -> CustomIconHelper.updateIcon());
        Configs.customWindowTitle.setValueChangeCallback(bl -> CustomWindowTitleHandler.getInstance().checkThread());
        Configs.customWindowTitleActivitySupport.setValueChangeCallback(list -> CustomWindowTitleHandler.getInstance().onConfigUpdate());
        Configs.customWindowTitleList.setValueChangeCallback(list -> CustomWindowTitleHandler.getInstance().onConfigUpdate());
        Configs.customWindowTitleRandomly.setValueChangeCallback(bl -> CustomWindowTitleHandler.getInstance().onConfigUpdate());
        Configs.customWindowTitleWithActivityList.setValueChangeCallback(list -> CustomWindowTitleHandler.getInstance().onConfigUpdate());
        Configs.disableToastRender.setValueChangeCallback(bl -> Minecraft.getInstance().getToasts().clear());

        // Debugs
        Configs.debugMode.setValueChangeCallback(redrawConfigGui);
        Configs.resetConfigStatistic.setCallBack((keyAction, iKeybind) -> {
            SharedConstants.getConfigManager().getAllContainers().forEach(configContainer -> configContainer.getStatistic().reset());
            return true;
        });

        // Event listeners
        MagicLib.getInstance().getEventManager().register(LocalPlayerListener.class, AutoDropHandler.getInstance());
        MagicLib.getInstance().getEventManager().register(LocalPlayerListener.class, AutoTotemHandler.getInstance());
        MagicLib.getInstance().getEventManager().register(MinecraftListener.class, CustomWindowTitleHandler.getInstance());
        MagicLib.getInstance().getEventManager().register(RenderLevelListener.class, CustomBlockHitBoxRenderer.getInstance());
        //#if MC > 11502
        MagicLib.getInstance().getEventManager().register(RenderLevelListener.class, OpenWaterHelperRenderer.getInstance());
        //#endif
        MagicLib.getInstance().getEventManager().register(RenderLevelListener.class, RestrictionBoxRenderer.getInstance());
    }

    public static class ConfigCategory {
        public static final String DEBUG = "debug";
        public static final String DISABLE = "disable";
        public static final String FEATURE = "feature";
        public static final String GENERIC = "generic";
        public static final String PATCH = "patch";
    }
}
