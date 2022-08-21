package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum EnderPortalRenderMode implements ConfigOptionListEntryApi {
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
        return StringUtil.tr(String.format("label.enderPortalRenderMode.%s", this.name));
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        return ConfigOptionListEntryHelper.cycle(forward, this.ordinal(), EnderPortalRenderMode.values());
    }

    @Override
    public IConfigOptionListEntry fromString(String value) {
        return ConfigOptionListEntryHelper.fromString(value, EnderPortalRenderMode.LEGACY, EnderPortalRenderMode.values());
    }

    @Override
    public String getName() {
        return this.name;
    }
}