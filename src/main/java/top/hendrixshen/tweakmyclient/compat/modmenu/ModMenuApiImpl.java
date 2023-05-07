package top.hendrixshen.tweakmyclient.compat.modmenu;

import top.hendrixshen.magiclib.compat.modmenu.ModMenuCompatApi;
import top.hendrixshen.tweakmyclient.TweakMyClientConfigGui;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

public class ModMenuApiImpl implements ModMenuCompatApi {
    @Override
    public ConfigScreenFactoryCompat<?> getConfigScreenFactoryCompat() {
        return (screen) -> {
            TweakMyClientConfigGui gui = TweakMyClientConfigGui.getInstance();
            //#if MC > 11903 && MC < 12000
            gui.setParent(screen);
            //#else
            //$$ gui.setParentGui(screen);
            //#endif
            return gui;
        };
    }

    @Override
    public String getModIdCompat() {
        return TweakMyClientReference.getModIdentifierCurrent();
    }
}