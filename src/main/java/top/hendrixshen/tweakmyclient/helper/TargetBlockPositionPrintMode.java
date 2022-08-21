package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum TargetBlockPositionPrintMode implements ConfigOptionListEntryApi {
    PUBLIC("public"),
    PRIVATE("private");

    private final String name;

    TargetBlockPositionPrintMode(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return StringUtil.tr(String.format("label.targetBlockPositionPrintMode.%s", this.name));
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        return ConfigOptionListEntryHelper.cycle(forward, this.ordinal(), TargetBlockPositionPrintMode.values());
    }

    @Override
    public IConfigOptionListEntry fromString(String value) {
        return ConfigOptionListEntryHelper.fromString(value, TargetBlockPositionPrintMode.PRIVATE, TargetBlockPositionPrintMode.values());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
