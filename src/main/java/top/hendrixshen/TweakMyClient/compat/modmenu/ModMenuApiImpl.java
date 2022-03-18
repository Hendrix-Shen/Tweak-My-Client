package top.hendrixshen.tweakmyclient.compat.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import top.hendrixshen.tweakmyclient.config.ConfigGui;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            ConfigGui gui = ConfigGui.getInstance();
            gui.setParent(screen);
            return gui;
        };
    }
}
