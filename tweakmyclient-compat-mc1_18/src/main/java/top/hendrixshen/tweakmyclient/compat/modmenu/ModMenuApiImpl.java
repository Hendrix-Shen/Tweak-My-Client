package top.hendrixshen.tweakmyclient.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import top.hendrixshen.tweakmyclient.TweakMyClientConfigGui;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            TweakMyClientConfigGui gui = TweakMyClientConfigGui.getInstance();
            gui.setParent(screen);
            return gui;
        };
    }
}
