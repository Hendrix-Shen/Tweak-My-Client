package top.hendrixshen.tweakmyclient.compat.modmenu;

import top.hendrixshen.magiclib.compat.modmenu.ModMenuCompatApi;
import top.hendrixshen.tweakmyclient.TweakMyClientConfigGui;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

public class ModMenuApiImpl implements ModMenuCompatApi {
    @Override
    public ConfigScreenFactoryCompat<?> getConfigScreenFactoryCompat() {
        return (screen) -> {
            TweakMyClientConfigGui gui = TweakMyClientConfigGui.getInstance();
            gui.setParentGui(screen);
            return gui;
        };
    }

    @Override
    public String getModIdCompat() {
        return TweakMyClientReference.getCurrentModId();
    }
}