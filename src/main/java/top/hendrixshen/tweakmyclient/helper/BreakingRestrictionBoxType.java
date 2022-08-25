package top.hendrixshen.tweakmyclient.helper;

import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum BreakingRestrictionBoxType implements ConfigOptionListEntryApi {
    BLACKLIST("blackList"),
    WHITELIST("whiteList");

    private final String name;

    BreakingRestrictionBoxType(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return StringUtil.tr(String.format("label.misc.%s", this.name));
    }

    @Override
    public ConfigOptionListEntryApi cycle(boolean forward) {
        return ConfigOptionListEntryHelper.cycle(forward, this.ordinal(), BreakingRestrictionBoxType.values());
    }

    @Override
    public ConfigOptionListEntryApi fromString(String value) {
        return ConfigOptionListEntryHelper.fromString(value, BreakingRestrictionBoxType.WHITELIST, BreakingRestrictionBoxType.values());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
