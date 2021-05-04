package top.hendrixshen.TweakMyClient.event;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import top.hendrixshen.TweakMyClient.config.Configs;

public class InputHandler implements IKeybindProvider {
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (ConfigHotkey configHotkey : Configs.Generic.HOTKEYS) manager.addKeybindToMap(configHotkey.getKeybind());
        for (IHotkey configHotkey : Configs.Feature.OPTIONS) manager.addKeybindToMap(configHotkey.getKeybind());
        for (IHotkey configHotkey : Configs.Disable.OPTIONS) manager.addKeybindToMap(configHotkey.getKeybind());
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
    }
}