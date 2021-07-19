package top.hendrixshen.TweakMyClient.config;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBoolean;
import top.hendrixshen.TweakMyClient.util.InfoUtils;

public class CallBacks {
    public static class KeyCallbackToggleBooleanConfigWithMessage extends KeyCallbackToggleBoolean {
        private final String translatedName;
        public KeyCallbackToggleBooleanConfigWithMessage(IConfigBoolean config, String translatedName) {
            super(config);
            this.translatedName = translatedName;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key)
        {
            super.onKeyAction(action, key);
            InfoUtils.printBooleanConfigToggleMessage(translatedName, this.config.getBooleanValue());
            return true;
        }

    }
}
