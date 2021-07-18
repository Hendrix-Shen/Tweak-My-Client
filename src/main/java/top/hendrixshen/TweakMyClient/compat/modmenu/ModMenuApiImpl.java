package top.hendrixshen.TweakMyClient.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import top.hendrixshen.TweakMyClient.gui.GuiConfigs;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return (screen) -> {
            GuiConfigs gui = new GuiConfigs();
            gui.setParent(screen);
            return gui;
        };
    }
}
