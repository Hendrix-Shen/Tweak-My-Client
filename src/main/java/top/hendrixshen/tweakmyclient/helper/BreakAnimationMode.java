package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum BreakAnimationMode implements ConfigOptionListEntryApi {
    DOWN("down"),
    NONE("none"),
    SHRINK("shrink");

    private final String name;

    BreakAnimationMode(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return StringUtil.tr(String.format("label.breakAnimationMode.%s", this.name));
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        return ConfigOptionListEntryHelper.cycle(forward, this.ordinal(), BreakAnimationMode.values());
    }

    @Override
    public IConfigOptionListEntry fromString(String value) {
        return ConfigOptionListEntryHelper.fromString(value, BreakAnimationMode.NONE, BreakAnimationMode.values());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
