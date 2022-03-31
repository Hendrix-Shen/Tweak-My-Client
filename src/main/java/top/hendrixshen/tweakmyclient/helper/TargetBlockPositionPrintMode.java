package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum TargetBlockPositionPrintMode implements IConfigOptionListEntry {
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
        return StringUtils.translate(String.format("tweakmyclient.label.targetBlockPositionPrintMode.%s", this.name));
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
        for (TargetBlockPositionPrintMode mode : TargetBlockPositionPrintMode.values()) {
            if (mode.name.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        return TargetBlockPositionPrintMode.PRIVATE;
    }
}
