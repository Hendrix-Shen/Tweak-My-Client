package top.hendrixshen.tweakmyclient.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.ConfigCategory;
import top.hendrixshen.tweakmyclient.config.ConfigGui;

import static top.hendrixshen.tweakmyclient.TweakMyClient.cm;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return (screen) -> {
            ConfigGui gui = new ConfigGui(TweakMyClientReference.getModId(), ConfigCategory.GENERIC, cm);
            gui.setParent(screen);
            return gui;
        };
    }
}
