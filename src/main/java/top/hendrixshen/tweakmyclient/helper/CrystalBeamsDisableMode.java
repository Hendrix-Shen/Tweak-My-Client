package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum CrystalBeamsDisableMode implements ConfigOptionListEntryApi {
    ALL("all"),
    FIXED("fixed"),
    TRACKING("tracking");

    private final String name;

    CrystalBeamsDisableMode(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return StringUtil.tr(String.format("label.crystalBeamsDisableMode.%s", this.name));
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        return ConfigOptionListEntryHelper.cycle(forward, this.ordinal(), CrystalBeamsDisableMode.values());
    }

    @Override
    public IConfigOptionListEntry fromString(String value) {
        return ConfigOptionListEntryHelper.fromString(value, CrystalBeamsDisableMode.ALL, CrystalBeamsDisableMode.values());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
