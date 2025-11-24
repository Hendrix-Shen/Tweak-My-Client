//#if FABRIC_LIKE

package top.hendrixshen.tweakmyclient.impl.compat.modmenu;

import top.hendrixshen.magiclib.api.compat.modmenu.ModMenuApiCompat;
import top.hendrixshen.tweakmyclient.SharedConstants;
import top.hendrixshen.tweakmyclient.game.ConfigGui;

public class ModMenuImpl implements ModMenuApiCompat {
    @Override
    public ConfigScreenFactoryCompat<?> getConfigScreenFactoryCompat() {
        return (screen) -> {
            ConfigGui configGui = new ConfigGui();
            //#if MC > 11903
            //$$ configGui.setParent(screen);
            //#else
            configGui.setParentGui(screen);
            //#endif
            return configGui;
        };
    }

    @Override
    public String getModIdCompat() {
        return SharedConstants.getModIdentifier();
    }
}
//#endif
