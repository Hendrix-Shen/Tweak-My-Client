package top.hendrixshen.tweakmyclient.util;

import org.jetbrains.annotations.NotNull;

public class VersionParser {
    public static @NotNull String getVersionType(@NotNull String version) {
        if (version.endsWith("stable")) {
            return "Public Release";
        } else if (version.endsWith("beta")) {
            return "Public Beta";
        } else if (version.endsWith("dev")) {
            return "Development";
        }
        return "Unknown";
    }
}
