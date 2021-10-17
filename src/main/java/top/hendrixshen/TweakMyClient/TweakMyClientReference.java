package top.hendrixshen.TweakMyClient;

import net.fabricmc.loader.api.FabricLoader;

public class TweakMyClientReference {
    private static final String MOD_ID = "tweakmyclient";
    private static final String MOD_NAME = "TweakMyClient";
    private static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    private static final String MOD_VERSION_TYPE = "Version Exception";

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
