package top.hendrixshen.TweakMyClient.config;

import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class TranslatableConfigBooleanHotkeyed extends top.hendrixshen.magiclib.untils.malilib.TranslatableConfigBooleanHotkeyed {
    public TranslatableConfigBooleanHotkeyed(String prefix, String name, boolean defaultValue, String defaultHotkey) {
        super(prefix, name, defaultValue, defaultHotkey);
        this.keybind.setCallback(new CallBacks.KeyCallbackToggleBooleanConfigWithMessage(this, this.getConfigGuiDisplayName()));
    }

    public TranslatableConfigBooleanHotkeyed(String prefix, String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings) {
        super(prefix, name, defaultValue, defaultHotkey, settings);
        this.keybind.setCallback(new CallBacks.KeyCallbackToggleBooleanConfigWithMessage(this, this.getConfigGuiDisplayName()));
    }
}