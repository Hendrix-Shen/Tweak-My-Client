package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

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
        return I18n.translate(String.format("%s.label.autoDropListType.%s", TweakMyClientReference.getModId(), this.name));
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
            if (mode.name.equalsIgnoreCase(name)) {
                return mode;
            }
        }
        return AutoDropListType.WHITELIST;
    }
}
