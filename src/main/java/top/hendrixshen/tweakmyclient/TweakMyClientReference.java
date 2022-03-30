package top.hendrixshen.tweakmyclient;

import net.fabricmc.loader.api.FabricLoader;
import top.hendrixshen.magiclib.util.FabricUtil;
import top.hendrixshen.tweakmyclient.util.VersionParser;

public class TweakMyClientReference {
    private static final String MOD_ID = "tweakmyclient";
    private static final String MOD_NAME = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getName();
    private static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    private static final String MOD_VERSION_TYPE = VersionParser.getVersionType(MOD_VERSION);

    private static final int CONFIG_VERSION = 1;

    public static boolean isAuthMeLoaded = FabricUtil.isModLoaded("authme");
    public static boolean isInGameAccountSwitcherLoaded = FabricUtil.isModLoaded("ias");
    public static boolean isReAuthLoaded = FabricUtil.isModLoaded("reauth");

    public static String getModId() {
        return MOD_ID;
    }

    public static String getModName() {
        return MOD_NAME;
    }

    public static String getModVersion() {
        return MOD_VERSION;
    }

    public static String getModVersionType() {
        return MOD_VERSION_TYPE;
    }

    public static int getConfigVersion() {
        return CONFIG_VERSION;
    }
}
