package top.hendrixshen.tweakmyclient.helper;

import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum AutoDropListType implements ConfigOptionListEntryApi {
    BLACKLIST("blackList"),
    WHITELIST("whiteList");

    private final String name;

    AutoDropListType(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return StringUtil.tr(String.format("label.autoDropListType.%s", this.name));
    }

    @Override
    public ConfigOptionListEntryApi cycle(boolean forward) {
        return ConfigOptionListEntryHelper.cycle(forward, this.ordinal(), AutoDropListType.values());
    }

    @Override
    public ConfigOptionListEntryApi fromString(String value) {
        return ConfigOptionListEntryHelper.fromString(value, AutoDropListType.WHITELIST, AutoDropListType.values());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
