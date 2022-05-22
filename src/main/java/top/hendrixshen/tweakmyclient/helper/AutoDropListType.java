package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import top.hendrixshen.tweakmyclient.util.StringUtil;

public enum AutoDropListType implements IConfigOptionListEntry {
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
        for (AutoDropListType mode : AutoDropListType.values()) {
            if (mode.name.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        return AutoDropListType.WHITELIST;
    }
}
