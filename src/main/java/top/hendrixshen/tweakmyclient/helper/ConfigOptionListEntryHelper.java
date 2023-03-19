package top.hendrixshen.tweakmyclient.helper;

import org.jetbrains.annotations.NotNull;

public class ConfigOptionListEntryHelper {
    public static ConfigOptionListEntryApi cycle(boolean forward, int id, ConfigOptionListEntryApi[] entries) {
        if (forward) {
            if (++id >= entries.length) {
                id = 0;
            }
        } else if (--id < 0) {
            id = entries.length - 1;
        }

        return entries[id % entries.length];
    }

    public static ConfigOptionListEntryApi fromString(String value, ConfigOptionListEntryApi fallbackBValue, ConfigOptionListEntryApi @NotNull [] entries) {
        for (ConfigOptionListEntryApi mode : entries) {
            if (mode.getName().equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return fallbackBValue;
    }
}
