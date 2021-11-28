package top.hendrixshen.TweakMyClient;

import net.fabricmc.loader.api.FabricLoader;

public class TweakMyClientReference {
    private static final String MOD_ID = "tweakmyclient";
    private static final String MOD_NAME = "TweakMyClient";
    private static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    private static final String MOD_VERSION_TYPE = "Development";

    public static boolean isAuthMeLoaded = FabricLoader.getInstance().isModLoaded("authme");
    public static boolean isInGameAccountSwitcherLoaded = FabricLoader.getInstance().isModLoaded("ias");
    public static boolean isLitematicaLoaded = FabricLoader.getInstance().isModLoaded("litematica");
    public static boolean isMasaGadgetLoaded = FabricLoader.getInstance().isModLoaded("masa_gadget_mod");
    public static boolean isReAuthLoaded = FabricLoader.getInstance().isModLoaded("reauth");

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
}
