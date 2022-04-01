package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum EnderPortalRenderMode implements IConfigOptionListEntry {
    ACTUAL("actual"),
    FULL("full"),
    LEGACY("legacy"),
    MODERN("modern");

    private final String name;

    EnderPortalRenderMode(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate(String.format("tweakmyclient.label.enderPortalRenderMode.%s", this.name));
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
        for (EnderPortalRenderMode mode : EnderPortalRenderMode.values()) {
            if (mode.name.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        return EnderPortalRenderMode.LEGACY;
    }
}