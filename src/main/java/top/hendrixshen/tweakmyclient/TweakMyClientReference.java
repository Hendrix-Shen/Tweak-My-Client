package top.hendrixshen.tweakmyclient;

import lombok.Getter;
import net.fabricmc.loader.api.FabricLoader;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.util.FabricUtil;
import top.hendrixshen.tweakmyclient.config.ConfigHandler;
import top.hendrixshen.tweakmyclient.util.VersionParser;

public class TweakMyClientReference {
    @Getter
    private static final String modIdentifier = "${mod_id}";
    @Getter
    private static final String currentModIdentifier = "${mod_id}-${minecraft_version_id}";
    @Getter
    private static final String modName = FabricLoader.getInstance().getModContainer(currentModIdentifier).orElseThrow(RuntimeException::new).getMetadata().getName();
    @Getter
    private static final String modVersion = FabricLoader.getInstance().getModContainer(currentModIdentifier).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    @Getter
    private static final String modVersionType = VersionParser.getVersionType(modVersion);

    private static final int CONFIG_VERSION = 1;
    @Getter
    private static final ConfigManager configManager = ConfigManager.get(modIdentifier);
    @Getter
    private static final ConfigHandler configHandler = new ConfigHandler(modIdentifier, configManager, CONFIG_VERSION);

    public static boolean isAuthMeLoaded = FabricUtil.isModLoaded("authme");
    public static boolean isHwylaLoaded = FabricUtil.isModLoaded("waila");
    public static boolean isInGameAccountSwitcherLoaded = FabricUtil.isModLoaded("ias");
    public static boolean isJadeLoaded = FabricUtil.isModLoaded("jade");
    public static boolean isOAuthLoaded = FabricUtil.isModLoaded("oauth-fabric");
    public static boolean isReAuthLoaded = FabricUtil.isModLoaded("reauth");
    public static boolean isWthitLoaded = FabricUtil.isModLoaded("wthit");
}
